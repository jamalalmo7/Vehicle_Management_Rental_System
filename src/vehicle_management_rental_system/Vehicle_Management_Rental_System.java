
package vehicle_management_rental_system;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;


public class Vehicle_Management_Rental_System {
    private static Scanner scanner = new Scanner(System.in);
    private static CustomerManager customerManager = new CustomerManager();
    private static Customer currentUser = null;
    private static VehicleManager vehicleManager = new VehicleManager();
    private static RentalManager rentalManager = new RentalManager();
    private static PaymentManager paymentManager = new PaymentManager();
   //=========================================================================
    public static void main(String[] args) {
        mainMenu();

    
    }
    //======================================================================
    public static void mainMenu(){
    int choice;
    do{
        System.out.println("====== WELCOME TO VEHICLE RENTAL SYSTEM ======");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println("Choose: ");
        choice = Integer.parseInt(scanner.nextLine());
        
        switch (choice){
            case 1:
                login();
                break;
            case 2:
                registerCustomer();
                break;
            case 3:
                System.out.println("Goodbye");
                break;
                
            default:
                System.out.println("Invalid choice.");
                }
    }while(choice != 3);
    }
    
    
    private static void login() {
        System.out.println("\n========== LOGIN ==========");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        Customer user = customerManager.authenticateCustomer(username, password);

        if (user == null) {
        System.out.println("\n[Error] Invalid username or password!");
        System.out.print("Account not found. Do you want to register? (y/n): ");
        String choice = scanner.nextLine();
        
            if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
                registerCustomer(); 
            }
        }
        else {
        currentUser = user;
        System.out.println("\nLogin successful! Welcome " + user.getName());

        if (user.getRole() == Role.ADMIN) {
            adminMenu();
        } 
        else {
            customerMenu();
        }

        currentUser = null; 
    }
}

  private static String readInput(String prompt) {
    System.out.print(prompt);
    String input = scanner.nextLine().trim();
    if (input.equals("0")) {
        throw new IllegalStateException("CANCEL");
    }
    return input;
}

public static void registerCustomer() {
    System.out.println("\n========== REGISTER NEW CUSTOMER ==========");
    System.out.println("(Enter '0' at any prompt to cancel and go back)\n");

    try {
       
        String username = readInput("Enter Username: ");
        String password = readInput("Enter Password: ");
        String name = readInput("Enter Full Name: ");
        String phone = readInput("Enter Phone Number: ");
        String email = readInput("Enter Email: ");
        String address = readInput("Enter Address: ");
        String license = readInput("Enter Driver License Number: ");

        boolean isSuccess = customerManager.addCustomer(username, password, name, phone,email,address, license);
        if (isSuccess) {
            System.out.println("\n[Success] Account created successfully!");
        } else {
            System.out.println("\n[Error] Username, Phone, or License already exists!");
        }

    } catch (IllegalStateException e) {
        System.out.println("\n[Cancelled] Registration cancelled by user.");
    }
}

    public static void adminMenu() {
    int choice;

    do {
        System.out.println("\n========== ADMIN MENU ==========");
        System.out.println("1. Vehicle Management");
        System.out.println("2. Customer Management");
        System.out.println("3. Rental Management");
        System.out.println("4. Payment Management");
        System.out.println("5. Reports");
        System.out.println("6. Logout");
        System.out.print("Choose: ");

        choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                vehicleManagement();
                break;

            case 2:
                customerManagement();
                break;

            case 3:
                rentalManagement();
                break;

            case 4:
                paymentManagement();
                break;

            case 5:
                reports();
                break;

            case 6:
                System.out.println("Logging out...");
                break;

            default:
                System.out.println("Invalid choice.");
        }

    } while (choice != 6);
}
   
   public static void customerMenu() {
    int choice;

    do {
        System.out.println("\n========== CUSTOMER MENU ==========");
        System.out.println("1. Browse Vehicles");
        System.out.println("2. Search Vehicles");
        System.out.println("3. My Rentals");
        System.out.println("4. My Payments");
        System.out.println("5. My Profile");
        System.out.println("6. Logout");
        System.out.print("Choose: ");

        choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                browseVehicles();
                break;

            case 2:
                searchVehicles();
                break;

            case 3:
                myRentals();
                break;

            case 4:
                myPayments();
                break;

            case 5:
                myProfile();
                break;

            case 6:
                System.out.println("Logging out...");
                break;

            default:
                System.out.println("Invalid choice.");
        }

    } while (choice != 6);
}
   //====================== ADMIN MENU ==================
    public static void vehicleManagement() {
    int choice;

    do {
        System.out.println("\n========== VEHICLE MANAGEMENT ==========");
        System.out.println("1. Add Vehicle");
        System.out.println("2. Update Vehicle");
        System.out.println("3. Delete Vehicle");
        System.out.println("4. Search Vehicle");
        System.out.println("5. View All Vehicles");
        System.out.println("6. Back");
        System.out.print("Choose: ");

        choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                addVehicle();
                break;

            case 2:
                updateVehicle();
                break;

            case 3:
                deleteVehicle();
                break;

            case 4:
                searchVehicle();
                break;

            case 5:
                viewAllVehicles();
                break;

            case 6:
                break;

            default:
                System.out.println("Invalid choice.");
        }

    } while (choice != 6);
}
public static void customerManagement() {
    int choice;

    do {
        System.out.println("\n========== CUSTOMER MANAGEMENT ==========");
        System.out.println("1. Add Customer");
        System.out.println("2. Update Customer");
        System.out.println("3. Delete Customer");
        System.out.println("4. Search Customer");
        System.out.println("5. View All Customers");
        System.out.println("6. Back");
        System.out.print("Choose: ");

        choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                addCustomer();
                break;

            case 2:
                updateCustomer();
                break;

            case 3:
                deleteCustomer();
                break;

            case 4:
                searchCustomer();
                break;

            case 5:
                viewAllCustomers();
                break;

            case 6:
                break;

            default:
                System.out.println("Invalid choice.");
        }

    } while (choice != 6);
}

