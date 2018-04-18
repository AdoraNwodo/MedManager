package com.developer.nennenwodo.medmanager.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.nennenwodo.medmanager.R;

import java.util.List;

public class MedicationScheduleListAdapter extends RecyclerView.Adapter<MedicationScheduleListAdapter.MyViewHolder> {

    private List<String> medicationList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView schedule;

        private MyViewHolder(View view) {
            super(view);
            schedule = view.findViewById(R.id.medicationScheduleTextView);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //init view holder
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medication_schedule_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    /**
     * Binds the data to the view
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final String item = medicationList.get(position);
        holder.schedule.setText(item);
    }

    @Override
    public int getItemCount() {
        return medicationList.size();
    }

    public MedicationScheduleListAdapter(List<String> medicationList) {

        this.medicationList = medicationList;
    }

}