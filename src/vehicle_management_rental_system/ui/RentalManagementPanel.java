package vehicle_management_rental_system.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vehicle_management_rental_system.Rental;

/**
 * Admin rental-management screen: styled table of rentals with colored
 * create / cancel / return actions.
 */
public class RentalManagementPanel extends JPanel {

    private final MainFrame parent;
    private final DefaultTableModel model;
    private final JTable table;

    public RentalManagementPanel(MainFrame parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Rental Management");
        title.setFont(Theme.TITLE_FONT);
        title.setBorder(BorderFactory.createEmptyBorder(4, 4, 12, 4));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new String[]{"ID", "Customer", "Vehicle", "Start", "End",
                    "Total", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        Theme.styleTable(table);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        JButton createBtn = Theme.successButton("Create Rental");
        JButton cancelBtn = Theme.dangerButton("Cancel Rental");
        JButton returnBtn = Theme.infoButton("Return Vehicle");
        JButton refreshBtn = Theme.neutralButton("Refresh");

        createBtn.addActionListener(e -> createRental());
        cancelBtn.addActionListener(e -> cancelSelected());
        returnBtn.addActionListener(e -> returnSelected());
        refreshBtn.addActionListener(e -> refresh());

        buttons.add(createBtn);
        buttons.add(cancelBtn);
        buttons.add(returnBtn);
        buttons.add(refreshBtn);
        buttons.setBorder(BorderFactory.createEmptyBorder(6, 4, 4, 4));
        add(buttons, BorderLayout.SOUTH);
    }

    public void refresh() {
        model.setRowCount(0);
        for (Rental r : parent.getContext().rentalManager().getAllRentals()) {
            model.addRow(new Object[]{r.getRentalId(), r.getCustomer().getUserName(),
                r.getVehicle().getId(), r.getStartDate(), r.getEndDate(),
                r.getTotalPrice(), r.getStatus()});
        }
    }

    private void createRental() {
        String username = JOptionPane.showInputDialog(this,
                "Enter Customer Username:");
        if (username == null || username.trim().isEmpty()) {
            return;
        }
        var customer = parent.getContext().customerManager()
                .getCustomerByUsername(username.trim());
        if (customer == null) {
            JOptionPane.showMessageDialog(this, "Customer not found!");
            return;
        }

        String vehicleIdStr = JOptionPane.showInputDialog(this,
                "Enter Vehicle ID:");
        if (vehicleIdStr == null) {
            return;
        }
        int vehicleId;
        try {
            vehicleId = Integer.parseInt(vehicleIdStr.trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid vehicle ID.");
            return;
        }
        var vehicle = parent.getContext().vehicleManager().getVehicleById(vehicleId);
        if (vehicle == null) {
            JOptionPane.showMessageDialog(this, "Vehicle not found!");
            return;
        }
        if (!vehicle.isAvailable()) {
            JOptionPane.showMessageDialog(this,
                    "Vehicle is not available for rental.",
                    "Rental Blocked", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String startStr = JOptionPane.showInputDialog(this,
                "Enter Start Date (YYYY-MM-DD):");
        if (startStr == null) {
            return;
        }
        String endStr = JOptionPane.showInputDialog(this,
                "Enter End Date (YYYY-MM-DD):");
        if (endStr == null) {
            return;
        }
        LocalDate start;
        LocalDate end;
        try {
            start = LocalDate.parse(startStr.trim());
            end = LocalDate.parse(endStr.trim());
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Use YYYY-MM-DD.");
            return;
        }

        boolean ok = parent.getContext().rentalManager()
                .createRental(customer, vehicle, start, end);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Rental created successfully.");
            refresh();
        } else {
            JOptionPane.showMessageDialog(this, "Rental couldn't be created!");
        }
    }

    private Rental selectedRental() {
        int row = table.getSelectedRow();
        if (row < 0) {
            return null;
        }
        int id = (Integer) model.getValueAt(row, 0);
        return parent.getContext().rentalManager().getRentalById(id);
    }

    private void cancelSelected() {
        Rental r = selectedRental();
        if (r == null) {
            JOptionPane.showMessageDialog(this, "Select a rental first.",
                    "Cancel", JOptionPane.WARNING_MESSAGE);
            return;
        }
        boolean ok = parent.getContext().rentalManager().cancelRental(r.getRentalId());
        if (ok) {
            JOptionPane.showMessageDialog(this, "Rental cancelled successfully.");
            refresh();
        } else {
            JOptionPane.showMessageDialog(this, "Rental couldn't be cancelled!");
        }
    }

    private void returnSelected() {
        Rental r = selectedRental();
        if (r == null) {
            JOptionPane.showMessageDialog(this, "Select a rental first.",
                    "Return", JOptionPane.WARNING_MESSAGE);
            return;
        }
        boolean ok = parent.getContext().rentalManager().returnVehicle(r.getRentalId());
        if (ok) {
            JOptionPane.showMessageDialog(this, "Vehicle returned successfully.");
            refresh();
        } else {
            JOptionPane.showMessageDialog(this, "Vehicle couldn't be returned!");
        }
    }
}