public static void rentalManagement() {
    int choice;

    do {
        System.out.println("\n========== RENTAL MANAGEMENT ==========");
        System.out.println("1. Create Rental");
        System.out.println("2. Cancel Rental");
        System.out.println("3. Return Vehicle");
        System.out.println("4. Search Rentals");
        System.out.println("5. View Rental By ID");
        System.out.println("6. View All Rentals");
        System.out.println("7. View Active Rentals");
        System.out.println("8. View Completed Rentals");
        System.out.println("9. View Customer Rentals");
        System.out.println("10. View Vehicle Rentals");
        System.out.println("11. Back");
        System.out.print("Choose: ");

        choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                createRental();
                break;

            case 2:
                cancelRental();
                break;

            case 3:
                returnVehicle();
                break;

            case 4:
                searchRentals();
                break;

            case 5:
                viewRentalById();
                break;

            case 6:
                viewAllRentals();
                break;

            case 7:
                viewActiveRentals();
                break;

            case 8:
                viewCompletedRentals();
                break;

            case 9:
                viewCustomerRentals();
                break;

            case 10:
                viewVehicleRentals();
                break;

            case 11:
                break;

            default:
                System.out.println("Invalid choice.");
        }

    } while (choice != 11);
}

public static void paymentManagement() {
    int choice;

    do {
        System.out.println("\n========== PAYMENT MANAGEMENT ==========");
        System.out.println("1. Create Payment");
        System.out.println("2. View Payment By ID");
        System.out.println("3. View Customer Payments");
        System.out.println("4. View Rental Payments");
        System.out.println("5. View All Payments");
        System.out.println("6. Back");
        System.out.print("Choose: ");

        choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                createPayment();
                break;

            case 2:
                viewPaymentById();
                break;

            case 3:
                viewCustomerPayments();
                break;

            case 4:
                viewRentalPayments();
                break;

            case 5:
                viewAllPayments();
                break;

            case 6:
                break;

            default:
                System.out.println("Invalid choice.");
        }

    } while (choice != 6);
}

