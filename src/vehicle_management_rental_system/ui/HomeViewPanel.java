package vehicle_management_rental_system.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import vehicle_management_rental_system.Customer;
import vehicle_management_rental_system.Rental;
import vehicle_management_rental_system.RentalStatus;

/**
 * Customer landing ("Home") view: a welcome banner, stat cards for available
 * vehicles, active rentals, and account status, plus a quick action to browse
 * all vehicles. Intentionally does NOT dump every vehicle here.
 */
public class HomeViewPanel extends JPanel {

    private final JLabel availableLabel;
    private final JLabel activeRentalsLabel;
    private final JLabel statusLabel;
    private final JLabel welcomeLabel;
    private final Runnable onBrowse;

    public HomeViewPanel(AppContext context, Runnable onBrowse) {
        this.onBrowse = onBrowse;
        setLayout(new BorderLayout());
        setBackground(Theme.BG);
        setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        welcomeLabel = new JLabel();
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcomeLabel.setForeground(Theme.TEXT);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(8, 4, 20, 4));

        JPanel cards = new JPanel(new GridLayout(1, 3, 18, 0));
        cards.setBackground(Theme.BG);

        availableLabel = new JLabel("0", JLabel.CENTER);
        availableLabel.setFont(new Font("Segoe UI", Font.BOLD, 34));
        availableLabel.setForeground(Theme.PRIMARY);
        JPanel availableCard = statCard(availableLabel, "Available Vehicles");

        activeRentalsLabel = new JLabel("0", JLabel.CENTER);
        activeRentalsLabel.setFont(new Font("Segoe UI", Font.BOLD, 34));
        activeRentalsLabel.setForeground(Theme.INFO);
        JPanel activeRentalsCard = statCard(activeRentalsLabel, "My Active Rentals");

        statusLabel = new JLabel("-", JLabel.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        statusLabel.setForeground(Theme.SUCCESS);
        JPanel statusCard = statCard(statusLabel, "Account Status");

        cards.add(availableCard);
        cards.add(activeRentalsCard);
        cards.add(statusCard);

        JButton browseBtn = Theme.primaryButton("Browse All Vehicles");
        browseBtn.setPreferredSize(new Dimension(170, 40));
        browseBtn.addActionListener(e -> {
            if (onBrowse != null) {
                onBrowse.run();
            }
        });
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        actions.setBackground(Theme.BG);
        actions.add(browseBtn);
        actions.setBorder(BorderFactory.createEmptyBorder(20, 4, 4, 4));

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(Theme.BG);
        center.add(welcomeLabel, BorderLayout.NORTH);
        center.add(cards, BorderLayout.CENTER);
        center.add(actions, BorderLayout.SOUTH);

        add(center, BorderLayout.NORTH);

        refresh(context);
    }

    private JPanel statCard(JLabel value, String caption) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Theme.CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER, 1),
                BorderFactory.createEmptyBorder(24, 16, 24, 16)));
        card.add(value, BorderLayout.CENTER);

        JLabel cap = new JLabel(caption, JLabel.CENTER);
        cap.setFont(Theme.LABEL_FONT);
        cap.setForeground(Theme.MUTED);
        cap.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        card.add(cap, BorderLayout.SOUTH);
        return card;
    }

    public void refresh(AppContext ctx) {
        Customer user = ctx.getCurrentUser();

        String name = user != null && user.getName() != null
                ? user.getName()
                : (user != null ? user.getUserName() : "there");
        welcomeLabel.setText("Welcome, " + name);

        availableLabel.setText(String.valueOf(
                ctx.vehicleManager().getAvailableVehicles().size()));

        int active = 0;
        if (user != null) {
            for (Rental r : ctx.rentalManager().getCustomerRentals(user.getUserName())) {
                if (r.getStatus() == RentalStatus.ACTIVE) {
                    active++;
                }
            }
        }
        activeRentalsLabel.setText(String.valueOf(active));

        if (user != null && user.getRole() != null) {
            statusLabel.setText(user.getRole().toString());
            statusLabel.setForeground(Theme.SUCCESS);
        } else {
            statusLabel.setText("-");
        }
    }
}
