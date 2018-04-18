package com.developer.nennenwodo.medmanager;

import com.developer.nennenwodo.medmanager.personalsettings.PersonalSettingsContract;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PersonalSettingsContractClassUnitTest {

    @Test
    public void class_isInterface() throws Exception {

        assertTrue("Settings Contract should be an interface", PersonalSettingsContract.class.isInterface());
    }

    @Test
    public void two_inner_interfaces_exists() throws Exception {

        Class[] settingsContractInnerClasses = PersonalSettingsContract.class.getDeclaredClasses();

        int numberOfInnerClasses = settingsContractInnerClasses.length;

        boolean twoInterfacesExists = numberOfInnerClasses == 2 && settingsContractInnerClasses[0].isInterface() && settingsContractInnerClasses[1].isInterface();

        assertTrue("Settings Contract should have two interfaces",twoInterfacesExists);
    }

}