public static void reports() {
    int choice;

    do {
        System.out.println("\n========== REPORTS ==========");
        System.out.println("1. Vehicle Report");
        System.out.println("2. Customer Report");
        System.out.println("3. Rental Report");
        System.out.println("4. Payment Report");
        System.out.println("5. Back");
        System.out.print("Choose: ");

        choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                vehicleReport();
                break;

            case 2:
                customerReport();
                break;

            case 3:
                rentalReport();
                break;

            case 4:
                paymentReport();
                break;

            case 5:
                break;

            default:
                System.out.println("Invalid choice.");
        }

    } while (choice != 5);
}   
    
    //====================== VEHICLE MANAGEMENT ===========
   public static void addVehicle() {
    System.out.println("\n========== ADD VEHICLE ==========");
    System.out.println("Enter Vehicle type (Car, Motorcycle, Truck): ");

    String type = scanner.nextLine();

    System.out.print("Enter Brand: ");
    String brand = scanner.nextLine();

    System.out.print("Enter Model: ");
    String model = scanner.nextLine();

    System.out.print("Enter Year: ");
    int year = Integer.parseInt(scanner.nextLine());

    System.out.print("Enter Price Per Day: ");
    double pricePerDay = Double.parseDouble(scanner.nextLine());

    if (type.equalsIgnoreCase("car")) {

        boolean success = vehicleManager.addVehicle(
                VehicleType.CAR, brand, model, year, pricePerDay);

        if (success) {
            System.out.println("Vehicle added successfully.");
        } else {
            System.out.println("Vehicle couldn't be added!");
        }

        return;
    }

    if (type.equalsIgnoreCase("motorcycle")) {

        boolean success = vehicleManager.addVehicle(
                VehicleType.MOTORCYCLE, brand, model, year, pricePerDay);

        if (success) {
            System.out.println("Vehicle added successfully.");
        } else {
            System.out.println("Vehicle couldn't be added!");
        }

        return;
    }

    if (type.equalsIgnoreCase("truck")) {

        boolean success = vehicleManager.addVehicle(
                VehicleType.TRUCK, brand, model, year, pricePerDay);

        if (success) {
            System.out.println("Vehicle added successfully.");
        } else {
            System.out.println("Vehicle couldn't be added!");
        }

        return;
    }

    System.out.println("Invalid Vehicle type!");
}
 public static void updateVehicle() {
    System.out.println("\n========== UPDATE VEHICLE ==========");

    System.out.print("Enter Vehicle ID: ");
    int vehicleId = Integer.parseInt(scanner.nextLine());

    Vehicle vehicle = vehicleManager.getVehicleById(vehicleId);

    if (vehicle == null) {
        System.out.println("Vehicle not found!");
        return;
    }

    System.out.print("Enter new Vehicle type (Car, Motorcycle, Truck): ");
    String type = scanner.nextLine();

    System.out.print("Enter new Brand: ");
    String brand = scanner.nextLine();

    System.out.print("Enter new Model: ");
    String model = scanner.nextLine();

    System.out.print("Enter new Year: ");
    int year = Integer.parseInt(scanner.nextLine());

    System.out.print("Enter new Price Per Day: ");
    double pricePerDay = Double.parseDouble(scanner.nextLine());

    VehicleType vehicleType;

    if (type.equalsIgnoreCase("car")) {
        vehicleType = VehicleType.CAR;
    } else if (type.equalsIgnoreCase("motorcycle")) {
        vehicleType = VehicleType.MOTORCYCLE;
    } else if (type.equalsIgnoreCase("truck")) {
        vehicleType = VehicleType.TRUCK;
    } else {
        System.out.println("Invalid Vehicle type!");
        return;
    }

    boolean success = vehicleManager.updateVehicle(vehicle, vehicleType,brand, model,year,pricePerDay);

    if (success) {
        System.out.println("Vehicle updated successfully.");
    } else {
        System.out.println("Vehicle couldn't be updated!");
    }
}
  public static void deleteVehicle() {
    System.out.println("\n========== DELETE VEHICLE ==========");

    System.out.print("Enter Vehicle ID: ");
    int vehicleId = Integer.parseInt(scanner.nextLine());

    boolean success = vehicleManager.deleteVehicle(vehicleId);

    if (success) {
        System.out.println("Vehicle deleted successfully.");
    } else {
        System.out.println("Vehicle couldn't be deleted!");
    }
}
public static void searchVehicle() {
    int choice;

    do {
        System.out.println("\n========== SEARCH VEHICLE ==========");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Keyword");
        System.out.println("3. Back");
        System.out.print("Choose: ");

        choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {

            case 1:
                System.out.print("Enter Vehicle ID: ");
                int vehicleId = Integer.parseInt(scanner.nextLine());

                Vehicle vehicle = vehicleManager.getVehicleById(vehicleId);

                if (vehicle != null) {
                    vehicle.getDetails();
                } else {
                    System.out.println("Vehicle not found!");
                }
                break;

            case 2:
                System.out.print("Enter keyword: ");
                String keyword = scanner.nextLine();

                ArrayList<Vehicle> results =
                        vehicleManager.searchVehicle(keyword);

                if (results.isEmpty()) {
                    System.out.println("No vehicles found!");
                } else {
                    for (Vehicle v : results) {
                        System.out.println("--------------------");
                        v.getDetails();
                    }
                }
                break;

            case 3:
                break;

            default:
                System.out.println("Invalid choice.");
        }

    } while (choice != 3);
}

public static void viewAllVehicles() {
    int choice;

    do {
        System.out.println("\n========== VIEW VEHICLES ==========");
        System.out.println("1. View All Vehicles");
        System.out.println("2. View Available Vehicles");
        System.out.println("3. Back");
        System.out.print("Choose: ");

        choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {

            case 1:
                ArrayList<Vehicle> allVehicles =
                        vehicleManager.getAllVehicles();

                if (allVehicles.isEmpty()) {
                    System.out.println("No vehicles found.");
                } else {
                    for (Vehicle v : allVehicles) {
                        System.out.println("--------------------");
                        v.getDetails();
                    }
                }
                break;

            case 2:
                ArrayList<Vehicle> availableVehicles =
                        vehicleManager.getAvailableVehicles();

                if (availableVehicles.isEmpty()) {
                    System.out.println("No available vehicles.");
                } else {
                    for (Vehicle v : availableVehicles) {
                        System.out.println("--------------------");
                        v.getDetails();
                    }
                }
                break;

            case 3:
                break;

            default:
                System.out.println("Invalid choice.");
        }

    } while (choice != 3);
}


    //======================= customer management ==============
