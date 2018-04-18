package com.developer.nennenwodo.medmanager.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.developer.nennenwodo.medmanager.R;

public class BaseActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT <= 22) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(R.color.dark_middle_gray));
        }
    }
}
