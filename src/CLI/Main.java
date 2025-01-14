package CLI;

import GUI.CanteenGUI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n\nByte Me");
        System.out.println("**********************************************************\n");
        MenuItem.loadMenu();
        Order.loadOrders();
        Customer.loadRegisteredCustomers();
        System.out.println("\n");

//password for the administrator is 12345678.
        String inp;
        Scanner sc = new Scanner(System.in);
        boolean flag = false;
        while (!flag) {
            System.out.println("For CLI press 1");
            System.out.println("For GUI press 2");
            System.out.println("To Exit press anything else");
            System.out.print("Enter your choice: ");
            inp = sc.nextLine();
            switch (inp) {
                case "1":
                    String choice;
                    boolean exit = false;
                    while (!exit){
                        System.out.println("\nTo Login press 1");
                        System.out.println("To Signup press 2");
                        System.out.println("To Exit press anything else");
                        System.out.print("\nEnter your choice: ");
                        choice=sc.nextLine();
                        switch (choice){
                            case "1":
                                while(User.login()){}
                                break;
                            case "2":
                                while(User.signUp()){}
                                break;
                            default:
                                MenuItem.saveMenu();
                                Order.saveOrders();
                                Customer.saveRegisteredCustomers();
//                                Order.saveOrdersToFile();
                                exit=true;
                        }
                    }
                    break;
                case "2":
                    CanteenGUI.GUI_main();
                    break;
                default:
                    flag = true;
            }
        }
        System.out.println("\nThank you for using our system.");
        System.out.println("**********************************************************\n\n");
    }
}