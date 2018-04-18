package com.developer.nennenwodo.medmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.developer.nennenwodo.medmanager.R;
import com.developer.nennenwodo.medmanager.medication.SingleMedicationActivity;
import com.developer.nennenwodo.medmanager.model.Medication;
import com.developer.nennenwodo.medmanager.utils.Utility;

import java.util.ArrayList;
import java.util.List;


public class MedicationListAdapter extends RecyclerView.Adapter<MedicationListAdapter.MyViewHolder> {
    private Context context;
    private List<Medication> medicationList;
    private boolean from_category;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, dosage;
        private RelativeLayout viewBackground;
        public RelativeLayout viewForeground;
        private ImageView status;

        private MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.text_name);
            dosage = view.findViewById(R.id.text_dosage);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
            status = view.findViewById(R.id.status);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //init view holder
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medication_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    /**
     * Binds the data to the view
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Medication item = medicationList.get(position);
        holder.name.setText(item.getName());
        holder.dosage.setText("To be taken " + item.getInterval() + " time(s) a day ");
        int daysBetween = (int) Utility.daysBetween(item.getStartDate(), item.getEndDate());
        if(daysBetween >= 0){
            holder.status.setImageResource(R.drawable.ic_hour_glass);
        }else{
            holder.status.setImageResource(R.drawable.ic_checked);
        }
        holder.viewBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SingleMedicationActivity.class);
                intent.putExtra("MEDICATION_ID", item.getId());
                intent.putExtra("FROM_CATEGORIES", from_category);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return medicationList.size();
    }

    public void removeItem(int position) {
        medicationList.remove(position);
        // notify the item removed by position
        notifyItemRemoved(position);
    }


    public MedicationListAdapter(Context context, List<Medication> medicationList) {
        this.context = context;
        this.medicationList = medicationList;
    }

    public MedicationListAdapter(Context context, List<Medication> medicationList, boolean from_category) {
        this.context = context;
        this.medicationList = medicationList;
        this.from_category = from_category;
    }

    /**
     * Sets filter for search function.
     * @param newMedicationList
     */
    public  void setFilter(ArrayList<Medication> newMedicationList){

        medicationList =  new ArrayList<>();
        medicationList.addAll(newMedicationList);

        notifyDataSetChanged();

    }


}