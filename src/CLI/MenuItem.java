package CLI;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;

public class MenuItem implements Serializable{

    private static final long serialVersionUID = 1L;

    public static TreeSet<MenuItem> snacks = new TreeSet<>(new MenuItemComparator());
    public static TreeSet<MenuItem> beverages = new TreeSet<>(new MenuItemComparator());
    public static TreeSet<MenuItem> meals = new TreeSet<>(new MenuItemComparator());
    public static HashMap<String, TreeSet<MenuItem>> keyWord = new HashMap<>();
    private String name;
    private int price;
    private String category;
    private Boolean availability;
    public TreeSet<MenuItem> categorySet;
    public ArrayList<String> reviews;
    public ArrayList<String> keywords;

    public MenuItem(String name, int price, String category, Boolean availability) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.availability = availability;
        this.reviews = new ArrayList<>();
        this.keywords = new ArrayList<>();
    }

    @Override
    public String toString() {
        String item=name+" "+price+" "+category+" ";
        if (availability) {
            item+="available";
        }
        else {
            item+="not available";
        }
        return item;
    }

    public static class MenuItemComparator implements Comparator<MenuItem>, Serializable {
        @Override
        public int compare(MenuItem o1, MenuItem o2) {
            // First compare by price, then by name
            int priceComparison = Integer.compare(o1.getPrice(), o2.getPrice());
            if (priceComparison == 0) {
                return o1.getName().compareTo(o2.getName());
            }
            return priceComparison;
        }
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public void AddKeyWord(String keyword) {
        if (!keyWord.containsKey(keyword)) {
            keyWord.put(keyword, new TreeSet<>(Comparator.comparing(MenuItem::getCategory).thenComparing(MenuItem::getPrice).thenComparing(MenuItem::getName)));
        }
        keyWord.get(keyword).add(this);
        keywords.add(keyword);
    }

    public static void viewMenu(){
        System.out.println("Snacks: ");
        for (MenuItem item : snacks) {
            System.out.println(item);
        }
        System.out.println();
        System.out.println("Beverages: ");
        for (MenuItem item : beverages) {
            System.out.println(item);
        }
        System.out.println();
        System.out.println("Meals: ");
        for (MenuItem item : meals) {
            System.out.println(item);
        }
        System.out.println();
    }

    public static ArrayList<MenuItem> MenuWithIndex() {
        ArrayList<MenuItem> allItems = new ArrayList<>();
        int index = 0;

        System.out.println("Snacks:");
        for (MenuItem item : snacks) {
            System.out.println(index + ": " + item);
            allItems.add(item);
            index++;
        }

        System.out.println("Beverages:");
        for (MenuItem item : beverages) {
            System.out.println(index + ": " + item);
            allItems.add(item);
            index++;
        }

        System.out.println("Meals:");
        for (MenuItem item : meals) {
            System.out.println(index + ": " + item);
            allItems.add(item);
            index++;
        }

        return allItems;
    }

    public static ArrayList<MenuItem> Menu() {
        ArrayList<MenuItem> menu = new ArrayList<>();
        for (MenuItem item : snacks) {
            menu.add(item);
        }
        for (MenuItem item : beverages) {
            menu.add(item);
        }
        for (MenuItem item : meals) {
            menu.add(item);
        }
        return menu;
    }

//    public static void saveMenuToFile() {
//        try{
//            PrintWriter pw = new PrintWriter(new FileWriter("menu.txt"));
//            if (!snacks.isEmpty()) {
//                pw.println("[Snacks], , ");
//            }
//            for (MenuItem item : MenuItem.snacks) {
//                pw.println(item.getName() + "," + item.getPrice() + "," + item.getAvailability());
//            }
//            if (!beverages.isEmpty()) {
//                pw.println("[Beverages], , ");
//            }
//            for (MenuItem item : MenuItem.beverages) {
//                pw.println(item.getName() + "," + item.getPrice() + "," + item.getAvailability());
//            }
//            if (!meals.isEmpty()) {
//                pw.println("[Meals], , ");
//            }
//            for (MenuItem item : MenuItem.meals) {
//                pw.println(item.getName() + "," + item.getPrice() + "," + item.getAvailability());
//            }
//            pw.close();
//        }
//        catch (IOException e) {
//            System.out.println("Error saving menu data.");
//        }
//    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();  // Serialize the non-static fields
        out.writeObject(snacks);   // Explicitly serialize the static fields
        out.writeObject(beverages);
        out.writeObject(meals);
//        out.writeObject(keyWord);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();  // Deserialize the non-static fields
        snacks = (TreeSet<MenuItem>) in.readObject();  // Deserialize the static fields
        beverages = (TreeSet<MenuItem>) in.readObject();
        meals = (TreeSet<MenuItem>) in.readObject();
//        keyWord = (HashMap<String, TreeSet<MenuItem>>) in.readObject();
    }

    public static void saveMenu() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Data/menu.ser"))) {
            out.writeObject(snacks);
            out.writeObject(beverages);
            out.writeObject(meals);
//            out.writeObject(keyWord);
            System.out.println("Menu saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving menu data.");
            e.printStackTrace();
        }
    }

    public static void loadMenu() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("Data/menu.ser"))) {
            snacks = (TreeSet<MenuItem>) in.readObject();
            beverages = (TreeSet<MenuItem>) in.readObject();
            meals = (TreeSet<MenuItem>) in.readObject();
//            keyWord = (HashMap<String, TreeSet<MenuItem>>) in.readObject();
            System.out.println("Menu loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading menu data.");
            e.printStackTrace();
        }
    }
}
