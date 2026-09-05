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
 * Customer landing screen: dark side navigation plus a card area switching
 * between home, browsing, rentals, payments, and profile.
 */
public class CustomerDashboardPanel extends JPanel {

    public static final String HOME = "HOME";
    public static final String BROWSE = "BROWSE";
    public static final String RENTALS = "RENTALS";
    public static final String PAYMENTS = "PAYMENTS";
    public static final String PROFILE = "PROFILE";

    private final MainFrame parent;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    private final HomeViewPanel homePanel;
    private final BrowseVehiclesPanel browsePanel;
    private final MyRentalsPanel rentalsPanel;
    private final MyPaymentsPanel paymentsPanel;
    private final MyProfilePanel profilePanel;

    public CustomerDashboardPanel(MainFrame parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        add(buildHeader(), BorderLayout.NORTH);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        homePanel = new HomeViewPanel(parent.getContext(), () ->
                cardLayout.show(cardPanel, BROWSE));
        browsePanel = new BrowseVehiclesPanel(parent);
        rentalsPanel = new MyRentalsPanel(parent);
        paymentsPanel = new MyPaymentsPanel(parent);
        profilePanel = new MyProfilePanel(parent);

        cardPanel.add(homePanel, HOME);
        cardPanel.add(browsePanel, BROWSE);
        cardPanel.add(rentalsPanel, RENTALS);
        cardPanel.add(paymentsPanel, PAYMENTS);
        cardPanel.add(profilePanel, PROFILE);

        SideNavList nav = new SideNavList(
                "Home",
                "Browse Vehicles",
                "My Rentals",
                "My Payments",
                "My Profile");

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
                case 0 -> {
                    homePanel.refresh(parent.getContext());
                    cardLayout.show(cardPanel, HOME);
                }
                case 1 -> cardLayout.show(cardPanel, BROWSE);
                case 2 -> cardLayout.show(cardPanel, RENTALS);
                case 3 -> cardLayout.show(cardPanel, PAYMENTS);
                case 4 -> {
                    profilePanel.refresh();
                    cardLayout.show(cardPanel, PROFILE);
                }
                default -> { }
            }
        });
        nav.setSelectedIndex(0);
        cardLayout.show(cardPanel, HOME);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));

        JLabel title = new JLabel("Customer Dashboard");
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
        homePanel.refresh(parent.getContext());
        browsePanel.refresh();
        rentalsPanel.refresh();
        paymentsPanel.refresh();
    }

    private void doLogout() {
        parent.getContext().setCurrentUser(null);
        parent.showLogin();
    }
}
