package org.cmcderm.cm1225.Models;

import java.math.BigDecimal;

public class ToolChargeDetail {
    private String toolType;
    private BigDecimal dailyCharge;
    private boolean weekdayCharge;
    private boolean weekendCharge;
    private boolean holidayCharge;

    public ToolChargeDetail(String toolType, BigDecimal dailyCharge, boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge) {
        this.toolType = toolType;
        this.dailyCharge = dailyCharge;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }

    public String getToolType() {
        return this.toolType;
    }

    public BigDecimal getDailyCharge() {
        return this.dailyCharge;
    }

    public boolean getWeekdayCharge() {
        return this.weekdayCharge;
    }

    public boolean getWeekendCharge() {
        return this.weekendCharge;
    }

    public boolean getHolidayCharge() {
        return this.holidayCharge;
    }
}
