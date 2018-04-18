package com.developer.nennenwodo.medmanager.medication;


import android.support.v7.widget.RecyclerView;

import com.developer.nennenwodo.medmanager.model.Medication;

/**
 * This interface defines the contract between {@link MedicationListContract} and {@link MedicationListPresenter}
 */
public interface MedicationListContract {

    interface View{

        void clearMedicationsList();
        void notifyAdapter();
        void addToMedicationsList(Medication medication);
        void removeAdapterItem(int position);
        Medication getMedication(int position);
        void serveViews();

    }

    interface Presenter{

        void prepareMedications();
        void handleSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}
