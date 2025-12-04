package org.cmcderm.cm1225.Models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class RentalAgreement {
    Tool tool;
    ToolChargeDetail toolChargeDetail;
    LocalDate checkoutDate;
    LocalDate dueDate;
    float discountAmount;

    public RentalAgreement(
            Tool tool,
            ToolChargeDetail toolChargeDetail,
            LocalDate checkoutDate,
            LocalDate dueDate,
            float discountAmount
            ) {
        this.tool = tool;
        this.toolChargeDetail = toolChargeDetail;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.discountAmount = discountAmount;
    }

    public String generateRentalAgreement() {
        StringBuilder s = new StringBuilder();

        addFieldLn(s, "Tool code: ", tool.getToolCode());

        addFieldLn(s, "Tool type: ", tool.getToolType());

        addFieldLn(s, "Tool brand: ", tool.getBrand());

        long rentalDays = ChronoUnit.DAYS.between(checkoutDate, dueDate);
        addFieldLn(s, "Rental days: ", Long.toString(rentalDays));

        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("mm/dd/yy");
        addFieldLn(s, "Checkout date: ", checkoutDate.format(dtFormatter));

        addFieldLn(s, "Due date: ", dueDate.format(dtFormatter));

        addFieldLn(s, "Daily rental charge: $", toolChargeDetail.getDailyCharge().toString());

        addFieldLn(s, "Charge days: ", calcChargeDays());

        return s.toString();
    }

    private long calcChargeDays() {

    }

    private void addFieldLn(StringBuilder s, String field, String value) {
        s.append(field);
        s.append(value);
        s.append(System.lineSeparator());
    }
}
