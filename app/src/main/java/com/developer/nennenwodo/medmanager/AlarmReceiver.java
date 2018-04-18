package com.developer.nennenwodo.medmanager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.NotificationCompat;

import com.developer.nennenwodo.medmanager.model.preferences.SharedPrefHelper;
import com.developer.nennenwodo.medmanager.model.sqlite.MedicationDBContract;
import com.developer.nennenwodo.medmanager.medication.SingleMedicationActivity;
import com.developer.nennenwodo.medmanager.model.sqlite.MedicationDBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AlarmReceiver extends BroadcastReceiver {

    private static final int MY_NOTIFICATION_ID = 1;
    NotificationManager notificationManager;
    Notification myNotification;

    public AlarmReceiver(){}

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra("MEDICATION_ID", 0);

        //get the specific medication

        MedicationDBHelper medicationDBHelper = new MedicationDBHelper(context);    //initialize db helper

        Cursor cursor = medicationDBHelper.readSingleMedication(id);      //get record

        if(cursor.getCount() <= 0){
            cancelAlarm(id, context);
            return;
        }

        cursor.moveToFirst();

        id = cursor.getInt(cursor.getColumnIndex(MedicationDBContract.MedicationEntry._ID));
        String endDateString = cursor.getString(cursor.getColumnIndex(MedicationDBContract.MedicationEntry.END_DATE));

        cursor.close();

        //close the db connection
        medicationDBHelper.close();

        //check if medication end date has expired
        String today = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date endDate = null, todaysDate = null;

        try {
            endDate = sdf.parse(endDateString);
            todaysDate = sdf.parse(today);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (endDate.compareTo(todaysDate) < 0) {

            //cancel alarm if day has passed
            cancelAlarm(id, context);

        }else {

            //check if user set notify me to true or false. display ic_notification if set to true, else do nothing.

            SharedPrefHelper sharedPrefHelper = new SharedPrefHelper(context);

            if(sharedPrefHelper.isNotificationOn()) {

                //display notification if end day hasn't passed
                Intent myIntent = new Intent(context, SingleMedicationActivity.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                myIntent.putExtra("MEDICATION_ID", id);

                PendingIntent pendingIntent = PendingIntent.getActivity(context, id, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);


                myNotification = new NotificationCompat.Builder(context, "MEDICATION CHANNEL")
                        .setContentTitle("Med Manager")
                        .setContentText("Hello!\n Remember to take your medication.")
                        .setTicker("Notification!")
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pendingIntent)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.ic_calendar)
                        .build();

                notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
            }//else do nothing
        }
    }

    /**
     * Cancels the alarm
     * @param id
     * @param context
     */
    private void cancelAlarm(int id, Context context){
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("MEDICATION_ID", id);
        intent.setAction(""+System.currentTimeMillis());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

}