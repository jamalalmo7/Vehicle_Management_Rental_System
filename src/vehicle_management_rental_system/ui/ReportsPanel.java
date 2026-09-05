package vehicle_management_rental_system.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import vehicle_management_rental_system.Payment;
import vehicle_management_rental_system.PaymentStatus;
import vehicle_management_rental_system.Vehicle;

/**
 * Admin reports screen: summarizes counts for vehicles, customers, rentals,
 * and payments in a styled read-only area.
 */
public class ReportsPanel extends JPanel {

    private final MainFrame parent;
    private final JTextArea textArea = new JTextArea(20, 60);

    public ReportsPanel(MainFrame parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Reports");
        title.setFont(Theme.TITLE_FONT);
        title.setBorder(BorderFactory.createEmptyBorder(4, 4, 12, 4));
        add(title, BorderLayout.NORTH);

        textArea.setEditable(false);
        textArea.setFont(new java.awt.Font("Consolas", java.awt.Font.PLAIN, 14));
        textArea.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));
        textArea.setBackground(Theme.CARD);
        textArea.setForeground(Theme.TEXT);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        JButton vehicleBtn = Theme.infoButton("Vehicle Report");
        JButton customerBtn = Theme.infoButton("Customer Report");
        JButton rentalBtn = Theme.infoButton("Rental Report");
        JButton paymentBtn = Theme.infoButton("Payment Report");

        vehicleBtn.addActionListener(e -> showVehicleReport());
        customerBtn.addActionListener(e -> showCustomerReport());
        rentalBtn.addActionListener(e -> showRentalReport());
        paymentBtn.addActionListener(e -> showPaymentReport());

        buttons.add(vehicleBtn);
        buttons.add(customerBtn);
        buttons.add(rentalBtn);
        buttons.add(paymentBtn);
        buttons.setBorder(BorderFactory.createEmptyBorder(6, 4, 4, 4));
        add(buttons, BorderLayout.SOUTH);
    }

    public void refresh() {
        // Reports are generated on demand.
    }

    private void showVehicleReport() {
        int available = 0, rented = 0, maintenance = 0;
        int total = parent.getContext().vehicleManager().getAllVehicles().size();
        for (Vehicle v : parent.getContext().vehicleManager().getAllVehicles()) {
            if (v.isAvailable()) {
                available++;
            } else if (v.isRented()) {
                rented++;
            } else if (v.isMaintenance()) {
                maintenance++;
            }
        }
        textArea.setText("========== VEHICLE REPORT ==========\n"
                + "Total Vehicles : " + total + "\n"
                + "Available      : " + available + "\n"
                + "Rented         : " + rented + "\n"
                + "Maintenance    : " + maintenance + "\n");
    }

    private void showCustomerReport() {
        var customers = parent.getContext().customerManager().getAllCustomers();
        StringBuilder sb = new StringBuilder("========== CUSTOMER REPORT ==========\n");
        sb.append("Total Customers : ").append(customers.size()).append("\n\n");
        for (var c : customers) {
            sb.append("ID: ").append(c.getCustomerId())
              .append(" | ").append(c.getName())
              .append(" | ").append(c.getUserName())
              .append(" | ").append(c.getPhone()).append("\n");
        }
        textArea.setText(sb.toString());
    }

    private void showRentalReport() {
        var all = parent.getContext().rentalManager().getAllRentals();
        var active = parent.getContext().rentalManager().getActiveRentals();
        var completed = parent.getContext().rentalManager().getCompletedRentals();
        textArea.setText("========== RENTAL REPORT ==========\n"
                + "Total Rentals     : " + all.size() + "\n"
                + "Active Rentals    : " + active.size() + "\n"
                + "Completed Rentals : " + completed.size() + "\n");
    }

    private void showPaymentReport() {
        int paid = 0, failed = 0, refunded = 0;
        double total = 0;
        int count = 0;
        for (Payment p : parent.getContext().paymentManager().getAllPayments()) {
            count++;
            total += p.getAmount();
            if (p.getStatus() == PaymentStatus.PAID) {
                paid++;
            } else if (p.getStatus() == PaymentStatus.FAILED) {
                failed++;
            } else if (p.getStatus() == PaymentStatus.REFUNDED) {
                refunded++;
            }
        }
        textArea.setText("========== PAYMENT REPORT ==========\n"
                + "Total Payments : " + count + "\n"
                + "Paid           : " + paid + "\n"
                + "Failed         : " + failed + "\n"
                + "Refunded       : " + refunded + "\n"
                + "Total Amount   : " + total + "\n");
    }
}
