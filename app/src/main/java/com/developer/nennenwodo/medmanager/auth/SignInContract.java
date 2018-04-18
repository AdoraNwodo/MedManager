package com.developer.nennenwodo.medmanager.auth;

import android.content.Context;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * This interface defines the contract between {@link SignInActivity} and {@link SignInPresenter}
 */
public interface SignInContract {

    interface View{

        void goToDashboard();
        void updateUI(boolean loggedIn);

    }

    interface Presenter{
        void checkIfLoggedIn();
        void handleSignInResult(GoogleSignInAccount account, Context context);
    }

}
