package com.developer.nennenwodo.medmanager.medication;


import android.content.Context;

/**
 * This interface defines the contract between {@link EditMedicationActivity} and {@link EditMedicationPresenter}
 */
public interface EditMedicationContract {

    interface View{

        void displayMedicationName(String name);
        void displayMedicationDescription(String description);
        void displayMedicationStartDate(String startDate);
        void displayMedicationEndDate(String endDate);
        void displayMedicationStartTime(String startTime);
        void displayMedicationFrequency(int frequency);
        void displayInvalidFrequencyErrorMessage();
        void displayMedicationUpdateSuccessMessage();
        void displayEmptyInputFieldMessage();
        void displayInvalidDateMessage();

    }

    interface Presenter{

        void getAndDisplayMedication(int id);
        void editMedicationClick(Context context, int medicationID, String medicationName, String medicationDescription,
                                String medicationStartDate, String medicationStartTime,
                                String medicationEndDate, String medicationFrequency
        );
        void medicationStartDayClick();
        void medicationEndDayClick();
        void medicationStartTimeClick();
    }
}
