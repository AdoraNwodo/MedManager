package com.developer.nennenwodo.medmanager.model.sqlite;

import android.provider.BaseColumns;

/**
 * This class shows the DB Contract.
 */

public class MedicationDBContract {

    private MedicationDBContract(){}

    /**
     * Fields for the Medication Table
     */
    public static final class MedicationEntry implements BaseColumns{

        public static final String TABLE_NAME = "medication_information";

        public static final String NAME = "medication_name";

        public static final String USER_ID = "user_id";

        public static final String DESCRIPTION = "medication_description";

        public static final String INTERVAL = "medication_interval";

        public static final String START_DATE = "medication_start_date";

        public static final String START_TIME = "medication_start_time";

        public static final String END_DATE = "medication_end_date";

    }

    /**
     * Fields for the User Table
     */
    public static final class UserEntry implements BaseColumns {

        public static final String TABLE_NAME = "user_information";

        public static final String USER_ID = "user_id";

        public static final String USER_NAME = "user_name";

        public static final String GENDER = "user_gender";

        public static final String NOTIFICATION_STATUS = "notification_status";

        public static final String BIRTHDAY = "user_birthday";

    }

}
