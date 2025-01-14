package CLI;

import exceptions.InvalidLoginException;

import java.io.*;
import java.util.*;

public class Customer extends User implements Common, Serializable {

    private static final long serialVersionUID = 1L;

    public static HashMap<String, Customer> registeredCustomers = new HashMap<>();
    public Boolean VIP;
    private HashMap<MenuItem, Integer> cart = new HashMap<>();
    private ArrayList<Order> orders = new ArrayList<>();

    public Customer(String Username, String Password) {
        super(Username, Password);
        registeredCustomers.put(Username, this);
        this.VIP = false;
//        System.out.println("Username: " + this.UserName + " Password: " + this.Password);
    }

    public Customer(){
        super();
        this.VIP = false;
    }

    public static boolean checkDetails(String username, String password) throws InvalidLoginException {
        if (registeredCustomers.containsKey(username) && registeredCustomers.get(username).Password.equals(password)) {
            return true;
        }
        throw new InvalidLoginException("Invalid Username or Password");
    }

    public Boolean getVIP() {
        return VIP;
    }

    public void setVIP(Boolean VIP) {
        this.VIP = VIP;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public static Customer getRegisteredCustomer(String username) {
        return registeredCustomers.get(username);
    }

    public static boolean usernameAvailable(String username) {
        return !registeredCustomers.containsKey(username);
    }

    @Override
    public void displayFunc() {
        System.out.println("To Browse Menu and add to cart press 1");
        System.out.println("To do Cart Operations press 2");
        System.out.println("To Track Orders press 3");
        System.out.println("To check or upgrade Customer type press 4");
        System.out.println("To give or view item reviews press 5");
        System.out.println("To exit press anything else");
        System.out.print("Input Choice: ");
    }

    @Override
    public void intrfce() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            displayFunc();
            String toDo = sc.nextLine();
            switch (toDo) {
                case "1":
                    BrowseMenu();
                    break;
                case "2":
                    CartOperations();
                    break;
                case "3":
                    TrackOrders();
                    break;
                case "4":
                    CustomerType();
                    break;
                case "5":
                    ItemReview();
                    break;
                default:
                    this.logout(this);
                    return;
            }
        }
    }

    //Browse Menu
    private void searchMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter keyword to search for items: ");
        String keyword = sc.nextLine();
        ArrayList<MenuItem> menu;
        if (MenuItem.keyWord.containsKey(keyword)) {
            menu = new ArrayList<>(MenuItem.keyWord.get(keyword));
            displayToAddInCart(menu);
        } else {
            System.out.println("No items found for the keyword.");
        }
    }

    private void printCategory() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter category to filter (snacks, beverages, meals): ");
        String category = sc.nextLine();
        ArrayList<MenuItem> menu;
        switch (category) {
            case "snacks":
                menu = new ArrayList<>(MenuItem.snacks);
                break;
            case "beverages":
                menu = new ArrayList<>(MenuItem.beverages);
                break;
            case "meals":
                menu = new ArrayList<>(MenuItem.meals);
                break;
            default:
                System.out.println("Invalid category.");
                return;
        }
        System.out.println("Filtered by category: ");
        displayToAddInCart(menu);
    }

    private void sortByPrice() {
        System.out.print("To sort in descending order press 1, for ascending press anything else: ");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        ArrayList<MenuItem> menu = MenuItem.Menu();
        if (input.equals("1")) {
            menu.sort(Comparator.comparing(MenuItem::getPrice).reversed());
        } else {
            menu.sort(Comparator.comparing(MenuItem::getPrice));
        }
        System.out.println("Sorted Menu Items by Price:");
        displayToAddInCart(menu);
    }

    private void displayToAddInCart(ArrayList<MenuItem> menu) {
        int index = 0;
        for (MenuItem item : menu) {
            System.out.println(index + ". " + item);
            index++;
        }
        String input;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("To add to cart press 1, to return press anything else: ");
            input = sc.nextLine();
            if (input.equals("1")) {
                System.out.print("Enter index of the item: ");
                index = sc.nextInt();
                sc.nextLine();
                if (index < 0 || index >= menu.size()) {
                    System.out.println("Invalid index.");
                    return;
                }
                addToCart(menu.get(index));
            } else {
                return;
            }
        }
    }

    public void addToCart(MenuItem item) {
        if (!item.getAvailability()) {
            System.out.println("Item is not available.");
            return;
        }
        System.out.print("Enter Quantity: ");
        Scanner sc = new Scanner(System.in);
        int quantity = sc.nextInt();
        sc.nextLine();
        if (quantity < 0) {
            System.out.println("Invalid Quantity.");
            return;
        }
        if (cart.containsKey(item)) {
            int q = cart.get(item);
            q += quantity;
            cart.put(item, q);
        } else {
            cart.put(item, quantity);
        }
        System.out.println("Added to cart");
    }

    private void BrowseMenu() {
        Scanner sc = new Scanner(System.in);
        String input;
        while (true) {
            System.out.println("To view all Items Press 1");
            System.out.println("To search for MenuItem Press 2");
            System.out.println("To filter by category Press 3");
            System.out.println("To sort by Price Press 4");
            System.out.println("To exit press anything else");
            System.out.println("Input Choice: ");
            input = sc.nextLine();
            switch (input) {
                case "1":
                    displayToAddInCart(MenuItem.Menu());
                    break;
                case "2":
                    searchMenu();
                    break;
                case "3":
                    printCategory();
                    break;
                case "4":
                    sortByPrice();
                    break;
                default:
                    return;
            }
        }
    }

    //Cart Operations
    private void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        System.out.println("Items in cart:");
        int index = 0;
        for (MenuItem item : cart.keySet()) {
            System.out.println(index + ". " + item + ": " + cart.get(item) + "(quantity)");
            index++;
        }
    }

    private void updateCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        viewCart();
        System.out.print("Enter index of item you want to update: ");
        Scanner sc = new Scanner(System.in);
        int index = sc.nextInt();
        sc.nextLine();
        if (index < 0 || index >= cart.size()) {
            System.out.println("Invalid index.");
            return;
        }
        ArrayList<MenuItem> myCart = new ArrayList<>(cart.keySet());
        MenuItem item = myCart.get(index);
        System.out.print("Enter new quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();
        if (quantity <= 0) {
            cart.remove(item);
            System.out.println("Item removed from cart");
        } else {
            cart.put(item, quantity);
            System.out.println("Quantity updated");
        }
    }

    private void removeCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        viewCart();
        System.out.print("Enter index of item you want to remove: ");
        Scanner sc = new Scanner(System.in);
        int index = sc.nextInt();
        sc.nextLine();
        if (index < 0 || index >= cart.size()) {
            System.out.println("Invalid index.");
            return;
        }
        ArrayList<MenuItem> myCart = new ArrayList<>(cart.keySet());
        MenuItem item = myCart.get(index);
        cart.remove(item);
        System.out.println("Item removed");
    }

    private int cartTotal() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return 0;
        }
        int total = 0;
        ArrayList<MenuItem> myCart = new ArrayList<>(cart.keySet());
        for (MenuItem item : myCart) {
            total += (item.getPrice() * (cart.get(item)));
        }
        System.out.println("Total: " + total);
        return total;
    }

    private void checkout() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        int total = cartTotal();
        System.out.print("Enter payment details: ");
        Scanner sc = new Scanner(System.in);
        String details = sc.nextLine();
        System.out.print("Enter special request if any else 1: ");
        String request = sc.nextLine();
        if (request.equals("1")) {
            Order order = new Order("", VIP, new HashMap<>(cart), total);
            orders.add(order);
            Order.pendingOrders.add(order);
        } else {
            Order order = new Order(request, VIP, new HashMap<>(cart), total);
            orders.add(order);
            Order.pendingOrders.add(order);
        }
        cart.clear();
    }

    private void CartOperations() {
        Scanner sc = new Scanner(System.in);
        String input;
        while (true) {
            System.out.println("To view items in cart Press 1");
            System.out.println("To Modify Quantities Press 2");
            System.out.println("To Remove Items Press 3");
            System.out.println("To view total Press 4");
            System.out.println("To checkout Press 5");
            System.out.println("To exit press anything else");
            System.out.println("Input Choice: ");
            input = sc.nextLine();
            switch (input) {
                case "1":
                    viewCart();
                    break;
                case "2":
                    updateCart();
                    break;
                case "3":
                    removeCart();
                    break;
                case "4":
                    cartTotal();
                    break;
                case "5":
                    checkout();
                    break;
                default:
                    return;
            }
        }
    }

    //Order Tracking
    private void viewOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }
        int index = 0;
        for (Order order : orders) {
            System.out.println(index + ". " + order);
            index++;
        }
    }

    private void viewStatus() {
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }
        viewOrders();
        System.out.print("Enter index of order to see status of: ");
        Scanner sc = new Scanner(System.in);
        int index = sc.nextInt();
        sc.nextLine();
        if (index < 0 || index >= orders.size()) {
            System.out.println("Invalid index.");
            return;
        }
        System.out.println(orders.get(index).getStatus());
    }

    private void cancelOrder() {
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }
        viewOrders();
        System.out.print("Enter index of order to see status of: ");
        Scanner sc = new Scanner(System.in);
        int index = sc.nextInt();
        sc.nextLine();
        if (index < 0 || index >= orders.size()) {
            System.out.println("Invalid index.");
            return;
        }
        Order order = orders.get(index);
        if (order.getStatus().equals(Order.Status.PENDING)) {
            order.setStatus(Order.Status.CANCELLED);
            Order.pendingOrders.remove(order);
            Order.toRefundOrders.add(order);
        } else {
            System.out.println("Order cannot be cancelled.");
        }
    }

    private void TrackOrders() {
        Scanner sc = new Scanner(System.in);
        String input;
        while (true) {
            System.out.println("To view order status Press 1");
            System.out.println("To cancel order Press 2");
            System.out.println("To view order history Press 3");
            System.out.println("To exit press anything else");
            System.out.println("Input Choice: ");
            input = sc.nextLine();
            switch (input) {
                case "1":
                    viewStatus();
                    break;
                case "2":
                    cancelOrder();
                    break;
                case "3":
                    viewOrders();
                    break;
                default:
                    return;
            }
        }
    }

    //Customer Type
    private void CustomerType() {
        if (this.VIP) {
            System.out.println("Thank You for being a VIP Customer");
            return;
        }
        System.out.print("To upgrade to VIP Customer press 1, to return press anything else: ");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        if (input.equals("1")) {
            this.VIP = true;
            System.out.println("You have become a VIP customer any orders from now will be given priority.");
        }
    }

    //MenuItem Reviews
    private void giveReview() {
        if (orders.isEmpty()) {
            System.out.println("You have not ordered any item yet.");
            return;
        }
        Set<MenuItem> oItems = new HashSet<>();
        for (Order order : orders) {
            for (MenuItem item : order.orderItems.keySet()) {
                oItems.add(item);
            }
        }
        ArrayList<MenuItem> orderedItems = new ArrayList<>(oItems);
        int index = 0;
        for (MenuItem item : orderedItems) {
            System.out.println(index + ". " + item);
            index++;
        }
        System.out.print("Enter index of item you want to give review for: ");
        Scanner sc = new Scanner(System.in);
        index = sc.nextInt();
        sc.nextLine();
        if (index < 0 || index >= orderedItems.size()) {
            System.out.println("Invalid index.");
            return;
        }
        MenuItem item = orderedItems.get(index);
        System.out.print("Enter Review: ");
        String review = sc.nextLine();
        item.reviews.add(review);
    }

    private void viewReview() {
        ArrayList<MenuItem> menu = MenuItem.MenuWithIndex();
        System.out.println("Enter index of item you want to read reviews of: ");
        Scanner sc = new Scanner(System.in);
        int index = sc.nextInt();
        sc.nextLine();
        if (index < 0 || index >= menu.size()) {
            System.out.println("Invalid index.");
            return;
        }
        MenuItem item = menu.get(index);
        if (item.reviews.isEmpty()) {
            System.out.println("No reviews currently.");
            return;
        }
        System.out.println("Reviews:");
        for (String s : item.reviews) {
            System.out.println(s);
        }
    }

    private void ItemReview() {
        Scanner sc = new Scanner(System.in);
        String input;
        while (true) {
            System.out.println("To give item reviews Press 1");
            System.out.println("To view item reviews Press 2");
            System.out.println("To exit press anything else");
            System.out.println("Input Choice: ");
            input = sc.nextLine();
            switch (input) {
                case "1":
                    giveReview();
                    break;
                case "2":
                    viewReview();
                    break;
                default:
                    return;
            }
        }
    }

    // Serialization and Deserialization of registeredCustomers
    private void writeObject(ObjectOutputStream out) throws IOException {
//        System.out.println("Serializing customer: "+UserName+" "+ Password);
        out.defaultWriteObject(); // Serialize non-static fields
        out.writeUTF(UserName);
        out.writeUTF(Password);
        out.writeObject(registeredCustomers); // Serialize static field manually
    }

    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // Deserialize non-static fields
        UserName = in.readUTF();
        Password = in.readUTF();
        registeredCustomers = (HashMap<String, Customer>) in.readObject(); // Deserialize static field manually
//        System.out.println("Deserializing customer: "+UserName+" "+ Password);
    }

    // Save registeredCustomers to a file
    public static void saveRegisteredCustomers() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Data\\customers.ser"))) {
            out.writeObject(registeredCustomers); // Serialize the map
            System.out.println("Customer data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving customer data.");
            e.printStackTrace();
        }
    }

    // Load registeredCustomers from a file
    @SuppressWarnings("unchecked")
    public static void loadRegisteredCustomers() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("Data\\customers.ser"))) {
            registeredCustomers = (HashMap<String, Customer>) in.readObject(); // Deserialize the map
            System.out.println("Customer data loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading customer data.");
            e.printStackTrace();
        }
    }
}
