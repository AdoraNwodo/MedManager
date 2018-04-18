package com.developer.nennenwodo.medmanager.contact;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.nennenwodo.medmanager.R;
import com.developer.nennenwodo.medmanager.utils.BaseActivity;

public class ContactActivity extends BaseActivity implements ContactContract.View, View.OnClickListener{

    ContactPresenter mContactPresenter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContactPresenter = new ContactPresenter(this, ContactActivity.this);

        //inject views
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ImageView telephoneImageView = (ImageView) findViewById(R.id.image_view_call);
        final ImageView mailImageView = (ImageView) findViewById(R.id.image_view_envelop);
        final ImageView githubImageView = (ImageView) findViewById(R.id.image_view_github);
        final ImageView linkedinImageView = (ImageView) findViewById(R.id.image_view_linkedin);
        final TextView slackUsernameTextView = (TextView) findViewById(R.id.textview_slack_name);
        final TextView whatsappTextView = (TextView) findViewById(R.id.textview_whatsapp);

        //set click listeners
        telephoneImageView.setOnClickListener(this);
        mailImageView.setOnClickListener(this);
        githubImageView.setOnClickListener(this);
        linkedinImageView.setOnClickListener(this);
        slackUsernameTextView.setOnClickListener(this);

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
    public void onClick(View view) {

        int mViewID = view.getId();

        switch (mViewID){
            case R.id.image_view_call:
                mContactPresenter.dial();
                break;
            case R.id.image_view_envelop:
                mContactPresenter.sendEmail();
                break;
            case R.id.image_view_github:
                mContactPresenter.loadWebPage("http://github.com/NenneAdora");
                break;
            case R.id.image_view_linkedin:
                mContactPresenter.loadWebPage("https://ng.ic_linkedin.com/in/nenne-nwodo-a73b0710a");
                break;
            case R.id.textview_slack_name:
                mContactPresenter.loadWebPage("https://slack.com");
                break;
            case R.id.textview_whatsapp:
                mContactPresenter.openWhatsApp();
                break;

        }

    }

    @Override
    public void displayNoEmailMessage() {
        Toast.makeText(ContactActivity.this, "Sorry! You don't have any mail app.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayNoWhatsappMessage() {
        Toast.makeText(ContactActivity.this, "Sorry! You don't have Whatsapp PREF_INSTALLED.", Toast.LENGTH_SHORT).show();
    }
}
