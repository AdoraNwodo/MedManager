package com.developer.nennenwodo.medmanager.auth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Toast;

import com.developer.nennenwodo.medmanager.utils.BaseActivity;
import com.developer.nennenwodo.medmanager.DashboardActivity;
import com.developer.nennenwodo.medmanager.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

/**
 * Authentication View
 */
public class SignInActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener, SignInContract.View, View.OnClickListener{

    private static final int REQUEST_CODE = 1002;
    private SignInPresenter mSignInPresenter;
    private GoogleSignInClient mGoogleSignInClient;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mSignInPresenter = new SignInPresenter(this, SignInActivity.this);


        mSignInPresenter.checkIfLoggedIn();

        final SignInButton signIn = (SignInButton) findViewById(R.id.btnLogin);

        //Configure sign-in to request the ic_default_profile_image's ID, email address, and basic profile
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, signInOptions);

        signIn.setOnClickListener(this);

    }

    private void handleResult(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            mSignInPresenter.handleSignInResult(account, SignInActivity.this);
        } catch (ApiException e) {
            updateUI(false);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE){
            @SuppressLint("RestrictedApi") Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleResult(task);
        }
    }


    /**
     * Shows the user the appropriate screen depending on the login action
     * @param loggedIn
     */
    @Override
    public void updateUI(boolean loggedIn){

        if(loggedIn){
            //redirect if logged in
            Intent intent = new Intent(SignInActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }else{
            //show error message if not logged in
            Toast.makeText(SignInActivity.this, "Authentication failed. Please try again.", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Redirects to the Dashboard Activity
     */
    @Override
    public void goToDashboard() {
        Intent mIntent = new Intent(SignInActivity.this, DashboardActivity.class);
        startActivity(mIntent);
        finish();
    }

    @Override
    public void onClick(View view) {

        int mViewID = view.getId();

        if(mViewID == R.id.btnLogin){
            //start the google sign in process
            @SuppressLint("RestrictedApi") Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, REQUEST_CODE);
        }
    }
}
