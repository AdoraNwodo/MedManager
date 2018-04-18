package com.developer.nennenwodo.medmanager.model.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

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
        mSharedPreferences = context.getSharedPreferences(SharedPrefContract.userPreference,
                Context.MODE_PRIVATE);
    }

    /**
     * Checks if user has installed the application before
     * @return
     */
    public boolean isNotFirstInstallation(){
        return mSharedPreferences.contains(SharedPrefContract.installed) && mSharedPreferences.getBoolean(SharedPrefContract.installed, false);
    }

    /**
     * Checks if a user is logged in
     * @return
     */
    public boolean isLoggedIn(){
        return mSharedPreferences.contains(SharedPrefContract.isLoggedIn) && mSharedPreferences.getBoolean(SharedPrefContract.isLoggedIn, true);
    }


    /**
     * Sets the value of installed to true in shared pref so the app knows that the first installation has occured.
     * For better user experience
     */
    public void setFirstInstall(){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(SharedPrefContract.installed, true);
        editor.apply();
    }

    /**
     * Gets the user ID stored in the shared preference file
     * @return
     */
    public String getUserID(){
        return mSharedPreferences.getString(SharedPrefContract.userID, null);
    }

    /**
     * Gets the user name stored in the shared preference file
     * @return
     */
    public String getUserName(){
        return mSharedPreferences.getString(SharedPrefContract.userName, null);
    }

    /**
     * Gets the gender stored in the shared preference file
     * @return
     */
    public String getUserGender(){
        return mSharedPreferences.getString(SharedPrefContract.gender, null);
    }

    /**
     * Gets the user's birthday stored in the shared preference file
     * @return
     */
    public String getUserBirthday(){
        return mSharedPreferences.getString(SharedPrefContract.birthday, null);
    }

    /**
     * Checks if the user set the notifications preference to true
     * @return
     */
    public boolean isNotificationOn(){
        return mSharedPreferences.getBoolean(SharedPrefContract.notifications_on, true);
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
        editor.remove(SharedPrefContract.gender);
        editor.remove(SharedPrefContract.birthday);
        editor.remove(SharedPrefContract.userName);
        editor.remove(SharedPrefContract.fullName);
        editor.remove(SharedPrefContract.userEmail);
        editor.remove(SharedPrefContract.profileImageURL);
        editor.remove(SharedPrefContract.userID);
        editor.putBoolean(SharedPrefContract.isLoggedIn, false);
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
