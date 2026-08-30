
package vehicle_management_rental_system;

import java.time.LocalDate;


public class Payment {
    private static int CountId  = 0;
    private final int paymentId;
    private Rental rental;
    private double amount;
    private LocalDate paymentDate;
    private PaymentMethod paymentMethod;// enum 
    private PaymentStatus status;// enum 

    public Payment(Rental rental, double amount, PaymentMethod paymentMethod) {
        this.paymentId = ++CountId;
        this.rental = rental;
        this.amount = amount;
        this.paymentDate = LocalDate.now();
        this.paymentMethod = paymentMethod;
        this.status = PaymentStatus.PAID;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
    
   /*Methods =======================================
    ================================================
    */
    public void getDetails(){
        System.out.println("Payment Details ");
        System.out.println("----------------");
        System.out.println("PaymentId: " + getPaymentId() );
        System.out.println("RentalId: " + getRental());
        System.out.println("Amount: " + getAmount() );
        System.out.println("PaymentDate: " + getPaymentDate());
        System.out.println("Status: " + getStatus());
    
    }
    
//    public void markAsPaid(){ // we can replace these is.. functions with the getstatus and that's it 
//    this.status = PaymentStatus.PAID;
//    }
//    
//    public void markAsFailed(){
//    this.status = PaymentStatus.FAILED;
//    }  we comment this for now because our method is not wnated for marking the constructor is already making the PAID status 
    
    public boolean isPaid(){
    return this.status == PaymentStatus.PAID  ;
    }
    
    public boolean isFailed(){
    return this.status == PaymentStatus.FAILED;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
