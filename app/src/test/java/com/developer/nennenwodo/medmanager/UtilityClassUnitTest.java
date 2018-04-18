package com.developer.nennenwodo.medmanager;

import com.developer.nennenwodo.medmanager.utils.Utility;

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
public class UtilityClassUnitTest {

    @Test
    public void one_constructor_exists() throws Exception {

        Constructor[] constructors = Utility.class.getDeclaredConstructors();

        int numberOfConstructors = constructors.length;

        assertEquals("Utility class should have one constructor", 1,numberOfConstructors);
    }

    @Test
    public void constructor_isPrivate() throws Exception {

        Constructor[] constructors = Utility.class.getDeclaredConstructors();

        boolean isPrivate = Modifier.isPrivate(constructors[0].getModifiers());

        assertTrue("Constructor should be private", isPrivate);

    }

    @Test
    public void toMonthMethodIsCorrect() throws Exception {

        assertEquals("First month should return January", "January", Utility.toMonthString("01"));
        assertEquals("Second month should return February", "February", Utility.toMonthString("02"));
        assertEquals("Third month should return March", "March", Utility.toMonthString("03"));
        assertEquals("Fourth month should return April", "April", Utility.toMonthString("04"));
        assertEquals("Fifth month should return May", "May", Utility.toMonthString("05"));
        assertEquals("Sixth month should return June", "June", Utility.toMonthString("06"));
        assertEquals("Seventh month should return July", "July", Utility.toMonthString("07"));
        assertEquals("Eighth month should return August", "August", Utility.toMonthString("08"));
        assertEquals("Ninth month should return September", "September", Utility.toMonthString("09"));
        assertEquals("Tenth month should return October", "October", Utility.toMonthString("10"));
        assertEquals("Eleventh month should return November", "November", Utility.toMonthString("11"));
        assertEquals("Twelfth month should return December", "December", Utility.toMonthString("12"));

        assertEquals("Invalid month should return null", null, Utility.toMonthString("1"));
        assertEquals("Invalid month should return null", null, Utility.toMonthString("Jan"));
    }

    @Test
    public void getDayMethodIsCorrect() throws Exception {

        assertEquals("Day should return 4", 4, Utility.getDay("2018-02-04"));

    }

    @Test
    public void getMonthMethodIsCorrect() throws Exception {

        assertEquals("Month should return 2", 1, Utility.getMonth("2018-02-04"));

    }

    @Test
    public void getYearMethodIsCorrect() throws Exception {

        assertEquals("Year should return 2018", 2018, Utility.getYear("2018-02-04"));

    }

    @Test
    public void getHourMethodIsCorrect() throws Exception {

        assertEquals("Hour should return 1", 1, Utility.getHour("01:20:00"));

    }

    @Test
    public void getMinuteMethodIsCorrect() throws Exception {

        assertEquals("Minute should return 20", 20, Utility.getMinute("01:20:00"));

    }

    @Test
    public void formatDateMethodIsCorrect() throws Exception {

        assertEquals("Date should be returned in yyyy-MM-dd format", "2018-01-01", Utility.formatDate(1,1,2018));

    }


    @Test
    public void formatTimeMethodIsCorrect() throws Exception {

        assertEquals("Time should be returned in hh:mm:ss format", "21:50:00", Utility.formatTime(21,50));

    }

    @Test
    public void isBeforeMethodIsCorrect() throws Exception {

        assertTrue("Start date should be before end date", Utility.isBefore("2018-01-01","2018-01-19"));
        assertFalse("Start date is after end date", Utility.isBefore("2019-01-01","2018-01-19"));

    }

}