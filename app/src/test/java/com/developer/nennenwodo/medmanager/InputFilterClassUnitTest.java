package com.developer.nennenwodo.medmanager;

import com.developer.nennenwodo.medmanager.utils.IntervalFilter;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class InputFilterClassUnitTest {

    IntervalFilter mFilter = new IntervalFilter();

    @Test
    public void one_constructor_exists() throws Exception {

        Constructor[] constructors = IntervalFilter.class.getDeclaredConstructors();

        int numberOfConstructors = constructors.length;

        assertEquals("IntervalFilter class should have two constructors", 2,numberOfConstructors);
    }

    @Test
    public void constructor_isPublic() throws Exception {

        Constructor[] constructors = IntervalFilter.class.getDeclaredConstructors();

        boolean isPrivate = Modifier.isPublic(constructors[0].getModifiers());

        assertTrue("Constructor should be private", isPrivate);

    }


    @Test
    public void number_isInRange() throws Exception {

        assertTrue("Input value should be between min and max", mFilter.isInRange(1,7,4));

    }

    @Test
    public void number_isNotInRange() throws Exception {

        assertFalse("Input value should not be higher than min and max.", mFilter.isInRange(1,7,14));

    }

}