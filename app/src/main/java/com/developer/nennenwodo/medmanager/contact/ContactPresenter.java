package com.developer.nennenwodo.medmanager.contact;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.developer.nennenwodo.medmanager.R;

/**
 * Responsible for handling actions from the view and updating the UI as required
 */
public class ContactPresenter implements ContactContract.Presenter{

    private ContactContract.View mView;
    private Context mContext;

    public ContactPresenter(ContactContract.View view, Context context){
        //init constructor
        mView = view;
        mContext = context;
    }

    @Override
    public void dial() {
        //launch dialer app to dial number
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(mContext.getString(R.string.telephone_number)));
        mContext.startActivity(intent);
    }

    @Override
    public void sendEmail() {
        //attempt to send email and print error message if ic_default_profile_image does not have a mail app
        try
        {
            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse(mContext.getString(R.string.email_address)));
            mContext.startActivity(intent);
        }
        catch(Exception e)
        {
            mView.displayNoEmailMessage();
        }
    }

    @Override
    public void loadWebPage(String webpage) {
        //open webpage and go to predefines url
        Uri uri = Uri.parse(webpage);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        mContext.startActivity(intent);
    }

    @Override
    public void openWhatsApp() {
        //open users phpne number on whatsapp to send message
        try {
            String toNumber = "+2347033610860";
            Intent mIntent = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:" + "" + toNumber + "?body=" + ""));
            mIntent.setPackage("com.whatsapp");
            mContext.startActivity(mIntent);
        }
        catch (Exception e){
            mView.displayNoWhatsappMessage();

        }

    }
}
