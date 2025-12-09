package org.cmcderm.cm1225.Models;

import org.cmcderm.cm1225.Managers.HolidayManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class RentalAgreement {
    Tool tool;
    ToolChargeDetail toolChargeDetail;
    LocalDate checkoutDate;
    LocalDate dueDate;
    int rentalDays;
    BigDecimal discountPercent;

    public RentalAgreement(
            Tool tool,
            ToolChargeDetail toolChargeDetail,
            LocalDate checkoutDate,
            int rentalDays,
            BigDecimal discountPercent
            ) {
        this.tool = tool;
        this.toolChargeDetail = toolChargeDetail;
        this.checkoutDate = checkoutDate;
        this.rentalDays = rentalDays;
        this.dueDate = checkoutDate.plusDays(rentalDays);
        this.discountPercent = discountPercent;
    }

    public String generate() {
        StringBuilder s = new StringBuilder();

        addFieldLn(s, "Tool code: ", tool.getToolCode());

        addFieldLn(s, "Tool type: ", tool.getToolType());

        addFieldLn(s, "Tool brand: ", tool.getBrand());

        addFieldLn(s, "Rental days: ", Long.toString(rentalDays));

        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        addFieldLn(s, "Checkout date: ", checkoutDate.format(dtFormatter));

        addFieldLn(s, "Due date: ", dueDate.format(dtFormatter));

        addFieldLn(s, "Daily rental charge: $", toolChargeDetail.getDailyCharge().toString());

        long chargeDaysCount = calcChargeDaysCount();
        addFieldLn(s, "Charge days: ", Long.toString(chargeDaysCount));

        BigDecimal preDiscountCharge = toolChargeDetail.getDailyCharge().multiply(BigDecimal.valueOf(chargeDaysCount));
        addFieldLn(s, "Pre-discount charge: ", preDiscountCharge.toString());

        s.append("Discount percent: ");
        s.append(discountPercent);
        s.append("%");
        s.append(System.lineSeparator());

        BigDecimal discountAmount = preDiscountCharge.multiply(discountPercent).divide(BigDecimal.valueOf(100), RoundingMode.HALF_DOWN);
        addFieldLn(s, "Discount amount: $", discountAmount.toString());

        BigDecimal finalCharge = preDiscountCharge.subtract(discountAmount);
        addFieldLn(s, "Final charge: ", finalCharge.toString());

        return s.toString();
    }

    private long calcChargeDaysCount() {
        return checkoutDate.datesUntil(dueDate).filter(this::isChargeDay).count();
    }

   private boolean isChargeDay(LocalDate d) {
        Set<DayOfWeek> weekdays = Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
        Set<DayOfWeek> weekend = Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
        Set<LocalDate> holidays = HolidayManager.getHolidaysByYear(d.getYear());

        if (holidays.contains(d)) {
            return toolChargeDetail.getHolidayCharge();
        }

        if (weekdays.contains(d.getDayOfWeek())) {
            return toolChargeDetail.getWeekdayCharge();
        } else if (weekend.contains(d.getDayOfWeek())) {
            return toolChargeDetail.getWeekdayCharge();
        }

        // Unreachable
        return false;
   }

    private void addFieldLn(StringBuilder s, String field, String value) {
        s.append(field);
        s.append(value);
        s.append(System.lineSeparator());
    }
}
