package com.developer.nennenwodo.medmanager.medication;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;

import com.developer.nennenwodo.medmanager.AlarmReceiver;
import com.developer.nennenwodo.medmanager.adapter.MedicationListAdapter;
import com.developer.nennenwodo.medmanager.model.preferences.SharedPrefHelper;
import com.developer.nennenwodo.medmanager.model.sqlite.MedicationDBContract;
import com.developer.nennenwodo.medmanager.model.sqlite.MedicationDBHelper;
import com.developer.nennenwodo.medmanager.model.Medication;


/**
 * Responsible for handling actions from the view and updating the UI as required
 */
public class MedicationListPresenter implements MedicationListContract.Presenter{

    private MedicationListContract.View mView;
    private Context mContext;

    public MedicationListPresenter(MedicationListContract.View view, Context context){
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
        Cursor cursor = medicationDBHelper.readMedications(userID);

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

        //close the db connection
        medicationDBHelper.close();

        mView.notifyAdapter();

    }

    /**
     * Handles swipe to delete
     * @param viewHolder
     * @param direction
     * @param position
     */
    @Override
    public void handleSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder instanceof MedicationListAdapter.MyViewHolder) {

            //get the medication
            Medication medication = mView.getMedication(viewHolder.getAdapterPosition());

            //make delete call to sqlite database and parse medication id
            MedicationDBHelper medicationDBHelper = new MedicationDBHelper(mContext);
            medicationDBHelper.deleteMedication(medication.getId());

            //close the connection
            medicationDBHelper.close();

            // remove the item from recycler view
            mView.removeAdapterItem(viewHolder.getAdapterPosition());
            cancelAlarm(medication.getId());

            mView.serveViews();

        }

    }

    /**
     * Helper method to cancel an alarm after deleting the medication it belongs to
     * @param id
     */
    private void cancelAlarm(int id){
        //Intent intent = new Intent(mContext, SingleMedicationActivity.class);
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        intent.putExtra("MEDICATION_ID", id);
        intent.setAction(""+System.currentTimeMillis());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
