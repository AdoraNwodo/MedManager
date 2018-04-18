package com.developer.nennenwodo.medmanager;

import com.developer.nennenwodo.medmanager.splash.MainContract;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MainContractClassUnitTest {

    @Test
    public void class_isInterface() throws Exception {

        assertTrue("Main Contract should be an interface", MainContract.class.isInterface());
    }

    @Test
    public void two_inner_interfaces_exists() throws Exception {

        Class[] mainContractInnerClasses = MainContract.class.getDeclaredClasses();

        int numberOfInnerClasses = mainContractInnerClasses.length;

        boolean twoInterfacesExists = numberOfInnerClasses == 2 && mainContractInnerClasses[0].isInterface() && mainContractInnerClasses[1].isInterface();

        assertTrue("Main Contract should have two interfaces",twoInterfacesExists);
    }

    @Test
    public void inner_interfaces_areCorrect() throws Exception {

        Class[] mainContractInnerClasses = MainContract.class.getDeclaredClasses();

        int numberOfInnerClasses = mainContractInnerClasses.length;

        boolean twoInterfacesExists = numberOfInnerClasses == 2 && mainContractInnerClasses[0].isInterface() && mainContractInnerClasses[1].isInterface();

        assertTrue("Main Contract should have two interfaces",twoInterfacesExists);

        Class mView = mainContractInnerClasses[0];

        Class mPresenter = mainContractInnerClasses[1];

        Method[] mViewMethods = mView.getDeclaredMethods();

        Method[] mPresenterMethods = mPresenter.getDeclaredMethods();

        assertTrue("Inner view interface should have only one void method",
                mViewMethods.length == 1 && mViewMethods[0].getReturnType().equals(Void.TYPE));

        assertTrue("Inner presenter interface should have only one void method",
                mPresenterMethods.length == 1 && mPresenterMethods[0].getReturnType().equals(Void.TYPE));

    }

}