package com.developer.nennenwodo.medmanager;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.nennenwodo.medmanager.medication.MedicationListContract;
import com.developer.nennenwodo.medmanager.medication.MedicationListPresenter;
import com.developer.nennenwodo.medmanager.adapter.MedicationListAdapter;
import com.developer.nennenwodo.medmanager.model.Medication;
import com.developer.nennenwodo.medmanager.utils.RecyclerItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener,
        SearchView.OnQueryTextListener, MedicationListContract.View{


    private RecyclerView recyclerView;
    private List<Medication> medicationList;
    private MedicationListAdapter mAdapter;
    private OnFragmentInteractionListener mListener;
    private SearchView mSearchView;
    private TextView noMedsYetTextView;
    private MedicationListPresenter medicationListPresenter;


    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.medicationRecyclerView);
        medicationList = new ArrayList<>();
        mAdapter = new MedicationListAdapter(getContext(), medicationList);
        noMedsYetTextView = (TextView) view.findViewById(R.id.textview_no_meds_yet);
        mSearchView = (SearchView) view.findViewById(R.id.searchView);
        mSearchView.setOnQueryTextListener(this);

        //Initialise recycler view and its properties
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        //initialise recycler item touch helper. (for swipe left to delete)
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        medicationListPresenter = new MedicationListPresenter(this, getContext());
        medicationListPresenter.prepareMedications();

        serveViews();

        return view;
    }


    /**
     * Displays the appropriate views based on data availability
     */
    public void serveViews(){

        if(medicationList.size() > 0){
            //display recycler view if there are medications
            recyclerView.setVisibility(View.VISIBLE);
            mSearchView.setVisibility(View.VISIBLE);
            noMedsYetTextView.setVisibility(View.GONE);
        }
        else{
            //display text saying there are no medications ifthere are medications
            recyclerView.setVisibility(View.GONE);
            mSearchView.setVisibility(View.GONE);
            noMedsYetTextView.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        //call to presenter method to handle swipe to delete
        medicationListPresenter.handleSwipe(viewHolder, direction, position);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Update List
        medicationListPresenter.prepareMedications();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        /// code to update data then notify Adapter
        medicationListPresenter.prepareMedications();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //listens to ic_search view on query text change and filters recycler view
        newText = newText.toLowerCase().trim();
        ArrayList<Medication> medicationArrayList = new ArrayList<>();
        for(Medication medication : medicationList){

            String medicationName = medication.getName().toLowerCase();
            if(medicationName.contains(newText)){
                medicationArrayList.add(medication);
            }
        }

        mAdapter.setFilter(medicationArrayList);

        return true;
    }

    @Override
    public void clearMedicationsList() {
        medicationList.clear();
    }

    @Override
    public void notifyAdapter() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void addToMedicationsList(Medication medication) {
        medicationList.add(medication);
    }

    @Override
    public void removeAdapterItem(int position) {
        mAdapter.removeItem(position);
    }

    @Override
    public Medication getMedication(int position) {
        return medicationList.get(position);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
