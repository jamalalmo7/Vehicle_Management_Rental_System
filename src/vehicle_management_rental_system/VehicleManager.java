

package vehicle_management_rental_system;

import java.util.ArrayList;


public class VehicleManager {
    private ArrayList<Vehicle> vehicleList = new ArrayList<>(); 
     
     
    public void addVehicle(int id, VehicleType type,String brand, String model,int year , double pricePerDay,VehicleStatus status){
   
        Vehicle vehicle;
        
        if (type == VehicleType.CAR){
            vehicle = new Car(id , type, brand , model, year, pricePerDay, status);
        }
        else if (type == VehicleType.MOTORCYCLE){
            vehicle = new MotorCycle(id , type, brand , model, year, pricePerDay, status);
        }
        else {
            vehicle = new Truck(id , type, brand , model, year, pricePerDay, status);
        }
  
        vehicleList.add(vehicle);
        System.out.println("Vehicle added successfully");
//        System.out.println("vehicle is : " + vehicleList);  This is just to check if it's added 
    }
    
    
    //      boolean isfound = false;
    //                isfound = true; 
   //           if (isfound == true)
//               return;
    
    
  public void deleteVehicle(int vehicleId){

        Vehicle vehicleToDelete = null;
       for (Vehicle v : vehicleList){
           if (v.getId() == vehicleId){
                vehicleToDelete = v;// it not only copy the v to it ,,,it points to v as a reference so vehicletodelete now it pointing to v so when we delete it .. it delets v normally
                break;
               
  
           }
         

       }
        if(vehicleToDelete != null){// not if vehicleToDelete.getId() == vehicleId  ** cause when you try to recall a getId fun to a null obj or variable the programm collaps (NullPointerException)
        vehicleList.remove(vehicleToDelete);
            System.out.println("Vehicle deleted successfully");
        return;
        }
        System.out.println("The Id is not found , please ensure again");

       
    }
  
  
  }  
    
    
    
    
    

