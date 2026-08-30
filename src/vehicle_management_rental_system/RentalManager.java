package vehicle_management_rental_system;

import java.time.LocalDate;
import java.util.ArrayList;

public class RentalManager {

    private ArrayList<Rental> rentalList = new ArrayList<>();

    public boolean createRental(Customer customer, Vehicle vehicle, LocalDate startDate, LocalDate endDate) {
         if(customer == null || vehicle == null || startDate == null || endDate == null){
             return false;
         }
        if (endDate.isBefore(startDate) || endDate.isEqual(startDate)) {
            return false;
        }
         
        if (vehicle.isAvailable()) {
            int duration = Rental.calculateDuration(startDate, endDate);

            double totalPrice = vehicle.calculateCost(duration);

            Rental rental = new Rental(customer, vehicle, startDate, endDate, totalPrice,
                    RentalStatus.ACTIVE);

            vehicle.setStatus(VehicleStatus.RENTED);
            rentalList.add(rental);
            return true;
        }
            return false;
    }

    public boolean cancelRental(int rentalId) {
        Rental r = getRentalById(rentalId);
        if (r != null) {
            return r.cancelRental();
        }
            // it was replaced all of this by the fun cancelRental in Rental class  to change the statuses 
            
//            if (r.getStatus() == RentalStatus.ACTIVE) {
//                r.setStatus(RentalStatus.CANCELLED);
//                r.getVehicle().setStatus(VehicleStatus.AVAILABLE);
//                System.out.println("Cancelled rental successfully");
//                return;
//            }
//            System.out.println("This rental is inactive.");
//            return;
        
        return false;
    }

    public boolean returnVehicle(int rentalId) {
        Rental r = getRentalById(rentalId);
        if (r != null) {
            return r.completeRental();
        }
//            if (r.getStatus() == RentalStatus.ACTIVE) {
//                r.setStatus(RentalStatus.COMPLETED);
//                r.getVehicle().setStatus(VehicleStatus.AVAILABLE);
//                System.out.println("Rental finished successfully");
//                return;
//            }
//            System.out.println("This rental is inactive.");
//            return;
        
            return false;
    }

    public Rental getRentalById(int rentalId) {
        if(rentalId <=0){
            return null;
        }
        for (Rental r : rentalList) {
            if (r.getRentalId() == rentalId) {
                return r;
            }
        }
        return null;
    }

    public ArrayList<Rental> searchRental(String keyword) {
        ArrayList<Rental> results = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) {
            return results;
        }
        String searchKeyword = keyword.toLowerCase();
        for (Rental r : rentalList) {
            boolean matchCustomerName = r.getCustomer() != null && r.getCustomer().getName() != null
                    && r.getCustomer().getName().toLowerCase().contains(searchKeyword);

            boolean matchCustomerUsername = r.getCustomer() != null && r.getCustomer().getUserName() != null
                    && r.getCustomer().getUserName().toLowerCase().contains(searchKeyword);

            boolean matchVehicleBrand = r.getVehicle() != null && r.getVehicle().getBrand() != null
                    && r.getVehicle().getBrand().toLowerCase().contains(searchKeyword);

            boolean matchVehicleModel = r.getVehicle() != null && r.getVehicle().getModel() != null
                    && r.getVehicle().getModel().toLowerCase().contains(searchKeyword);

            boolean matchVehicleType = r.getVehicle() != null && r.getVehicle().getType() != null
                    && r.getVehicle().getType().name().toLowerCase().contains(searchKeyword);

            if (matchCustomerName || matchCustomerUsername
                    || matchVehicleBrand || matchVehicleModel
                    || matchVehicleType) {
                results.add(r);
            }
        }
        return results;

    }

    public ArrayList<Rental> getAllRentals() {
        return new ArrayList<>(rentalList);
    }

    public ArrayList<Rental> getActiveRentals() {
        ArrayList<Rental> results = new ArrayList<>();
        for (Rental r : rentalList) {
            if (r.getStatus() == RentalStatus.ACTIVE) {
                results.add(r);
            }
        }
        return results;
    }

    public ArrayList<Rental> getCompletedRentals() {
        ArrayList<Rental> results = new ArrayList<>();
        for (Rental r : rentalList) {
            if (r.getStatus() == RentalStatus.COMPLETED) {
                results.add(r);
            }
        }
        return results;
    }

    public ArrayList<Rental> getCustomerRentals(String username) {
        ArrayList<Rental> results = new ArrayList<>();
        if (username == null || username.trim().isEmpty()) {
            return results;
        }
        String cleanUsername = username.trim();
        for (Rental r : rentalList) {
            if (r.getCustomer() != null && r.getCustomer().getUserName() != null) {
                if (r.getCustomer().getUserName().equalsIgnoreCase(cleanUsername)) {
                    results.add(r);
                }
            }

        }
        return results;
    }

    public ArrayList<Rental> getVehicleRentals(int vehicleId) {
        ArrayList<Rental> results = new ArrayList<>();
        for (Rental r : rentalList) {
            if (r.getVehicle() != null && r.getVehicle().getId() == vehicleId) {
                results.add(r);
            }
        }
        return results;
    }

    public int getRentalCount() {
        return rentalList.size();
    }

}
