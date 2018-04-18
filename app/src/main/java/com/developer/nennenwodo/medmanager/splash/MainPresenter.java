package com.developer.nennenwodo.medmanager.splash;


import android.os.Handler;

/**
 * Responsible for handling actions from the view and updating the UI as required
 */
public class MainPresenter implements MainContract.Presenter{

    private MainContract.View mView;
    private int SPLASH_TIME_OUT;

    public MainPresenter(MainContract.View view){
        mView = view;
        SPLASH_TIME_OUT = 4000;
    }


    @Override
    public void splashDisplay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mView.splashComplete();
            }
        }, SPLASH_TIME_OUT);
    }
}
