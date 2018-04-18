package com.developer.nennenwodo.medmanager.medication;


import android.content.Context;

/**
 * This interface defines the contract between {@link NewMedicationActivity} and {@link NewMedicationPresenter}
 */
public interface NewMedicationContract {

    interface View{

        void resetFields();
        void displayEmptyInputFieldMessage();
        void displayInvalidDateMessage();
        void displayInvalidFrequencyErrorMessage();
        void displayMedicationInsertSuccessMessage();
        void showStartDay(String day);
        void showStartTime(String time);
        void showEndDay(String day);
        void toPreviousActivity();
    }

    interface Presenter{
        void addMedicationClick(Context context, String medicationName, String medicationDescription,
                                String medicationStartDate, String medicationStartTime,
                                String medicationEndDate, String medicationFrequency
        );

        void medicationStartDayClick();
        void medicationEndDayClick();
        void medicationStartTimeClick();

    }

}
