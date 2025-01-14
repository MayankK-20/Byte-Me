package GUI;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// Custom renderer to make category rows bold and distinct
public class TableRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //get default component
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        if (model.getValueAt(row, 1).equals(" ")) {
            //If it is a filter
            c.setFont(c.getFont().deriveFont(Font.BOLD));
            c.setBackground(Color.LIGHT_GRAY);
        } else {
            c.setFont(c.getFont().deriveFont(Font.PLAIN));
            c.setBackground(Color.WHITE);
        }
        return c;
    }
}