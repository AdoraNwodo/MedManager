package com.developer.nennenwodo.medmanager;

import com.developer.nennenwodo.medmanager.contact.ContactContract;
import com.developer.nennenwodo.medmanager.splash.MainContract;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ContactContractClassUnitTest {

    @Test
    public void class_isInterface() throws Exception {

        assertTrue("Contact Contract should be an interface", ContactContract.class.isInterface());
    }

    @Test
    public void two_inner_interfaces_exists() throws Exception {

        Class[] contactContractInnerClasses = ContactContract.class.getDeclaredClasses();

        int numberOfInnerClasses = contactContractInnerClasses.length;

        boolean twoInterfacesExists = numberOfInnerClasses == 2 && contactContractInnerClasses[0].isInterface() && contactContractInnerClasses[1].isInterface();

        assertTrue("Main Contract should have two interfaces", twoInterfacesExists);
    }


}