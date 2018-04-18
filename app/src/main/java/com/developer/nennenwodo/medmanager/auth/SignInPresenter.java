package com.developer.nennenwodo.medmanager.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;


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
    private Context mContext;
    private SharedPrefHelper mSharedPrefHelper;


    public SignInPresenter(SignInContract.View view, Context context){
        //init constructor
        mView = view;
        mContext = context;
        mSharedPrefHelper = new SharedPrefHelper(context);
    }

    /**
     * Checks the user session (shared preferences) to see if a user already logged in
     */
    @Override
    public void checkIfLoggedIn() {

        if(mSharedPrefHelper.isLoggedIn()){
            //redirect to dashboard if logged in
            mView.goToDashboard();
        }

    }

    /**
     * Checks if google account has been used to sign in on the app before,
     * If this is the case, it fetches the existing account.
     * If this is not the case, a new account entry is made on med manager.
     * @param account
     * @param mContext
     */
    @Override
    public void handleSignInResult(GoogleSignInAccount account, Context mContext) {

        //add user to db or get corresponding user
        MedicationDBHelper medicationDBHelper = new MedicationDBHelper(mContext);

        //get the user with ID in DB or add user if record does not exist in DB
        User user = medicationDBHelper.addOrReturnUser(new User(account.getId(), "", "", "", "on"));

        //close the connection
        medicationDBHelper.close();

        HashMap<String,String> mHashMap = new HashMap<>();
        mHashMap.put(SharedPrefContract.fullName, account.getDisplayName());
        mHashMap.put(SharedPrefContract.userEmail, account.getEmail());
        mHashMap.put(SharedPrefContract.userID,account.getId());
        if(account.getPhotoUrl() != null) {
            mHashMap.put(SharedPrefContract.profileImageURL, account.getPhotoUrl().toString());
        }




        if(user != null){

            //user already exists
            mHashMap.put(SharedPrefContract.userName, user.getUserName());
            mHashMap.put(SharedPrefContract.birthday, user.getBirthday());
            mHashMap.put(SharedPrefContract.gender, user.getGender());
            if(user.getNotificationStatus().equalsIgnoreCase("on")){
                mSharedPrefHelper.putBoolean(SharedPrefContract.notifications_on, true);
            }else{
                mSharedPrefHelper.putBoolean(SharedPrefContract.notifications_on, false);
            }

        }

        //add to shared preferences
        mSharedPrefHelper.putStrings(mHashMap);
        mSharedPrefHelper.putBoolean(SharedPrefContract.isLoggedIn, true);

        mView.updateUI(true);

    }
}
