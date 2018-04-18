package com.developer.nennenwodo.medmanager.monthlycategory;

import android.support.v7.widget.RecyclerView;

import com.developer.nennenwodo.medmanager.model.Medication;

/**
 * This defines the contract between {@link SingleCategoryActivity} and {@link SingleCategoryPresenter}
 */
public interface SingleCategoryContract {

    interface View{
        void clearList();
        void addToList(int id, String userID, String name, String description, int frequencyOrInterval, String startDate, String startTime, String endDate);
        void notifyAdapter();
        Medication getMedication(int position);
        void removeItem(int position);
    }

    interface Presenter{

        void prepareMedication();
        void handleSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);

    }


}
