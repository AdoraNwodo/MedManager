package com.developer.nennenwodo.medmanager.medication;


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
