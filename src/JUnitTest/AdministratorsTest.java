package JUnitTest;

import CLI.Administrators;
import CLI.Customer;
import CLI.MenuItem;
import exceptions.InvalidLoginException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AdministratorsTest {
    private Administrators admin;

    @Before
    public void setUp() {
        // Create a customer with valid credentials
        admin = new Administrators("Admin", "12345678");
    }

    @Test
    public void testInvalidLogin() {
        boolean flag = false;

        // Attempting to login with invalid credentials
        try{
            admin.checkDetails("Wrong Password");
        }
        catch(InvalidLoginException e){
            flag=true;
        }

        // Check if flag is true
        assertTrue(flag);
    }

    @Test
    public void testValidLogin() {
        boolean flag = true;

        // Attempting to login with invalid credentials
        try{
            admin.checkDetails("12345678");
        }
        catch(InvalidLoginException e){
            flag=false;
        }

        // Check if flag is true
        assertTrue(flag);
    }
}
