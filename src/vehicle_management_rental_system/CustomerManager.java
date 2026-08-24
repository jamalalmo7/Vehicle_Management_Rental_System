
package vehicle_management_rental_system;

import java.util.ArrayList;


public class CustomerManager {
   private ArrayList<Customer> customerList = new ArrayList<>();
   
   public void addCustomer(String userName, String password, String name,String phone ,String email ,String address ,String licenseNumber){
        for(Customer c : customerList){
        if (c.getUserName().equalsIgnoreCase(userName)){
            System.out.println("Error.. Customer with username : " + userName + " already exists.");
            return;
            }
        }
       
       
       Customer customer;
        customer = new Customer(userName, password, Role.CUSTOMER,name, phone, email, address, licenseNumber);
        customerList.add(customer);
        System.out.println("Added customer successfully");
   }
    
   public void deleteCustomer(String userName){
       Customer customerToDelete = null;
   for (Customer c : customerList){
    if(c.getUserName().equalsIgnoreCase(userName)){
        customerToDelete = c;
        break;
        }
        
    }
   if (customerToDelete != null){
       customerList.remove(customerToDelete);
       System.out.println("Delete customer successfully");
       return;
   }
   
       System.out.println("This user is not found");
   
   }
   
   
    public Customer getCustomerByUsername(String userName){
      for(Customer c : customerList){
          if (c.getUserName().equalsIgnoreCase(userName)){
             return c;
          }
      }
      System.out.println("This customer user is not found");
      return null;
  }
    
    
    public void updateCustomer(Customer customer, String name, String phone, String email, String address, String licenseNumber){
        if (customer == null){
            System.out.println("Error.. Provided customer object is null");
        return;
        }
        
        customer.setName(name);
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setAddress(address);
        customer.setLicenseNumber(licenseNumber);
        
        System.out.println("Update done successfully");
        System.out.println("Customer after update: ");
        customer.getDetails();
    
    
    }
    
    public ArrayList<Customer> searchCustomer(String keyword){
        ArrayList<Customer> results = new ArrayList<>();
        
        if (keyword == null || keyword.trim().isEmpty()){return results;}
        // to see if the input is empty or trimed to nothing for check and safety
        String searchKeyword = keyword.toLowerCase();
        for(Customer c : customerList){
            boolean matchName = c.getName()!= null && c.getName().toLowerCase().contains(searchKeyword);
            boolean matchPhone = c.getPhone()!= null && c.getPhone().toLowerCase().contains(searchKeyword);
                if(matchName || matchPhone){
                    results.add(c);
                }
            }
                return results;
                }
        
    
    
    
    public ArrayList<Customer> getAllCustomers(){
        return new ArrayList<>(customerList);
    
    
    }
    
}
