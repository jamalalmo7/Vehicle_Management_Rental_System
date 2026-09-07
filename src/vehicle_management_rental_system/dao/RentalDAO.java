package vehicle_management_rental_system.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import vehicle_management_rental_system.Car;
import vehicle_management_rental_system.Customer;
import vehicle_management_rental_system.DatabaseConnection;
import vehicle_management_rental_system.MotorCycle;
import vehicle_management_rental_system.Rental;
import vehicle_management_rental_system.RentalStatus;
import vehicle_management_rental_system.Role;
import vehicle_management_rental_system.Truck;
import vehicle_management_rental_system.Vehicle;
import vehicle_management_rental_system.VehicleStatus;
import vehicle_management_rental_system.VehicleType;

public class RentalDAO {

    private static final String SELECT_BASE =
            "SELECT r.rental_id, r.user_id, r.vehicle_id, r.rental_date, r.return_date, "
          + "r.total_cost, r.rental_status, "
          + "u.username, u.password, u.full_name, u.phone, u.email, u.address, u.license_number, u.role, "
          + "v.brand, v.model, v.vehicle_type, v.year, v.rental_rate_per_day, v.status "
          + "FROM rentals r "
          + "JOIN users u ON u.user_id = r.user_id "
          + "JOIN vehicles v ON v.vehicle_id = r.vehicle_id ";

    public boolean createRental(int userId, int vehicleId, LocalDate rentalDate,
                                LocalDate returnDate, double totalCost) {
        String sql = "INSERT INTO rentals "
                   + "(user_id, vehicle_id, rental_date, return_date, total_cost, rental_status) "
                   + "VALUES (?, ?, ?, ?, ?, 'ACTIVE')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, vehicleId);
            ps.setDate(3, Date.valueOf(rentalDate));
            ps.setDate(4, Date.valueOf(returnDate));
            ps.setDouble(5, totalCost);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Rental getRentalById(int rentalId) {
        String sql = SELECT_BASE + "WHERE r.rental_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rentalId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRental(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Rental> getAllRentals() {
        ArrayList<Rental> rentals = new ArrayList<>();
        String sql = SELECT_BASE + "ORDER BY r.rental_id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                rentals.add(mapRental(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }

    public ArrayList<Rental> getActiveRentals() {
        ArrayList<Rental> rentals = new ArrayList<>();
        String sql = SELECT_BASE + "WHERE r.rental_status = 'ACTIVE' ORDER BY r.rental_id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                rentals.add(mapRental(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }

    public ArrayList<Rental> getCompletedRentals() {
        ArrayList<Rental> rentals = new ArrayList<>();
        String sql = SELECT_BASE + "WHERE r.rental_status = 'COMPLETED' ORDER BY r.rental_id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                rentals.add(mapRental(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }

    public ArrayList<Rental> getUserRentals(int userId) {
        ArrayList<Rental> rentals = new ArrayList<>();
        String sql = SELECT_BASE + "WHERE r.user_id = ? ORDER BY r.rental_id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rentals.add(mapRental(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }

    public ArrayList<Rental> getUserRentalsByUsername(String username) {
        ArrayList<Rental> rentals = new ArrayList<>();
        String sql = SELECT_BASE + "WHERE u.username = ? ORDER BY r.rental_id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rentals.add(mapRental(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }

    public ArrayList<Rental> getVehicleRentals(int vehicleId) {
        ArrayList<Rental> rentals = new ArrayList<>();
        String sql = SELECT_BASE + "WHERE r.vehicle_id = ? ORDER BY r.rental_id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, vehicleId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rentals.add(mapRental(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }

    public ArrayList<Rental> searchRentals(String keyword) {
        ArrayList<Rental> rentals = new ArrayList<>();
        String sql = SELECT_BASE + "WHERE LOWER(u.full_name) LIKE ? "
                   + "OR LOWER(u.username) LIKE ? "
                   + "OR LOWER(v.brand) LIKE ? "
                   + "OR LOWER(v.model) LIKE ? "
                   + "OR LOWER(v.vehicle_type) LIKE ? ORDER BY r.rental_id";
        String pattern = "%" + keyword.toLowerCase() + "%";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setString(3, pattern);
            ps.setString(4, pattern);
            ps.setString(5, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rentals.add(mapRental(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }

    public boolean updateRentalStatus(int rentalId, String status) {
        String sql = "UPDATE rentals SET rental_status = ? WHERE rental_id = ? AND rental_status = 'ACTIVE'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status.toUpperCase());
            ps.setInt(2, rentalId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getRentalCount() {
        String sql = "SELECT COUNT(*) FROM rentals";
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

    private Rental mapRental(ResultSet rs) throws SQLException {
        VehicleType type;
        try {
            type = VehicleType.valueOf(rs.getString("vehicle_type").toUpperCase());
        } catch (IllegalArgumentException e) {
            type = VehicleType.CAR;
        }
        VehicleStatus vs;
        try {
            vs = VehicleStatus.valueOf(rs.getString("v.status").toUpperCase());
        } catch (IllegalArgumentException e) {
            vs = VehicleStatus.AVAILABLE;
        }

        Vehicle vehicle;
        if (type == VehicleType.CAR) {
            vehicle = new Car(type, rs.getString("brand"), rs.getString("model"),
                    rs.getInt("year"), rs.getDouble("rental_rate_per_day"));
        } else if (type == VehicleType.MOTORCYCLE) {
            vehicle = new MotorCycle(type, rs.getString("brand"), rs.getString("model"),
                    rs.getInt("year"), rs.getDouble("rental_rate_per_day"));
        } else {
            vehicle = new Truck(type, rs.getString("brand"), rs.getString("model"),
                    rs.getInt("year"), rs.getDouble("rental_rate_per_day"));
        }
        vehicle.setId(rs.getInt("vehicle_id"));
        vehicle.setStatus(vs);

        Role role;
        try {
            role = Role.valueOf(rs.getString("role"));
        } catch (IllegalArgumentException e) {
            role = Role.CUSTOMER;
        }
        Customer customer = new Customer(
                rs.getString("username"), rs.getString("password"), role,
                rs.getString("full_name"), rs.getString("phone"),
                rs.getString("email"), rs.getString("address"),
                rs.getString("license_number"));
        customer.setCustomerId(rs.getInt("user_id"));

        RentalStatus rstatus;
        try {
            rstatus = RentalStatus.valueOf(rs.getString("rental_status").toUpperCase());
        } catch (IllegalArgumentException e) {
            rstatus = RentalStatus.ACTIVE;
        }

        Rental rental = new Rental(customer, vehicle,
                rs.getDate("rental_date").toLocalDate(),
                rs.getDate("return_date").toLocalDate(),
                rs.getDouble("total_cost"), rstatus);
        rental.setRentalId(rs.getInt("rental_id"));
        return rental;
    }
}
