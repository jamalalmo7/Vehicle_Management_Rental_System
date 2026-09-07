package vehicle_management_rental_system.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vehicle_management_rental_system.Car;
import vehicle_management_rental_system.Customer;
import vehicle_management_rental_system.DatabaseConnection;
import vehicle_management_rental_system.MotorCycle;
import vehicle_management_rental_system.Payment;
import vehicle_management_rental_system.PaymentMethod;
import vehicle_management_rental_system.PaymentStatus;
import vehicle_management_rental_system.Rental;
import vehicle_management_rental_system.RentalStatus;
import vehicle_management_rental_system.Role;
import vehicle_management_rental_system.Truck;
import vehicle_management_rental_system.Vehicle;
import vehicle_management_rental_system.VehicleStatus;
import vehicle_management_rental_system.VehicleType;

public class PaymentDAO {

    private static final String SELECT_BASE =
            "SELECT p.payment_id, p.rental_id, p.amount, p.payment_date, p.payment_method, "
          + "p.payment_status, "
          + "r.rental_id AS r_rental_id, r.user_id AS r_user_id, r.vehicle_id AS r_vehicle_id, "
          + "r.rental_date, r.return_date, r.total_cost, r.rental_status, "
          + "u.username, u.password, u.full_name, u.phone, u.email, u.address, u.license_number, u.role, "
          + "v.brand, v.model, v.vehicle_type, v.year, v.rental_rate_per_day, v.status AS v_status "
          + "FROM payments p "
          + "JOIN rentals r ON r.rental_id = p.rental_id "
          + "JOIN users u ON u.user_id = r.user_id "
          + "JOIN vehicles v ON v.vehicle_id = r.vehicle_id ";

    public boolean processPayment(int rentalId, double amount, PaymentMethod paymentMethod) {
        String sql = "INSERT INTO payments "
                   + "(rental_id, amount, payment_date, payment_method, payment_status) "
                   + "VALUES (?, ?, CURRENT_DATE, ?, 'PAID')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rentalId);
            ps.setDouble(2, amount);
            ps.setString(3, paymentMethod.name());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasPaymentForRental(int rentalId) {
        String sql = "SELECT payment_id FROM payments WHERE rental_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rentalId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Payment getPaymentById(int paymentId) {
        String sql = SELECT_BASE + "WHERE p.payment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, paymentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapPayment(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Payment> getPaymentsByRental(int rentalId) {
        ArrayList<Payment> payments = new ArrayList<>();
        String sql = SELECT_BASE + "WHERE p.rental_id = ? ORDER BY p.payment_id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rentalId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    payments.add(mapPayment(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public ArrayList<Payment> getPaymentsByCustomer(int customerId) {
        ArrayList<Payment> payments = new ArrayList<>();
        String sql = SELECT_BASE + "WHERE r.user_id = ? ORDER BY p.payment_id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    payments.add(mapPayment(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public ArrayList<Payment> getAllPayments() {
        ArrayList<Payment> payments = new ArrayList<>();
        String sql = SELECT_BASE + "ORDER BY p.payment_id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                payments.add(mapPayment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public int getPaymentCount() {
        String sql = "SELECT COUNT(*) FROM payments";
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

    private Payment mapPayment(ResultSet rs) throws SQLException {
        VehicleType type;
        try {
            type = VehicleType.valueOf(rs.getString("vehicle_type").toUpperCase());
        } catch (IllegalArgumentException e) {
            type = VehicleType.CAR;
        }
        VehicleStatus vs;
        try {
            vs = VehicleStatus.valueOf(rs.getString("v_status").toUpperCase());
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
        vehicle.setId(rs.getInt("r_vehicle_id"));
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
        customer.setCustomerId(rs.getInt("r_user_id"));

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
        rental.setRentalId(rs.getInt("r_rental_id"));

        PaymentMethod method;
        try {
            method = PaymentMethod.valueOf(rs.getString("payment_method"));
        } catch (IllegalArgumentException e) {
            method = PaymentMethod.CARD;
        }
        PaymentStatus pstatus;
        try {
            pstatus = PaymentStatus.valueOf(rs.getString("payment_status"));
        } catch (IllegalArgumentException e) {
            pstatus = PaymentStatus.PAID;
        }

        Payment payment = new Payment(rental, rs.getDouble("amount"), method);
        payment.setPaymentId(rs.getInt("payment_id"));
        payment.setPaymentDate(rs.getDate("payment_date").toLocalDate());
        payment.setStatus(pstatus);
        return payment;
    }
}
