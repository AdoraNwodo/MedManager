package com.developer.nennenwodo.medmanager.medication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.nennenwodo.medmanager.DashboardActivity;
import com.developer.nennenwodo.medmanager.utils.BaseActivity;
import com.developer.nennenwodo.medmanager.utils.IntervalFilter;
import com.developer.nennenwodo.medmanager.R;

public class NewMedicationActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, NewMedicationContract.View, View.OnClickListener{

    private NewMedicationPresenter mNewMedicationPresenter;
    private EditText mEditTextMedicationName, mEditTextMedicationDescription, mEditTextStartDate, mEditTextStartTime, mEditTextEndDate, mEditTextFrequencyOrInterval;
    private Button btnAddMedication;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_medication);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //initialise presenter
        mNewMedicationPresenter = new NewMedicationPresenter(this, NewMedicationActivity.this);

        injectViews();

        //prevent edit text from allowing numeric values outside the range of 1-24
        mEditTextFrequencyOrInterval.setFilters(new InputFilter[]{new IntervalFilter("1", "24")});

        btnAddMedication.setOnClickListener(this);
        mEditTextStartTime.setOnClickListener(this);
        mEditTextStartDate.setOnClickListener(this);
        mEditTextEndDate.setOnClickListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * Helper method to inject all ic_default_profile_image defined views
     */
    private void injectViews(){

        mEditTextMedicationName = (EditText) findViewById(R.id.edit_text_medication_name);
        mEditTextMedicationDescription = (EditText) findViewById(R.id.edit_text_medication_description);
        mEditTextStartDate = (EditText) findViewById(R.id.edit_text_medication_start_date);
        mEditTextEndDate = (EditText) findViewById(R.id.edit_text_medication_end_date);
        mEditTextStartTime = (EditText) findViewById(R.id.edit_text_medication_start_time);
        mEditTextFrequencyOrInterval = (EditText) findViewById(R.id.edit_text_medication_intake_frequency);
        btnAddMedication = (Button) findViewById(R.id.btnAddMedication);

    }


    @Override
    public void displayEmptyInputFieldMessage() {
        Toast.makeText(NewMedicationActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayInvalidDateMessage() {
        Toast.makeText(NewMedicationActivity.this, "End date shouldn't be before start date", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayInvalidFrequencyErrorMessage() {
        Toast.makeText(NewMedicationActivity.this, "The Enter Number (#) field allows only integer values.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayMedicationInsertSuccessMessage() {
        Toast.makeText(NewMedicationActivity.this, "New Medication Added!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showStartDay(String day) {
        mEditTextStartDate.setText(day);
    }

    @Override
    public void showStartTime(String time) {
        mEditTextStartTime.setText(time);
    }

    @Override
    public void showEndDay(String day) {
        mEditTextEndDate.setText(day);
    }

    @Override
    public void toPreviousActivity() {
        Intent returnIntent = new Intent(NewMedicationActivity.this, DashboardActivity.class);
        //setResult(RESULT_OK,returnIntent);
        startActivity(returnIntent);
        finishAffinity();
    }


    @Override
    public void resetFields() {
        mEditTextMedicationName.setText("");
        mEditTextMedicationDescription.setText("");
        mEditTextStartDate.setText("");
        mEditTextStartTime.setText("");
        mEditTextEndDate.setText("");
        mEditTextFrequencyOrInterval.setText("");
    }



    @Override
    public void onClick(View view) {

        int mViewID = view.getId();

        if(mViewID == R.id.btnAddMedication){

            String medicationName = mEditTextMedicationName.getText().toString().trim();
            String medicationDescription = mEditTextMedicationDescription.getText().toString().trim();
            String medicationStartDate = mEditTextStartDate.getText().toString().trim();
            String medicationStartTime = mEditTextStartTime.getText().toString().trim();
            String medicationEndDate = mEditTextEndDate.getText().toString().trim();
            String medicationFrequency = mEditTextFrequencyOrInterval.getText().toString();

            //call to presenter method to ic_add medication
            mNewMedicationPresenter.addMedicationClick(NewMedicationActivity.this, medicationName, medicationDescription,
                    medicationStartDate, medicationStartTime, medicationEndDate, medicationFrequency
            );

            //finish();

        }else if(mViewID == R.id.edit_text_medication_start_time){
            //call to presenter method to show time picker dialog and display chosen time
            mNewMedicationPresenter.medicationStartTimeClick();
        }else if(mViewID == R.id.edit_text_medication_start_date){
            //call to presenter method to show date picker dialog and display chosen date
            mNewMedicationPresenter.medicationStartDayClick();
        }else if(mViewID == R.id.edit_text_medication_end_date){
            //call to presenter method to show date picker dialog and display chosen date
            mNewMedicationPresenter.medicationEndDayClick();
        }

    }

}
