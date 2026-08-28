

package vehicle_management_rental_system;

import java.util.ArrayList;


public class PaymentManager {
    private ArrayList<Payment> paymentList = new ArrayList<>();

public void createPayment(Rental rental, PaymentMethod paymentMethod){
    if(rental == null){
        System.out.println("Rental is not found!");
        return;
    }
    if(rental.getStatus()!= RentalStatus.ACTIVE){
        System.out.println("Cannot pay, the rental is not active");
        return;
    }
    double amount = rental.getTotalPrice();
    
    Payment payment;
    payment = new Payment(rental,amount,paymentMethod);
    paymentList.add(payment);
    System.out.println("Payment created successfully.");
}

public Payment getPaymentById(int paymentId){
    for(Payment p : paymentList){
        if(p.getPaymentId() == paymentId){
            return p;
        }
    }
    System.out.println("Payment ID is not found");
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







