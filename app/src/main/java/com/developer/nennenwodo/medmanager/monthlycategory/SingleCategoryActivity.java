package com.developer.nennenwodo.medmanager.monthlycategory;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;

import com.developer.nennenwodo.medmanager.R;
import com.developer.nennenwodo.medmanager.model.Medication;
import com.developer.nennenwodo.medmanager.adapter.MedicationListAdapter;
import com.developer.nennenwodo.medmanager.utils.BaseActivity;
import com.developer.nennenwodo.medmanager.utils.RecyclerItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

public class SingleCategoryActivity extends BaseActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener, SingleCategoryContract.View{

    private SingleCategoryPresenter mPresenter;
    private List<Medication> medicationList;
    private MedicationListAdapter mAdapter;
    private boolean from_category = false;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_category);

        //get date extras from intent
        Bundle extras = getIntent().getExtras();

        assert extras != null;
        if(extras.containsKey("from_category")){
            from_category = extras.getBoolean("from_category");
        }
        String date = extras.getString("date"); //date extra
        String month = extras.getString("month");   //month extra
        String year = extras.getString("year"); //year extra

        mPresenter = new SingleCategoryPresenter(this, SingleCategoryActivity.this, month, year);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(date);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initRecyclerView();

        mPresenter.prepareMedication();


    }


    /**
     * Initialises recyclerview and its attributes
     */
    private void initRecyclerView(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.medsRecyclerView);
        medicationList = new ArrayList<>();
        mAdapter = new MedicationListAdapter(SingleCategoryActivity.this, medicationList, from_category);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(SingleCategoryActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(SingleCategoryActivity.this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        //call to presenter method to handle swipe to delete
        mPresenter.handleSwiped(viewHolder, direction, position);
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


    /**
     * Clears medication list
     */
    @Override
    public void clearList() {
        medicationList.clear();
    }

    /**
     * Adds new medication to list
     * @param id medication identifier
     * @param userId id of user who medication belongs to
     * @param name name of the medication
     * @param description description of the medication
     * @param startDate date the medication starts
     * @param startTime time the medication starts
     * @param endDate date the medication ends
     * @param frequencyOrInterval number of times a day the medication is to be taken
     */
    @Override
    public void addToList(int id, String userId, String name, String description, int frequencyOrInterval, String startDate, String startTime, String endDate) {
        medicationList.add(new Medication(id, userId, name, description, frequencyOrInterval, startDate,startTime, endDate));
    }

    /**
     * Notifies recyclerview adapter of any change
     */
    @Override
    public void notifyAdapter() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public Medication getMedication(int position) {
        return medicationList.get(position);
    }

    @Override
    public void removeItem(int position) {
        mAdapter.removeItem(position);
    }
}
