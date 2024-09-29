
import Model.FlightInformation;
import Controller.Manager;
import Model.Reservation;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import static org.testng.Assert.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class TestCase {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");

    String[] actions = new String[5];
    String[] data = new String[5];
    String[] results = new String[5];
    String[] notes = new String[5];

    @BeforeClass
    public void setUpClass() {
        System.out.println("Setting up test cases.");
    }

    @AfterClass
    public void tearDownClass() {
        System.out.println("Cleaning up after all test cases.");
        try {
            TestResultWriter.writeTestResults(actions, data, results, notes);
            System.out.println("Exported test results to Excel successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddValidReservation() throws Exception {
        LocalDateTime flightTime = LocalDateTime.parse("22/12/2024 10:00 AM", formatterTime);
        FlightInformation flight = new FlightInformation("NY0123", "A123", flightTime);
        LocalDate bookingDate = LocalDate.parse("21/12/2024", formatter);
        Reservation reservation = new Reservation("000001", "Nguyen Thanh An", "098312345678", "1001", bookingDate, flight);

        boolean isValid = reservation.getBookingID().equals("000001")
                && reservation.getCustomerName().equals("Nguyen Thanh An")
                && reservation.getPhoneNumber().equals("098312345678")
                && "1001".equals(reservation.getRoomNumber());

        actions[0] = "Add a valid reservation";
        data[0] = "000001 Nguyen Thanh An 098312345678 1001 21/12/2024";
        results[0] = isValid ? "Pass" : "Fail";
        notes[0] = isValid ? "Successfully added a new valid reservation." : "Reservation details did not match expected values.";
    }
    
    @Test
    public void testInvalidRoomNumber() throws Exception {
        LocalDateTime pickUp = LocalDateTime.parse("22/12/2024 10:00 AM", formatterTime);
        FlightInformation flight = new FlightInformation("NY0123", "A123", pickUp);
        LocalDate dateTime = LocalDate.parse("21/12/2024", formatter);
        try {
            new Reservation("000002", "Nguyen Van B", "098765432109", "-1", dateTime, flight);
            results[1] = "Fail";
            notes[1] = "No exception thrown for invalid room number.";
        } catch (IllegalArgumentException e) {
            results[1] = "Pass";
            notes[1] = "Invalid room number: Room number cannot be negative or zero.";
        }

        actions[1] = "Add reservation with invalid room number";
        data[1] = "000002 Nguyen Van B 098765432109 -1 21/12/2024";
    }

    @Test
    public void testInvalidBookingDate() throws Exception {
        LocalDateTime invalidFlightTime = LocalDateTime.parse("01/01/2020 10:00 AM", formatterTime);
        FlightInformation flight = new FlightInformation("NY0124", "A124", invalidFlightTime);
        LocalDate dateTime = LocalDate.parse("01/01/2020", formatter);
        try {
            new Reservation("000003", "Le Thi C", "098765432110", "1001", dateTime, flight);
            results[2] = "Fail";
            notes[2] = "No exception thrown for invalid booking date.";
        } catch (IllegalArgumentException e) {
            results[2] = "Pass";
            notes[2] = "Invalid booking date: Booking date cannot be in the past.";
        }

        actions[2] = "Add reservation with invalid booking date";
        data[2] = "000003 Le Thi C 098765432110 1002 01/01/2020";
    }

    @Test
    public void testDeleteReservation() throws Exception {
        Manager manager = new Manager();
        LocalDateTime flightTime = LocalDateTime.parse("22/12/2024 10:00 AM", formatterTime);
        FlightInformation flight = new FlightInformation("NY0123", "A123", flightTime);
        LocalDate bookingDate = LocalDate.parse("21/12/2024", formatter);
        manager.createReservation("000004", "Nguyen Thanh An", "098312345678", "1", bookingDate, flight);
        boolean isDeleted = manager.deleteReservation("000004");

        actions[3] = "Delete reservation";
        data[3] = "Booking ID: 000004";
        results[3] = isDeleted ? "Pass" : "Fail";
        notes[3] = isDeleted ? "Reservation deleted successfully." : "Failed to delete reservation.";

        assertTrue(isDeleted, "Expected reservation to be deleted successfully.");
    }
}
