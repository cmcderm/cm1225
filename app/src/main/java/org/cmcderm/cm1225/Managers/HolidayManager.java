package org.cmcderm.cm1225.Managers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class HolidayManager {
    public static Set<LocalDate> getHolidaysByYear(int year) {
        Set<LocalDate> holidays = new HashSet<LocalDate>();

        // July 4th, move to nearest weekday if on weekend
        LocalDate july4th = LocalDate.of(year, 7, 4);
        if (july4th.getDayOfWeek() == DayOfWeek.SATURDAY) {
            july4th = july4th.minusDays(1);
        } else if (july4th.getDayOfWeek() == DayOfWeek.SUNDAY) {
            july4th = july4th.plusDays(1);
        }
        holidays.add(july4th);

        // Labor day, first monday of september
        LocalDate laborDay = LocalDate.of(year, 9, 1);

        while (laborDay.getDayOfWeek() != DayOfWeek.MONDAY) {
            laborDay = laborDay.plusDays(1);
        }
        holidays.add(laborDay);

        return holidays;
    }
}
