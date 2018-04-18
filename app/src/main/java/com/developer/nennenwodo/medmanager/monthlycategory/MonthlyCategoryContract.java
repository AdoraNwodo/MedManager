package com.developer.nennenwodo.medmanager.monthlycategory;


/**
 * This defines the contract between {@link MonthlyCategoryPresenter} and {@link com.developer.nennenwodo.medmanager.MonthlyCategoryFragment}
 */

public interface MonthlyCategoryContract {

    interface View{
        void serveViews();
        void clearList();
        void addToList(String month, String year);
        void notifyAdapter();
    }

    interface Presenter{
        void prepareMedication();
    }
}
