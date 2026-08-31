
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

    public static void AdminMenu(){
        System.out.println("hello i am adminmenue");}
    public static void CustomerMenu(){System.out.println("hello i am customermenu");}
    
}
