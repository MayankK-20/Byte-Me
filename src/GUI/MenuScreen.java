package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import CLI.MenuItem;

public class MenuScreen extends JPanel {
    private JTable menuTable;

    public MenuScreen() {
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Canteen Menu", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(18f));
        add(titleLabel, BorderLayout.NORTH);

        // Table for displaying menu
        menuTable = new JTable(new DefaultTableModel(new String[]{"Category/Name", "Price", "Availability"}, 0));
        add(new JScrollPane(menuTable), BorderLayout.CENTER);

        // Customize rendering for category headers
        menuTable.setDefaultRenderer(Object.class, new TableRenderer());

        // Load menu data
        loadMenuData();
    }

    private void loadMenuData() {
        DefaultTableModel model = (DefaultTableModel) menuTable.getModel();
        model.setRowCount(0); // Clear table
        //        try {
        //            BufferedReader br = new BufferedReader(new FileReader("menu.txt"));
        //            String line;
        //            while ((line = br.readLine()) != null) {
        //                String[] parts = line.split(",");       //splitting on comma
        //                model.addRow(new Object[]{parts[0], parts[1], parts[2]});
        //            }
        //        }
        //        catch (IOException e) {
        //            JOptionPane.showMessageDialog(this, "Error loading menu data.", "Error", JOptionPane.ERROR_MESSAGE);
        //        }

        if (!MenuItem.snacks.isEmpty()){
            model.addRow(new Object[]{"[Snacks]"," "," "});
            for (MenuItem item : MenuItem.snacks){
                model.addRow(new Object[]{item.getName(),item.getPrice(),item.getAvailability()});
            }
        }

        if (!MenuItem.beverages.isEmpty()){
            model.addRow(new Object[]{"[Beverages]"," "," "});
            for (MenuItem item : MenuItem.beverages){
                model.addRow(new Object[]{item.getName(),item.getPrice(),item.getAvailability()});
            }
        }

        if (!MenuItem.meals.isEmpty()){
            model.addRow(new Object[]{"[Meals]"," "," "});
            for (MenuItem item : MenuItem.meals){
                model.addRow(new Object[]{item.getName(),item.getPrice(),item.getAvailability()});
            }
        }
    }

}