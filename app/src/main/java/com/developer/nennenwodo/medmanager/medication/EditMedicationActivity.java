package com.developer.nennenwodo.medmanager.medication;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.nennenwodo.medmanager.utils.BaseActivity;
import com.developer.nennenwodo.medmanager.R;

import java.util.Locale;


public class EditMedicationActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, EditMedicationContract.View{

    private EditText  mEditTextMedicationName, mEditTextMedicationDescription, mEditTextFrequencyOrInterval, mEditTextStartDate, mEditTextStartTime, mEditTextEndDate;
    private EditMedicationPresenter mEditMedicationPresenter;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inject views
        setContentView(R.layout.activity_edit_medication);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_single_medication);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mEditTextMedicationName = (EditText) findViewById(R.id.edit_text_medication_name);
        mEditTextMedicationDescription = (EditText) findViewById(R.id.edit_text_medication_description);
        mEditTextStartDate = (EditText) findViewById(R.id.edit_text_medication_start_date);
        mEditTextEndDate = (EditText) findViewById(R.id.edit_text_medication_end_date);
        mEditTextStartTime = (EditText) findViewById(R.id.edit_text_medication_start_time);
        mEditTextFrequencyOrInterval = (EditText) findViewById(R.id.edit_text_medication_intake_frequency);
        final Button mButtonUpdateMedication = (Button) findViewById(R.id.btnEditMedication);

        final int medicationID = getIntent().getExtras().getInt("MEDICATION_ID"); //get intent
        mEditMedicationPresenter = new EditMedicationPresenter(this, EditMedicationActivity.this);
        mEditMedicationPresenter.getAndDisplayMedication(medicationID); //get medication from DB and display in views


        mButtonUpdateMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get values of edit text fields
                String medicationName = mEditTextMedicationName.getText().toString().trim();
                String medicationDescription = mEditTextMedicationDescription.getText().toString().trim();
                String medicationStartDate = mEditTextStartDate.getText().toString().trim();
                String medicationStartTime = mEditTextStartTime.getText().toString().trim();
                String medicationEndDate = mEditTextEndDate.getText().toString().trim();
                String medicationFrequency = mEditTextFrequencyOrInterval.getText().toString();

                //call to presenter method to update the medication
                mEditMedicationPresenter.editMedicationClick(EditMedicationActivity.this,medicationID,
                        medicationName,medicationDescription,medicationStartDate,medicationStartTime,medicationEndDate,medicationFrequency );


            }
        });


        mEditTextStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call to presenter method to show time picker dialog and display chosen time
                mEditMedicationPresenter.medicationStartTimeClick();

            }
        });

        mEditTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call to presenter method to show date picker dialog amd display chosen date
                mEditMedicationPresenter.medicationStartDayClick();

            }
        });


        mEditTextEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call to presenter method to show date picker dialog amd display chosen date
                mEditMedicationPresenter.medicationEndDayClick();

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void displayMedicationName(String name) {
        mEditTextMedicationName.setText(name);

    }

    @Override
    public void displayMedicationDescription(String description) {
        mEditTextMedicationDescription.setText(description);

    }

    @Override
    public void displayMedicationStartDate(String startDate) {
        mEditTextStartDate.setText(startDate);
    }

    @Override
    public void displayMedicationEndDate(String endDate) {
        mEditTextEndDate.setText(endDate);

    }

    @Override
    public void displayMedicationStartTime(String startTime) {
        mEditTextStartTime.setText(startTime);
    }

    @Override
    public void displayMedicationFrequency(int frequency) {
        mEditTextFrequencyOrInterval.setText(String.format(Locale.ENGLISH,"%d", frequency));
    }

    @Override
    public void displayEmptyInputFieldMessage() {
        Toast.makeText(EditMedicationActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayInvalidDateMessage() {
        Toast.makeText(EditMedicationActivity.this, "End date shouldn't be before start date", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayInvalidFrequencyErrorMessage() {
        Toast.makeText(EditMedicationActivity.this, "The Enter Number (#) field allows only integer values.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayMedicationUpdateSuccessMessage() {
        Toast.makeText(EditMedicationActivity.this, "Medication Updated!", Toast.LENGTH_SHORT).show();
    }

}

