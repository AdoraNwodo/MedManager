package com.developer.nennenwodo.medmanager.account;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.developer.nennenwodo.medmanager.R;
import com.developer.nennenwodo.medmanager.auth.SignInActivity;
import com.developer.nennenwodo.medmanager.utils.BaseActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;


public class AccountActivity extends BaseActivity implements AccountContract.View{

    private AccountPresenter mAccountPresenter;
    RelativeLayout logoutRelativeLayout, deleteAccountRelativeLayout;
    GoogleSignInClient mGoogleSignInClient;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        logoutRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_logout);
        deleteAccountRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_delete_account);

        //initialise presenter
        mAccountPresenter = new AccountPresenter(this, AccountActivity.this, AccountActivity.this);


        //initialise google sign in options
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        logoutRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sign out action
                mAccountPresenter.signOut(mGoogleSignInClient);
            }
        });


        deleteAccountRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete ic_account action
                mAccountPresenter.revokeAccess(mGoogleSignInClient);
            }
        });
    }

    @Override
    public void backToLoginPage() {
        //launch intent to go to login page
        Intent intent = new Intent(AccountActivity.this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        getFragmentManager().popBackStack();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }

}
