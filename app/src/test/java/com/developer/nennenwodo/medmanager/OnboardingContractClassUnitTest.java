package com.developer.nennenwodo.medmanager;

import com.developer.nennenwodo.medmanager.onboarding.OnboardingContract;
import com.developer.nennenwodo.medmanager.profile.ProfileContract;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class OnboardingContractClassUnitTest {

    @Test
    public void class_isInterface() throws Exception {

        assertTrue("Onboarding Contract should be an interface", OnboardingContract.class.isInterface());
    }

    @Test
    public void two_inner_interfaces_exists() throws Exception {

        Class[] onboardingContractInnerClasses = OnboardingContract.class.getDeclaredClasses();

        int numberOfInnerClasses = onboardingContractInnerClasses.length;

        boolean twoInterfacesExists = numberOfInnerClasses == 2 &&
                onboardingContractInnerClasses[0].isInterface() && onboardingContractInnerClasses[1].isInterface();

        assertTrue("Profile Contract should have two interfaces",twoInterfacesExists);
    }

}