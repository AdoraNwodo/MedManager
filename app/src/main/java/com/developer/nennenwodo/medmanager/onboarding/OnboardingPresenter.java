package com.developer.nennenwodo.medmanager.onboarding;

import android.content.Context;
import android.content.SharedPreferences;

import com.developer.nennenwodo.medmanager.model.preferences.SharedPrefContract;
import com.developer.nennenwodo.medmanager.model.preferences.SharedPrefHelper;

/**
 * Responsible for handling actions from the view and updating the UI as required
 */
public class OnboardingPresenter implements OnboardingContract.Presenter {

    private OnboardingContract.View mView;
    private Context mContext;
    private SharedPrefHelper mSharedPrefHelper;

    public OnboardingPresenter(OnboardingContract.View view, Context context){
        mView = view;
        mContext = context;
        mSharedPrefHelper = new SharedPrefHelper(context);
    }

    /**
     * Checks if app has been PREF_INSTALLED prior to now using shared preferences.
     * If this is the first installation, onboarding screen is displayed.
     * If not, the page is not displayed and the ic_default_profile_image sees the next visible page on med manager
     */
    @Override
    public void checkInstall() {

        if(mSharedPrefHelper.isNotFirstInstallation()){
            //ic_default_profile_image has logged in before
            mView.goToLogin();
            mView.setFullScreen();

        }

        //set shared preference so nonboarding screen never shows after now
        mSharedPrefHelper.setFirstInstall();

    }

    @Override
    public void onSkipClick() {
        mView.goToLogin();  //skip onboarding process
    }

    @Override
    public void onNextClick(int numberOfScreens) { //go to next onboarding screen
        int current = mView.getItem(+1);
        if(current < numberOfScreens){
            //if not on the last screen, show next screen
            mView.setCurrentViewPagerItem(current);
        }else{
            //if on the last screen, show login
            mView.goToLogin();
        }
    }

}
