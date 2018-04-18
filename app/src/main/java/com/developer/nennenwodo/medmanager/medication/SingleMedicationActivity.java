package com.developer.nennenwodo.medmanager.medication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.developer.nennenwodo.medmanager.R;
import com.developer.nennenwodo.medmanager.adapter.MedicationScheduleListAdapter;
import com.developer.nennenwodo.medmanager.utils.BaseActivity;
import com.developer.nennenwodo.medmanager.utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class SingleMedicationActivity extends BaseActivity implements SingleMedicationContract.View{

    private TextView mTextViewMedicationDescription, mTextViewStartDate, mTextViewStartTime, mTextViewtEndDate,
            mTextViewInterval, mTextViewTodaysSchedule, mTextViewPastSchedule, mTextViewUpcomingSchedule;
    private Toolbar toolbar;
    private int medicationID;
    private RecyclerView recyclerView, pastRecyclerView, upcomingRecyclerView;
    private List<String> medicationList;
    private List<String> pastList;
    private List<String> upcomingList;
    private MedicationScheduleListAdapter mAdapter,mPastAdapter, mUpcomingAdapter;
    private SingleMedicationPresenter mPresenter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_medication);
        toolbar = (Toolbar) findViewById(R.id.toolbar_single_medication);

        medicationID = getIntent().getExtras().getInt("MEDICATION_ID");

        mPresenter = new SingleMedicationPresenter(this, SingleMedicationActivity.this);

        injectViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        medicationList = new ArrayList<>();
        pastList = new ArrayList<>();
        upcomingList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.todaysRecyclerView);
        pastRecyclerView = (RecyclerView) findViewById(R.id.pastRecyclerView);
        upcomingRecyclerView = (RecyclerView) findViewById(R.id.upcomingRecyclerView);

        mAdapter = new MedicationScheduleListAdapter(medicationList);
        mPastAdapter = new MedicationScheduleListAdapter(pastList);
        mUpcomingAdapter = new MedicationScheduleListAdapter(upcomingList);


        //Initialise recycler view and its properties
        recyclerView.setLayoutManager(new LinearLayoutManager(SingleMedicationActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(SingleMedicationActivity.this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        pastRecyclerView.setLayoutManager(new LinearLayoutManager(SingleMedicationActivity.this));
        pastRecyclerView.setItemAnimator(new DefaultItemAnimator());
        pastRecyclerView.addItemDecoration(new DividerItemDecoration(SingleMedicationActivity.this, DividerItemDecoration.VERTICAL));
        pastRecyclerView.setAdapter(mPastAdapter);
        pastRecyclerView.setNestedScrollingEnabled(false);

        upcomingRecyclerView.setLayoutManager(new LinearLayoutManager(SingleMedicationActivity.this));
        upcomingRecyclerView.setItemAnimator(new DefaultItemAnimator());
        upcomingRecyclerView.addItemDecoration(new DividerItemDecoration(SingleMedicationActivity.this, DividerItemDecoration.VERTICAL));
        upcomingRecyclerView.setAdapter(mUpcomingAdapter);
        upcomingRecyclerView.setNestedScrollingEnabled(false);

        mPresenter.getMedicationData(medicationID);

        hideIrrelevantViews();


    }

    private void injectViews(){
        mTextViewMedicationDescription = (TextView) findViewById(R.id.textview_medication_description);
        mTextViewStartDate = (TextView) findViewById(R.id.textview_medication_start_date);
        mTextViewStartTime = (TextView) findViewById(R.id.textview_medication_start_time);
        mTextViewtEndDate = (TextView) findViewById(R.id.textview_medication_end_date);
        mTextViewInterval = (TextView) findViewById(R.id.textview_interval);
        mTextViewTodaysSchedule = (TextView) findViewById(R.id.todaysScheduleTextView);
        mTextViewPastSchedule = (TextView) findViewById(R.id.pastScheduleTextView);
        mTextViewUpcomingSchedule = (TextView) findViewById(R.id.upcomingsScheduleTextView);
    }

    @Override
    public void goToEditPage(int id){

        Intent intent = new Intent(SingleMedicationActivity.this,EditMedicationActivity.class);
        intent.putExtra("MEDICATION_ID", id);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_medication, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_edit_medication:
                goToEditPage(medicationID);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void setMedicationDescription(String description) {
        mTextViewMedicationDescription.setText(description);
    }

    /**
     * Helper methods to hide views that do not show data on page
     */
    private void hideIrrelevantViews(){

        //if there are no medications today, no need to display views that contain todays medications
        if(medicationList.size() <= 0){
            mTextViewTodaysSchedule.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        }

        //if there are no past medications, no need to display views that contain past medications
        if(pastList.size() <= 0){
            mTextViewPastSchedule.setVisibility(View.GONE);
            pastRecyclerView.setVisibility(View.GONE);
        }

        //if there are no upcoming medications, no need to display views that contain upcoming medications
        if(upcomingList.size() <= 0){
            mTextViewUpcomingSchedule.setVisibility(View.GONE);
            upcomingRecyclerView.setVisibility(View.GONE);
        }

    }

    @Override
    public void clearMedicationsList() {
        medicationList.clear();
        pastList.clear();
        upcomingList.clear();

    }

    @Override
    public void notifyAdapter() {
        mAdapter.notifyDataSetChanged();
        mPastAdapter.notifyDataSetChanged();
        mUpcomingAdapter.notifyDataSetChanged();
    }

    @Override
    public void addToMedicationsList(List<List<String>> medication) {
        medicationList.addAll(medication.get(0));
        pastList.addAll(medication.get(1));
        upcomingList.addAll(medication.get(2));
    }

    @Override
    public void setMedicationStartDate(String startDate) {
        mTextViewStartDate.setText(startDate);
    }

    @Override
    public void setMedicationStartTime(String startTime) {
        mTextViewStartTime.setText(startTime);
    }

    @Override
    public void setMedicationEndDate(String endDate) {
        mTextViewtEndDate.setText(endDate);
    }


    @Override
    public void setMedicationInterval(int interval, String startDate, String endDate) {
        int daysBetween = (int) Utility.daysBetween(startDate, endDate);
        if(daysBetween >= 0){
            mTextViewInterval.setText("To be taken "+interval+" time(s) a day");
        }else{
            mTextViewInterval.setText(R.string.completed);
        }

    }
}
