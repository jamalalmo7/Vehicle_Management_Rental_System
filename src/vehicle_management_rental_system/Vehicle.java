 
package vehicle_management_rental_system;


public class Vehicle {
    private int id;
    private VehicleType type;// enum 
    private String brand;
    private String model;
    private int year;
    private double pricePerDay;
    private VehicleStatus status; // enum
    
    public Vehicle
    (int id,VehicleType type, String brand,String model,int year,
            double pricePerDay, VehicleStatus status){
        this.id = id ;
        this.type = type ;
        this.brand = brand ;
        this.model = model ;
        this.year = year ;
        this.pricePerDay = pricePerDay ;
        this.status = status ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }
    
    /* Methods ==================================
    =============================================*/
    
    public void getDetails(){
        System.out.println("Vehivle Details ");
        System.out.println("----------------");
        System.out.println("ID: " + getId());
        System.out.println("Type: " + getType());
        System.out.println("Brand: " + getBrand());
        System.out.println("Model: " + getModel());
        System.out.println("Year: " + getYear());
        System.out.println("PricePerDay: " + getPricePerDay());
        System.out.println("Status: " + getStatus());
    }
     @Override
    public String toString() {
        return "Vehicle{" + "id=" + id + ", type=" + type + ", brand=" + brand + ", model=" + model + ", year=" + year + ", pricePerDay=" + pricePerDay + ", status=" + status + '}';
    }
   
    public boolean isAvailable(){
        return this.status == VehicleStatus.AVAILABLE;
    }

   public boolean isRented(){
       return this.status == VehicleStatus.RENTED;
   }
   
   public boolean isMaintenance(){
       return this.status == VehicleStatus.MAINTENANCE;
   }
    
    public double calculateCost(int numberOfDays){
    return pricePerDay * numberOfDays;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
