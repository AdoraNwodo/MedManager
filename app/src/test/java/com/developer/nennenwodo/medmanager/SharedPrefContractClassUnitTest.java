package com.developer.nennenwodo.medmanager;

import com.developer.nennenwodo.medmanager.model.preferences.SharedPrefContract;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SharedPrefContractClassUnitTest {

    @Test
    public void one_constructor_exists() throws Exception {

        Constructor[] constructors = SharedPrefContract.class.getDeclaredConstructors();

        int numberOfConstructors = constructors.length;

        assertEquals("SharedPrefContract class should have one constructor", 1,numberOfConstructors);
    }

    @Test
    public void constructor_isPrivate() throws Exception {

        Constructor[] constructors = SharedPrefContract.class.getDeclaredConstructors();

        boolean isPrivate = Modifier.isPrivate(constructors[0].getModifiers());

        assertTrue("Constructor should be private", isPrivate);

    }


    @Test
    public void members_areCorrect() throws Exception {

        Class sharedPrefClass = SharedPrefContract.class;

        Field[] declaredFields = sharedPrefClass.getDeclaredFields();

        int numberOfDeclaredFields = declaredFields.length;

        assertEquals("There should be exactly 11 String members in the class", 11, numberOfDeclaredFields);

        for (Field field : declaredFields) {

            boolean membersArePublicStaticAndFinalStrings = Modifier.isStatic(field.getModifiers()) &&
                    Modifier.isFinal(field.getModifiers()) && Modifier.isPublic(field.getModifiers())
                    && field.getType()==String.class;

            assertTrue("All members in the contract class should be public, final and static strings", membersArePublicStaticAndFinalStrings);
        }
    }

}