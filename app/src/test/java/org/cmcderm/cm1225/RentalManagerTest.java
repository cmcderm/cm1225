package org.cmcderm.cm1225;

import org.cmcderm.cm1225.Managers.RentalManager;
import org.cmcderm.cm1225.Models.RentalAgreement;
import org.cmcderm.cm1225.Models.Tool;
import org.cmcderm.cm1225.Models.ToolChargeDetail;
import org.junit.Test;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class RentalManagerTest {
    List<Tool> tools;
    List<ToolChargeDetail> toolChargeDetails;

    public RentalManagerTest() {
        tools = List.of(
                new Tool("CHNS", "Chainsaw", "Stihl"),
                new Tool("LADW", "Ladder", "Werner"),
                new Tool("JAKD", "Jackhammer", "DeWalt"),
                new Tool("JAKR", "Jackhammer", "Ridgid")
        );
        toolChargeDetails = List.of(
                new ToolChargeDetail("Ladder", BigDecimal.valueOf(1.99), true, true, false),
                new ToolChargeDetail("Chainsaw", BigDecimal.valueOf(1.49), true, false, true),
                new ToolChargeDetail("Jackhammer", BigDecimal.valueOf(2.99), true, false, false)
        );
    }

    // Test 1
    @Test
    public void RentalManager_When_RentalDaysOver100_ShouldThrowInvalidParameterException() {
        RentalManager rentalManager = new RentalManager( tools, toolChargeDetails );

        assertThrows(InvalidParameterException.class, () ->
            rentalManager.createRentalAgreement(
                    "JAKR",
                    LocalDate.of(2015, 9, 3),
                    5,
                    BigDecimal.valueOf(101)
            )
        );
    }

    // Test 2
    @Test
    public void RentalManager_When_LadderThreeDays10Percent() {
        RentalManager rentalManager = new RentalManager( tools, toolChargeDetails );

        try {
            RentalAgreement rental = rentalManager.createRentalAgreement(
                    "LADW",
                    LocalDate.of(2020, 7, 2),
                    3,
                    BigDecimal.valueOf(10)
            );
            String result = rental.generate();

            System.out.println(result);

            assertEquals("2", getField(result, "Charge days"));
            assertEquals("$3.59", getField(result, "Final charge"));
        } catch (Exception ignored) {}
    }

    // Test 3
    @Test
    public void RentalManager_When_ChainsawFiveDays25Percent() {
        RentalManager rentalManager = new RentalManager( tools, toolChargeDetails );

        try {
            RentalAgreement rental = rentalManager.createRentalAgreement(
                    "CHNS",
                    LocalDate.of(2015, 7, 2),
                    5,
                    BigDecimal.valueOf(25)
            );
            String result = rental.generate();

            System.out.println(result);

            assertEquals("3", getField(result, "Charge days"));
            assertEquals("$3.36", getField(result, "Final charge"));
        } catch (Exception ignored) {}
    }

    // Test 4
    @Test
    public void RentalManager_When_JackhammerSixDays0Percent() {
        RentalManager rentalManager = new RentalManager( tools, toolChargeDetails );

        try {
            RentalAgreement rental = rentalManager.createRentalAgreement(
                    "CHNS",
                    LocalDate.of(2015, 9, 3),
                    6,
                    BigDecimal.ZERO
            );
            String result = rental.generate();

            System.out.println(result);

            assertEquals("4", getField(result, "Charge days"));
            assertEquals("$5.96", getField(result, "Final charge"));
        } catch (Exception ignored) {}
    }

    // Test 5
    @Test
    public void RentalManager_When_JackhammerNineDays0Percent() {
        RentalManager rentalManager = new RentalManager( tools, toolChargeDetails );

        try {
            RentalAgreement rental = rentalManager.createRentalAgreement(
                    "JAKR",
                    LocalDate.of(2015, 7, 2),
                    9,
                    BigDecimal.ZERO
            );
            String result = rental.generate();

            System.out.println(result);


            assertEquals("6", getField(result, "Charge days"));
            assertEquals("$17.94", getField(result, "Final charge"));
        } catch (Exception ignored) {}
    }

    // Test 6
    @Test
    public void RentalManager_When_JackhammerFourDays50Percent() {
        RentalManager rentalManager = new RentalManager( tools, toolChargeDetails );

        try {
            RentalAgreement rental = rentalManager.createRentalAgreement(
                    "JAKR",
                    LocalDate.of(2015, 7, 2),
                    4,
                    BigDecimal.valueOf(25)
            );
            String result = rental.generate();

            System.out.println(result);

            assertEquals("1", getField(result, "Charge days"));
            assertEquals("$2.25", getField(result, "Final charge"));
        } catch (Exception ignored) {}
    }

    private String getField(String s, String fieldName) {
        var fields = s.split("\n");
        for (String f : fields) {
            var keyVal = f.split(": ");

            if (keyVal[0].equals(fieldName)) {
                return keyVal[1];
            }
        }

        return null;
    }

}
