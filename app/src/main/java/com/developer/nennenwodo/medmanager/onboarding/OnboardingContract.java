package com.developer.nennenwodo.medmanager.onboarding;

import android.content.SharedPreferences;

/**
 * Created by nennenwodo on 4/7/18.
 */

public interface OnboardingContract {

    interface View{
        void goToLogin();
        void setFullScreen();
        void addBottomDots(int position);
        void setCurrentViewPagerItem(int current);
        int getItem(int i);
    }

    interface Presenter{
        void checkInstall();
        void onSkipClick();
        void onNextClick(int numberOfScreens);
    }

}
