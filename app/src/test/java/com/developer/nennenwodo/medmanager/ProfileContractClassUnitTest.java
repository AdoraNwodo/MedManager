package com.developer.nennenwodo.medmanager;

import com.developer.nennenwodo.medmanager.profile.ProfileContract;
import com.developer.nennenwodo.medmanager.splash.MainContract;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ProfileContractClassUnitTest {

    @Test
    public void class_isInterface() throws Exception {

        assertTrue("Profile Contract should be an interface", ProfileContract.class.isInterface());
    }

    @Test
    public void two_inner_interfaces_exists() throws Exception {

        Class[] profileContractInnerClasses = ProfileContract.class.getDeclaredClasses();

        int numberOfInnerClasses = profileContractInnerClasses.length;

        boolean twoInterfacesExists = numberOfInnerClasses == 2 &&
                profileContractInnerClasses[0].isInterface() && profileContractInnerClasses[1].isInterface();

        assertTrue("Profile Contract should have two interfaces",twoInterfacesExists);
    }

}