public static void addCustomer() {
    System.out.println("\n========== ADD CUSTOMER ==========");

    System.out.print("Enter Username: ");
    String username = scanner.nextLine();

    System.out.print("Enter Password: ");
    String password = scanner.nextLine();

    System.out.print("Enter Full Name: ");
    String name = scanner.nextLine();

    System.out.print("Enter Phone Number: ");
    String phone = scanner.nextLine();

    System.out.print("Enter Email: ");
    String email = scanner.nextLine();

    System.out.print("Enter Address: ");
    String address = scanner.nextLine();

    System.out.print("Enter Driver License Number: ");
    String license = scanner.nextLine();

    boolean success = customerManager.addCustomer(
            username,
            password,
            name,
            phone,
            email,
            address,
            license
    );

    if (success) {
        System.out.println("Customer added successfully.");
    } else {
        System.out.println("Customer couldn't be added!");
    }
}

public static void updateCustomer() {
    System.out.println("\n========== UPDATE CUSTOMER ==========");

    System.out.print("Enter Customer Username: ");
    String username = scanner.nextLine();

    Customer customer = customerManager.getCustomerByUsername(username);

    if (customer == null) {
        System.out.println("Customer not found!");
        return;
    }

    System.out.print("Enter New Name: ");
    String name = scanner.nextLine();

    System.out.print("Enter New Phone: ");
    String phone = scanner.nextLine();

    System.out.print("Enter New Email: ");
    String email = scanner.nextLine();

    System.out.print("Enter New Address: ");
    String address = scanner.nextLine();

    System.out.print("Enter New License Number: ");
    String license = scanner.nextLine();

    boolean success = customerManager.updateCustomer(
            customer,
            name,
            phone,
            email,
            address,
            license
    );

    if (success) {
        System.out.println("Customer updated successfully.");
    } else {
        System.out.println("Customer couldn't be updated!");
    }
}

public static void deleteCustomer() {
    System.out.println("\n========== DELETE CUSTOMER ==========");

    System.out.print("Enter Customer Username: ");
    String username = scanner.nextLine();

    System.out.print("Are you sure you want to delete this customer? (y/n): ");
    String confirm = scanner.nextLine();

    if (confirm.equalsIgnoreCase("y")) {

        boolean success = customerManager.deleteCustomer(username);

        if (success) {
            System.out.println("Customer deleted successfully.");
        } else {
            System.out.println("Customer not found or couldn't be deleted!");
        }

    } else {
        System.out.println("Delete cancelled.");
    }
}
public static void searchCustomer() {
    int choice;

    do {
        System.out.println("\n========== SEARCH CUSTOMER ==========");
        System.out.println("1. Search by Username");
        System.out.println("2. Search by Keyword");
        System.out.println("3. Back");
        System.out.print("Choose: ");

        choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {

            case 1:
                System.out.print("Enter Username: ");
                String username = scanner.nextLine();

                Customer customer =
                        customerManager.getCustomerByUsername(username);

                if (customer != null) {
                    customer.getDetails();
                } else {
                    System.out.println("Customer not found!");
                }
                break;

            case 2:
                System.out.print("Enter Keyword: ");
                String keyword = scanner.nextLine();

                ArrayList<Customer> results =
                        customerManager.searchCustomer(keyword);

                if (results.isEmpty()) {
                    System.out.println("No customers found.");
                } else {
                    for (Customer c : results) {
                        System.out.println("----------------------------");
                        c.getDetails();
                    }
                }
                break;

            case 3:
                break;

            default:
                System.out.println("Invalid choice.");
        }

    } while (choice != 3);
}

public static void viewAllCustomers() {
    System.out.println("\n========== ALL CUSTOMERS ==========");

    ArrayList<Customer> customers =
            customerManager.getAllCustomers();

    if (customers.isEmpty()) {
        System.out.println("No customers found.");
        return;
    }

    for (Customer customer : customers) {
        System.out.println("----------------------------");
        customer.getDetails();
    }
}

    //======================= rental management ==============
public static void createRental() {
    System.out.println("\n========== CREATE RENTAL ==========");

    System.out.print("Enter Customer Username: ");
    String username = scanner.nextLine();

    Customer customer = customerManager.getCustomerByUsername(username);

    if (customer == null) {
        System.out.println("Customer not found!");
        return;
    }

    System.out.print("Enter Vehicle ID: ");
    int vehicleId = Integer.parseInt(scanner.nextLine());

    Vehicle vehicle = vehicleManager.getVehicleById(vehicleId);

    if (vehicle == null) {
        System.out.println("Vehicle not found!");
        return;
    }

    System.out.print("Enter Start Date (YYYY-MM-DD): ");
    LocalDate startDate = LocalDate.parse(scanner.nextLine());

    System.out.print("Enter End Date (YYYY-MM-DD): ");
    LocalDate endDate = LocalDate.parse(scanner.nextLine());

    boolean success = rentalManager.createRental(customer, vehicle, startDate, endDate);

    if (success) {
        System.out.println("Rental created successfully.");
    } else {
        System.out.println("Rental couldn't be created!");
    }
}

