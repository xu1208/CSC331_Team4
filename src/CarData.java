public class CarData {
    //type, charge, location, isAvailable, maintenanceLevel, numPassengers
    private String type;
    private double charge;
    private String location;
    private String isAvailable;
    private double maintenanceLevel;
    private int numPassengers;

    public CarData() { super(); }

    public  CarData(String type, double charge, String location, String isAvailable, double maintenanceLevel, int numPassengers) {
        super();
        this.type = type;
        this.charge = charge;
        this.location = location;
        this.isAvailable = isAvailable;
        this.maintenanceLevel = maintenanceLevel;
        this.numPassengers = numPassengers;
    }

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

    public String getIsAvailable() {
        return isAvailable;
    }

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
