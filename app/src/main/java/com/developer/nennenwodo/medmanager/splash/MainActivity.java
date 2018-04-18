package com.developer.nennenwodo.medmanager.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.developer.nennenwodo.medmanager.onboarding.OnboardingActivity;
import com.developer.nennenwodo.medmanager.R;

/**
 * Displays the main screen.
 */
public class MainActivity extends AppCompatActivity implements MainContract.View{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MainPresenter mainPresenter = new MainPresenter(this);

        mainPresenter.splashDisplay();


    }

    @Override
    public void splashComplete() {
        Intent homeIntent = new Intent(MainActivity.this, OnboardingActivity.class);
        startActivity(homeIntent);
        finish();
    }
}
