package com.developer.nennenwodo.medmanager.profile;


import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.DatePicker;

import com.developer.nennenwodo.medmanager.model.preferences.SharedPrefContract;
import com.developer.nennenwodo.medmanager.model.preferences.SharedPrefHelper;
import com.developer.nennenwodo.medmanager.model.sqlite.MedicationDBHelper;
import com.developer.nennenwodo.medmanager.model.User;
import com.developer.nennenwodo.medmanager.utils.Utility;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Responsible for handling actions from the view and updating the UI as required
 */
public class ProfilePresenter implements ProfileContract.Presenter {

    private ProfileContract.View mView;
    private Context mContext;
    private SharedPrefHelper mSharedPrefHelper;

    public ProfilePresenter(ProfileContract.View view, Context context){
        mView = view;
        mContext = context;
        mSharedPrefHelper = new SharedPrefHelper(context);
    }

    /**
     * Fetch user profile details from shared preferences and display
     */
    @Override
    public void fetchSharedPreferences() {

        if (mSharedPrefHelper.isContainedInSharedPreference(SharedPrefContract.PREF_USER_NAME)) {
            mView.displayUsername(mSharedPrefHelper.getUserName());
        }
        if (mSharedPrefHelper.isContainedInSharedPreference(SharedPrefContract.PREF_BIRTHDAY)) {
            mView.displayBirthday(mSharedPrefHelper.getUserBirthday());

        }
        if (mSharedPrefHelper.isContainedInSharedPreference(SharedPrefContract.PREF_GENDER)) {
            mView.displayGender(mSharedPrefHelper.getUserGender());
        }

    }

    /**
     * Updates authenticated users details both in shared preference(session) and database
     * @param userName
     * @param birthday
     * @param gender
     */
    @Override
    public void updateProfile(String userName, String birthday, String gender) {


        MedicationDBHelper medicationDBHelper = new MedicationDBHelper(mContext);

        String userID = mSharedPrefHelper.getUserID();

        //update DB
        medicationDBHelper.updateUser(
                new User(userID, userName, birthday, gender));

        //close the db connection
        medicationDBHelper.close();


        HashMap<String,String> mHashMap = new HashMap<>();
        mHashMap.put(SharedPrefContract.PREF_USER_NAME, userName);
        mHashMap.put(SharedPrefContract.PREF_BIRTHDAY, birthday);
        mHashMap.put(SharedPrefContract.PREF_GENDER, gender);

        //add to shared preferences
        mSharedPrefHelper.putStrings(mHashMap);

        mView.displayProfileUpdatedMessage();

    }


    /**
     * Launches date picker to choose birth date
     */
    @Override
    public void datePickerDialog() {

        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog birthDatePickerDialog = new DatePickerDialog(mContext,
                android.R.style.Theme_Holo_Dialog,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;

                        String endDate = Utility.formatDate(day,month,year);

                        mView.displayPickedDate(endDate);

                    }
                }, year, month, day);

        birthDatePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        birthDatePickerDialog.show();

    }
}
