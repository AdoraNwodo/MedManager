package com.developer.nennenwodo.medmanager.medication;


import android.content.Context;
import android.database.Cursor;

import com.developer.nennenwodo.medmanager.model.preferences.SharedPrefHelper;
import com.developer.nennenwodo.medmanager.model.sqlite.MedicationDBContract;
import com.developer.nennenwodo.medmanager.model.sqlite.MedicationDBHelper;
import com.developer.nennenwodo.medmanager.model.Medication;

/**
 * Responsible for handling actions from the view and updating the UI as required
 */
public class TodaysMedicationPresenter implements TodaysMedicationContract.Presenter{

    private TodaysMedicationContract.View mView;
    private Context mContext;

    public TodaysMedicationPresenter(TodaysMedicationContract.View view, Context context){
        mView = view;
        mContext = context;
    }

    /**
     * Gets a list of medications to be displayed on the recyclerview
     */
    @Override
    public void prepareMedications() {

        mView.clearMedicationsList();   //clear list to avoid duplicates

        //get id of authenticated ic_default_profile_image from session using shared preferences
        SharedPrefHelper mSharedPrefHelper = new SharedPrefHelper(mContext);
        String userID = mSharedPrefHelper.getUserID();

        //get all users medications and add to list that would be displayed in recycler view
        MedicationDBHelper medicationDBHelper = new MedicationDBHelper(mContext);
        Cursor cursor = medicationDBHelper.getTodaysMedication(userID);


        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(MedicationDBContract.MedicationEntry._ID));
            String name = cursor.getString(cursor.getColumnIndex(MedicationDBContract.MedicationEntry.NAME));
            String description = cursor.getString(cursor.getColumnIndex(MedicationDBContract.MedicationEntry.DESCRIPTION));
            int frequencyOrInterval = cursor.getInt(cursor.getColumnIndex(MedicationDBContract.MedicationEntry.INTERVAL));
            String startDate = cursor.getString(cursor.getColumnIndex(MedicationDBContract.MedicationEntry.START_DATE));
            String startTime = cursor.getString(cursor.getColumnIndex(MedicationDBContract.MedicationEntry.START_TIME));
            String endDate = cursor.getString(cursor.getColumnIndex(MedicationDBContract.MedicationEntry.END_DATE));

            mView.addToMedicationsList(new Medication(id, userID, name, description, frequencyOrInterval, startDate,startTime, endDate));

        }

        cursor.close();

        //close the connection
        medicationDBHelper.close();

        mView.notifyAdapter();

    }
}
