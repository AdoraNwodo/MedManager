package com.developer.nennenwodo.medmanager.profile;


/**
 * This shows the contract between {@link ProfileActivity} and {@link ProfilePresenter}
 */

public interface ProfileContract {

    interface View{

        void displayUsername(String username);
        void displayBirthday(String birthday);
        void displayGender(String gender);
        void displayProfileUpdatedMessage();
        void displayPickedDate(String date);

    }

    interface  Presenter{

        void fetchSharedPreferences();
        void updateProfile(String userName, String birthday, String gender);
        void datePickerDialog();

    }

}
