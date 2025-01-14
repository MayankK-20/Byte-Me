package CLI;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum Status { PENDING, PREPARING, OUT_FOR_DELIVERY, COMPLETED, CANCELLED, REFUNDED, DENIED }

    public static ArrayList<Order> processedOrders = new ArrayList<>();
    public static PriorityQueue<Order> pendingOrders=new PriorityQueue<>(new OrderComparator());
    public static ArrayList<Order> toRefundOrders = new ArrayList<>();

    public static class OrderComparator implements java.util.Comparator<Order>, Serializable {
        @Override
        public int compare(Order o1, Order o2) {
            if (Boolean.compare(o2.VIP, o1.VIP) != 0) {
                return Boolean.compare(o2.VIP, o1.VIP);
            }
            return Integer.compare(o1.ID, o2.ID);
        }
    }

    private static int orderNum=0;

    private int ID;
    private String specialRequest;
    private Status status;
    private boolean VIP;
    public HashMap<MenuItem,Integer> orderItems;
    private int total;

    public Order(String specialRequest, boolean VIP, HashMap<MenuItem,Integer> orderItems,int total) {
        this.ID=orderNum++;
        this.specialRequest = specialRequest;
        this.status=Status.PENDING;
        this.VIP = VIP;
        this.orderItems=orderItems;
        this.total=total;
    }

    public Order(int ID, String specialRequest, boolean VIP, HashMap<MenuItem,Integer> orderItems,int total, String status) {
        this.ID=ID;
        this.specialRequest = specialRequest;
        this.VIP = VIP;
        this.orderItems=orderItems;
        this.total=total;
        this.status=Status.valueOf(status);
    }

    public int getID() {
        return ID;
    }

    public boolean isVIP() {
        return VIP;
    }

    public HashMap<MenuItem,Integer> getOrderItems() {
        return orderItems;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public int getTotal() {
        return total;
    }

    @Override
    public String toString() {
        String order = "ID: "+this.ID+", ";
        order += " Status: "+this.status+", ";
        order += " Special Request: "+this.specialRequest+", ";
        if (this.VIP){
            order += " VIP, ";
        }
        order += " Order Items: "+this.orderItems;
        return order;
    }

//    public static void saveOrdersToFile() {
//        try{
//            PrintWriter pw = new PrintWriter(new FileWriter("order.txt"));
//            if (!processedOrders.isEmpty()){
//                pw.println("Processed Orders:, , ");
//            }
//            for (Order order : processedOrders) {
//                String s=order.ID+",";
//                for (MenuItem item: order.orderItems.keySet()){
//                    s+=item.getName()+"("+order.orderItems.get(item)+"). ";
//                }
//                s+=(","+order.status);
//                pw.println(s);
//            }
//            if (!pendingOrders.isEmpty()){
//                pw.println("Pending Orders:, , ");
//            }
//            for (Order order : pendingOrders) {
//                String s=order.ID+",";
//                for (MenuItem item: order.orderItems.keySet()){
//                    s+=item.getName()+"("+order.orderItems.get(item)+"). ";
//                }
//                s+=(","+order.status);
//                pw.println(s);
//            }
//            if (!toRefundOrders.isEmpty()){
//                pw.println("To Refund Orders:, , ");
//            }
//            for (Order order : toRefundOrders) {
//                String s=order.ID+",";
//                for (MenuItem item: order.orderItems.keySet()){
//                    s+=item.getName()+"("+order.orderItems.get(item)+"). ";
//                }
//                s+=(","+order.status);
//                pw.println(s);
//            }
//            pw.close();
//        }
//        catch (IOException e) {
//            System.out.println("Error saving menu data.");
//        }
//    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject(); // Serialize the non-static fields
        out.writeObject(processedOrders); // Serialize the static field processedOrders
        out.writeObject(pendingOrders); // Serialize the static field pendingOrders
        out.writeObject(toRefundOrders); // Serialize the static field toRefundOrders
        out.writeInt(orderNum);
    }

    // Custom deserialization to handle static fields
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // Deserialize the non-static fields
        processedOrders = (ArrayList<Order>) in.readObject(); // Deserialize the static field processedOrders
        pendingOrders = (PriorityQueue<Order>) in.readObject(); // Deserialize the static field pendingOrders
        toRefundOrders = (ArrayList<Order>) in.readObject(); // Deserialize the static field toRefundOrders
        orderNum = in.readInt();
    }

    public static void saveOrders() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Data\\orders.ser"))) {
            out.writeObject(processedOrders);
            out.writeObject(pendingOrders);
            out.writeObject(toRefundOrders);
        } catch (IOException e) {
            System.out.println("Error saving order data.");
            e.printStackTrace();
        }
    }

    public static void loadOrders() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("Data/orders.ser"))) {
            processedOrders = (ArrayList<Order>) in.readObject();
            pendingOrders = (PriorityQueue<Order>) in.readObject();
            toRefundOrders = (ArrayList<Order>) in.readObject();
            System.out.println("Orders loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading order data.");
            e.printStackTrace();
        }
    }
}
