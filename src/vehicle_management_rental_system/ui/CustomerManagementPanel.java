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
import vehicle_management_rental_system.Customer;

/**
 * Admin customer-management screen: styled table of customers with colored
 * add / update / delete / search actions.
 */
public class CustomerManagementPanel extends JPanel {

    private final MainFrame parent;
    private final DefaultTableModel model;
    private final JTable table;

    public CustomerManagementPanel(MainFrame parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Customer Management");
        title.setFont(Theme.TITLE_FONT);
        title.setBorder(BorderFactory.createEmptyBorder(4, 4, 12, 4));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new String[]{"ID", "Username", "Name", "Phone", "Email",
                    "Address", "License"}, 0) {
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

        addBtn.addActionListener(e -> new CustomerFormDialog(parent, null, this::refresh).setVisible(true));
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
        for (Customer c : parent.getContext().customerManager().getAllCustomers()) {
            model.addRow(new Object[]{c.getCustomerId(), c.getUserName(), c.getName(),
                c.getPhone(), c.getEmail(), c.getAddress(), c.getLicenseNumber()});
        }
    }

    private Customer selectedCustomer() {
        int row = table.getSelectedRow();
        if (row < 0) {
            return null;
        }
        String username = (String) model.getValueAt(row, 1);
        return parent.getContext().customerManager().getCustomerByUsername(username);
    }

    private void updateSelected() {
        Customer c = selectedCustomer();
        if (c == null) {
            JOptionPane.showMessageDialog(this, "Select a customer first.",
                    "Update", JOptionPane.WARNING_MESSAGE);
            return;
        }
        new CustomerFormDialog(parent, c, this::refresh).setVisible(true);
    }

    private void deleteSelected() {
        Customer c = selectedCustomer();
        if (c == null) {
            JOptionPane.showMessageDialog(this, "Select a customer first.",
                    "Delete", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete customer '" + c.getUserName() + "'?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = parent.getContext().customerManager()
                    .deleteCustomer(c.getUserName());
            if (ok) {
                JOptionPane.showMessageDialog(this, "Customer deleted successfully.");
                refresh();
            } else {
                JOptionPane.showMessageDialog(this, "Customer couldn't be deleted!");
            }
        }
    }

    private void search() {
        String keyword = JOptionPane.showInputDialog(this,
                "Search by Name or Phone:");
        if (keyword == null || keyword.trim().isEmpty()) {
            return;
        }
        ArrayList<Customer> results =
                parent.getContext().customerManager().searchCustomer(keyword.trim());
        model.setRowCount(0);
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No customers found.");
        }
        for (Customer c : results) {
            model.addRow(new Object[]{c.getCustomerId(), c.getUserName(), c.getName(),
                c.getPhone(), c.getEmail(), c.getAddress(), c.getLicenseNumber()});
        }
    }
}
