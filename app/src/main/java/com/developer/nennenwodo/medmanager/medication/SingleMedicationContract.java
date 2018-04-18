package com.developer.nennenwodo.medmanager.medication;


import java.util.List;

public interface SingleMedicationContract {

    interface View{

        void setToolbarTitle(String title);
        void goToEditPage(int id);
        void setMedicationDescription(String description);
        void setMedicationStartDate(String startDate);
        void setMedicationStartTime(String startTime);
        void setMedicationEndDate(String endDate);
        void setMedicationInterval(int interval, String start, String end);
        void clearMedicationsList();
        void notifyAdapter();
        void addToMedicationsList(List<List<String>> medication);

    }

    interface Presenter{


        void getMedicationData(int medicationID);

    }

}
