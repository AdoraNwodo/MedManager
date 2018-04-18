package com.developer.nennenwodo.medmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.developer.nennenwodo.medmanager.R;
import com.developer.nennenwodo.medmanager.monthlycategory.SingleCategoryActivity;
import com.developer.nennenwodo.medmanager.utils.Utility;

import java.util.List;



public class MonthlyCategoryListAdapter extends RecyclerView.Adapter<MonthlyCategoryListAdapter.MyViewHolder>{

    private Context context;
    private List<String[]> monthList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView monthAndYear;
        public RelativeLayout relativeLayoutItem;

        public MyViewHolder(View view) {
            super(view);
            monthAndYear = view.findViewById(R.id.text_view_month_and_year);
            relativeLayoutItem = view.findViewById(R.id.relative_layout_item);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //init view holder
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    /**
     * Binds the data to the view
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final String[] item = monthList.get(position);
        final String monthAndYear = Utility.toMonthString(item[0])+" "+item[1];
        holder.monthAndYear.setText(monthAndYear);
        holder.relativeLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SingleCategoryActivity.class);
                intent.putExtra("from_category", true);
                intent.putExtra("date", monthAndYear);
                intent.putExtra("month", item[0]);
                intent.putExtra("year", item[1]);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return monthList.size();
    }


    public MonthlyCategoryListAdapter(Context context, List<String[]> monthList) {
        this.context = context;
        this.monthList = monthList;
    }


}
