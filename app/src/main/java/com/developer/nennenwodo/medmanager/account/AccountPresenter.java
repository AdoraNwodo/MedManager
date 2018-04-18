package com.developer.nennenwodo.medmanager.account;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.developer.nennenwodo.medmanager.model.preferences.SharedPrefHelper;
import com.developer.nennenwodo.medmanager.model.sqlite.MedicationDBHelper;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


/**
 * Responsible for handling actions from the view and updating the UI as required
 */
public class AccountPresenter implements AccountContract.Presenter {

    private AccountContract.View mView;
    private Activity mActivity;
    private Context mContext;
    private SharedPrefHelper mSharedPrefHelper;

    public AccountPresenter(AccountContract.View view, Activity activity, Context context){
        //init presenter constructor
        mView = view;
        mActivity = activity;
        mContext = context;
        mSharedPrefHelper = new SharedPrefHelper(context);
    }

    /**
     * Signs out the ic_default_profile_image. User can sign back in and continue using the app normally
     * @param mGoogleSignInClient
     */
    @SuppressLint("RestrictedApi")
    @Override
    public void signOut(GoogleSignInClient mGoogleSignInClient){

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(mActivity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        //sign out - clear ic_default_profile_image shared preferences and go back to login activity
                        mSharedPrefHelper.clearSharedPreferences();
                        mView.backToLoginPage();

                    }
                });
    }

    /**
     * Deletes the users ic_account and all medications belonging to that particular ic_default_profile_image
     * @param mGoogleSignInClient
     */
    @Override
    public void revokeAccess(GoogleSignInClient mGoogleSignInClient) {

        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(mActivity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        MedicationDBHelper medicationDBHelper = new MedicationDBHelper(mContext);

                        String userId = mSharedPrefHelper.getUserID();

                        medicationDBHelper.deleteAllUsersMedications(userId);
                        medicationDBHelper.deleteUsersAccount(userId);

                        //clear ic_default_profile_image shared preferences and go back to login activity
                        mSharedPrefHelper.clearSharedPreferences();
                        mView.backToLoginPage();

                    }
                });

    }




}
