package com.developer.nennenwodo.medmanager.auth;

import android.content.Context;


import com.developer.nennenwodo.medmanager.model.preferences.SharedPrefContract;
import com.developer.nennenwodo.medmanager.model.preferences.SharedPrefHelper;
import com.developer.nennenwodo.medmanager.model.sqlite.MedicationDBHelper;
import com.developer.nennenwodo.medmanager.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.HashMap;


/**
 * Authentication Logic
 */
public class SignInPresenter implements SignInContract.Presenter {

    private SignInContract.View mView;
    private SharedPrefHelper mSharedPrefHelper;


    public SignInPresenter(SignInContract.View view, Context context){
        //init constructor
        mView = view;
        mSharedPrefHelper = new SharedPrefHelper(context);
    }

    /**
     * Checks the ic_default_profile_image session (shared preferences) to see if a ic_default_profile_image already logged in
     */
    @Override
    public void checkIfLoggedIn() {

        if(mSharedPrefHelper.isLoggedIn()){
            //redirect to dashboard if logged in
            mView.goToDashboard();
        }

    }

    /**
     * Checks if google ic_account has been used to sign in on the app before,
     * If this is the case, it fetches the existing ic_account.
     * If this is not the case, a new ic_account entry is made on med manager.
     * @param account
     * @param mContext
     */
    @Override
    public void handleSignInResult(GoogleSignInAccount account, Context mContext) {

        //ic_add ic_default_profile_image to db or get corresponding ic_default_profile_image
        MedicationDBHelper medicationDBHelper = new MedicationDBHelper(mContext);

        //get the ic_default_profile_image with ID in DB or ic_add ic_default_profile_image if record does not exist in DB
        User user = medicationDBHelper.addOrReturnUser(new User(account.getId(), "", "", "", "on"));

        //close the connection
        medicationDBHelper.close();

        HashMap<String,String> mHashMap = new HashMap<>();
        mHashMap.put(SharedPrefContract.PREF_FULL_NAME, account.getDisplayName());
        mHashMap.put(SharedPrefContract.PREF_USER_EMAIL, account.getEmail());
        mHashMap.put(SharedPrefContract.PREF_USER_ID,account.getId());
        if(account.getPhotoUrl() != null) {
            mHashMap.put(SharedPrefContract.PREF_PROFILE_IMAGE, account.getPhotoUrl().toString());
        }




        if(user != null){

            //ic_default_profile_image already exists
            mHashMap.put(SharedPrefContract.PREF_USER_NAME, user.getUserName());
            mHashMap.put(SharedPrefContract.PREF_BIRTHDAY, user.getBirthday());
            mHashMap.put(SharedPrefContract.PREF_GENDER, user.getGender());
            if(user.getNotificationStatus().equalsIgnoreCase("on")){
                mSharedPrefHelper.putBoolean(SharedPrefContract.PREF_NOTIFICATION_TURNED_ON, true);
            }else{
                mSharedPrefHelper.putBoolean(SharedPrefContract.PREF_NOTIFICATION_TURNED_ON, false);
            }

        }

        //ic_add to shared preferences
        mSharedPrefHelper.putStrings(mHashMap);
        mSharedPrefHelper.putBoolean(SharedPrefContract.PREF_IS_LOGGED_IN, true);

        mView.updateUI(true);

    }
}
