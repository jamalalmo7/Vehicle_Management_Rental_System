package vehicle_management_rental_system.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import vehicle_management_rental_system.Vehicle;
import vehicle_management_rental_system.VehicleType;

/**
 * Modal dialog to add a new vehicle or update an existing one. When
 * {@code vehicle} is {@code null} the dialog is in "Add" mode, otherwise it
 * pre-fills the fields and works in "Update" mode. The {@code onSaved}
 * callback refreshes the calling panel after a successful save.
 */
public class VehicleFormDialog extends JDialog {

    private final MainFrame parent;
    private final Vehicle vehicle; // null => add mode
    private final Runnable onSaved;

    private final JComboBox<String> typeBox =
            new JComboBox<>(new String[]{"CAR", "MOTORCYCLE", "TRUCK"});
    private final JTextField brandField = Theme.inputField();
    private final JTextField modelField = Theme.inputField();
    private final JTextField yearField = Theme.inputField();
    private final JTextField priceField = Theme.inputField();

    public VehicleFormDialog(MainFrame parent, Vehicle vehicle, Runnable onSaved) {
        super(parent, vehicle == null ? "Add Vehicle" : "Update Vehicle", true);
        this.parent = parent;
        this.vehicle = vehicle;
        this.onSaved = onSaved;

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Theme.CARD);
        form.setBorder(BorderFactory.createEmptyBorder(20, 26, 20, 26));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(7, 8, 7, 8);
        g.anchor = GridBagConstraints.WEST;

        g.gridx = 0;
        g.gridy = 0;
        form.add(Theme.label("Type:"), g);
        g.gridx = 1;
        typeBox.setPreferredSize(Theme.INPUT_SIZE);
        form.add(typeBox, g);

        g.gridx = 0;
        g.gridy = 1;
        form.add(Theme.label("Brand:"), g);
        g.gridx = 1;
        form.add(brandField, g);

        g.gridx = 0;
        g.gridy = 2;
        form.add(Theme.label("Model:"), g);
        g.gridx = 1;
        form.add(modelField, g);

        g.gridx = 0;
        g.gridy = 3;
        form.add(Theme.label("Year:"), g);
        g.gridx = 1;
        form.add(yearField, g);

        g.gridx = 0;
        g.gridy = 4;
        form.add(Theme.label("Price Per Day:"), g);
        g.gridx = 1;
        form.add(priceField, g);

        if (vehicle != null) {
            typeBox.setSelectedItem(vehicle.getType().name());
            brandField.setText(vehicle.getBrand());
            modelField.setText(vehicle.getModel());
            yearField.setText(String.valueOf(vehicle.getYear()));
            priceField.setText(String.valueOf(vehicle.getPricePerDay()));
        }

        JPanel buttons = new JPanel();
        buttons.setBackground(Theme.CARD);
        JButton saveBtn = vehicle == null ? Theme.primaryButton("Add") : Theme.primaryButton("Save");
        JButton cancelBtn = Theme.neutralButton("Cancel");
        saveBtn.setPreferredSize(new java.awt.Dimension(110, 36));
        cancelBtn.setPreferredSize(new java.awt.Dimension(110, 36));
        saveBtn.addActionListener(e -> save());
        cancelBtn.addActionListener(e -> dispose());
        buttons.add(saveBtn);
        buttons.add(cancelBtn);

        setLayout(new BorderLayout());
        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(parent);
    }

    private void save() {
        String brand = brandField.getText().trim();
        String model = modelField.getText().trim();

        int year;
        double price;
        try {
            year = Integer.parseInt(yearField.getText().trim());
            price = Double.parseDouble(priceField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Year and Price must be valid numbers.");
            return;
        }

        String typeName = (String) typeBox.getSelectedItem();
        VehicleType type = VehicleType.valueOf(typeName);

        boolean ok;
        if (vehicle == null) {
            ok = parent.getContext().vehicleManager()
                    .addVehicle(type, brand, model, year, price);
        } else {
            ok = parent.getContext().vehicleManager()
                    .updateVehicle(vehicle, type, brand, model, year, price);
        }

        if (ok) {
            if (onSaved != null) {
                onSaved.run();
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Vehicle couldn't be saved!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
