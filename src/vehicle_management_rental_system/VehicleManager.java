package vehicle_management_rental_system;

import java.time.Year;
import java.util.ArrayList;
import vehicle_management_rental_system.dao.VehicleDAO;

public class VehicleManager {
    private final VehicleDAO vehicleDAO;

    public VehicleManager() {
        this.vehicleDAO = new VehicleDAO();
    }

    public boolean addVehicle(VehicleType type, String brand, String model, int year, double pricePerDay) {
        int currentYear = Year.now().getValue();
        if (type == null || brand == null || model == null || pricePerDay <= 0
                || year < 1900 || year > currentYear) {
            return false;
        }
        return vehicleDAO.addVehicle(type, brand, model, year, pricePerDay);
    }

    public boolean deleteVehicle(int vehicleId) {
        Vehicle vehicleToDelete = getVehicleById(vehicleId);
        if (vehicleToDelete == null) {
            return false;
        }
        if (vehicleToDelete.isRented()) {
            return false;
        }
        return vehicleDAO.deleteVehicle(vehicleId);
    }

    public Vehicle getVehicleById(int vehicleId) {
        return vehicleDAO.getVehicleById(vehicleId);
    }

    public boolean updateVehicle(Vehicle vehicle, VehicleType newType, String newBrand,
                                 String newModel, int newYear, double newPricePerDay) {
        int currentYear = Year.now().getValue();
        if (vehicle == null || newPricePerDay <= 0) {
            return false;
        }
        if (newYear < 1900 || newYear > currentYear) {
            return false;
        }
        if (newBrand == null || newBrand.trim().isEmpty()
                || newModel == null || newModel.trim().isEmpty()) {
            return false;
        }
        boolean ok = vehicleDAO.updateVehicle(vehicle.getId(), newType, newBrand, newModel,
                newYear, newPricePerDay);
        if (ok) {
            vehicle.setType(newType);
            vehicle.setBrand(newBrand);
            vehicle.setModel(newModel);
            vehicle.setYear(newYear);
            vehicle.setPricePerDay(newPricePerDay);
        }
        return ok;
    }

    public ArrayList<Vehicle> searchVehicle(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return vehicleDAO.searchVehicles(keyword);
    }

    public ArrayList<Vehicle> getAvailableVehicles() {
        return vehicleDAO.getAvailableVehicles();
    }

    public ArrayList<Vehicle> getAllVehicles() {
        return vehicleDAO.getAllVehicles();
    }

    public ArrayList<Vehicle> searchVehicleByPriceRange(double minPrice, double maxPrice) {
        return vehicleDAO.searchVehicleByPriceRange(minPrice, maxPrice);
    }

    public int getVehicleCount() {
        return vehicleDAO.getVehicleCount();
    }
}
