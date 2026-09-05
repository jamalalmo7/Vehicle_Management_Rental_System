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
import javax.swing.JTextField;
import vehicle_management_rental_system.Customer;
/**
 * Customer "My Profile" screen: styled form to edit username, password, and
 * personal information.
 */
public class MyProfilePanel extends JPanel {

    private final MainFrame parent;

    private final JTextField usernameField = Theme.inputField();
    private final JPasswordField currentPasswordField = Theme.passwordField();
    private final JPasswordField newPasswordField = Theme.passwordField();
    private final JTextField nameField = Theme.inputField();
    private final JTextField phoneField = Theme.inputField();
    private final JTextField emailField = Theme.inputField();
    private final JTextField addressField = Theme.inputField();
    private final JTextField licenseField = Theme.inputField();
    public MyProfilePanel(MainFrame parent) {
        this.parent = parent;
        setLayout(new GridBagLayout());

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Theme.CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER, 1),
                BorderFactory.createEmptyBorder(24, 30, 24, 30)));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(7, 8, 7, 8);
        g.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("My Profile");
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
        addRow(card, g, 2, "Current Password:", currentPasswordField);
        addRow(card, g, 3, "New Password:", newPasswordField);
        addRow(card, g, 4, "Full Name:", nameField);
        addRow(card, g, 5, "Phone:", phoneField);
        addRow(card, g, 6, "Email:", emailField);
        addRow(card, g, 7, "Address:", addressField);
        addRow(card, g, 8, "License Number:", licenseField);

        g.gridx = 0;
        g.gridy = 9;
        g.gridwidth = 2;
        g.anchor = GridBagConstraints.CENTER;
        g.insets = new Insets(16, 8, 4, 8);
        JPanel buttons = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 0));
        buttons.setBackground(Theme.CARD);
        JButton saveBtn = Theme.primaryButton("Save Changes");
        JButton reloadBtn = Theme.neutralButton("Reload");
        saveBtn.setPreferredSize(new java.awt.Dimension(130, 36));
        saveBtn.addActionListener(e -> save());
        reloadBtn.addActionListener(e -> refresh());
        buttons.add(saveBtn);
        buttons.add(reloadBtn);
        card.add(buttons, g);

        setBackground(Theme.BG);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(card);
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

    public void refresh() {
        Customer c = parent.getContext().getCurrentUser();
        if (c == null) {
            return;
        }
        usernameField.setText(c.getUserName());
        currentPasswordField.setText("");
        newPasswordField.setText("");
        nameField.setText(c.getName());
        phoneField.setText(c.getPhone());
        emailField.setText(c.getEmail());
        addressField.setText(c.getAddress());
        licenseField.setText(c.getLicenseNumber());
    }

    private void save() {
        Customer c = parent.getContext().getCurrentUser();
        if (c == null) {
            return;
        }

        AppContext ctx = parent.getContext();

        String newUsername = usernameField.getText().trim();
        if (!newUsername.isEmpty() && !newUsername.equals(c.getUserName())) {
            boolean ok = ctx.customerManager().updateUsername(c, newUsername);
            if (!ok) {
                JOptionPane.showMessageDialog(this,
                        "Failed to update username (already taken or invalid).",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        String oldPass = new String(currentPasswordField.getPassword());
        String newPass = new String(newPasswordField.getPassword());
        if (!newPass.isEmpty()) {
            boolean ok = c.changePassword(oldPass, newPass);
            if (!ok) {
                JOptionPane.showMessageDialog(this,
                        "Current password is incorrect or new password is empty.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        boolean ok = ctx.customerManager().updateCustomer(c,
                nameField.getText().trim(), phoneField.getText().trim(),
                emailField.getText().trim(), addressField.getText().trim(),
                licenseField.getText().trim());
        if (ok) {
            JOptionPane.showMessageDialog(this, "Profile updated successfully.");
            refresh();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to update personal information (phone/license conflict).",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
