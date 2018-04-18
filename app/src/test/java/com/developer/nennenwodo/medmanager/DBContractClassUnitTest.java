package com.developer.nennenwodo.medmanager;

import android.provider.BaseColumns;

import com.developer.nennenwodo.medmanager.model.sqlite.MedicationDBContract;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DBContractClassUnitTest {

    @Test
    public void two_inner_classes_exists() throws Exception {

        Class[] medicationContractInnerClasses = MedicationDBContract.class.getDeclaredClasses();

        int numberOfInnerClasses = medicationContractInnerClasses.length;

        assertEquals("Contract class should have two inner classes", 2,numberOfInnerClasses);
    }

    @Test
    public void inner_class_types_areCorrect() throws Exception {

        Class[] medicationContractInnerClasses = MedicationDBContract.class.getDeclaredClasses();

        int numberOfInnerClasses = medicationContractInnerClasses.length;

        assertEquals("Contract class should have two inner classes", 2, numberOfInnerClasses);

        Class medicationEntryClass = medicationContractInnerClasses[1];
        Class userEntryClass = medicationContractInnerClasses[0];

        boolean medicationInnerClassIsPublicStaticAndFinal = Modifier.isPublic(medicationEntryClass.getModifiers()) &&
                Modifier.isStatic(medicationEntryClass.getModifiers()) && Modifier.isFinal(medicationEntryClass.getModifiers());

        boolean userInnerClassIsPublicStaticAndFinal = Modifier.isPublic(userEntryClass.getModifiers()) &&
                Modifier.isStatic(userEntryClass.getModifiers()) && Modifier.isFinal(userEntryClass.getModifiers());


        assertTrue("Inner class should implement the BaseColumns interface", BaseColumns.class.isAssignableFrom(medicationEntryClass));
        assertTrue("Inner class should be public, final and static", medicationInnerClassIsPublicStaticAndFinal);

        assertTrue("Inner class should implement the BaseColumns interface", BaseColumns.class.isAssignableFrom(userEntryClass));
        assertTrue("Inner class should be public, final and static", userInnerClassIsPublicStaticAndFinal);

    }


    @Test
    public void inner_class_members_areCorrect() throws Exception {

        Class[] medicationContractInnerClasses = MedicationDBContract.class.getDeclaredClasses();

        int numberOfInnerClasses = medicationContractInnerClasses.length;

        assertEquals("Contract class should have two inner classes", 2, numberOfInnerClasses);

        Class userEntryClass = medicationContractInnerClasses[0];
        Class medicationEntryClass = medicationContractInnerClasses[1];

        Field[] medicationClassDeclaredFields = medicationEntryClass.getDeclaredFields();
        Field[] userClassDeclaredFields = userEntryClass.getDeclaredFields();

        int medicationFields = medicationClassDeclaredFields.length;
        int userFields = userClassDeclaredFields.length;

        assertEquals("There should be exactly 8 String members in the inner class", 8, medicationFields);
        assertEquals("There should be exactly 6 String members in the inner class", 6, userFields);

        for (Field field : medicationClassDeclaredFields) {

            boolean membersArePublicStaticAndFinalStrings = Modifier.isStatic(field.getModifiers()) &&
                    Modifier.isFinal(field.getModifiers()) && Modifier.isPublic(field.getModifiers())
                    && field.getType()==String.class;

            assertTrue("All members in the contract class should be public, final and static strings", membersArePublicStaticAndFinalStrings);
        }

        for (Field field : userClassDeclaredFields) {

            boolean membersArePublicStaticAndFinalStrings = Modifier.isStatic(field.getModifiers()) &&
                    Modifier.isFinal(field.getModifiers()) && Modifier.isPublic(field.getModifiers())
                    && field.getType()==String.class;

            assertTrue("All members in the contract class should be public, final and static strings", membersArePublicStaticAndFinalStrings);
        }
    }

}