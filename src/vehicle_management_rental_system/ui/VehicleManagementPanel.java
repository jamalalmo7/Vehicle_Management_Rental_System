package vehicle_management_rental_system.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vehicle_management_rental_system.Vehicle;
import vehicle_management_rental_system.VehicleStatus;

/**
 * Admin vehicle-management screen: styled table of vehicles with colored
 * add / update / delete / search actions.
 */
public class VehicleManagementPanel extends JPanel {

    private final MainFrame parent;
    private final DefaultTableModel model;
    private final JTable table;

    public VehicleManagementPanel(MainFrame parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Vehicle Management");
        title.setFont(Theme.TITLE_FONT);
        title.setBorder(BorderFactory.createEmptyBorder(4, 4, 12, 4));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new String[]{"ID", "Type", "Brand", "Model", "Year",
                    "Price/Day", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        Theme.styleTable(table);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        JButton addBtn = Theme.successButton("Add");
        JButton updateBtn = Theme.infoButton("Update");
        JButton deleteBtn = Theme.dangerButton("Delete");
        JButton searchBtn = Theme.infoButton("Search");
        JButton refreshBtn = Theme.neutralButton("Refresh");

        addBtn.addActionListener(e -> new VehicleFormDialog(parent, null, this::refresh).setVisible(true));
        updateBtn.addActionListener(e -> updateSelected());
        deleteBtn.addActionListener(e -> deleteSelected());
        searchBtn.addActionListener(e -> search());
        refreshBtn.addActionListener(e -> refresh());

        buttons.add(addBtn);
        buttons.add(updateBtn);
        buttons.add(deleteBtn);
        buttons.add(searchBtn);
        buttons.add(refreshBtn);
        buttons.setBorder(BorderFactory.createEmptyBorder(6, 4, 4, 4));
        add(buttons, BorderLayout.SOUTH);
    }

    public void refresh() {
        model.setRowCount(0);
        for (Vehicle v : parent.getContext().vehicleManager().getAllVehicles()) {
            model.addRow(new Object[]{
                v.getId(), v.getType(), v.getBrand(), v.getModel(),
                v.getYear(), v.getPricePerDay(), v.getStatus()
            });
        }
    }

    private Vehicle selectedVehicle() {
        int row = table.getSelectedRow();
        if (row < 0) {
            return null;
        }
        int id = (Integer) model.getValueAt(row, 0);
        return parent.getContext().vehicleManager().getVehicleById(id);
    }

    private void updateSelected() {
        Vehicle v = selectedVehicle();
        if (v == null) {
            JOptionPane.showMessageDialog(this, "Select a vehicle first.",
                    "Update", JOptionPane.WARNING_MESSAGE);
            return;
        }
        new VehicleFormDialog(parent, v, this::refresh).setVisible(true);
    }

    private void deleteSelected() {
        Vehicle v = selectedVehicle();
        if (v == null) {
            JOptionPane.showMessageDialog(this, "Select a vehicle first.",
                    "Delete", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (v.getStatus() == VehicleStatus.RENTED) {
            JOptionPane.showMessageDialog(this,
                    "Vehicle is currently RENTED and cannot be deleted.",
                    "Delete Blocked", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete vehicle " + v.getId() + " (" + v.getBrand() + " " + v.getModel() + ")?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = parent.getContext().vehicleManager().deleteVehicle(v.getId());
            if (ok) {
                JOptionPane.showMessageDialog(this, "Vehicle deleted successfully.");
                refresh();
            } else {
                JOptionPane.showMessageDialog(this, "Vehicle couldn't be deleted!");
            }
        }
    }

    private void search() {
        String keyword = JOptionPane.showInputDialog(this,
                "Search by keyword (Brand / Model / Type):");
        if (keyword == null || keyword.trim().isEmpty()) {
            return;
        }
        ArrayList<Vehicle> results =
                parent.getContext().vehicleManager().searchVehicle(keyword.trim());
        model.setRowCount(0);
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No vehicles found.");
        }
        for (Vehicle v : results) {
            model.addRow(new Object[]{
                v.getId(), v.getType(), v.getBrand(), v.getModel(),
                v.getYear(), v.getPricePerDay(), v.getStatus()
            });
        }
    }
}
