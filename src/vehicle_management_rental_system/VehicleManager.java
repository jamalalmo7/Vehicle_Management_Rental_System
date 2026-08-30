

package vehicle_management_rental_system;

import java.util.ArrayList;


public class VehicleManager {
    private final ArrayList<Vehicle> vehicleList = new ArrayList<>(); 
     
     //================= ADD VEHICLE =====================
    public boolean addVehicle(VehicleType type,String brand, String model,int year , double pricePerDay){
   
      if(type == null || brand == null || model == null || pricePerDay <= 0){
          return false;
      }
        
        Vehicle vehicle;
     
        
        if (type == VehicleType.CAR){
            vehicle = new Car(type, brand , model, year, pricePerDay);
        }
        else if (type == VehicleType.MOTORCYCLE){
            vehicle = new MotorCycle(type, brand , model, year, pricePerDay);
        }
        else {
            vehicle = new Truck(type, brand , model, year, pricePerDay);
        }
  
        vehicleList.add(vehicle);
      return true;
//        System.out.println("vehicle is : " + vehicleList);  This is just to check if it's added 
    }
    
    
         //================= DELETE VEHICLE ====================
  public boolean deleteVehicle(int vehicleId){

        Vehicle vehicleToDelete = getVehicleById(vehicleId);
        if(vehicleToDelete == null){// not if vehicleToDelete.getId() == vehicleId  ** cause when you try to recall a getId fun to a null obj or variable the programm collaps (NullPointerException)

            return false;
        }
        
        if(vehicleToDelete.isRented()){
            return false;
        }
      
        return vehicleList.remove(vehicleToDelete);

        }

    
  
       //================= GET VEHICLE BY ID =====================
  public Vehicle getVehicleById(int vehicleId){
      for(Vehicle v : vehicleList){
          if (v.getId() == vehicleId){
             // v.getDetails();
              return v;
          }
      }
      return null;
  }
  
       //================= UPDATE FULL VEHICLE =====================
  public boolean updateVehicle(Vehicle vehicle, VehicleType newType,String newBrand , String newModel, int newYear , double newPricePerDay, VehicleStatus newStatus ){
      int currentYear = java.time.Year.now().getValue();
      if(vehicle == null || newPricePerDay <=0){
      return false;
      }
      if(newYear < 1900 || newYear > currentYear){
      return false;
      }
      if(newBrand == null || newBrand.trim().isEmpty() ||
         newModel == null || newModel.trim().isEmpty()){
      return false;
      }
      vehicle.setType(newType);
      vehicle.setBrand(newBrand);
      vehicle.setModel(newModel);
      vehicle.setYear(newYear);
      vehicle.setPricePerDay(newPricePerDay);
      if(newStatus != null){
         vehicle.setStatus(newStatus);
        }
      return true;
  }
// the specific update would be in main with cases and setter directly for each one  
  
  
       //================= SEARCH VEHICLE ====================
  public ArrayList<Vehicle> searchVehicle(String keyword){ 
      ArrayList<Vehicle> results = new ArrayList<>();
      if (keyword == null || keyword.trim().isEmpty()){return results;}
      String searchKeyword = keyword.toLowerCase();
      for (Vehicle v : vehicleList){
          boolean matchBrand = v.getBrand() != null && v.getBrand().toLowerCase().contains(searchKeyword);
          boolean matchModel = v.getModel() != null && v.getModel().toLowerCase().contains(searchKeyword);
          boolean matchType = v.getType() != null && v.getType().name().toLowerCase().contains(searchKeyword);
        if (matchBrand || matchModel || matchType){
            results.add(v);
                } 
            
            }
     return results;// Possible loop in main to print attributes of this arrayOfObjects
     
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
    
