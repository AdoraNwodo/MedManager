package com.developer.nennenwodo.medmanager.profile;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.developer.nennenwodo.medmanager.R;
import com.developer.nennenwodo.medmanager.utils.BaseActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.google.android.gms.auth.api.signin.GoogleSignIn.getLastSignedInAccount;

public class ProfileActivity extends BaseActivity implements ProfileContract.View, View.OnClickListener{


    private ProfilePresenter mProfilePresenter;
    private EditText nameEditText, userNameEditText, emailEditText, genderEditText, birthdayEditText;
    private CircleImageView profilePictureImageView;
    private Button updateProfileButton;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProfilePresenter = new ProfilePresenter(this, ProfileActivity.this);

        //use ic_account instead of shared preferences because users could have updated their details on gmail
        @SuppressLint("RestrictedApi") GoogleSignInAccount account = getLastSignedInAccount(ProfileActivity.this);

        injectViews();

        mProfilePresenter.fetchSharedPreferences();


        //Users cannot edit these fields. They are fetched from Gmail
        nameEditText.setText(account.getDisplayName());
        emailEditText.setText(account.getEmail());
        if(account.getPhotoUrl() != null) {
            Glide.with(this).load(account.getPhotoUrl()).into(profilePictureImageView);
        }


        birthdayEditText.setOnClickListener(this);
        updateProfileButton.setOnClickListener(this);

    }

    @Override
    public void displayPickedDate(String date) {
        birthdayEditText.setText(date);
    }

    private void injectViews(){

        nameEditText = (EditText) findViewById(R.id.edit_text_name);
        userNameEditText = (EditText) findViewById(R.id.edit_text_user_name);
        emailEditText = (EditText) findViewById(R.id.edit_text_email);
        genderEditText = (EditText) findViewById(R.id.edit_text_gender);
        birthdayEditText = (EditText) findViewById(R.id.edit_text_birthday);
        profilePictureImageView = (CircleImageView) findViewById(R.id.imageview_user_profile_picture);
        updateProfileButton = (Button) findViewById(R.id.btnSaveProfile);

    }

    @Override
    public void displayProfileUpdatedMessage() {
        Toast.makeText(ProfileActivity.this, "Profile Updated!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void displayUsername(String username) {
        userNameEditText.setText(username);
    }

    @Override
    public void displayBirthday(String birthday) {
        birthdayEditText.setText(birthday);
    }

    @Override
    public void displayGender(String gender) {
        genderEditText.setText(gender);
    }

    @Override
    public void onClick(View view) {

        int mViewID = view.getId();

        switch (mViewID){
            case R.id.edit_text_birthday:
                mProfilePresenter.datePickerDialog();
                break;
            case R.id.btnSaveProfile:
                mProfilePresenter.updateProfile(userNameEditText.getText().toString(),
                        birthdayEditText.getText().toString(), genderEditText.getText().toString());
                break;
            default:
                break;
        }

    }
}
