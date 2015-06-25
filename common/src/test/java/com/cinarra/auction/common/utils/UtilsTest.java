package com.cinarra.auction.common.utils;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static com.cinarra.auction.common.utils.Utils.*;


public class UtilsTest {

    @Test
    public void testGetDay() {
        long day = getDay(1432459646975L);
        assertEquals(day, 16579);
    }

    @Test
    public void testFirsDayOfWeek() {
        LocalDate day = LocalDate.parse("2015-05-26");
        assertEquals(firstDayOfWeek(day), LocalDate.parse("2015-05-25"));

        day = LocalDate.parse("2015-05-02");
        assertEquals(firstDayOfWeek(day), LocalDate.parse("2015-04-27"));
    }

    @Test
    public void testLastDayOfWeek() {
        LocalDate day = LocalDate.parse("2015-05-26");
        assertEquals(lastDayOfWeek(day), LocalDate.parse("2015-05-31"));

        day = LocalDate.parse("2015-04-29");
        assertEquals(lastDayOfWeek(day), LocalDate.parse("2015-05-03"));
    }

}
