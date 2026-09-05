package vehicle_management_rental_system.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vehicle_management_rental_system.Payment;

/**
 * Customer "My Payments" screen: styled read-only table of the logged-in
 * customer's payments.
 */
public class MyPaymentsPanel extends JPanel {

    private final MainFrame parent;
    private final DefaultTableModel model;
    private final JTable table;

    public MyPaymentsPanel(MainFrame parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("My Payments");
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
        JButton refreshBtn = Theme.neutralButton("Refresh");
        refreshBtn.addActionListener(e -> refresh());
        buttons.add(refreshBtn);
        buttons.setBorder(BorderFactory.createEmptyBorder(6, 4, 4, 4));
        add(buttons, BorderLayout.SOUTH);
    }

    public void refresh() {
        model.setRowCount(0);
        var user = parent.getContext().getCurrentUser();
        if (user == null) {
            return;
        }
        for (Payment p : parent.getContext().paymentManager()
                .getCustomerPayments(user.getCustomerId())) {
            model.addRow(new Object[]{p.getPaymentId(), p.getRental().getRentalId(),
                p.getAmount(), p.getPaymentDate(), p.getPaymentMethod(), p.getStatus()});
        }
    }
}