public static void cancelRental() {
    System.out.println("\n========== CANCEL RENTAL ==========");

    System.out.print("Enter Rental ID: ");
    int rentalId = Integer.parseInt(scanner.nextLine());

    boolean success = rentalManager.cancelRental(rentalId);

    if (success) {
        System.out.println("Rental cancelled successfully.");
    } else {
        System.out.println("Rental couldn't be cancelled!");
    }
}

public static void returnVehicle() {
    System.out.println("\n========== RETURN VEHICLE ==========");

    System.out.print("Enter Rental ID: ");
    int rentalId = Integer.parseInt(scanner.nextLine());

    boolean success = rentalManager.returnVehicle(rentalId);

    if (success) {
        System.out.println("Vehicle returned successfully.");
    } else {
        System.out.println("Vehicle couldn't be returned!");
    }
}

public static void searchRentals() {
    System.out.println("\n========== SEARCH RENTALS ==========");

    System.out.print("Enter keyword: ");
    String keyword = scanner.nextLine();

    ArrayList<Rental> results =
            rentalManager.searchRental(keyword);

    if (results.isEmpty()) {
        System.out.println("No rentals found.");
        return;
    }

    for (Rental rental : results) {
        System.out.println("----------------------------");
        rental.getDetails();
    }
}   

public static void viewRentalById() {
    System.out.println("\n========== RENTAL BY ID ==========");

    System.out.print("Enter Rental ID: ");
    int rentalId = Integer.parseInt(scanner.nextLine());

    Rental rental = rentalManager.getRentalById(rentalId);

    if (rental != null) {
        rental.getDetails();
    } else {
        System.out.println("Rental not found!");
    }
} 

public static void viewAllRentals() {
    System.out.println("\n========== ALL RENTALS ==========");

    ArrayList<Rental> rentals =
            rentalManager.getAllRentals();

    if (rentals.isEmpty()) {
        System.out.println("No rentals found.");
        return;
    }

    for (Rental rental : rentals) {
        System.out.println("----------------------------");
        rental.getDetails();
    }
}
public static void viewActiveRentals() {
    System.out.println("\n========== ACTIVE RENTALS ==========");

    ArrayList<Rental> rentals =
            rentalManager.getActiveRentals();

    if (rentals.isEmpty()) {
        System.out.println("No active rentals found.");
        return;
    }

    for (Rental rental : rentals) {
        System.out.println("----------------------------");
        rental.getDetails();
    }
}

public static void viewCompletedRentals() {
    System.out.println("\n========== COMPLETED RENTALS ==========");

    ArrayList<Rental> rentals =
            rentalManager.getCompletedRentals();

    if (rentals.isEmpty()) {
        System.out.println("No completed rentals found.");
        return;
    }

    for (Rental rental : rentals) {
        System.out.println("----------------------------");
        rental.getDetails();
    }
}

public static void viewCustomerRentals() {
    System.out.println("\n========== CUSTOMER RENTALS ==========");

    System.out.print("Enter Customer Username: ");
    String username = scanner.nextLine();

    ArrayList<Rental> rentals =
            rentalManager.getCustomerRentals(username);

    if (rentals.isEmpty()) {
        System.out.println("No rentals found for this customer.");
        return;
    }

    for (Rental rental : rentals) {
        System.out.println("----------------------------");
        rental.getDetails();
    }
}

public static void viewVehicleRentals() {
    System.out.println("\n========== VEHICLE RENTALS ==========");

    System.out.print("Enter Vehicle ID: ");
    int vehicleId = Integer.parseInt(scanner.nextLine());

    ArrayList<Rental> rentals =
            rentalManager.getVehicleRentals(vehicleId);

    if (rentals.isEmpty()) {
        System.out.println("No rentals found for this vehicle.");
        return;
    }

    for (Rental rental : rentals) {
        System.out.println("----------------------------");
        rental.getDetails();
    }
}

    //======================= payment management ==============
public static void createPayment() {
    System.out.println("\n========== CREATE PAYMENT ==========");

    System.out.print("Enter Rental ID: ");
    int rentalId = Integer.parseInt(scanner.nextLine());

    Rental rental = rentalManager.getRentalById(rentalId);

    if (rental == null) {
        System.out.println("Rental not found!");
        return;
    }

    System.out.println("Select Payment Method:");
    System.out.println("1. CASH");
    System.out.println("2. CARD");
    System.out.print("Choose: ");

    int methodChoice = Integer.parseInt(scanner.nextLine());

    PaymentMethod paymentMethod;

    switch (methodChoice) {
        case 1:
            paymentMethod = PaymentMethod.CASH;
            break;

        case 2:
            paymentMethod = PaymentMethod.CARD;
            break;

        default:
            System.out.println("Invalid payment method!");
            return;
    }

    boolean success =
            paymentManager.createPayment(rental, paymentMethod);

    if (success) {
        System.out.println("Payment created successfully.");
    } else {
        System.out.println("Payment couldn't be created!");
    }
}  

