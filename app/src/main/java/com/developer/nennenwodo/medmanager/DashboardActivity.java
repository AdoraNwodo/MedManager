package com.developer.nennenwodo.medmanager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.developer.nennenwodo.medmanager.auth.SignInActivity;
import com.developer.nennenwodo.medmanager.medication.NewMedicationActivity;
import com.developer.nennenwodo.medmanager.model.preferences.SharedPrefHelper;
import com.developer.nennenwodo.medmanager.utils.BaseActivity;


public class DashboardActivity extends BaseActivity implements HomeFragment.OnFragmentInteractionListener,
        MonthlyCategoryFragment.OnFragmentInteractionListener, PersonalFragment.OnFragmentInteractionListener{

    private BottomNavigationView navigation;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setTitle(R.string.medications);
                    navigation.getMenu().getItem(0).setChecked(true);
                    transaction.replace(R.id.container, new HomeFragment(), "home-fragment").addToBackStack("home").commit();
                    return true;
                case R.id.navigation_categorize:
                    toolbar.setTitle(R.string.categories);
                    navigation.getMenu().getItem(1).setChecked(true);
                    transaction.replace(R.id.container, new MonthlyCategoryFragment(), "monthly-categories-fragment").addToBackStack("monthly-category").commit();
                    return true;
                case R.id.navigation_user:
                    toolbar.setTitle(R.string.personal);
                    navigation.getMenu().getItem(2).setChecked(true);
                    transaction.replace(R.id.container, new PersonalFragment(), "settings-fragment").addToBackStack("settings").commit();
                    return true;
            }
            return false;
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Context context = DashboardActivity.this;

        SharedPrefHelper mSharedPrefHelper = new SharedPrefHelper(context);

        //if user is not logged in, return to sign in page
        if(! mSharedPrefHelper.isLoggedIn()){
            Intent intent = new Intent(DashboardActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        }

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.medications);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, new HomeFragment(), "home-fragment").commit();


        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        //Find active fragment when back button is pressed and set active fragment
                        Fragment homeFragment = fragmentManager.findFragmentByTag("home-fragment");
                        Fragment monthlyFragment = fragmentManager.findFragmentByTag("monthly-categories-fragment");
                        Fragment settingsFragment = fragmentManager.findFragmentByTag("settings-fragment");

                        if(homeFragment != null && homeFragment.isVisible()){
                            navigation.getMenu().getItem(0).setChecked(true);

                        }
                        if(monthlyFragment != null && monthlyFragment.isVisible()){
                            navigation.getMenu().getItem(1).setChecked(true);

                        }
                        if(settingsFragment != null && settingsFragment.isVisible()){
                            navigation.getMenu().getItem(2).setChecked(true);
                        }
                    }
                });

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void toNewMedicationView(){
        Intent mIntent = new Intent(DashboardActivity.this, NewMedicationActivity.class);
        startActivity(mIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add_medication:
                toNewMedicationView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
