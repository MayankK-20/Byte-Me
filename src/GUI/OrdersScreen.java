package GUI;
import CLI.MenuItem;
import CLI.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class OrdersScreen extends JPanel {
    private JTable ordersTable;

    public OrdersScreen() {
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Pending Orders", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(18f));
        add(titleLabel, BorderLayout.NORTH);

        // Table for displaying orders
        ordersTable = new JTable(new DefaultTableModel(new String[]{"Order Type/ Order Number", "Items Ordered", "Status"}, 0));
        add(new JScrollPane(ordersTable), BorderLayout.CENTER);

        // Customize rendering for type of orders
        ordersTable.setDefaultRenderer(Object.class, new TableRenderer());

        // Load orders data
        loadOrdersData();
    }

    private void loadOrdersData() {
        DefaultTableModel model = (DefaultTableModel) ordersTable.getModel();
        model.setRowCount(0); // Clear table
//        try{
//            BufferedReader br = new BufferedReader(new FileReader("order.txt"));
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split(",");
//                model.addRow(new Object[]{parts[0], parts[1], parts[2]});
//            }
//        }
//        catch (IOException e) {
//            JOptionPane.showMessageDialog(this, "Error loading orders data.", "Error", JOptionPane.ERROR_MESSAGE);
//        }

        if (!Order.processedOrders.isEmpty()){
            model.addRow(new Object[]{"[Processed Orders]"," "," "});
            for (CLI.Order order : Order.processedOrders){
                String s="";
                HashMap<MenuItem,Integer> h=order.getOrderItems();
                for (MenuItem item : h.keySet()){
                    String tmp=(item.getName()+"("+order.orderItems.get(item)+")")+". ";
                    s+=tmp;
                }
                model.addRow(new Object[]{order.getID(),s,order.getStatus()});
            }
        }

        if (!Order.pendingOrders.isEmpty()){
            model.addRow(new Object[]{"[Pending Orders]"," "," "});
            for (CLI.Order order : Order.pendingOrders){
                String s="";
                HashMap<MenuItem,Integer> h=order.getOrderItems();
                for (MenuItem item : h.keySet()){
                    String tmp=(item.getName()+"("+order.orderItems.get(item)+")")+". ";
                    s+=tmp;
                }
                model.addRow(new Object[]{order.getID(),s,order.getStatus()});
            }
        }

        if (!Order.toRefundOrders.isEmpty()){
            model.addRow(new Object[]{"[To Refund Orders]"," "," "});
            for (CLI.Order order : Order.toRefundOrders){
                String s="";
                HashMap<MenuItem,Integer> h=order.getOrderItems();
                for (MenuItem item : h.keySet()){
                    String tmp=(item.getName()+"("+order.orderItems.get(item)+")")+". ";
                    s+=tmp;
                }
                model.addRow(new Object[]{order.getID(),s,order.getStatus()});
            }
        }
    }
}

