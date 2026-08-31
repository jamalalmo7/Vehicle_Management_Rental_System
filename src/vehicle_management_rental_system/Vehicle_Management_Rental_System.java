
package vehicle_management_rental_system;

import java.util.Scanner;


public class Vehicle_Management_Rental_System {
    private static Scanner scanner = new Scanner(System.in);
    private static CustomerManager customerManager = new CustomerManager();
    private static Customer currentUser = null;
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
            AdminMenu();
        } 
        else {
            CustomerMenu();
        }

        currentUser = null; 
    }
}

    
    public static void registerCustomer(){
        System.out.println("\n======= REGISTER NEW CUSTOMER ========");
        System.out.println("Enter Username: ");
        String username = scanner.nextLine();
        
        System.out.println("Enter Password: ");
        String password = scanner.nextLine();
        
        System.out.println("Enter Fullname: ");
        String name = scanner.nextLine();
        
        System.out.println("Enter Phone: ");
        String phone = scanner.nextLine();
        
        System.out.println("Enter Email: ");
        String email = scanner.nextLine();
        
        System.out.println("Enter Address: ");
        String address = scanner.nextLine();
        
        System.out.println("Enter LicenseNumber: ");
        String licensNumber = scanner.nextLine();
        
        boolean isSuccess = customerManager.addCustomer(username, password, name, phone, email, address, licensNumber);
        if(isSuccess){
            System.out.println("\n*Success* Account created successfully! you can no login.");
        }else{
            System.out.println("\nError* Registration failed! Username,Phone,or License Number already exists.");
        }
    
    }
    public static void AdminMenu(){}
    public static void CustomerMenu(){}
    
}
