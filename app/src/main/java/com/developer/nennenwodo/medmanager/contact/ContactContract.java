package com.developer.nennenwodo.medmanager.contact;


/**
 * This interface defines the contract between {@link ContactActivity} and {@link ContactPresenter}
 */
public interface ContactContract {

    interface View{
        void displayNoEmailMessage();
        void displayNoWhatsappMessage();
    }

    interface Presenter{
        void dial();
        void sendEmail();
        void loadWebPage(String webpage);
        void openWhatsApp();
    }

}
