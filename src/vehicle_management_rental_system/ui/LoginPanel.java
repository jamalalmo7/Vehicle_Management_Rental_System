package vehicle_management_rental_system.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import vehicle_management_rental_system.Customer;

/**
 * Login screen. Authenticates against {@code CustomerManager} and routes to
 * the correct dashboard on success.
 */
public class LoginPanel extends JPanel {

    private final MainFrame parent;
    private final JTextField usernameField = Theme.inputField();
    private final JPasswordField passwordField = Theme.passwordField();

    public LoginPanel(MainFrame parent) {
        this.parent = parent;

        setBackground(Theme.BG);
        setLayout(new GridBagLayout());

        // ---- Centered card ----
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Theme.CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER, 1),
                BorderFactory.createEmptyBorder(32, 40, 32, 40)));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("Vehicle Rental System");
        title.setFont(Theme.HEADER_FONT);
        title.setForeground(Theme.PRIMARY);
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 2;
        g.anchor = GridBagConstraints.CENTER;
        card.add(title, g);

        g.gridwidth = 1;
        g.anchor = GridBagConstraints.WEST;

        g.gridx = 0;
        g.gridy = 1;
        JLabel unameLabel = Theme.label("Username");
        card.add(unameLabel, g);
        g.gridx = 1;
        card.add(usernameField, g);

        g.gridx = 0;
        g.gridy = 2;
        card.add(Theme.label("Password"), g);
        g.gridx = 1;
        card.add(passwordField, g);

        // ---- Buttons ----
        g.gridx = 0;
        g.gridy = 3;
        g.gridwidth = 2;
        g.anchor = GridBagConstraints.CENTER;
        JPanel buttons = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 0));
        buttons.setBackground(Theme.CARD);
        JButton loginBtn = Theme.primaryButton("Login");
        JButton registerBtn = Theme.neutralButton("Register");
        loginBtn.setPreferredSize(new java.awt.Dimension(110, 36));
        registerBtn.setPreferredSize(new java.awt.Dimension(110, 36));
        loginBtn.addActionListener(e -> doLogin());
        registerBtn.addActionListener(e -> parent.showRegister());
        buttons.add(loginBtn);
        buttons.add(registerBtn);
        card.add(buttons, g);

        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(card);
    }

    private void doLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.",
                    "Login", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Customer user = parent.getContext().customerManager()
                .authenticateCustomer(username, password);

        if (user == null) {
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password!",
                    "Login Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        parent.getContext().setCurrentUser(user);
        clearFields();
        parent.showDashboard();
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }
}
