package vehicle_management_rental_system.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import vehicle_management_rental_system.Vehicle;

/**
 * Customer browse/search screen: clean vehicle table with a top toolbar row of
 * search/filter controls (by ID, keyword, and price range).
 */
public class BrowseVehiclesPanel extends JPanel {

    private final MainFrame parent;
    private final DefaultTableModel model;
    private final JTable table;

    private final JTextField idField = Theme.inputField();
    private final JTextField keywordField = Theme.inputField();
    private final JTextField minPriceField = Theme.inputField();
    private final JTextField maxPriceField = Theme.inputField();

    public BrowseVehiclesPanel(MainFrame parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Browse & Search Vehicles");
        title.setFont(Theme.TITLE_FONT);
        title.setBorder(BorderFactory.createEmptyBorder(4, 4, 10, 4));

        JPanel toolbar = buildToolbar();

        JPanel north = new JPanel(new BorderLayout());
        north.setOpaque(false);
        north.add(title, BorderLayout.NORTH);
        north.add(toolbar, BorderLayout.SOUTH);
        north.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        add(north, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new String[]{"ID", "Type", "Brand", "Model", "Year",
                    "Price/Day", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        Theme.styleTable(table);
        table.setFillsViewportHeight(true);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refresh();
    }

    private JPanel buildToolbar() {
        JPanel bar = new JPanel(new GridLayout(1, 1));
        bar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bar.setBackground(Theme.CARD);

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        row.setBackground(Theme.CARD);

        idField.setPreferredSize(new Dimension(60, 30));
        keywordField.setPreferredSize(new Dimension(95, 30));
        minPriceField.setPreferredSize(new Dimension(50, 30));
        maxPriceField.setPreferredSize(new Dimension(50, 30));

        JButton byIdBtn = Theme.neutralButton("Search by ID");
        JButton byKeywordBtn = Theme.neutralButton("Search");
        JButton byPriceBtn = Theme.neutralButton("Filter Price");
        JButton allBtn = Theme.neutralButton("Reset / View All");

        byIdBtn.setPreferredSize(new Dimension(95, 30));
        byKeywordBtn.setPreferredSize(new Dimension(70, 30));
        byPriceBtn.setPreferredSize(new Dimension(95, 30));
        allBtn.setPreferredSize(new Dimension(115, 30));

        row.add(Theme.label("ID:"));
        row.add(idField);
        row.add(byIdBtn);

        row.add(Theme.label("Keyword:"));
        row.add(keywordField);
        row.add(byKeywordBtn);

        row.add(Theme.label("Min:"));
        row.add(minPriceField);
        row.add(Theme.label("Max:"));
        row.add(maxPriceField);
        row.add(byPriceBtn);

        row.add(allBtn);

        byIdBtn.addActionListener(e -> searchById());
        byKeywordBtn.addActionListener(e -> searchByKeyword());
        byPriceBtn.addActionListener(e -> searchByPrice());
        allBtn.addActionListener(e -> refresh());

        bar.add(row);
        return bar;
    }

    public void refresh() {
        model.setRowCount(0);
        for (Vehicle v : parent.getContext().vehicleManager().getAllVehicles()) {
            addRow(v);
        }
    }

    private void addRow(Vehicle v) {
        model.addRow(new Object[]{v.getId(), v.getType(), v.getBrand(), v.getModel(),
            v.getYear(), v.getPricePerDay(), v.getStatus()});
    }

    private void searchById() {
        String idStr = idField.getText().trim();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a Vehicle ID first.");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID.");
            return;
        }
        Vehicle v = parent.getContext().vehicleManager().getVehicleById(id);
        model.setRowCount(0);
        if (v == null) {
            JOptionPane.showMessageDialog(this, "Vehicle not found.");
        } else {
            addRow(v);
        }
    }

    private void searchByKeyword() {
        String kw = keywordField.getText().trim();
        if (kw.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Enter a keyword (Brand / Model / Type) first.");
            return;
        }
        var results = parent.getContext().vehicleManager().searchVehicle(kw);
        model.setRowCount(0);
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No vehicles found.");
        }
        for (Vehicle v : results) {
            addRow(v);
        }
    }

    private void searchByPrice() {
        String minStr = minPriceField.getText().trim();
        String maxStr = maxPriceField.getText().trim();
        if (minStr.isEmpty() || maxStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter both Min and Max price first.");
            return;
        }
        double min, max;
        try {
            min = Double.parseDouble(minStr);
            max = Double.parseDouble(maxStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid price value.");
            return;
        }
        if (min < 0 || max < min) {
            JOptionPane.showMessageDialog(this, "Invalid price range.");
            return;
        }
        var results = parent.getContext().vehicleManager()
                .searchVehicleByPriceRange(min, max);
        model.setRowCount(0);
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No vehicles found in this price range.");
        }
        for (Vehicle v : results) {
            addRow(v);
        }
    }
}