public static void viewPaymentById() {
    System.out.println("\n========== PAYMENT BY ID ==========");

    System.out.print("Enter Payment ID: ");
    int paymentId = Integer.parseInt(scanner.nextLine());

    Payment payment = paymentManager.getPaymentById(paymentId);

    if (payment != null) {
        payment.getDetails();
    } else {
        System.out.println("Payment not found!");
    }
}

public static void viewCustomerPayments() {
    System.out.println("\n========== CUSTOMER PAYMENTS ==========");

    System.out.print("Enter Customer ID: ");
    int customerId = Integer.parseInt(scanner.nextLine());

    ArrayList<Payment> payments =
            paymentManager.getCustomerPayments(customerId);

    if (payments.isEmpty()) {
        System.out.println("No payments found for this customer.");
        return;
    }

    for (Payment payment : payments) {
        System.out.println("----------------------------");
        payment.getDetails();
    }
}

public static void viewRentalPayments() {

    System.out.println("\n========== RENTAL PAYMENTS ==========");

    System.out.print("Enter Rental ID: ");
    int rentalId = Integer.parseInt(scanner.nextLine());

    ArrayList<Payment> payments =
            paymentManager.getRentalPayments(rentalId);

    if (payments.isEmpty()) {
        System.out.println("No payments found for this rental.");
        return;
    }

    for (Payment payment : payments) {
        System.out.println("--------------------------------");
        payment.getDetails();
    }
}

public static void viewAllPayments() {
    System.out.println("\n========== ALL PAYMENTS ==========");

    ArrayList<Payment> payments =
            paymentManager.getAllPayments();

    if (payments.isEmpty()) {
        System.out.println("No payments found.");
        return;
    }

    for (Payment payment : payments) {
        System.out.println("----------------------------");
        payment.getDetails();
    }
}    
    //======================= reports management ==============
public static void vehicleReport() {

    System.out.println("\n========== VEHICLE REPORT ==========");

    ArrayList<Vehicle> vehicles = vehicleManager.getAllVehicles();

    int available = 0;
    int rented = 0;
    int maintenance = 0;

    for (Vehicle vehicle : vehicles) {

        if (vehicle.isAvailable()) {
            available++;
        } else if (vehicle.isRented()) {
            rented++;
        } else if (vehicle.isMaintenance()) {
            maintenance++;
        }
    }

    System.out.println("Total Vehicles   : " + vehicles.size());
    System.out.println("Available        : " + available);
    System.out.println("Rented           : " + rented);
    System.out.println("Maintenance      : " + maintenance);
}

public static void customerReport() {

    System.out.println("\n========== CUSTOMER REPORT ==========");

    ArrayList<Customer> customers =
            customerManager.getAllCustomers();

    System.out.println("Total Customers: " + customers.size());

    if (customers.isEmpty()) {
        System.out.println("No customers found.");
        return;
    }

    System.out.println("\n----- Customer List -----");

    for (Customer customer : customers) {
        customer.getDetails();
        System.out.println("--------------------------------");
    }
}

public static void rentalReport() {

    System.out.println("\n========== RENTAL REPORT ==========");

    ArrayList<Rental> allRentals =
            rentalManager.getAllRentals();

    ArrayList<Rental> activeRentals =
            rentalManager.getActiveRentals();

    ArrayList<Rental> completedRentals =
            rentalManager.getCompletedRentals();

    System.out.println("Total Rentals     : " + allRentals.size());
    System.out.println("Active Rentals    : " + activeRentals.size());
    System.out.println("Completed Rentals : " + completedRentals.size());
}

public static void paymentReport() {

    System.out.println("\n========== PAYMENT REPORT ==========");

    ArrayList<Payment> payments =
            paymentManager.getAllPayments();

    int paid = 0;
    int failed = 0;
    int refunded = 0;

    double totalAmount = 0;

    for (Payment payment : payments) {

        totalAmount += payment.getAmount();

        if (payment.getStatus() == PaymentStatus.PAID) {
            paid++;
        } else if (payment.getStatus() == PaymentStatus.FAILED) {
            failed++;
        } else if (payment.getStatus() == PaymentStatus.REFUNDED) {
            refunded++;
        }
    }

    System.out.println("Total Payments : " + payments.size());
    System.out.println("Paid           : " + paid);
    System.out.println("Failed         : " + failed);
    System.out.println("Refunded       : " + refunded);
    System.out.println("Total Amount   : " + totalAmount);
}   

    //=========================================================================
    //======================================================================
     //===================== CUSTOMER MENU ================
