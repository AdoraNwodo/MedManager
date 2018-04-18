package com.developer.nennenwodo.medmanager.personalsettings;

import com.developer.nennenwodo.medmanager.PersonalFragment;

/**
 * This defines the contract between {@link PersonalSettingsPresenter} and {@link PersonalFragment}
 */

public interface PersonalSettingsContract {

    interface View{
        void initNotificationSwitch(boolean notifyMe);
    }

    interface Presenter{

        void launchContactActivity();
        void launchFAQActivity();
        void launchEditProfileActivity();
        void launchAccountActivity();
        void launchTodaysMedsActivity();
        void toggleNotificationsOnOff(boolean notifyMe);
        void checkUncheckNotificationSwitch();
    }

}
