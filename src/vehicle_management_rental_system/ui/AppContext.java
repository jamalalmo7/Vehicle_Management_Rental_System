package vehicle_management_rental_system.ui;

import vehicle_management_rental_system.Customer;
import vehicle_management_rental_system.CustomerManager;
import vehicle_management_rental_system.PaymentManager;
import vehicle_management_rental_system.RentalManager;
import vehicle_management_rental_system.VehicleManager;

/**
 * Shared runtime context for the GUI layer.
 *
 * <p>Holds the business-logic manager instances so every panel can reach the
 * same in-memory data without touching the original classes.</p>
 */
public class AppContext {

    private final CustomerManager customerManager;
    private final VehicleManager vehicleManager;
    private final RentalManager rentalManager;
    private final PaymentManager paymentManager;

    private Customer currentUser;

    public AppContext() {
        this.customerManager = new CustomerManager();
        this.vehicleManager = new VehicleManager();
        this.rentalManager = new RentalManager();
        this.paymentManager = new PaymentManager();
    }

    public CustomerManager customerManager() {
        return customerManager;
    }

    public VehicleManager vehicleManager() {
        return vehicleManager;
    }

    public RentalManager rentalManager() {
        return rentalManager;
    }

    public PaymentManager paymentManager() {
        return paymentManager;
    }

    public Customer getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Customer currentUser) {
        this.currentUser = currentUser;
    }
}
