package CLI;

import exceptions.InvalidLoginException;

import java.io.Serializable;
import java.util.Scanner;

abstract class User implements Serializable {

    protected String UserName;
    protected String Password;

    public User(){}

    public User(String UserName, String Password) {
        this.UserName = UserName;
        this.Password = Password;
    }

    public String getUsername() {
        return UserName;
    }

    public String getPassword() {
        return Password;
    }

    public void setUsername(String userName) {
        UserName = userName;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public static boolean login(){
        Common current;
        Scanner sc = new Scanner(System.in);
        System.out.println("\nTo Login as Customer press 1");
        System.out.println("To Login as Administrator press 2");
        System.out.println("To Exit press any other number");
        System.out.print("\nEnter your choice: ");
        String type = sc.nextLine();
        String username = "Administrator";
        String password;
        switch (type) {
            case "2":
                System.out.print("Input Password: ");
                password = sc.nextLine();
                try{
                    Administrators.checkDetails(password);
                }
                catch (InvalidLoginException e){
                    System.out.println(e.getMessage());
                    break;
                }
                System.out.println("Logged in");
                current = new Administrators(username, password);
                current.intrfce();
                break;
            case "1":
                System.out.print("Input Username: ");
                username = sc.nextLine();
                System.out.print("Input Password: ");
                password = sc.nextLine();
                try{
                    Customer.checkDetails(username, password);
                }
                catch(InvalidLoginException e){
                    System.out.println(e.getMessage());
                    break;
                }
                System.out.println("Logged in");
                Customer.getRegisteredCustomer(username).intrfce();
                break;
            default:
                System.out.println("Exiting\n");
                return false;
        }
        return true;
    }

    public static boolean signUp(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\nTo Sign Up as Customer press 1");
        System.out.println("To exit press anything else");
        System.out.print("\nEnter your choice: ");
        String type = sc.nextLine();
        String username;
        String password;
        switch (type) {
            case "1":
                System.out.print("Input Username: ");
                username = sc.nextLine();
                System.out.print("Input Password: ");
                password = sc.nextLine();
                if (Customer.usernameAvailable(username)) {
                    System.out.println("Logged in");
                    Customer newCustomer = new Customer(username, password);
                    newCustomer.intrfce();
                } else {
                    System.out.println("Username is already in use");
                }
                break;
            default:
                System.out.println("Exiting\n");
                return false;
        }
        return true;
    }

    //Runtime Polymorphism
    public void logout(User user){
        System.out.println("Logging out "+user.UserName);
    }

}