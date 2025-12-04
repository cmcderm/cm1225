package org.cmcderm.cm1225.Models;

public class Tool {
    private String toolCode;
    private String toolType;
    private String brand;

    public Tool(String toolCode, String toolType, String brand) {
        this.toolCode = toolCode;
        this.toolType = toolType;
        this.brand = brand;
    }

    public String getToolCode() {
        return this.toolCode;
    }

    public String getToolType() {
        return this.toolType;
    }

    public String getBrand() {
        return this.brand;
    }
}
