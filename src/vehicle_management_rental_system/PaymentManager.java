

package vehicle_management_rental_system;

import java.util.ArrayList;


public class PaymentManager {
    private ArrayList<Payment> paymentList = new ArrayList<>();

public boolean createPayment(Rental rental, PaymentMethod paymentMethod){
    //to avoid paying twice we need to check if this rental has already had a payment 
    for(Payment p : paymentList){
        if(p.getRental() == rental){
            return false;
        }
    }
    if(rental == null || rental.getStatus()!= RentalStatus.ACTIVE){
        return false;
    }
   
    double amount = rental.getTotalPrice();
    
    Payment payment = new Payment(rental,amount,paymentMethod);
    paymentList.add(payment);
return true;
}

public Payment getPaymentById(int paymentId){
    if(paymentId <=0){return null;}
    for(Payment p : paymentList){
        if(p.getPaymentId() == paymentId){
            return p;
        }
    }
    return null;
}
public ArrayList<Payment> getRentalPayments(int rentalId){
    ArrayList<Payment> results = new ArrayList<>();
    for(Payment p : paymentList){
        if(p.getRental()!= null && p.getRental().getRentalId() == rentalId){
            results.add(p);
        }
        
    }
    return results;
}

public ArrayList<Payment> getCustomerPayments(int customerId){
    ArrayList<Payment> results = new ArrayList<>();
    for(Payment p : paymentList){
        if(p.getRental()!= null && 
           p.getRental().getCustomer()!= null && 
           p.getRental().getCustomer().getCustomerId() == customerId){
            results.add(p);
        }
    }
    return results;

}

public ArrayList<Payment> getAllPayments(){
    return new ArrayList<>(paymentList);
}

public int getPaymentCount(){
    return paymentList.size(); 
}

    
}







