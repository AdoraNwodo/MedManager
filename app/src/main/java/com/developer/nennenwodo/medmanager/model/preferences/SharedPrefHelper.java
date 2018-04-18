package com.developer.nennenwodo.medmanager.model.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;


public class SharedPrefHelper {

    private SharedPreferences mSharedPreferences;

    public SharedPrefHelper(Context context){
        mSharedPreferences = context.getSharedPreferences(SharedPrefContract.PREF_MEDMANAGER,
                Context.MODE_PRIVATE);
    }

    /**
     * Checks if user has installed the application before
     * @return
     */
    public boolean isNotFirstInstallation(){
        return mSharedPreferences.contains(SharedPrefContract.PREF_INSTALLED) && mSharedPreferences.getBoolean(SharedPrefContract.PREF_INSTALLED, false);
    }

    /**
     * Checks if a user is logged in
     * @return
     */
    public boolean isLoggedIn(){
        return mSharedPreferences.contains(SharedPrefContract.PREF_IS_LOGGED_IN) && mSharedPreferences.getBoolean(SharedPrefContract.PREF_IS_LOGGED_IN, true);
    }


    /**
     * Sets the value of installed to true in shared pref so the app knows that the first installation has occurred.
     * For better user experience
     */
    public void setFirstInstall(){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(SharedPrefContract.PREF_INSTALLED, true);
        editor.apply();
    }

    /**
     * Gets the user id stored in the shared preference file
     * @return
     */
    public String getUserID(){
        return mSharedPreferences.getString(SharedPrefContract.PREF_USER_ID, null);
    }

    /**
     * Gets the user name stored in the shared preference file
     * @return
     */
    public String getUserName(){
        return mSharedPreferences.getString(SharedPrefContract.PREF_USER_NAME, null);
    }

    /**
     * Gets the gender stored in the shared preference file
     * @return
     */
    public String getUserGender(){
        return mSharedPreferences.getString(SharedPrefContract.PREF_GENDER, null);
    }

    /**
     * Gets the user's birthday stored in the shared preference file
     * @return
     */
    public String getUserBirthday(){
        return mSharedPreferences.getString(SharedPrefContract.PREF_BIRTHDAY, null);
    }

    /**
     * Checks if the user set the notifications preference to true
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
     * Clears all user session data stored in shared preferences
     */
    public void clearSharedPreferences(){

        //clear user session data
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
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


}
