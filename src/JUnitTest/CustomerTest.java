package JUnitTest;

import CLI.Customer;
import CLI.MenuItem;
import exceptions.InvalidLoginException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CustomerTest {
    private Customer customer;
    private MenuItem unavailableItem;

    @Before
    public void setUp() {
        // Create a customer with valid credentials
        customer = new Customer("testUser", "123");

        // Creating MenuItem which is not available
        unavailableItem = new MenuItem("Unavailable Item", 10, "snacks", false);
    }

    @Test
    public void testAddUnavailableItemToCart() {
        PrintStream originalSystemOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Adding unavailable item
        customer.addToCart(unavailableItem);
        assertTrue(outputStream.toString().contains("Item is not available."));

        System.setOut(originalSystemOut);
    }

    @Test
    public void testInvalidLogin() {
        boolean flag = false;
        try {
            customer.checkDetails("wrongUser", "wrongPassword");
        } catch (InvalidLoginException e) {
            flag = true;
        }
        assertTrue(flag); // Expecting an exception for invalid login
    }

    public void testInvalidLthWrongCredentials() {
        boolean flag = false;
        try {
            customer.checkDetails("", "");
        } catch (InvalidLoginException e) {
            flag = true;
        }
        assertTrue(flag); // Expecting an exception for invalid login
    }

    @Test
    public void testValidLogin() {
        boolean flag = true;
        try {
            customer.checkDetails(customer.getUsername(), customer.getPassword());
        } catch (InvalidLoginException e) {
            flag = false;
        }
        assertTrue(flag); // Expecting no exception for valid login
    }

    @Test
    public void testEmptyLogin() {
        boolean flag = false;
        try {
            customer.checkDetails("", "");
        } catch (InvalidLoginException e) {
            flag = true;
        }
        assertTrue(flag); // Expecting an exception for invalid login
    }
}
