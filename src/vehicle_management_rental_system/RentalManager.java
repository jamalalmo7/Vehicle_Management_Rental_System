package vehicle_management_rental_system;

import java.time.LocalDate;
import java.util.ArrayList;
import vehicle_management_rental_system.dao.RentalDAO;
import vehicle_management_rental_system.dao.VehicleDAO;

public class RentalManager {

    private final RentalDAO rentalDAO;
    private final VehicleDAO vehicleDAO;

    public RentalManager() {
        this.rentalDAO = new RentalDAO();
        this.vehicleDAO = new VehicleDAO();
    }

    public boolean createRental(Customer customer, Vehicle vehicle, LocalDate startDate, LocalDate endDate) {
        if (customer == null || vehicle == null || startDate == null || endDate == null) {
            return false;
        }
        if (endDate.isBefore(startDate) || endDate.isEqual(startDate)) {
            return false;
        }
        if (!vehicle.isAvailable()) {
            return false;
        }
        int duration = Rental.calculateDuration(startDate, endDate);
        double totalPrice = vehicle.calculateCost(duration);

        int userId = customer.getCustomerId();
        if (userId <= 0) {
            return false;
        }
        boolean ok = rentalDAO.createRental(userId, vehicle.getId(), startDate, endDate, totalPrice);
        if (ok) {
            vehicle.setStatus(VehicleStatus.RENTED);
            vehicleDAO.updateVehicleStatus(vehicle.getId(), VehicleStatus.RENTED.name());
        }
        return ok;
    }

    public boolean cancelRental(int rentalId) {
        Rental r = getRentalById(rentalId);
        if (r == null || r.getStatus() != RentalStatus.ACTIVE) {
            return false;
        }
        boolean ok = rentalDAO.updateRentalStatus(rentalId, RentalStatus.CANCELLED.name());
        if (ok && r.getVehicle() != null) {
            r.getVehicle().setStatus(VehicleStatus.AVAILABLE);
            vehicleDAO.updateVehicleStatus(r.getVehicle().getId(), VehicleStatus.AVAILABLE.name());
            r.setStatus(RentalStatus.CANCELLED);
        }
        return ok;
    }

    public boolean returnVehicle(int rentalId) {
        Rental r = getRentalById(rentalId);
        if (r == null || r.getStatus() != RentalStatus.ACTIVE) {
            return false;
        }
        boolean ok = rentalDAO.updateRentalStatus(rentalId, RentalStatus.COMPLETED.name());
        if (ok && r.getVehicle() != null) {
            r.getVehicle().setStatus(VehicleStatus.AVAILABLE);
            vehicleDAO.updateVehicleStatus(r.getVehicle().getId(), VehicleStatus.AVAILABLE.name());
            r.setStatus(RentalStatus.COMPLETED);
        }
        return ok;
    }

    public Rental getRentalById(int rentalId) {
        if (rentalId <= 0) {
            return null;
        }
        return rentalDAO.getRentalById(rentalId);
    }

    public ArrayList<Rental> searchRental(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return rentalDAO.searchRentals(keyword);
    }

    public ArrayList<Rental> getAllRentals() {
        return rentalDAO.getAllRentals();
    }

    public ArrayList<Rental> getActiveRentals() {
        return rentalDAO.getActiveRentals();
    }

    public ArrayList<Rental> getCompletedRentals() {
        return rentalDAO.getCompletedRentals();
    }

    public ArrayList<Rental> getCustomerRentals(String username) {
        if (username == null || username.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return rentalDAO.getUserRentalsByUsername(username);
    }

    public ArrayList<Rental> getVehicleRentals(int vehicleId) {
        return rentalDAO.getVehicleRentals(vehicleId);
    }

    public int getRentalCount() {
        return rentalDAO.getRentalCount();
    }
}
