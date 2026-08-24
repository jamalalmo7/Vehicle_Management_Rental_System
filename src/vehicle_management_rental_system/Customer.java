
package vehicle_management_rental_system;


public class Customer extends User {
    private String name;
    private String phone;
    private String email;
    private String address;
    private String licenseNumber;
 
    public Customer(String userName, String password, Role role, String name, String phone, String email, String address, String licenseNumber) {
        super(userName, password, role);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.licenseNumber = licenseNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    
    public void getDetails(){
        System.out.println("Customer Details ");
        System.out.println("----------------");
        System.out.println("Name: " + getName());
        System.out.println("Phone: " + getPhone());
        System.out.println("Email: " + getEmail());
        System.out.println("Address: " + getAddress());
        System.out.println("LicensNumber: " + getLicenseNumber());
    
    }
    
//    public void updateProfile(){
//      would be with the Setters
//    }
    
    
    
    
    
    
    
    
    
    
}
