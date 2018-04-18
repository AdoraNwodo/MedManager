package com.developer.nennenwodo.medmanager.medication;


import android.support.v7.widget.RecyclerView;

import com.developer.nennenwodo.medmanager.model.Medication;

public interface TodaysMedicationContract {

    interface View{

        void clearMedicationsList();
        void notifyAdapter();
        void addToMedicationsList(Medication medication);

    }

    interface Presenter{

        void prepareMedications();
    }

}
