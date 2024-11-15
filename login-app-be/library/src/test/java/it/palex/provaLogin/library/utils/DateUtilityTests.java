package it.palex.provaLogin.library.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class DateUtilityTests {

    @Test
    public void checkRetriveIpNoProxy() {
        Date dateUtc = DateUtility.getCurrentDateInUTC();
        Calendar caledarUtc = DateUtility.getCurrentCalendarInUTC();

        Assertions.assertEquals(dateUtc, caledarUtc.getTime());

        Assertions.assertEquals(dateUtc, DateUtility.calendarToDate(caledarUtc));

        //check antialiasing
        final int secondsToAdd = 5;
        Date dateIncr5 = DateUtility.addSecondsToDate(dateUtc, secondsToAdd);
        Assertions.assertTrue(DateUtility.diffInSeconds(dateIncr5, dateUtc) == secondsToAdd);
    }
}
