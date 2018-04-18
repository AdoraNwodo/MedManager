package com.developer.nennenwodo.medmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.developer.nennenwodo.medmanager.personalsettings.PersonalSettingsContract;
import com.developer.nennenwodo.medmanager.personalsettings.PersonalSettingsPresenter;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.google.android.gms.auth.api.signin.GoogleSignIn.getLastSignedInAccount;


public class PersonalFragment extends Fragment implements View.OnClickListener, PersonalSettingsContract.View, CompoundButton.OnCheckedChangeListener {


    private OnFragmentInteractionListener mListener;
    private PersonalSettingsPresenter mPersonalSettingsPresenter;
    private SwitchCompat notifyMeSwitch;

    public PersonalFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //use ic_account instead of shared preferences because users could have updated their details on gmail
        @SuppressLint("RestrictedApi") GoogleSignInAccount account = getLastSignedInAccount(getActivity());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        //inject user defined views
        final RelativeLayout contactMeRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_layout_contact_me);
        final RelativeLayout faqsRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_layout_faqs);
        final RelativeLayout profileRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_layout_profile);
        final RelativeLayout accountRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_layout_account);
        final RelativeLayout todaysMedsRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_layout_todays_meds);
        final TextView userNameTextView = (TextView) view.findViewById(R.id.text_view_user_name);
        final TextView userEmailTextView = (TextView) view.findViewById(R.id.text_view_user_email);
        final CircleImageView profilePictureImageView = (CircleImageView) view.findViewById(R.id.image_profile);
        notifyMeSwitch = (SwitchCompat) view.findViewById(R.id.notifyMeSwitch);

        //display user data on allocated views
        userNameTextView.setText(account.getDisplayName());
        userEmailTextView.setText(account.getEmail());
        if(account.getPhotoUrl() != null) {
            Glide.with(this).load(account.getPhotoUrl()).into(profilePictureImageView);
        }

        //initialise presenter
        mPersonalSettingsPresenter = new PersonalSettingsPresenter(this, getContext());

        mPersonalSettingsPresenter.checkUncheckNotificationSwitch();

        //set click listeners
        contactMeRelativeLayout.setOnClickListener(this);
        faqsRelativeLayout.setOnClickListener(this);
        profileRelativeLayout.setOnClickListener(this);
        accountRelativeLayout.setOnClickListener(this);
        todaysMedsRelativeLayout.setOnClickListener(this);
        notifyMeSwitch.setOnCheckedChangeListener(this);

        return view;
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_add_medication).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {

        int mViewID = view.getId();

        switch (mViewID){
            case R.id.relative_layout_contact_me:
                mPersonalSettingsPresenter.launchContactActivity();
                break;
            case R.id.relative_layout_faqs:
                mPersonalSettingsPresenter.launchFAQActivity();
                break;
            case R.id.relative_layout_profile:
                mPersonalSettingsPresenter.launchEditProfileActivity();
                break;
            case R.id.relative_layout_account:
                mPersonalSettingsPresenter.launchAccountActivity();
                break;
            case R.id.relative_layout_todays_meds:
                mPersonalSettingsPresenter.launchTodaysMedsActivity();
                break;


        }

    }

    @Override
    public void initNotificationSwitch(boolean notifyMe) {
        //checks notification switch compat based on if user has set notify me to true of false
        if(notifyMe){
            notifyMeSwitch.setChecked(true);
        }else{
            notifyMeSwitch.setChecked(false);
        }
    }

    /**
     * Listens to check changes and updates notification settings based on users preference
     * @param compoundButton
     * @param b
     */
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        int id = compoundButton.getId();

        if(id == R.id.notifyMeSwitch){
            if(notifyMeSwitch.isChecked()){
                mPersonalSettingsPresenter.toggleNotificationsOnOff(true);
            }else{
                mPersonalSettingsPresenter.toggleNotificationsOnOff(false);
            }
        }

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
