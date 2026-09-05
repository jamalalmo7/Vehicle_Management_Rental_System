package vehicle_management_rental_system.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

/**
 * Styled vertical navigation list used on the dashboard sidebars.
 */
public class SideNavList extends JList<String> {

    public SideNavList(String... items) {
        super(items);
        setBackground(Theme.SIDEBAR);
        setForeground(new Color(200, 204, 220));
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setFixedCellHeight(42);
        setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setCellRenderer(lavaRenderer());
        setSelectionBackground(Theme.PRIMARY);
        setSelectionForeground(Color.WHITE);
    }

    private ListCellRenderer<? super String> lavaRenderer() {
        return (list, value, index, isSelected, cellHasFocus) -> {
            JLabel l = new JLabel(value);
            l.setOpaque(true);
            l.setBorder(BorderFactory.createEmptyBorder(0, 14, 0, 8));
            l.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            if (isSelected) {
                l.setBackground(Theme.PRIMARY);
                l.setForeground(Color.WHITE);
            } else {
                l.setBackground(Theme.SIDEBAR);
                l.setForeground(new Color(200, 204, 220));
            }
            return l;
        };
    }
}