public static void browseVehicles() {

    int choice;

    do {
        System.out.println("\n========== BROWSE VEHICLES ==========");
        System.out.println("1. View Vehicles");
        System.out.println("2. Back");
        System.out.print("Choose: ");

        choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {

            case 1:
                viewAllVehicles();
                break;

            case 2:
                break;

            default:
                System.out.println("Invalid choice.");
        }

    } while (choice != 2);
}

public static void searchVehicles() {

    int choice;

    do {
        System.out.println("\n========== SEARCH VEHICLES ==========");
        System.out.println("1. Search By ID");
        System.out.println("2. Search By Keyword");
        System.out.println("3. Search By Price Range");
        System.out.println("4. Back");
        System.out.print("Choose: ");

        choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {

            case 1:
                searchVehicleById();
                break;

            case 2:
                searchVehicleByKeyword();
                break;

            case 3:
                searchVehicleByPriceRange();
                break;

            case 4:
                break;

            default:
                System.out.println("Invalid choice.");
        }

    } while (choice != 4);
}

public static void myRentals() {

    int choice;

    do {
        System.out.println("\n========== MY RENTALS ==========");
        System.out.println("1. View My Rentals");
        System.out.println("2. View Rental Details");
        System.out.println("3. View Rental Payment");
        System.out.println("4. Back");
        System.out.print("Choose: ");

        choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {

            case 1:
                viewMyRentals();
                break;

            case 2:
                viewMyRentalDetails();
                break;

            case 3:
                viewMyRentalPayment();
                break;

            case 4:
                break;

            default:
                System.out.println("Invalid choice.");
        }

    } while (choice != 4);
}

public static void myPayments() {

    int choice;

    do {
        System.out.println("\n========== MY PAYMENTS ==========");
        System.out.println("1. View My Payments");
        System.out.println("2. View Payment Details");
        System.out.println("3. Back");
        System.out.print("Choose: ");

        choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {

            case 1:
                viewMyPayments();
                break;

            case 2:
                viewMyPaymentDetails();
                break;

            case 3:
                break;

            default:
                System.out.println("Invalid choice.");
        }

    } while (choice != 3);
}

public static void myProfile() {

    int choice;

    do {
        System.out.println("\n========== MY PROFILE ==========");
        System.out.println("1. View My Profile");
        System.out.println("2. Update My Profile");
        System.out.println("3. Back");
        System.out.print("Choose: ");

        choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {

            case 1:
                viewMyProfile();
                break;

            case 2:
                updateMyProfile();
                break;

            case 3:
                break;

            default:
                System.out.println("Invalid choice.");
        }

    } while (choice != 3);
}


    //======================================
    //======================================
    public static void searchVehicleById() {

    System.out.println("\n========== SEARCH BY ID ==========");

    System.out.print("Enter Vehicle ID: ");
    int vehicleId = Integer.parseInt(scanner.nextLine());

    Vehicle vehicle = vehicleManager.getVehicleById(vehicleId);

    if (vehicle == null) {
        System.out.println("Vehicle not found.");
        return;
    }

    vehicle.getDetails();
}
    
    public static void searchVehicleByKeyword() {

    System.out.println("\n========== SEARCH BY KEYWORD ==========");

    System.out.print("Enter keyword (Brand / Model / Type): ");
    String keyword = scanner.nextLine();

    ArrayList<Vehicle> vehicles =
            vehicleManager.searchVehicle(keyword);

    if (vehicles.isEmpty()) {
        System.out.println("No vehicles found.");
        return;
    }

    for (Vehicle vehicle : vehicles) {
        System.out.println("--------------------------------");
        vehicle.getDetails();
    }
}
    
    public static void searchVehicleByPriceRange() {

    System.out.println("\n========== SEARCH BY PRICE RANGE ==========");

    System.out.print("Enter Minimum Price: ");
    double minPrice = Double.parseDouble(scanner.nextLine());

    System.out.print("Enter Maximum Price: ");
    double maxPrice = Double.parseDouble(scanner.nextLine());

    if (minPrice < 0 || maxPrice < minPrice) {
        System.out.println("Invalid price range.");
        return;
    }

    ArrayList<Vehicle> vehicles =
            vehicleManager.searchVehicleByPriceRange(minPrice, maxPrice);

    if (vehicles.isEmpty()) {
        System.out.println("No vehicles found in this price range.");
        return;
    }

    for (Vehicle vehicle : vehicles) {
        System.out.println("--------------------------------");
        vehicle.getDetails();
    }
}
    
    public static void viewMyRentals() {

    System.out.println("\n========== MY RENTALS ==========");

    ArrayList<Rental> rentals =
            rentalManager.getCustomerRentals(currentUser.getUserName());

    if (rentals.isEmpty()) {
        System.out.println("You have no rentals.");
        return;
    }

    for (Rental rental : rentals) {
        System.out.println("--------------------------------");
        rental.getDetails();
    }
}
   
    public static void viewMyRentalDetails() {

    System.out.println("\n========== RENTAL DETAILS ==========");

    System.out.print("Enter Rental ID: ");
    int rentalId = Integer.parseInt(scanner.nextLine());

    Rental rental = rentalManager.getRentalById(rentalId);

    if (rental == null) {
        System.out.println("Rental not found.");
        return;
    }

    if (!rental.getCustomer().getUserName()
            .equalsIgnoreCase(currentUser.getUserName())) {

        System.out.println("This rental does not belong to you.");
        return;
    }

    rental.getDetails();
}
   
    public static void viewMyRentalPayment() {

    System.out.println("\n========== RENTAL PAYMENT ==========");

    System.out.print("Enter Rental ID: ");
    int rentalId = Integer.parseInt(scanner.nextLine());

    Rental rental = rentalManager.getRentalById(rentalId);

    if (rental == null) {
        System.out.println("Rental not found.");
        return;
    }

    if (!rental.getCustomer().getUserName()
            .equalsIgnoreCase(currentUser.getUserName())) {

        System.out.println("This rental does not belong to you.");
        return;
    }

    ArrayList<Payment> payments =
            paymentManager.getRentalPayments(rentalId);

    if (payments.isEmpty()) {
        System.out.println("No payments found for this rental.");
        return;
    }

    for (Payment payment : payments) {
        System.out.println("--------------------------------");
        payment.getDetails();
    }
}
    public static void viewMyPayments() {

    System.out.println("\n========== MY PAYMENTS ==========");

    ArrayList<Payment> payments =
            paymentManager.getCustomerPayments(currentUser.getCustomerId());

    if (payments.isEmpty()) {
        System.out.println("You have no payments.");
        return;
    }

    for (Payment payment : payments) {
        System.out.println("--------------------------------");
        payment.getDetails();
    }
}
    
    public static void viewMyPaymentDetails() {

    System.out.println("\n========== PAYMENT DETAILS ==========");

    System.out.print("Enter Payment ID: ");
    int paymentId = Integer.parseInt(scanner.nextLine());

    Payment payment = paymentManager.getPaymentById(paymentId);

    if (payment == null) {
        System.out.println("Payment not found.");
        return;
    }

    if (!payment.getRental().getCustomer().getUserName()
            .equalsIgnoreCase(currentUser.getUserName())) {

        System.out.println("This payment does not belong to you.");
        return;
    }

    payment.getDetails();
}
    
