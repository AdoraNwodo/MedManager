package com.developer.nennenwodo.medmanager.model.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.developer.nennenwodo.medmanager.model.Medication;
import com.developer.nennenwodo.medmanager.model.User;


/**
 * Helper Class for Database. Defines all database functions for Med manager
 */

public class MedicationDBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;

    private static final String DATABASE_NAME = "medmanager.db";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_MEDICATION_INFORMATION_TABLE = "CREATE TABLE " +
            MedicationDBContract.MedicationEntry.TABLE_NAME + " (" +
            MedicationDBContract.MedicationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            MedicationDBContract.MedicationEntry.USER_ID + " TEXT NOT NULL, " +
            MedicationDBContract.MedicationEntry.NAME + " TEXT NOT NULL, " +
            MedicationDBContract.MedicationEntry.DESCRIPTION + " TEXT NOT NULL, " +
            MedicationDBContract.MedicationEntry.INTERVAL + " INTEGER NOT NULL, " +
            MedicationDBContract.MedicationEntry.START_DATE + " TEXT NOT NULL, " +
            MedicationDBContract.MedicationEntry.START_TIME + " TEXT NOT NULL, " +
            MedicationDBContract.MedicationEntry.END_DATE + " TEXT NOT NULL " +
            ");";

    private static final String SQL_CREATE_USER_INFORMATION_TABLE = "CREATE TABLE " +
            MedicationDBContract.UserEntry.TABLE_NAME + " (" +
            MedicationDBContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            MedicationDBContract.UserEntry.USER_ID + " TEXT NOT NULL, " +
            MedicationDBContract.UserEntry.USER_NAME + " TEXT, " +
            MedicationDBContract.UserEntry.BIRTHDAY + " TEXT, " +
            MedicationDBContract.UserEntry.NOTIFICATION_STATUS + " TEXT, " +
            MedicationDBContract.UserEntry.GENDER + " TEXT );";

    private static final String SQL_DROP_MEDICATION_INFORMATION_TABLE = "DROP TABLE IF EXISTS "+ MedicationDBContract.MedicationEntry.TABLE_NAME;

    private static final String SQL_DROP_USER_INFORMATION_TABLE = "DROP TABLE IF EXISTS "+ MedicationDBContract.UserEntry.TABLE_NAME;


    public MedicationDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(SQL_CREATE_MEDICATION_INFORMATION_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_USER_INFORMATION_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        //delete tables and ic_add them back on database upgrade
        sqLiteDatabase.execSQL(SQL_DROP_MEDICATION_INFORMATION_TABLE);
        sqLiteDatabase.execSQL(SQL_DROP_USER_INFORMATION_TABLE);
        onCreate(sqLiteDatabase);

    }

    /**
     * Adds a new medication to the SQLite Database and returns its id
     * @param medication
     * @return
     */
    public long addMedication(Medication medication){

        database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MedicationDBContract.MedicationEntry.NAME, medication.getName());
        contentValues.put(MedicationDBContract.MedicationEntry.USER_ID, medication.getUserID());
        contentValues.put(MedicationDBContract.MedicationEntry.DESCRIPTION, medication.getDescription());
        contentValues.put(MedicationDBContract.MedicationEntry.INTERVAL, medication.getInterval());
        contentValues.put(MedicationDBContract.MedicationEntry.START_DATE, medication.getStartDate());
        contentValues.put(MedicationDBContract.MedicationEntry.START_TIME, medication.getStartTime());
        contentValues.put(MedicationDBContract.MedicationEntry.END_DATE, medication.getEndDate());

        long rowID = database.insert(MedicationDBContract.MedicationEntry.TABLE_NAME, null, contentValues);

        return rowID;
    }

    /**
     * Checks if a ic_default_profile_image with a given id has logged in before. If so, their details are returned.
     * If not, a new ic_default_profile_image instance is created and logged to the database
     * @param user
     * @return
     */
    public User addOrReturnUser(User user){

        database = this.getWritableDatabase();

        //checks if ic_default_profile_image exists in database before adding
        String[] projections = { MedicationDBContract.UserEntry.USER_ID ,
                MedicationDBContract.UserEntry.USER_NAME,
                MedicationDBContract.UserEntry.GENDER,
                MedicationDBContract.UserEntry.BIRTHDAY,
                MedicationDBContract.UserEntry.NOTIFICATION_STATUS
        };

        Cursor cursor = database.query(MedicationDBContract.UserEntry.TABLE_NAME, projections
                , MedicationDBContract.UserEntry.USER_ID +" = '" + user.getUserID() + "'"
                ,null,null,null, null);

        if(cursor.moveToFirst()) {
            //ic_default_profile_image exists
            User existingUser =  new User(cursor.getString(cursor.getColumnIndex(MedicationDBContract.UserEntry.USER_ID)),
                    cursor.getString(cursor.getColumnIndex(MedicationDBContract.UserEntry.USER_NAME)),
                    cursor.getString(cursor.getColumnIndex(MedicationDBContract.UserEntry.GENDER)),
                    cursor.getString(cursor.getColumnIndex(MedicationDBContract.UserEntry.BIRTHDAY)),
                    cursor.getString(cursor.getColumnIndex(MedicationDBContract.UserEntry.NOTIFICATION_STATUS

                    )));

            cursor.close();

            return existingUser;

        }else{
            //ic_default_profile_image does not exist - create ic_default_profile_image record
            ContentValues contentValues = new ContentValues();
            contentValues.put(MedicationDBContract.UserEntry.USER_ID, user.getUserID());
            contentValues.put(MedicationDBContract.UserEntry.USER_NAME, user.getUserName());
            contentValues.put(MedicationDBContract.UserEntry.BIRTHDAY, user.getBirthday());
            contentValues.put(MedicationDBContract.UserEntry.GENDER, user.getGender());
            contentValues.put(MedicationDBContract.UserEntry.NOTIFICATION_STATUS, user.getNotificationStatus());

            database.insert(MedicationDBContract.UserEntry.TABLE_NAME, null, contentValues);

            return null;

        }

    }

    /**
     * Selects distinct month and years from medications table.
     * These distinct months and years form the monthly categories on the app
     * @param userID
     * @return
     */
    public Cursor readCategories(String userID){

        database = this.getReadableDatabase();

        Cursor cursor  = database.rawQuery(
                "  SELECT DISTINCT " +
                        "strftime('%m', "+ MedicationDBContract.MedicationEntry.START_DATE +") as m, " +
                        "strftime('%Y', "+ MedicationDBContract.MedicationEntry.START_DATE +") as y " +
                        "FROM " + MedicationDBContract.MedicationEntry.TABLE_NAME +
                        " WHERE "+ MedicationDBContract.MedicationEntry.USER_ID+ " = '" + userID + "' " +
                        "ORDER BY y, m", null);

        return cursor;

    }

    /**
     * Updates a given medication record
     * @param id
     * @param medication
     */
    public void updateMedication(int id, Medication medication){

        database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MedicationDBContract.MedicationEntry.NAME, medication.getName());
        contentValues.put(MedicationDBContract.MedicationEntry.DESCRIPTION, medication.getDescription());
        contentValues.put(MedicationDBContract.MedicationEntry.INTERVAL, medication.getInterval());
        contentValues.put(MedicationDBContract.MedicationEntry.START_DATE, medication.getStartDate());
        contentValues.put(MedicationDBContract.MedicationEntry.START_TIME, medication.getStartTime());
        contentValues.put(MedicationDBContract.MedicationEntry.END_DATE, medication.getEndDate());

        String condition = MedicationDBContract.MedicationEntry._ID + " = " + id;

        database.update(MedicationDBContract.MedicationEntry.TABLE_NAME, contentValues, condition, null);

    }


    /**
     * Updates a users record in database
     * @param user
     */
    public void updateUser(User user){

        database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MedicationDBContract.UserEntry.USER_NAME, user.getUserName());
        contentValues.put(MedicationDBContract.UserEntry.GENDER, user.getGender());
        contentValues.put(MedicationDBContract.UserEntry.BIRTHDAY, user.getBirthday());

        String condition = MedicationDBContract.UserEntry.USER_ID + " =  ? ";

        database.update(MedicationDBContract.UserEntry.TABLE_NAME, contentValues, condition, new String[]{user.getUserID()});

    }

    /**
     * Updates a users ic_notification status
     * @param userID
     * @param status
     */
    public void updateUserNotification(String userID, String status){

        database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MedicationDBContract.UserEntry.NOTIFICATION_STATUS, status);

        String condition = MedicationDBContract.UserEntry.USER_ID + " =  ? ";

        database.update(MedicationDBContract.UserEntry.TABLE_NAME, contentValues, condition, new String[]{userID});

    }


    /**
     * Fetches the medications belonging to a particular category and ic_default_profile_image
     * @param month
     * @param year
     * @param userID
     * @return
     */
    public Cursor readMedicationsForCategory(String month, String year, String userID){

        database = this.getReadableDatabase();

        Cursor cursor  = database.rawQuery(
                "SELECT * FROM "+ MedicationDBContract.MedicationEntry.TABLE_NAME+" WHERE strftime('%m', "+
                        MedicationDBContract.MedicationEntry.START_DATE + ") = '" + month +
                        "' AND strftime('%Y', "+
                        MedicationDBContract.MedicationEntry.START_DATE + ") = '" + year +"' AND " +
                        MedicationDBContract.MedicationEntry.USER_ID + " = '" + userID + "'"+
                        "ORDER BY "+ MedicationDBContract.MedicationEntry._ID + " desc", null);

        return cursor;

    }

    /**
     * Fetches all medications belonging to a particular ic_default_profile_image
     * @param userID
     * @return
     */
    public Cursor readMedications(String userID){

        database = this.getReadableDatabase();

        String[] projections = { MedicationDBContract.MedicationEntry._ID,
                MedicationDBContract.MedicationEntry.NAME,
                MedicationDBContract.MedicationEntry.DESCRIPTION,
                MedicationDBContract.MedicationEntry.INTERVAL,
                MedicationDBContract.MedicationEntry.START_DATE,
                MedicationDBContract.MedicationEntry.START_TIME,
                MedicationDBContract.MedicationEntry.END_DATE
        };

        Cursor cursor = database.query(MedicationDBContract.MedicationEntry.TABLE_NAME, projections
                , MedicationDBContract.MedicationEntry.USER_ID +" = '" + userID + "'"
                ,null,null,null, MedicationDBContract.MedicationEntry._ID + " desc");

        return cursor;

    }

    /**
     * Fetches all medications belonging to a particular ic_default_profile_image that have not been completed
     * @param userID
     * @return
     */
    public Cursor readOngoingAndFutureMedications(String userID){

        database = this.getReadableDatabase();

        String[] projections = { MedicationDBContract.MedicationEntry._ID,
                MedicationDBContract.MedicationEntry.NAME,
                MedicationDBContract.MedicationEntry.DESCRIPTION,
                MedicationDBContract.MedicationEntry.INTERVAL,
                MedicationDBContract.MedicationEntry.START_DATE,
                MedicationDBContract.MedicationEntry.START_TIME,
                MedicationDBContract.MedicationEntry.END_DATE
        };

        Cursor cursor = database.query(MedicationDBContract.MedicationEntry.TABLE_NAME, projections
                , MedicationDBContract.MedicationEntry.USER_ID +" = '" + userID + "' AND "+
                        MedicationDBContract.MedicationEntry.END_DATE+" >= strftime('%Y-%m-%d','now')"
                ,null,null,null, MedicationDBContract.MedicationEntry._ID + " desc");

        return cursor;

    }

    /**
     * Fetches details of a medication with a particular id from database
     * @param id
     * @return
     */
    public Cursor readSingleMedication(int id){

        database = this.getReadableDatabase();

        String selection = MedicationDBContract.MedicationEntry._ID + " = " + id;

        String[] projections = { MedicationDBContract.MedicationEntry._ID,
                MedicationDBContract.MedicationEntry.NAME,
                MedicationDBContract.MedicationEntry.DESCRIPTION,
                MedicationDBContract.MedicationEntry.INTERVAL,
                MedicationDBContract.MedicationEntry.START_DATE,
                MedicationDBContract.MedicationEntry.START_TIME,
                MedicationDBContract.MedicationEntry.END_DATE
        };

        Cursor cursor = database.query(MedicationDBContract.MedicationEntry.TABLE_NAME, projections,selection,null,
                null,null, MedicationDBContract.MedicationEntry._ID + " desc");

        return cursor;

    }

    /**
     * Fetches all users medications for the day
     * @param userID
     */
    public Cursor getTodaysMedication(String userID){

        database = this.getReadableDatabase();

        Cursor cursor  = database.rawQuery(
                "SELECT * FROM "
                        + MedicationDBContract.MedicationEntry.TABLE_NAME+
                        " WHERE "+MedicationDBContract.MedicationEntry.USER_ID + " =  '"+userID+"'" +
                        " AND  strftime('%Y-%m-%d','now') BETWEEN " + MedicationDBContract.MedicationEntry.START_DATE
                        + " AND " + MedicationDBContract.MedicationEntry.END_DATE +
                        " ORDER BY "+ MedicationDBContract.MedicationEntry._ID + " desc", null);

        return cursor;

    }

    /**
     * Deletes a medication with a particular id from the database
     * @param id
     */
    public void deleteMedication(int id){

        database = this.getWritableDatabase();

        String selection = MedicationDBContract.MedicationEntry._ID + " = " + id;

        database.delete(MedicationDBContract.MedicationEntry.TABLE_NAME, selection, null);

    }

    /**
     * Deletes all medications belonging to a particular ic_default_profile_image
     * @param userId
     */
    public void deleteAllUsersMedications(String userId){

        database = this.getWritableDatabase();

        String selection = MedicationDBContract.MedicationEntry.USER_ID + " =  ?";

        database.delete(MedicationDBContract.MedicationEntry.TABLE_NAME, selection, new String[]{userId});

    }

    /**
     * Deletes a ic_default_profile_image record with a particular id in the database
     * @param userId
     */
    public void deleteUsersAccount(String userId){

        database = this.getWritableDatabase();

        String selection = MedicationDBContract.UserEntry.USER_ID + " = ?";

        database.delete(MedicationDBContract.UserEntry.TABLE_NAME, selection,  new String[]{userId});

    }
}
