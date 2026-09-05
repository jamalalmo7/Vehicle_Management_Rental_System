package vehicle_management_rental_system.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import vehicle_management_rental_system.Customer;

/**
 * Modal dialog to add a new customer or update an existing one. When
 * {@code customer} is {@code null} the dialog is in "Add" mode (password field
 * enabled). In "Update" mode the password field is hidden and the username is
 * fixed.
 */
public class CustomerFormDialog extends JDialog {

    private final MainFrame parent;
    private final Customer customer; // null => add mode
    private final Runnable onSaved;

    private final JTextField usernameField = Theme.inputField();
    private final JPasswordField passwordField = Theme.passwordField();
    private final JTextField nameField = Theme.inputField();
    private final JTextField phoneField = Theme.inputField();
    private final JTextField emailField = Theme.inputField();
    private final JTextField addressField = Theme.inputField();
    private final JTextField licenseField = Theme.inputField();

    public CustomerFormDialog(MainFrame parent, Customer customer, Runnable onSaved) {
        super(parent, customer == null ? "Add Customer" : "Update Customer", true);
        this.parent = parent;
        this.customer = customer;
        this.onSaved = onSaved;

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Theme.CARD);
        form.setBorder(BorderFactory.createEmptyBorder(20, 26, 20, 26));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 8, 6, 8);
        g.anchor = GridBagConstraints.WEST;

        addRow(form, g, 0, "Username:", usernameField);
        addRow(form, g, 1, "Password:", passwordField);
        addRow(form, g, 2, "Full Name:", nameField);
        addRow(form, g, 3, "Phone:", phoneField);
        addRow(form, g, 4, "Email:", emailField);
        addRow(form, g, 5, "Address:", addressField);
        addRow(form, g, 6, "License Number:", licenseField);

        if (customer != null) {
            usernameField.setText(customer.getUserName());
            usernameField.setEditable(false);
            passwordField.setEnabled(false);
            nameField.setText(customer.getName());
            phoneField.setText(customer.getPhone());
            emailField.setText(customer.getEmail());
            addressField.setText(customer.getAddress());
            licenseField.setText(customer.getLicenseNumber());
        }

        JPanel buttons = new JPanel();
        buttons.setBackground(Theme.CARD);
        JButton saveBtn = customer == null ? Theme.primaryButton("Add") : Theme.primaryButton("Save");
        JButton cancelBtn = Theme.neutralButton("Cancel");
        saveBtn.setPreferredSize(new java.awt.Dimension(110, 36));
        cancelBtn.setPreferredSize(new java.awt.Dimension(110, 36));
        saveBtn.addActionListener(e -> save());
        cancelBtn.addActionListener(e -> dispose());
        buttons.add(saveBtn);
        buttons.add(cancelBtn);

        setLayout(new BorderLayout());
        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(parent);
    }

    private void addRow(JPanel form, GridBagConstraints g, int y, String label, java.awt.Component field) {
        g.gridx = 0;
        g.gridy = y;
        g.gridwidth = 1;
        form.add(Theme.label(label), g);
        g.gridx = 1;
        form.add(field, g);
    }

    private void save() {
        if (customer == null) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String address = addressField.getText().trim();
            String license = licenseField.getText().trim();

            if (username.isEmpty() || password.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Username, Password and Full Name are required.");
                return;
            }
            boolean ok = parent.getContext().customerManager()
                    .addCustomer(username, password, name, phone, email, address, license);
            if (ok) {
                if (onSaved != null) {
                    onSaved.run();
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Username, Phone, or License already exists!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            boolean ok = parent.getContext().customerManager().updateCustomer(
                    customer, nameField.getText().trim(),
                    phoneField.getText().trim(), emailField.getText().trim(),
                    addressField.getText().trim(), licenseField.getText().trim());
            if (ok) {
                if (onSaved != null) {
                    onSaved.run();
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Customer couldn't be updated (phone/license conflict)!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
