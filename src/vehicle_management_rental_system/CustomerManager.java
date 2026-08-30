
package vehicle_management_rental_system;

import java.util.ArrayList;


public class CustomerManager {
   private final ArrayList<Customer> customerList = new ArrayList<>();
   
   public boolean addCustomer(String userName, String password, String name,String phone ,String email ,String address ,String licenseNumber){
        for(Customer c : customerList){
        if (c.getUserName().equalsIgnoreCase(userName)){
         //leave printing to main // System.out.println("Error.. Customer with username : " + userName + " already exists.");
            return false;
            }
        if(c.getPhone().equals(phone) || c.getLicenseNumber().equals(licenseNumber)){return false;}
        }
      
       
       Customer customer;
        customer = new Customer(userName, password, Role.CUSTOMER,name, phone, email, address, licenseNumber);
        customerList.add(customer);
        return true;
//        System.out.println("Added customer successfully");
   }
    
   public boolean deleteCustomer(String userName){
       Customer customerToDelete = null;
   for (Customer c : customerList){
    if(c.getUserName().equalsIgnoreCase(userName)){
        customerToDelete = c;
        break;
        }
        
    }
   if (customerToDelete != null){
       customerList.remove(customerToDelete);
       return true;
   }
   
        return false;
   }
   
   
    public Customer getCustomerByUsername(String userName){
      for(Customer c : customerList){
          if (c.getUserName().equalsIgnoreCase(userName)){
             return c;
          }
      }
//      System.out.println("This customer user is not found");
      return null;
  }
    
    public boolean updateCustomer(Customer customer, String newName, String newPhone, String newEmail, String newAddress, String newLicenseNumber) {
        if (customer == null) {
        return false;
        }
        for (Customer c : customerList) {
        
        if (c.getCustomerId() != customer.getCustomerId()) {
            if (c.getPhone().equals(newPhone) || c.getLicenseNumber().equalsIgnoreCase(newLicenseNumber)) {
                return false; 
            }
        }
    }

    
        customer.setName(newName);
        customer.setPhone(newPhone);
        customer.setEmail(newEmail);
        customer.setAddress(newAddress);
        customer.setLicenseNumber(newLicenseNumber);

        return true; 
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
    
    public int getCustomerCount(){
    return customerList.size();
    }
    
    
    //needs tracking and comprehending how it works
    public Customer authenticateCustomer(String userName, String password){
        Customer c = getCustomerByUsername(userName);
        if (c!= null && c.login(userName, password)){
        return c;
        }
        return null;
    }
    
    public boolean updateUsername(Customer customer, String newUsername){
        if(customer == null || newUsername == null || newUsername.trim().isEmpty()){
            return false;
        }
        String trimmedUsername = newUsername.trim();
        
        for(Customer c : customerList){
            if(c.getCustomerId() != customer.getCustomerId()){
                if((c.getUserName().equalsIgnoreCase(trimmedUsername))){
                return false;}
            }
        }
       customer.setUserName(trimmedUsername);
       return true;
    }
}
