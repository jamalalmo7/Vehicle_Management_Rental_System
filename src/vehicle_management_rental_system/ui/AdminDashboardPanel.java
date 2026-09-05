package vehicle_management_rental_system.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import vehicle_management_rental_system.Customer;

/**
 * Admin landing screen: dark side navigation plus a card area switching
 * between vehicle/customer/rental/payment/report management.
 */
public class AdminDashboardPanel extends JPanel {

    public static final String VEHICLES = "VEHICLES";
    public static final String CUSTOMERS = "CUSTOMERS";
    public static final String RENTALS = "RENTALS";
    public static final String PAYMENTS = "PAYMENTS";
    public static final String REPORTS = "REPORTS";

    private final MainFrame parent;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    private final VehicleManagementPanel vehiclePanel;
    private final CustomerManagementPanel customerPanel;
    private final RentalManagementPanel rentalPanel;
    private final PaymentManagementPanel paymentPanel;
    private final ReportsPanel reportsPanel;

    public AdminDashboardPanel(MainFrame parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        add(buildHeader(), BorderLayout.NORTH);

        // ---- Card area ----
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        vehiclePanel = new VehicleManagementPanel(parent);
        customerPanel = new CustomerManagementPanel(parent);
        rentalPanel = new RentalManagementPanel(parent);
        paymentPanel = new PaymentManagementPanel(parent);
        reportsPanel = new ReportsPanel(parent);

        cardPanel.add(vehiclePanel, VEHICLES);
        cardPanel.add(customerPanel, CUSTOMERS);
        cardPanel.add(rentalPanel, RENTALS);
        cardPanel.add(paymentPanel, PAYMENTS);
        cardPanel.add(reportsPanel, REPORTS);

        // ---- Sidebar ----
        SideNavList nav = new SideNavList(
                "Vehicle Management",
                "Customer Management",
                "Rental Management",
                "Payment Management",
                "Reports");

        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(Theme.SIDEBAR);
        sidebar.add(nav, BorderLayout.CENTER);
        JLabel who = new JLabel(welcomeName(), JLabel.CENTER);
        who.setForeground(new Color(150, 158, 190));
        who.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        who.setBorder(BorderFactory.createEmptyBorder(10, 4, 14, 4));
        sidebar.add(who, BorderLayout.SOUTH);
        sidebar.setPreferredSize(new Dimension(230, 0));
        add(sidebar, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);

        nav.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                return;
            }
            int idx = nav.getSelectedIndex();
            switch (idx) {
                case 0 -> cardLayout.show(cardPanel, VEHICLES);
                case 1 -> cardLayout.show(cardPanel, CUSTOMERS);
                case 2 -> cardLayout.show(cardPanel, RENTALS);
                case 3 -> cardLayout.show(cardPanel, PAYMENTS);
                case 4 -> cardLayout.show(cardPanel, REPORTS);
                default -> { }
            }
        });
        nav.setSelectedIndex(0);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));

        JLabel title = new JLabel("Admin Dashboard");
        title.setFont(Theme.HEADER_FONT);
        title.setForeground(Theme.TEXT);
        header.add(title, BorderLayout.WEST);

        JButton logout = Theme.dangerButton("Logout");
        logout.setPreferredSize(new Dimension(110, 36));
        logout.addActionListener(e -> doLogout());
        header.add(logout, BorderLayout.EAST);
        return header;
    }

    private String welcomeName() {
        Customer u = parent.getContext().getCurrentUser();
        return u != null ? "Logged in: " + u.getName() : "";
    }

    public void refreshAll() {
        vehiclePanel.refresh();
        customerPanel.refresh();
        rentalPanel.refresh();
        paymentPanel.refresh();
        reportsPanel.refresh();
    }

    private void doLogout() {
        parent.getContext().setCurrentUser(null);
        parent.showLogin();
    }
}