public static void viewMyProfile() {

    System.out.println("\n========== MY PROFILE ==========");

    System.out.println("Username: " + currentUser.getUserName());

    currentUser.getDetails();
} 
    
  public static void updateMyProfile() {

    int choice;

    do {
        System.out.println("\n========== UPDATE MY PROFILE ==========");
        System.out.println("1. Update Username");
        System.out.println("2. Change Password");
        System.out.println("3. Update Personal Information");
        System.out.println("4. Back");
        System.out.print("Choose: ");

        choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {

            case 1:
                updateMyUsername();
                break;

            case 2:
                changeMyPassword();
                break;

            case 3:
                updateMyPersonalInfo();
                break;

            case 4:
                break;

            default:
                System.out.println("Invalid choice.");
        }

    } while (choice != 4);
}
  
  public static void updateMyUsername() {

    System.out.println("\n========== UPDATE USERNAME ==========");

    System.out.print("Enter New Username: ");
    String newUsername = scanner.nextLine();

    boolean success =
            customerManager.updateUsername(currentUser, newUsername);

    if (success) {
        System.out.println("Username updated successfully.");
    } else {
        System.out.println("Failed to update username.");
    }
}
  
  public static void changeMyPassword() {

    System.out.println("\n========== CHANGE PASSWORD ==========");

    System.out.print("Enter Current Password: ");
    String oldPassword = scanner.nextLine();

    System.out.print("Enter New Password: ");
    String newPassword = scanner.nextLine();

    boolean success =
            currentUser.changePassword(oldPassword, newPassword);

    if (success) {
        System.out.println("Password changed successfully.");
    } else {
        System.out.println("Current password is incorrect.");
    }
}
  
  public static void updateMyPersonalInfo() {

    System.out.println("\n========== UPDATE PERSONAL INFORMATION ==========");

    System.out.print("Enter New Name: ");
    String name = scanner.nextLine();

    System.out.print("Enter New Phone: ");
    String phone = scanner.nextLine();

    System.out.print("Enter New Email: ");
    String email = scanner.nextLine();

    System.out.print("Enter New Address: ");
    String address = scanner.nextLine();

    System.out.print("Enter New License Number: ");
    String license = scanner.nextLine();

    boolean success = customerManager.updateCustomer(
            currentUser,
            name,
            phone,
            email,
            address,
            license
    );

    if (success) {
        System.out.println("Personal information updated successfully.");
    } else {
        System.out.println("Failed to update personal information.");
    }
}
    
    
}
