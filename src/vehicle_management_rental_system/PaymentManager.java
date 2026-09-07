package vehicle_management_rental_system;

import java.util.ArrayList;
import vehicle_management_rental_system.dao.PaymentDAO;

public class PaymentManager {

    private final PaymentDAO paymentDAO;

    public PaymentManager() {
        this.paymentDAO = new PaymentDAO();
    }

    public boolean createPayment(Rental rental, PaymentMethod paymentMethod) {
        if (rental == null || rental.getStatus() != RentalStatus.ACTIVE) {
            return false;
        }
        if (paymentDAO.hasPaymentForRental(rental.getRentalId())) {
            return false;
        }
        double amount = rental.getTotalPrice();
        return paymentDAO.processPayment(rental.getRentalId(), amount, paymentMethod);
    }

    public Payment getPaymentById(int paymentId) {
        if (paymentId <= 0) {
            return null;
        }
        return paymentDAO.getPaymentById(paymentId);
    }

    public ArrayList<Payment> getRentalPayments(int rentalId) {
        return paymentDAO.getPaymentsByRental(rentalId);
    }

    public ArrayList<Payment> getCustomerPayments(int customerId) {
        return paymentDAO.getPaymentsByCustomer(customerId);
    }

    public ArrayList<Payment> getAllPayments() {
        return paymentDAO.getAllPayments();
    }

    public int getPaymentCount() {
        return paymentDAO.getPaymentCount();
    }
}
