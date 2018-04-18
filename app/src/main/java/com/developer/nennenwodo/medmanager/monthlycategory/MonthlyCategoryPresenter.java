package com.developer.nennenwodo.medmanager.monthlycategory;

import android.content.Context;
import android.database.Cursor;

import com.developer.nennenwodo.medmanager.model.preferences.SharedPrefHelper;
import com.developer.nennenwodo.medmanager.model.sqlite.MedicationDBHelper;


/**
 * Responsible for handling actions from the view and updating the UI as required
 */
public class MonthlyCategoryPresenter implements MonthlyCategoryContract.Presenter{

    private MonthlyCategoryContract.View mView;
    private Context mContext;

    public MonthlyCategoryPresenter(MonthlyCategoryContract.View view, Context context){
        mView = view;
        mContext = context;
    }

    /**
     * Gets a list of categories to be displayed on the recyclerview
     */
    @Override
    public void prepareMedication() {

        mView.clearList(); //clear list to avoid duplicates

        //get id of authenticated user from session using shared preferences
        SharedPrefHelper mSharedPrefHelper = new SharedPrefHelper(mContext);
        String userID = mSharedPrefHelper.getUserID();


        //get all users medications and add to list that would be displayed in recycler view
        MedicationDBHelper medicationDBHelper = new MedicationDBHelper(mContext);

        Cursor cursor = medicationDBHelper.readCategories(userID);

        while(cursor.moveToNext()){
            String month = cursor.getString(cursor.getColumnIndex("m"));   //get month
            String year = cursor.getString(cursor.getColumnIndex("y"));    //get year

            mView.addToList(month, year);

        }

        cursor.close();

        //close the db connection
        medicationDBHelper.close();

        mView.notifyAdapter();
        mView.serveViews();

    }
}
