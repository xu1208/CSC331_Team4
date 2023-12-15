public class CarData {
    // The CarData class represents the data for a single car, including its type, charge level, location,
    // availability status, maintenance level, and the number of passengers it can carry.

    // Fields representing the properties of a car
    private String type; // Type of the car (e.g., Sedan, SUV)
    private double charge; // Current charge level of the car (as a percentage in decimal)
    private String location; // Geographical location of the car (latitude and longitude)
    private String isAvailable; // Availability status of the car (True or False)
    private double maintenanceLevel; // Maintenance level of the car (as a percentage in decimal)
    private int numPassengers; // Number of passengers the car can carry

    // Default constructor
    public CarData() { super(); }

    // Parameterized constructor
    public CarData(String type, double charge, String location, String isAvailable, double maintenanceLevel, int numPassengers) {
        super();
        this.type = type; // Initialize type
        this.charge = charge; // Initialize charge
        this.location = location; // Initialize location
        this.isAvailable = isAvailable; // Initialize availability status
        this.maintenanceLevel = maintenanceLevel; // Initialize maintenance level
        this.numPassengers = numPassengers; // Initialize number of passengers
    }

    // Method to convert CarData object to a string representation
    @Override
    public String toString() {
        return "CarDataFile{" +
                "type='" + type + '\'' +
                ", charge=" + charge +
                ", location=" + location +
                ", isAvailable=" + isAvailable +
                ", maintenanceLevel=" + maintenanceLevel +
                ", numPassengers=" + numPassengers +
                '}';
    }

    // Getters and setters for each field
    public String getType() {
        return type;
    }

    public double getCharge() {
        return charge;
    }

    public double getMaintenanceLevel() {
        return maintenanceLevel;
    }

    public int getNumPassengers() {
        return numPassengers;
    }

    public String getLocation() {
        return location;
    }

    public String getIsAvailable() { return isAvailable; }

    public void setAvailable(String available) {
        isAvailable = available;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMaintenanceLevel(double maintenanceLevel) {
        this.maintenanceLevel = maintenanceLevel;
    }

    public void setNumPassengers(int numPassengers) {
        this.numPassengers = numPassengers;
    }
}
