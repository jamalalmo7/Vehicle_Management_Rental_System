package vehicle_management_rental_system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vehicle_management_rental_system.Car;
import vehicle_management_rental_system.DatabaseConnection;
import vehicle_management_rental_system.MotorCycle;
import vehicle_management_rental_system.Truck;
import vehicle_management_rental_system.Vehicle;
import vehicle_management_rental_system.VehicleStatus;
import vehicle_management_rental_system.VehicleType;

public class VehicleDAO {

    public boolean addVehicle(VehicleType type, String brand, String model, int year,
                              double rentalRatePerDay) {
        String sql = "INSERT INTO vehicles (brand, model, vehicle_type, year, "
                   + "rental_rate_per_day, status) "
                   + "VALUES (?, ?, ?, ?, ?, 'AVAILABLE')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, brand);
            ps.setString(2, model);
            ps.setString(3, type.name().toUpperCase());
            ps.setInt(4, year);
            ps.setDouble(5, rentalRatePerDay);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Vehicle> getAllVehicles() {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT vehicle_id, brand, model, vehicle_type, year, rental_rate_per_day, "
                   + "status FROM vehicles ORDER BY vehicle_id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                vehicles.add(mapVehicle(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public ArrayList<Vehicle> getAvailableVehicles() {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT vehicle_id, brand, model, vehicle_type, year, rental_rate_per_day, "
                   + "status FROM vehicles WHERE status = 'AVAILABLE' ORDER BY vehicle_id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                vehicles.add(mapVehicle(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public Vehicle getVehicleById(int vehicleId) {
        String sql = "SELECT vehicle_id, brand, model, vehicle_type, year, rental_rate_per_day, "
                   + "status FROM vehicles WHERE vehicle_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, vehicleId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapVehicle(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Vehicle> searchVehicles(String keyword) {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT vehicle_id, brand, model, vehicle_type, year, rental_rate_per_day, "
                   + "status FROM vehicles "
                   + "WHERE LOWER(brand) LIKE ? OR LOWER(model) LIKE ? "
                   + "OR LOWER(vehicle_type) LIKE ? ORDER BY vehicle_id";
        String pattern = "%" + keyword.toLowerCase() + "%";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setString(3, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    vehicles.add(mapVehicle(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public ArrayList<Vehicle> searchVehicleByPriceRange(double minPrice, double maxPrice) {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT vehicle_id, brand, model, vehicle_type, year, rental_rate_per_day, "
                   + "status FROM vehicles "
                   + "WHERE rental_rate_per_day >= ? AND rental_rate_per_day <= ? "
                   + "ORDER BY vehicle_id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, minPrice);
            ps.setDouble(2, maxPrice);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    vehicles.add(mapVehicle(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public boolean updateVehicle(int vehicleId, VehicleType type, String brand, String model,
                                 int year, double rentalRatePerDay) {
        String sql = "UPDATE vehicles SET brand = ?, model = ?, vehicle_type = ?, year = ?, "
                   + "rental_rate_per_day = ? WHERE vehicle_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, brand);
            ps.setString(2, model);
            ps.setString(3, type.name().toUpperCase());
            ps.setInt(4, year);
            ps.setDouble(5, rentalRatePerDay);
            ps.setInt(6, vehicleId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateVehicleStatus(int vehicleId, String newStatus) {
        String sql = "UPDATE vehicles SET status = ? WHERE vehicle_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus.toUpperCase());
            ps.setInt(2, vehicleId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteVehicle(int vehicleId) {
        String sql = "DELETE FROM vehicles WHERE vehicle_id = ? AND status <> 'RENTED'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, vehicleId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getVehicleCount() {
        String sql = "SELECT COUNT(*) FROM vehicles";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Vehicle mapVehicle(ResultSet rs) throws SQLException {
        VehicleType type;
        try {
            type = VehicleType.valueOf(rs.getString("vehicle_type").toUpperCase());
        } catch (IllegalArgumentException e) {
            type = VehicleType.CAR;
        }
        String brand = rs.getString("brand");
        String model = rs.getString("model");
        int year = rs.getInt("year");
        double price = rs.getDouble("rental_rate_per_day");
        VehicleStatus status;
        try {
            status = VehicleStatus.valueOf(rs.getString("status").toUpperCase());
        } catch (IllegalArgumentException e) {
            status = VehicleStatus.AVAILABLE;
        }

        Vehicle vehicle;
        if (type == VehicleType.CAR) {
            vehicle = new Car(type, brand, model, year, price);
        } else if (type == VehicleType.MOTORCYCLE) {
            vehicle = new MotorCycle(type, brand, model, year, price);
        } else {
            vehicle = new Truck(type, brand, model, year, price);
        }
        vehicle.setId(rs.getInt("vehicle_id"));
        vehicle.setStatus(status);
        return vehicle;
    }
}
