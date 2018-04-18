package com.developer.nennenwodo.medmanager.medication;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.developer.nennenwodo.medmanager.AlarmReceiver;
import com.developer.nennenwodo.medmanager.model.preferences.SharedPrefContract;
import com.developer.nennenwodo.medmanager.model.preferences.SharedPrefHelper;
import com.developer.nennenwodo.medmanager.utils.Utility;
import com.developer.nennenwodo.medmanager.model.Medication;
import com.developer.nennenwodo.medmanager.model.sqlite.MedicationDBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Responsible for handling actions from the view and updating the UI as required
 */
public class NewMedicationPresenter implements NewMedicationContract.Presenter{

    private NewMedicationContract.View mView;
    private Context mContext;

    public NewMedicationPresenter(NewMedicationContract.View view, Context context){
        mView = view;
        mContext = context;
    }


    /**
     * Performs actions when the add medication button has been clicked
     * @param context
     * @param medicationName
     * @param medicationDescription
     * @param medicationStartDate
     * @param medicationStartTime
     * @param medicationEndDate
     * @param medicationFrequency
     */
    @Override
    public void addMedicationClick(Context context, String medicationName, String medicationDescription,
                                   String medicationStartDate, String medicationStartTime,
                                   String medicationEndDate, String medicationFrequency) {

        //make sure no field is empty
        if(medicationName.isEmpty() || medicationDescription.isEmpty() ||
                medicationStartDate.isEmpty() || medicationStartTime.isEmpty() ||
                medicationEndDate.isEmpty() || medicationFrequency.isEmpty()
                ){
            mView.displayEmptyInputFieldMessage();
            return;
        }

        //if user enters an earlier date for medication end date, show toast error message
        if(! Utility.isBefore(medicationStartDate, medicationEndDate)){
            mView.displayInvalidDateMessage();
            return;
        }

        int medicationFrequencyOrInterval = 24; //default is set to every 24 hours (once a day).

        try{
            medicationFrequencyOrInterval = Integer.parseInt(medicationFrequency);

        }catch (Exception ex){
            mView.displayInvalidFrequencyErrorMessage();
        }

        //get user in current session using shared preferences
        SharedPrefHelper mSharedPrefHelper = new SharedPrefHelper(mContext);
        String userID = mSharedPrefHelper.getUserID();

        //create new medication instance
        Medication medication = new Medication(userID, medicationName, medicationDescription, medicationFrequencyOrInterval, medicationStartDate, medicationStartTime, medicationEndDate );

        //call to db to add medication
        MedicationDBHelper medicationDBHelper = new MedicationDBHelper(context);
        long rowId = medicationDBHelper.addMedication(medication);

        //close the connection
        medicationDBHelper.close();

        Calendar calStartDate = Calendar.getInstance();
        calStartDate.set(Calendar.YEAR, Utility.getYear(medicationStartDate));
        calStartDate.set(Calendar.MONTH, Utility.getMonth(medicationStartDate));
        calStartDate.set(Calendar.DAY_OF_MONTH, Utility.getDay(medicationStartDate));
        calStartDate.set(Calendar.HOUR_OF_DAY, Utility.getHour(medicationStartTime));
        calStartDate.set(Calendar.MINUTE, Utility.getMinute(medicationStartTime));

        Calendar calEndDate = Calendar.getInstance();
        calEndDate.set(Calendar.YEAR, Utility.getYear(medicationEndDate));
        calEndDate.set(Calendar.MONTH, Utility.getMonth(medicationEndDate));
        calEndDate.set(Calendar.DAY_OF_MONTH, Utility.getDay(medicationEndDate));
        calEndDate.set(Calendar.HOUR_OF_DAY, Utility.getHour(medicationStartTime));
        calEndDate.set(Calendar.MINUTE, Utility.getMinute(medicationStartTime));

        int daysBetweenStartAndFinish = (int) Utility.daysBetween(medicationStartDate, medicationEndDate);

        int numberOfIterations = medicationFrequencyOrInterval * daysBetweenStartAndFinish;

        Date now = new Date();

        for(int i = 0; i < numberOfIterations; i++){

            Date dateTime = calStartDate.getTime();
            if(dateTime.before(now)){
                calStartDate.add(Calendar.HOUR, 24/medicationFrequencyOrInterval);
            }else{
                int year = calStartDate.get(Calendar.YEAR);
                int month = calStartDate.get(Calendar.MONTH) + 1;
                int day = calStartDate.get(Calendar.DAY_OF_MONTH);

                String formattedStartDate = Utility.formatDate(day,month,year);

                //add alarm
                //setAlarm(formattedStartDate, medicationEndDate, medicationStartTime, mContext, rowId, medicationFrequencyOrInterval);
                setAlarm(calStartDate, mContext, rowId, medicationFrequencyOrInterval);

                break;
            }
        }

        mView.resetFields();

        mView.displayMedicationInsertSuccessMessage();

        mView.toPreviousActivity();

    }

