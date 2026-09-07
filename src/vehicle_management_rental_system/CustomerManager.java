
package vehicle_management_rental_system;

import java.util.ArrayList;
import vehicle_management_rental_system.dao.UserDAO;

public class CustomerManager {
    private final UserDAO userDAO;

    public CustomerManager() {
        this.userDAO = new UserDAO();
        userDAO.ensureDefaultAdmin();
    }

    public boolean addCustomer(String userName, String password, String name, String phone,
                               String email, String address, String licenseNumber) {
        if (userName == null || userName.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {
            return false;
        }
        if (getCustomerByUsername(userName) != null) {
            return false;
        }
        if (userDAO.existsPhone(phone) || userDAO.existsLicense(licenseNumber)) {
            return false;
        }
        return userDAO.registerCustomer(userName, password, name, phone, email, address, licenseNumber);
    }

    public boolean deleteCustomer(String userName) {
        Customer c = getCustomerByUsername(userName);
        if (c == null) {
            return false;
        }
        return userDAO.deleteCustomer(c.getCustomerId());
    }

    public Customer getCustomerByUsername(String userName) {
        return userDAO.getCustomerByUsername(userName);
    }

    public boolean updateCustomer(Customer customer, String newName, String newPhone,
                                  String newEmail, String newAddress, String newLicenseNumber) {
        if (customer == null) {
            return false;
        }
        int id = customer.getCustomerId();
        if (userDAO.existsPhoneExcluding(newPhone, id)
                || userDAO.existsLicenseExcluding(newLicenseNumber, id)) {
            return false;
        }
        boolean ok = userDAO.updateCustomer(id, newName, newPhone, newEmail, newAddress, newLicenseNumber);
        if (ok) {
            customer.setName(newName);
            customer.setPhone(newPhone);
            customer.setEmail(newEmail);
            customer.setAddress(newAddress);
            customer.setLicenseNumber(newLicenseNumber);
        }
        return ok;
    }

    public ArrayList<Customer> searchCustomer(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return userDAO.searchCustomers(keyword);
    }

    public ArrayList<Customer> getAllCustomers() {
        return userDAO.getAllCustomers();
    }

    public int getCustomerCount() {
        return userDAO.getCustomerCount();
    }

    public Customer authenticateCustomer(String userName, String password) {
        Customer c = getCustomerByUsername(userName);
        if (c != null && c.login(userName, password)) {
            return c;
        }
        return null;
    }

    public boolean updateUsername(Customer customer, String newUsername) {
        if (customer == null || newUsername == null || newUsername.trim().isEmpty()) {
            return false;
        }
        String trimmed = newUsername.trim();
        Customer existing = getCustomerByUsername(trimmed);
        if (existing != null && existing.getCustomerId() != customer.getCustomerId()) {
            return false;
        }
        boolean ok = userDAO.updateUsername(customer.getCustomerId(), trimmed);
        if (ok) {
            customer.setUserName(trimmed);
        }
        return ok;
    }

    public boolean changePassword(Customer customer, String oldPassword, String newPassword) {
        if (customer == null) {
            return false;
        }
        if (!customer.login(customer.getUserName(), oldPassword)) {
            return false;
        }
        boolean ok = userDAO.updatePassword(customer.getCustomerId(), newPassword);
        if (ok && customer.changePassword(oldPassword, newPassword)) {
            return true;
        }
        return ok;
    }
}
