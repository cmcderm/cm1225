package org.cmcderm.cm1225.Managers;

import org.cmcderm.cm1225.Models.Tool;
import org.cmcderm.cm1225.Models.ToolChargeDetail;

import java.util.List;

public class RentalManager {
    private List<Tool> tools;
    private List<ToolChargeDetail> toolChargeDetails;

    public RentalManager(List<Tool> tools, List<ToolChargeDetail> toolChargeDetails) {
        this.tools = tools;
        this.toolChargeDetails = toolChargeDetails;
    }

    public RentalAgreement generateRentalAgreement() {

    }
}
