package vehicle_management_rental_system.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import vehicle_management_rental_system.CustomerManager;

/**
 * New-customer registration screen. Adds the account via the shared
 * {@link CustomerManager}.
 */
public class RegisterPanel extends JPanel {

    private final MainFrame parent;

    private final JTextField usernameField = Theme.inputField();
    private final JPasswordField passwordField = Theme.passwordField();
    private final JTextField nameField = Theme.inputField();
    private final JTextField phoneField = Theme.inputField();
    private final JTextField emailField = Theme.inputField();
    private final JTextField addressField = Theme.inputField();
    private final JTextField licenseField = Theme.inputField();

    public RegisterPanel(MainFrame parent) {
        this.parent = parent;

        setBackground(Theme.BG);
        setLayout(new BorderLayout());

        // ---- Centered scrollable card ----
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Theme.CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER, 1),
                BorderFactory.createEmptyBorder(28, 36, 28, 36)));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(7, 8, 7, 8);
        g.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("Create Account");
        title.setFont(Theme.HEADER_FONT);
        title.setForeground(Theme.PRIMARY);
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 2;
        g.anchor = GridBagConstraints.CENTER;
        card.add(title, g);

        g.gridwidth = 1;
        g.anchor = GridBagConstraints.WEST;

        addRow(card, g, 1, "Username:", usernameField);
        addRow(card, g, 2, "Password:", passwordField);
        addRow(card, g, 3, "Full Name:", nameField);
        addRow(card, g, 4, "Phone Number:", phoneField);
        addRow(card, g, 5, "Email:", emailField);
        addRow(card, g, 6, "Address:", addressField);
        addRow(card, g, 7, "License Number:", licenseField);

        g.gridx = 0;
        g.gridy = 8;
        g.gridwidth = 2;
        g.anchor = GridBagConstraints.CENTER;
        g.insets = new Insets(16, 8, 4, 8);
        JPanel buttons = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 0));
        buttons.setBackground(Theme.CARD);
        JButton registerBtn = Theme.primaryButton("Register");
        JButton backBtn = Theme.neutralButton("Back");
        registerBtn.addActionListener(e -> doRegister());
        backBtn.addActionListener(e -> parent.showLogin());
        buttons.add(registerBtn);
        buttons.add(backBtn);
        card.add(buttons, g);

        JScrollPane scroll = new JScrollPane(card);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Theme.BG);
        add(scroll, BorderLayout.CENTER);
    }

    private void addRow(JPanel card, GridBagConstraints g, int y, String labelText, java.awt.Component field) {
        g.gridx = 0;
        g.gridy = y;
        g.gridwidth = 1;
        g.anchor = GridBagConstraints.WEST;
        card.add(Theme.label(labelText), g);
        g.gridx = 1;
        card.add(field, g);
    }

    private void doRegister() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressField.getText().trim();
        String license = licenseField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Username, Password and Full Name are required.",
                    "Registration", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean success = parent.getContext().customerManager()
                .addCustomer(username, password, name, phone, email, address, license);

        if (success) {
            JOptionPane.showMessageDialog(this,
                    "Account created successfully! You can now log in.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            parent.showLogin();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Username, Phone, or License already exists!",
                    "Registration Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        addressField.setText("");
        licenseField.setText("");
    }
}