    /**
     * sets an alarm for the users new medication
     */
    private void setAlarm(Calendar start, Context ctx, long id, int frequency){

        /*Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, Utility.getYear(startDate));
        calendar.set(Calendar.MONTH, Utility.getMonth(startDate));
        calendar.set(Calendar.DAY_OF_MONTH, Utility.getDay(startDate));
        calendar.set(Calendar.HOUR_OF_DAY, Utility.getHour(startTime));
        calendar.set(Calendar.MINUTE, Utility.getMinute(startTime));*/

        Intent intent = new Intent(ctx,AlarmReceiver.class);
        intent.putExtra("MEDICATION_ID", (int)id);
        intent.setAction(""+System.currentTimeMillis());

        PendingIntent mPendingIntent = PendingIntent.getBroadcast(ctx, (int) id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, start.getTimeInMillis(), (AlarmManager.INTERVAL_DAY / frequency), mPendingIntent);

    }

    /**
     * Shows timepicker dialog and displays chosen time
     */
    public void medicationStartTimeClick(){

        Calendar mCalendar = Calendar.getInstance();

        TimePickerDialog startTimePickerDialog = new TimePickerDialog(mContext,
                new TimePickerDialog.OnTimeSetListener(){

                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                        String startTime = Utility.formatTime(hourOfDay, minute);//hourOfDay + ":" + minute;

                        mView.showStartTime(startTime);
                        //mEditTextStartTime.setText(startTime);
                    }
                },
                mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE),
                true);
        startTimePickerDialog.show();

    }

    /**
     *Shows datepicker dialog and displays chosen date
     */
    public void medicationStartDayClick(){

        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog startDatePickerDialog = new DatePickerDialog(mContext,
                android.R.style.Theme_Holo_Dialog,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;

                        String startDate = Utility.formatDate(day,month,year);

                        mView.showStartDay(startDate);

                        //mEditTextStartDate.setText(startDate);
                    }
                }, year, month, day);

        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        Date d = null;
        try {
            d = f.parse(day+"/"+month+"/"+year);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(d != null) {
            startDatePickerDialog.getDatePicker().setMinDate(d.getTime());
        }

        startDatePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        startDatePickerDialog.show();

    }

    /**
     *Shows datepicker dialog and displays chosen date
     */
    public void medicationEndDayClick(){

        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH) + 1;
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog endDatePickerDialog = new DatePickerDialog(mContext,
                android.R.style.Theme_Holo_Dialog,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;

                        String endDate = Utility.formatDate(day,month,year);

                        //mEditTextEndDate.setText(endDate);
                        mView.showEndDay(endDate);
                    }
                },
                year, month, day);


        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        Date d = null;
        try {
            d = f.parse(day+"/"+month+"/"+year);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(d != null) {
            endDatePickerDialog.getDatePicker().setMinDate(d.getTime());
        }
        endDatePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        endDatePickerDialog.show();

    }
}
