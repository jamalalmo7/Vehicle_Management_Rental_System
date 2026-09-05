package vehicle_management_rental_system.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vehicle_management_rental_system.Payment;
import vehicle_management_rental_system.PaymentMethod;
import vehicle_management_rental_system.Rental;

/**
 * Admin payment-management screen: styled table of payments with a colored
 * create-payment action.
 */
public class PaymentManagementPanel extends JPanel {

    private final MainFrame parent;
    private final DefaultTableModel model;
    private final JTable table;

    public PaymentManagementPanel(MainFrame parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Payment Management");
        title.setFont(Theme.TITLE_FONT);
        title.setBorder(BorderFactory.createEmptyBorder(4, 4, 12, 4));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new String[]{"ID", "Rental ID", "Amount", "Date",
                    "Method", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        Theme.styleTable(table);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        JButton createBtn = Theme.successButton("Create Payment");
        JButton refreshBtn = Theme.neutralButton("Refresh");
        createBtn.addActionListener(e -> createPayment());
        refreshBtn.addActionListener(e -> refresh());

        buttons.add(createBtn);
        buttons.add(refreshBtn);
        buttons.setBorder(BorderFactory.createEmptyBorder(6, 4, 4, 4));
        add(buttons, BorderLayout.SOUTH);
    }

    public void refresh() {
        model.setRowCount(0);
        for (Payment p : parent.getContext().paymentManager().getAllPayments()) {
            model.addRow(new Object[]{p.getPaymentId(),
                p.getRental().getRentalId(), p.getAmount(),
                p.getPaymentDate(), p.getPaymentMethod(), p.getStatus()});
        }
    }

    private void createPayment() {
        String rentalIdStr = JOptionPane.showInputDialog(this,
                "Enter Rental ID:");
        if (rentalIdStr == null) {
            return;
        }
        int rentalId;
        try {
            rentalId = Integer.parseInt(rentalIdStr.trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid rental ID.");
            return;
        }
        Rental rental = parent.getContext().rentalManager().getRentalById(rentalId);
        if (rental == null) {
            JOptionPane.showMessageDialog(this, "Rental not found!");
            return;
        }

        Object[] methods = {PaymentMethod.CASH, PaymentMethod.CARD};
        PaymentMethod method = (PaymentMethod) JOptionPane.showInputDialog(this,
                "Select Payment Method:", "Payment Method",
                JOptionPane.PLAIN_MESSAGE, null, methods, PaymentMethod.CASH);
        if (method == null) {
            return;
        }

        boolean ok = parent.getContext().paymentManager()
                .createPayment(rental, method);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Payment created successfully.");
            refresh();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Payment couldn't be created (already paid or rental not active)!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
