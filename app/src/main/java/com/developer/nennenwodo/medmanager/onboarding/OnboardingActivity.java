package com.developer.nennenwodo.medmanager.onboarding;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developer.nennenwodo.medmanager.utils.BaseActivity;
import com.developer.nennenwodo.medmanager.R;
import com.developer.nennenwodo.medmanager.auth.SignInActivity;


public class OnboardingActivity extends BaseActivity implements OnboardingContract.View, View.OnClickListener{

    private ViewPager viewPager;
    private int[] layouts;
    private  Button next, skip;
    private LinearLayout dotsLayout;
    private OnboardingPresenter mOnboardingPresenter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //init presenter
        mOnboardingPresenter = new OnboardingPresenter(this, OnboardingActivity.this);

        //check if user has installed app and leave page if app has been PREF_INSTALLED prior to now
        mOnboardingPresenter.checkInstall();

        setContentView(R.layout.activity_onboarding);

        //inject user defined views
        viewPager = (ViewPager)findViewById(R.id.onboardingViewPager);
        dotsLayout = (LinearLayout)findViewById(R.id.onboardingLayoutDots);
        skip = (Button)findViewById(R.id.onboardingSkipButton);
        next = (Button)findViewById(R.id.onboardingNextButton);
        layouts = new int[]{R.layout.activity_screen_one, R.layout.activity_screen_two, R.layout.activity_screen_three, R.layout.activity_screen_four};

        //add dots to bottom of page
        addBottomDots(0);

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewListener);

        skip.setOnClickListener(this);
        next.setOnClickListener(this);

    }


    @Override
    public void goToLogin() {
        Intent mIntent = new Intent(OnboardingActivity.this, SignInActivity.class);
        startActivity(mIntent);
        finish();

    }

    @Override
    public void setFullScreen() {

        if(Build.VERSION.SDK_INT >= 21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        }

    }

    /**
     * Add bottom dots for splash screen
     * @param position
     */
    @Override
    public void addBottomDots(int position){

        final TextView[] dots = new TextView[layouts.length];
        int[] colorActive = getResources().getIntArray(R.array.dot_active);
        int[] colorInactive = getResources().getIntArray(R.array.dot_inactive);

        dotsLayout.removeAllViews();

        for(int i = 0; i< dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInactive[position]);
            dotsLayout.addView(dots[i]);
        }

        if(dots.length > 0){
            dots[position].setTextColor(colorActive[position]);
        }


    }

    @Override
    public void setCurrentViewPagerItem(int current) {
        viewPager.setCurrentItem(current);
    }

    @Override
    public int getItem(int i){
        return viewPager.getCurrentItem() + i;
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }


        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            //show text on onboarding footer based on level
            if(position == layouts.length - 1){
                //last page shows proceed
                next.setText(R.string.proceed);
                skip.setVisibility(View.GONE);
            }else{
                //first pages show next
                next.setText(R.string.next);
                skip.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onClick(View view) {

        int mViewID = view.getId();

        switch (mViewID){
            case (R.id.onboardingSkipButton):
                mOnboardingPresenter.onSkipClick();
                break;
            case (R.id.onboardingNextButton):
                mOnboardingPresenter.onNextClick(layouts.length);
                break;
            default:
                break;
        }
    }


    public class ViewPagerAdapter extends PagerAdapter{

        private LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(layouts[position], container, false);
            container.addView(v);
            return v;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v = (View) object;
            container.removeView(v);
        }
    }

}
