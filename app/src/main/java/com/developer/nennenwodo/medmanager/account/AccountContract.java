package com.developer.nennenwodo.medmanager.account;


import com.google.android.gms.auth.api.signin.GoogleSignInClient;

/**
 * This interface defines the contract between {@link AccountActivity} and {@link AccountPresenter}
 */
public interface AccountContract {

    interface View{
        void backToLoginPage();
    }

    interface Presenter{
        void signOut(GoogleSignInClient mGoogleSignInClient);
        void revokeAccess(GoogleSignInClient mGoogleSignInClient);
    }

}
