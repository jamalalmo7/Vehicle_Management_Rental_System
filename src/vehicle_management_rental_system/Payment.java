
package vehicle_management_rental_system;

import java.time.LocalDate;


public class Payment {
    private int paymentId;
    private int rentalId;
    private double amount;
    private LocalDate paymentDate;
    private PaymentMethod paymentMethod;// Perhaps would be enum 
    private PaymentStatus status;// Perhaps would be enum 

    public Payment(int paymentId, int rentalId, double amount, LocalDate paymentDate, PaymentMethod paymentMethod, PaymentStatus status) {
        this.paymentId = paymentId;
        this.rentalId = rentalId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
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
        System.out.println("RentalId: " + getRentalId() );
        System.out.println("Amount: " + getAmount() );
        System.out.println("PaymentDate: " + getPaymentDate());
        System.out.println("Status: " + getStatus());
    
    }
    
    public void markAsPaid(){ // we can replace these is.. functions with the getstatus and that's it 
    this.status = PaymentStatus.PAID;
    }
    
    public void markAsFailed(){
    this.status = PaymentStatus.FAILED;
    }
    
    public boolean isPaid(){
    return this.status == PaymentStatus.PAID  ;
    }
    
    public boolean isFailed(){
    return this.status == PaymentStatus.FAILED;
    }
    
    public boolean isPending(){
    return this.status == PaymentStatus.PENDING;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
