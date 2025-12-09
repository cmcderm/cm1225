package org.cmcderm.cm1225.Managers;

import org.cmcderm.cm1225.Models.RentalAgreement;
import org.cmcderm.cm1225.Models.Tool;
import org.cmcderm.cm1225.Models.ToolChargeDetail;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class RentalManager {
    private List<Tool> tools;
    private List<ToolChargeDetail> toolChargeDetails;

    public RentalManager(List<Tool> tools, List<ToolChargeDetail> toolChargeDetails) {
        this.tools = tools;
        this.toolChargeDetails = toolChargeDetails;
    }

    public RentalAgreement createRentalAgreement(
            String toolCode,
            LocalDate checkoutDate,
            int rentalDays,
            BigDecimal discountAmount
    ) throws NoSuchFieldException {
        Optional<Tool> tool = tools.stream().filter(t -> t.getToolCode().equals(toolCode)).findAny();

        if (tool.isEmpty()) {
            throw new NoSuchFieldException("Tool code " + toolCode + " does not exist");
        }

        Optional<ToolChargeDetail> toolChargeDetail = toolChargeDetails.stream()
                .filter(t -> t.getToolType() == tool.get().getToolType())
                .findAny();

        if (toolChargeDetail.isEmpty()) {
            throw new NoSuchFieldException("Tool type " + tool.get().getToolType() + " has no Charge Details");
        }

        RentalAgreement rentalAgreement = new RentalAgreement(tool.get(), toolChargeDetail.get(), checkoutDate, rentalDays, discountAmount);

        return rentalAgreement;
    }
}
