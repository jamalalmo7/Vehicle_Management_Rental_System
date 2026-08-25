
package vehicle_management_rental_system;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class Rental {
    private static int idCounter = 0;
    private int rentalId; 
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalPrice;
    private RentalStatus status;//enum
    
    private Customer customer;// (has a ) relationship to reference the rental to the customer with set/get.customer
    private Vehicle vehicle;// for knowing which car reference 
    private Payment payment;
    
    public Rental(){}
    public Rental 
    (Customer customer, Vehicle vehicle, LocalDate startDate, LocalDate endDate , double totalPrice,RentalStatus status){
      this.rentalId = ++idCounter;
      this.customer = customer;
      this.vehicle = vehicle;
      this.startDate = startDate;
      this.endDate = endDate;
      this.totalPrice = totalPrice;
      this.status = status;
      
      
      
        
    
    
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }
//Setter/Getter for customer, vehicle,and payment
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    
     public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
    
  // Above Setter/Getter for customer, vehicle,and payment

   /* NOw the Methods =====================================
    =======================================================
    */
    
     public void getDetails(){
        System.out.println("Rental Details ");
        System.out.println("----------------");
        System.out.println("RentalId: " + getRentalId());
        System.out.println("StartDate: " + getStartDate());
        System.out.println("=EndDate: " + getEndDate());
        System.out.println("TotalPrice: " + getTotalPrice());
        System.out.println("Status: " + getStatus());
    
    }
    
    public static int calculateDuration(LocalDate startDate,LocalDate endDate){
    return (int) ChronoUnit.DAYS.between(startDate,endDate);
    
    }
    
//     public void startRental(){
//     this.status = RentalStatus.ACTIVE;
//     }//no need cause there is no any status before active to change directly will take active if booked 
     
     public void completeRental(){
     if(this.status == RentalStatus.ACTIVE){
        this.status = RentalStatus.COMPLETED;
     }
     }
     
     public void cancelRental(){
     if(this.status == RentalStatus.ACTIVE){
     this.status = RentalStatus.COMPLETED;
     }
         
     }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
