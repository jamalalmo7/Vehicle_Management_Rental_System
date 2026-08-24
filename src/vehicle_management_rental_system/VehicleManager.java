

package vehicle_management_rental_system;

import java.util.ArrayList;


public class VehicleManager {
    private ArrayList<Vehicle> vehicleList = new ArrayList<>(); 
     
     //================= ADD VEHICLE =====================
    public void addVehicle(int id, VehicleType type,String brand, String model,int year , double pricePerDay,VehicleStatus status){
   
        for (Vehicle v : vehicleList){
            if(v.getId() == id){
                System.out.println("Error.. Vehicle with ID " + id + " already exist!");
                return;
            }
        }
        
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
    
    
         //================= DELETE VEHICLE ====================
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
  
       //================= GET VEHICLE BY ID =====================
  public Vehicle getVehicleById(int vehicleId){
      for(Vehicle v : vehicleList){
          if (v.getId() == vehicleId){
             // v.getDetails();
              return v;
          }
      }
      System.out.println("This Id Vehicle is not found");
      return null;
  }
  
       //================= UPDATE FULL VEHICLE =====================
  public void updateVehicle(Vehicle vehicle, VehicleType newType,String newBrand , String newModel, int newYear , double newPricePerDay, VehicleStatus newStatus ){
      vehicle.setType(newType);
      vehicle.setBrand(newBrand);
      vehicle.setModel(newModel);
      vehicle.setYear(newYear);
      vehicle.setPricePerDay(newPricePerDay);
      vehicle.setStatus(newStatus);
      
      System.out.println("Update done successfully");
      System.out.println("The Vehicle after update: ");
      vehicle.getDetails();
  }
// the specific update would be in main with cases and setter directly for each one  
  
  
       //================= SEARCH VEHICLE ====================
  public ArrayList<Vehicle> searchVehicle(String keyword){
      ArrayList<Vehicle> results = new ArrayList<>();
      String searchKeyword = keyword.toLowerCase();
      for (Vehicle v : vehicleList){
        if (v.getBrand().toLowerCase().contains(searchKeyword) ||
            v.getModel().toLowerCase().contains(searchKeyword) || 
            v.getType().name().toLowerCase().contains(searchKeyword))
                {
            results.add(v);
                } 
            
            }
     return results;
  }
       //================= GET FREE VEHICLE =====================
  public ArrayList<Vehicle> getAvailableVehicles(){
      ArrayList<Vehicle> availableVehicles = new ArrayList<>();
      for (Vehicle v : vehicleList){
          if (v.getStatus()== VehicleStatus.AVAILABLE){
               availableVehicles.add(v);
          }
      }
      return availableVehicles;
  
  }
  
       //================= GET ALL VEHICLE =====================
  public ArrayList<Vehicle> getAllVehicles(){
        return new ArrayList<>(vehicleList); 
  }
  
 
  }  
    
