package com.developer.nennenwodo.medmanager.model.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nennenwodo on 4/16/18.
 */

public class SharedPrefHelper {

    private SharedPreferences mSharedPreferences;
    private Context mContext;

    public SharedPrefHelper(Context context){
        mContext = context;
        mSharedPreferences = context.getSharedPreferences(SharedPrefContract.PREF_MEDMANAGER,
                Context.MODE_PRIVATE);
    }

    /**
     * Checks if ic_default_profile_image has PREF_INSTALLED the application before
     * @return
     */
    public boolean isNotFirstInstallation(){
        return mSharedPreferences.contains(SharedPrefContract.PREF_INSTALLED) && mSharedPreferences.getBoolean(SharedPrefContract.PREF_INSTALLED, false);
    }

    /**
     * Checks if a ic_default_profile_image is logged in
     * @return
     */
    public boolean isLoggedIn(){
        return mSharedPreferences.contains(SharedPrefContract.PREF_IS_LOGGED_IN) && mSharedPreferences.getBoolean(SharedPrefContract.PREF_IS_LOGGED_IN, true);
    }


    /**
     * Sets the value of PREF_INSTALLED to true in shared pref so the app knows that the first installation has occured.
     * For better ic_default_profile_image experience
     */
    public void setFirstInstall(){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(SharedPrefContract.PREF_INSTALLED, true);
        editor.apply();
    }

    /**
     * Gets the ic_default_profile_image ID stored in the shared preference file
     * @return
     */
    public String getUserID(){
        return mSharedPreferences.getString(SharedPrefContract.PREF_USER_ID, null);
    }

    /**
     * Gets the ic_default_profile_image name stored in the shared preference file
     * @return
     */
    public String getUserName(){
        return mSharedPreferences.getString(SharedPrefContract.PREF_USER_NAME, null);
    }

    /**
     * Gets the PREF_GENDER stored in the shared preference file
     * @return
     */
    public String getUserGender(){
        return mSharedPreferences.getString(SharedPrefContract.PREF_GENDER, null);
    }

    /**
     * Gets the ic_default_profile_image's PREF_BIRTHDAY stored in the shared preference file
     * @return
     */
    public String getUserBirthday(){
        return mSharedPreferences.getString(SharedPrefContract.PREF_BIRTHDAY, null);
    }

    /**
     * Checks if the ic_default_profile_image set the notifications preference to true
     * @return
     */
    public boolean isNotificationOn(){
        return mSharedPreferences.getBoolean(SharedPrefContract.PREF_NOTIFICATION_TURNED_ON, true);
    }

    /**
     * Checks if a key exists in shared preferences
     * @return
     */
    public boolean isContainedInSharedPreference(String key){
        return mSharedPreferences.contains(key);
    }

    /**
     * Clears all ic_default_profile_image session data stored in shared preferences
     */
    public void clearSharedPreferences(){

        //clear ic_default_profile_image session data
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(SharedPrefContract.PREF_GENDER);
        editor.remove(SharedPrefContract.PREF_BIRTHDAY);
        editor.remove(SharedPrefContract.PREF_USER_NAME);
        editor.remove(SharedPrefContract.PREF_FULL_NAME);
        editor.remove(SharedPrefContract.PREF_USER_EMAIL);
        editor.remove(SharedPrefContract.PREF_PROFILE_IMAGE);
        editor.remove(SharedPrefContract.PREF_USER_ID);
        editor.putBoolean(SharedPrefContract.PREF_IS_LOGGED_IN, false);
        editor.apply();

    }

    /**
     * Adds a list of string key value pairs to shared preferences
     * @param mHashMap
     */
    public void putStrings(HashMap<String,String> mHashMap){

        SharedPreferences.Editor editor = mSharedPreferences.edit();

        for(Map.Entry m: mHashMap.entrySet()){

            editor.putString(m.getKey().toString(), m.getValue().toString());
        }

        editor.apply();
    }

    /**
     * Adds a boolean key value pair to shared preferences
     * @param key
     * @param value
     */
    public void putBoolean(String key, boolean value){

        SharedPreferences.Editor editor = mSharedPreferences.edit();

        editor.putBoolean(key, value);

        editor.apply();
    }

    /**
     * Makes the shared preference file editable
     */
    public void initSharedPrefEdit(){

    }

    /**
     * Saves the new edits made to the shared preference file
     */
    public void saveSharedPrefEdits(){

    }

}
