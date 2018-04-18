package com.developer.nennenwodo.medmanager.adapter;


import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.nennenwodo.medmanager.R;
import com.developer.nennenwodo.medmanager.model.Medication;
import com.developer.nennenwodo.medmanager.utils.Utility;

import java.util.List;

public class TodaysMedicationListAdapter extends RecyclerView.Adapter<TodaysMedicationListAdapter.MyViewHolder> {
    private List<Medication> medicationList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, description, dosage, finish;

        private MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.text_name);
            description = view.findViewById(R.id.text_description);
            dosage = view.findViewById(R.id.text_dosage);
            finish = view.findViewById(R.id.text_end_date);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //init view holder
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todays_medication_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    /**
     * Binds the data to the view
     * @param holder
     * @param position
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Medication item = medicationList.get(position);
        holder.name.setText(item.getName());
        holder.description.setText(item.getDescription());
        holder.dosage.setText("To be taken " + item.getInterval() + " time(s) a day ");
        holder.finish.setText(Utility.daysBetween(item.getStartDate(), item.getEndDate()) + " day(s) left");

    }

    @Override
    public int getItemCount() {
        return medicationList.size();
    }


    public TodaysMedicationListAdapter(List<Medication> medicationList) {
        this.medicationList = medicationList;
    }


}