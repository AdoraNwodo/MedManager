package com.developer.nennenwodo.medmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.developer.nennenwodo.medmanager.model.preferences.SharedPrefHelper;
import com.developer.nennenwodo.medmanager.model.sqlite.MedicationDBContract;
import com.developer.nennenwodo.medmanager.model.sqlite.MedicationDBHelper;
import com.developer.nennenwodo.medmanager.utils.Utility;

import java.util.Calendar;
import java.util.Date;


public class BootReceiver extends BroadcastReceiver {

    /**
     * Add medications to alarm on device reboot
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){

            SharedPrefHelper mSharedPrefHelper = new SharedPrefHelper(context);

            String userID = mSharedPrefHelper.getUserID();

            MedicationDBHelper mDBHelper = new MedicationDBHelper(context);

            Cursor cursor = mDBHelper.readOngoingAndFutureMedications(userID);

            while(cursor.moveToNext()){

                int id = cursor.getInt(cursor.getColumnIndex(MedicationDBContract.MedicationEntry._ID));
                int frequency = cursor.getInt(cursor.getColumnIndex(MedicationDBContract.MedicationEntry.INTERVAL));
                String startDate = cursor.getString(cursor.getColumnIndex(MedicationDBContract.MedicationEntry.START_DATE));
                String startTime = cursor.getString(cursor.getColumnIndex(MedicationDBContract.MedicationEntry.START_TIME));
                String endDate = cursor.getString(cursor.getColumnIndex(MedicationDBContract.MedicationEntry.END_DATE));

                //start calendar date
                Calendar calStartDate = Calendar.getInstance();
                calStartDate.set(Calendar.YEAR, Utility.getYear(startDate));
                calStartDate.set(Calendar.MONTH, Utility.getMonth(startDate));
                calStartDate.set(Calendar.DAY_OF_MONTH, Utility.getDay(startDate));
                calStartDate.set(Calendar.HOUR_OF_DAY, Utility.getHour(startTime));
                calStartDate.set(Calendar.MINUTE, Utility.getMinute(startTime));

                //end calendar date
                Calendar calEndDate = Calendar.getInstance();
                calEndDate.set(Calendar.YEAR, Utility.getYear(endDate));
                calEndDate.set(Calendar.MONTH, Utility.getMonth(endDate));
                calEndDate.set(Calendar.DAY_OF_MONTH, Utility.getDay(endDate));
                calEndDate.set(Calendar.HOUR_OF_DAY, Utility.getHour(startTime));
                calEndDate.set(Calendar.MINUTE, Utility.getMinute(startTime));

                int daysBetweenStartAndFinish = (int) Utility.daysBetween(startDate, endDate);

                int numberOfIterations = frequency * daysBetweenStartAndFinish;

                Date today = new Date();

                for(int i = 0; i < numberOfIterations; i++){

                    Date dateTime = calStartDate.getTime();
                    if(dateTime.before(today)){
                        //add hours to date if we are still encountering past dates
                        calStartDate.add(Calendar.HOUR, 24/frequency);
                    }else{
                        //on seeing future date, set alarm and break out of loop
                        setAlarm(calStartDate, context, id, frequency);

                        break;
                    }
                }

            }

            cursor.close();

            //close the db connection
            mDBHelper.close();

        }

    }

    /**
     * Sets alarm on device reboot
     * @param start
     * @param ctx
     * @param id
     * @param frequency
     */
    private void setAlarm(Calendar start, Context ctx, int id, int frequency){

        Intent intent = new Intent(ctx,AlarmReceiver.class);
        intent.putExtra("MEDICATION_ID", id);
        intent.setAction(""+System.currentTimeMillis());

        PendingIntent mPendingIntent = PendingIntent.getBroadcast(ctx, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, start.getTimeInMillis(), (AlarmManager.INTERVAL_DAY / frequency), mPendingIntent);

    }

}
