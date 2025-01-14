package GUI;
import javax.swing.*;
import java.awt.*;

public class CanteenGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public CanteenGUI() {
        setTitle("Canteen Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add pages to the card layout
        mainPanel.add(new MenuScreen(), "GUI.OrdersScreen.MenuScreen");
        mainPanel.add(new OrdersScreen(), "OrdersScreen");

        //adding main panel to the center of the frame.
        add(mainPanel, BorderLayout.CENTER);

        // Navigation Panel
        JPanel navigationPanel = new JPanel();
        JButton menuButton = new JButton("View Menu");
        JButton ordersButton = new JButton("View Orders");
        navigationPanel.add(menuButton);
        navigationPanel.add(ordersButton);
        add(navigationPanel, BorderLayout.SOUTH);

        // Button Listeners
        //e=action event
        menuButton.addActionListener(e -> cardLayout.show(mainPanel, "GUI.OrdersScreen.MenuScreen"));
        ordersButton.addActionListener(e -> cardLayout.show(mainPanel, "OrdersScreen"));
    }

    public static void GUI_main() {
        System.out.println("Launching GUI");
        //run on same thread. Swing is not thread safe.
        //lambda function for runnable
        SwingUtilities.invokeLater(() -> {
            CanteenGUI gui = new CanteenGUI();
            gui.setVisible(true);
        });
    }
}
