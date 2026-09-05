package vehicle_management_rental_system.ui;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import vehicle_management_rental_system.Role;

/**
 * Root window of the application. Holds every screen in a {@link CardLayout}
 * so the app can switch between login, admin dashboard, and customer
 * dashboard panels.
 */
public class MainFrame extends JFrame {

    public static final String LOGIN = "LOGIN";
    public static final String REGISTER = "REGISTER";
    public static final String ADMIN = "ADMIN";
    public static final String CUSTOMER = "CUSTOMER";

    private final AppContext context;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    // Sub-panels
    private final LoginPanel loginPanel;
    private final RegisterPanel registerPanel;
    private final AdminDashboardPanel adminDashboard;
    private final CustomerDashboardPanel customerDashboard;

    public MainFrame(AppContext context) {
        this.context = context;

        setTitle("Vehicle Rental Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 720);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        loginPanel = new LoginPanel(this);
        registerPanel = new RegisterPanel(this);
        adminDashboard = new AdminDashboardPanel(this);
        customerDashboard = new CustomerDashboardPanel(this);

        cardPanel.add(loginPanel, LOGIN);
        cardPanel.add(registerPanel, REGISTER);
        cardPanel.add(adminDashboard, ADMIN);
        cardPanel.add(customerDashboard, CUSTOMER);

        setContentPane(cardPanel);
        showLogin();
    }

    public AppContext getContext() {
        return context;
    }

    /** Shows the login screen. */
    public void showLogin() {
        cardLayout.show(cardPanel, LOGIN);
    }

    /** Shows the registration screen. */
    public void showRegister() {
        cardLayout.show(cardPanel, REGISTER);
    }

    /** Routes to the correct dashboard based on the logged-in user's role. */
    public void showDashboard() {
        if (context.getCurrentUser() == null) {
            showLogin();
            return;
        }
        if (context.getCurrentUser().getRole() == Role.ADMIN) {
            adminDashboard.refreshAll();
            cardLayout.show(cardPanel, ADMIN);
        } else {
            customerDashboard.refreshAll();
            cardLayout.show(cardPanel, CUSTOMER);
        }
    }
}
