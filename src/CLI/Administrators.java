package CLI;

import exceptions.InvalidLoginException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Administrators extends User implements Common{

    private static final String _password="12345678";
    public Administrators(String username, String password) {
        super(username, password);
    }

    public static boolean checkDetails(String password) throws InvalidLoginException {
        if (password.equals(_password)){
            return true;
        }
        throw new InvalidLoginException("Invalid password");
    }

    @Override
    public void displayFunc() {
        System.out.println("\nAdministrators Functionalities:");
        System.out.println("To manage Menu Press 1");
        System.out.println("To manage Orders Press 2");
        System.out.println("To generate Report Press 3");
        System.out.println("To logout from the system Press anything else");
    }

    @Override
    public void intrfce(){
        Scanner sc=new Scanner(System.in);
        while (true){
            displayFunc();
            System.out.print("\nInput Choice: ");
            String input=sc.nextLine();
            switch(input){
                case "1":
                    MenuManagement();
                    break;
                case "2":
                    OrderManagement();
                    break;
                case "3":
                    GenerateReport();
                    break;
                default:
                    this.logout(this);
                    return;
            }
        }
    }

    //Menu Management
    private void addItem() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter item name: ");
        String name = sc.nextLine();
        System.out.print("Enter item price: ");
        int price = Integer.parseInt(sc.nextLine());
        System.out.print("Enter item category (snacks/beverages/meals): ");
        String category = sc.nextLine().toLowerCase();
        System.out.print("Is the item available? (true/false): ");
        Boolean availability = Boolean.parseBoolean(sc.nextLine());
        MenuItem newItem = new MenuItem(name, price, category, availability);
        switch (category) {
            case "snacks":
                MenuItem.snacks.add(newItem);
                newItem.categorySet=MenuItem.snacks;
                break;
            case "beverages":
                MenuItem.beverages.add(newItem);
                newItem.categorySet=MenuItem.beverages;
                break;
            case "meals":
                MenuItem.meals.add(newItem);
                newItem.categorySet=MenuItem.meals;
                break;
            default:
                System.out.println("Invalid category. Item not added.");
                return;
        }

        while (true){
            System.out.print("To Add Key words for the item press 1, to skip press anything else: ");
            String input = sc.nextLine();
            if (input.equals("1")){
                System.out.print("Input Key Word: ");
                String key = sc.nextLine();
                newItem.AddKeyWord(key);
            }
            else{
                break;
            }
        }


        System.out.println("Item added successfully!");
    }

    private void updateItem() {
        ArrayList<MenuItem> menu=MenuItem.MenuWithIndex();

        Scanner sc=new Scanner(System.in);
        System.out.print("Enter index of item to update: ");
        int index = sc.nextInt();
        sc.nextLine();

        if (index<0 || index>=menu.size()){
            System.out.println("Invalid index.");
            return;
        }

        MenuItem item=menu.get(index);
        item.categorySet.remove(item);

        System.out.print("To assign new price press 1 to keep same price press anything else: ");
        String input=sc.nextLine();
        if (input.equals("1")){
            System.out.print("Enter new price: ");
            int newPrice=sc.nextInt();
            sc.nextLine();
            if (newPrice<0){
                System.out.println("Invalid price.");
                return;
            }
            item.setPrice(newPrice);
        }

        System.out.println("To assign availability press 1 to keep same availability press anything else: ");
        input=sc.nextLine();
        if (input.equals("1")){
            System.out.print("Enter 0 for false and anything else for true: ");
            String availability=sc.nextLine();
            if (availability.equals("0")){
                item.setAvailability(false);
            }
            else{
                item.setAvailability(true);
            }
        }
        item.categorySet.add(item);

        System.out.println("Item updated successfully!");
    }

    private void removeItem() {
        ArrayList<MenuItem> menu=MenuItem.MenuWithIndex();

        Scanner sc=new Scanner(System.in);
        System.out.print("Enter index of item to remove: ");
        int index = sc.nextInt();
        sc.nextLine();

        if (index<0 || index>=menu.size()){
            System.out.println("Invalid index.");
            return;
        }

        MenuItem item=menu.get(index);

       item.categorySet.remove(item);
       for (String s: item.keywords){
           try{
               MenuItem.keyWord.get(s).remove(item);
           }
           catch (Exception e){}
       }

        ArrayList<Order> pending = new ArrayList<>();

        while (!Order.pendingOrders.isEmpty()) {
            Order order = Order.pendingOrders.poll();
            if (order.orderItems.containsKey(item)) {
                order.setStatus(Order.Status.DENIED);
                Order.toRefundOrders.add(order);
                continue;
            }
            pending.add(order);
        }

        for (Order order : pending) {
            Order.pendingOrders.add(order);
        }

        System.out.println("Item removed successfully, and pending orders updated.");
    }

    private void MenuManagement(){
        Scanner sc=new Scanner(System.in);
        String input;
        while (true){
            System.out.println("To view Menu Press 1");
            System.out.println("To add New Items to Menu Press 2");
            System.out.println("To update details of Menu Items Press 3");
            System.out.println("To remove Items from the Menu Press 4");
            System.out.println("To exit press anything else");
            System.out.print("Input Choice: ");
            input=sc.nextLine();
            switch(input){
                case "1":
                    MenuItem.viewMenu();
                    break;
                case "2":
                    addItem();
                    break;
                case "3":
                    updateItem();
                    break;
                case "4":
                    removeItem();
                    break;
                default:
                    return;
            }
        }
    }

    //Order Management
    private void viewPendingOrders(){
        if (Order.pendingOrders.isEmpty()){
            System.out.println("No pending orders found.");
            return;
        }
        ArrayList<Order> pending = new ArrayList<>();

        while (!Order.pendingOrders.isEmpty()) {
            Order order = Order.pendingOrders.poll();
            pending.add(order);
            System.out.println(order);
        }

        for (Order order : pending) {
            Order.pendingOrders.add(order);
        }
    }

    private void updateOrderStatus(){
        if (Order.processedOrders.isEmpty()) {
            System.out.println("No processed orders.");
            return;
        }
        int index=0;
        for (Order order : Order.processedOrders) {
            System.out.println(index+". "+order);
        }
        System.out.print("Enter index of order to update: ");
        Scanner sc=new Scanner(System.in);
        int idx=sc.nextInt();
        sc.nextLine();
        if (idx<0 || idx>=Order.processedOrders.size()){
            System.out.println("Invalid index.");
            return;
        }
        Order order=Order.processedOrders.get(idx);
        System.out.print("Enter new order status (OUT_FOR_DELIVERY, COMPLETED): ");
        String status=sc.nextLine();
        try{
            order.setStatus(Order.Status.valueOf(status));
            System.out.println("Updated order status successfully.");
        }
        catch(Exception e){
            System.out.println("Invalid order status.");
        }
    }

    private void processRefunds(){
        if (Order.toRefundOrders.isEmpty()){
            System.out.println("No orders to refund.");
            return;
        }
        int index=0;
        for (Order order: Order.toRefundOrders){
            System.out.println(index+". "+order);
            index++;
        }
        System.out.println("Enter index of item to refund: ");
        Scanner sc=new Scanner(System.in);
        int input=sc.nextInt();
        sc.nextLine();
        if (input<0 || input>=index){
            System.out.println("Invalid index.");
            return;
        }
        Order order=Order.toRefundOrders.get(input);
        Order.toRefundOrders.remove(order);
        order.setStatus(Order.Status.REFUNDED);
    }

    private void handleOrder(){
        if (Order.pendingOrders.isEmpty()){
            System.out.println("No pending orders");
            return;
        }
        Order order = Order.pendingOrders.poll();
        System.out.println(order);
        System.out.println();
        System.out.println("To prepare order press 1");
        System.out.println("To deny order press anything else");
        System.out.print("Input Choice: ");
        Scanner sc=new Scanner(System.in);
        String input=sc.nextLine();
        if (input.equals("1")){
            order.setStatus(Order.Status.PREPARING);
            Order.processedOrders.add(order);
            if (order.getSpecialRequest().isEmpty()){
                System.out.println("No special request for this order");
            }
            else{
                System.out.println("Order has special request: "+order.getSpecialRequest());
            }
        }
        else{
            order.setStatus(Order.Status.DENIED);
            Order.toRefundOrders.add(order);
        }
    }

    private void OrderManagement(){
        Scanner sc=new Scanner(System.in);
        String input;
        while (true){
            System.out.println("To view Pending Orders Press 1");
            System.out.println("To Update Order Status Press 2");
            System.out.println("To Process Refunds Press 3");
            System.out.println("To Handle Orders by Priority with their special request Press 4");
            System.out.println("To exit press anything else");
            System.out.print("Input Choice: ");
            input=sc.nextLine();
            switch(input){
                case "1":
                    viewPendingOrders();
                    break;
                case "2":
                    updateOrderStatus();
                    break;
                case "3":
                    processRefunds();
                    break;
                case "4":
                    handleOrder();
                    break;
                default:
                    return;
            }
        }

    }

    //Report Generation
    private void GenerateReport(){
        int totalSales=0;
        int totalOrders=Order.processedOrders.size();
        HashMap<MenuItem,Integer> itemsSold = new HashMap<>();
        System.out.println("Orders: ");
        for (Order order: Order.processedOrders){
            totalSales+=order.getTotal();
            System.out.println(order);
            for (HashMap.Entry<MenuItem, Integer> entry : order.orderItems.entrySet()) {
                MenuItem item = entry.getKey();
                int quantity = entry.getValue();
                itemsSold.put(item, itemsSold.getOrDefault(item, 0) + quantity);
            }
        }
        System.out.println();
        System.out.println("Total Sales: "+totalSales);
        System.out.println("Total Orders: "+totalOrders);
        System.out.println("Most Popular Items: ");
        itemsSold.entrySet().stream().sorted((e1,e2)->e2.getValue().compareTo(e1.getValue())).forEach(e-> System.out.println(e.getKey()+": "+e.getValue()));
    }
}
