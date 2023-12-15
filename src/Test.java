import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Test {
    // The Test class is responsible for reading car data from a file and performing various operations
    // such as finding cars based on certain criteria.
    public static void main(String[] args) throws IOException {
        // The main method is the entry point of the program.
        // It handles file input and initializes car data processing.

        //Code for reading in contents of a file into an ArrayList
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        inputFileNameStream = new FileInputStream("CarDataFile");
        inputFileNameScanner = new Scanner(inputFileNameStream);

        //Skip first line of file
        inputFileNameScanner.nextLine();

        //Three ArrayLists that hold cars in different conditions
        ArrayList<CarData> workingCars = new ArrayList<CarData>();
        ArrayList<Object> inRepair = new ArrayList<Object>();
        ArrayList<Object> inUse = new ArrayList<Object>();

        while (inputFileNameScanner.hasNext()) {
            String line = inputFileNameScanner.nextLine();
            String[] parts = line.split(",");
            CarData Car = new CarData(parts[0], Double.parseDouble(parts[1]), parts[2], parts[3], Double.parseDouble(parts[4]), Integer.parseInt(parts[5]));
            workingCars.add(Car);

            if (Double.parseDouble(parts[4]) < 70.0) {
                inRepair.add(Car);
                workingCars.remove(Car);
            }

            if (parts[3].equals(" False")) {
                inUse.add(Car);
            }

        }

        //Output lists regarding different car statuses
        System.out.println("Working Cars");
        int count = 0;
        while (count < workingCars.size()) {
            System.out.println(workingCars.get(count));
            count++;
        }
        System.out.println();

        System.out.println("In for Maintenance");
        int count1 = 0;
        while (count1 < inRepair.size()) {
            System.out.println(inRepair.get(count1));
            count1++;
        }
        System.out.println();

        System.out.println("In Use");
        int count2 = 0;
        while (count2 < inUse.size()) {
            System.out.println(inUse.get(count2));
            count2++;
        }
        System.out.println();

        //Examples:
        //Update car charge
        chargeUpdate("CarDataFile", 0.65, 2);

        //Update car location
        locationUpdate("CarDataFile", " 38.8977° N 77.0365° W", 2);

        //Change usage status
        availabilityUpdate("CarDataFile", " True", 2);

        //Give list of cars with x number of seats or more
        List<String> carsWithMinSeats = getSeats("CarDataFile", 7);
        System.out.println("Cars with more than x seats");
        carsWithMinSeats.forEach(System.out::println);

        //Get location and find the nearest car
        System.out.println();
        String userInput = " 38.8956° N 77.0355° W";
        CarData closestCar = findClosestCarByAsciiDifference(workingCars, userInput);
        System.out.println("Closest Car: " + closestCar);

        //Get list of cars above a certain charge level
        System.out.println();
        double chargeThreshold = 0.75; // Replace with desired charge value
        ArrayList<CarData> carsAboveCharge = getCarsAboveCharge(workingCars, chargeThreshold);
        System.out.println("Cars above charge " + chargeThreshold + ": " + carsAboveCharge);

        //Get list of certain type of car
        System.out.println();
        String carType = "Sedan"; // Replace with desired car type
        ArrayList<CarData> carsOfType = getCarsOfType(workingCars, carType);
        int j = 0;
        while (j < carsOfType.size()) {
            System.out.println("Cars of type " + carType + ": " + carsOfType.get(j));
            j++;
        }


    }

    //Update charge value
    public static void chargeUpdate(String filePath, double newValue, int lineNumber) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        // Check if the specified line number is within the file's line count
        if (lineNumber >= 0 && lineNumber < lines.size()) {
            lines.set(lineNumber, chargeUpdateHelper(lines.get(lineNumber), newValue));
        }

        // Write the updated content back to the file
        Files.write(Paths.get(filePath), lines);
    }


    // Helper method to update charge
    private static String chargeUpdateHelper(String line, double newValue) {
        String[] parts = line.split(",");
        if (parts.length > 1) {
            parts[1] = String.valueOf(newValue); // Replace the second item
            return String.join(",", parts);
        }
        return line;
    }

    // Method to update the location
    public static void locationUpdate(String filePath, String newLocation, int lineNumber) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        // Check if the specified line number is within the file's line count
        if (lineNumber >= 0 && lineNumber < lines.size()) {
            lines.set(lineNumber, locationUpdateHelper(lines.get(lineNumber), newLocation));
        }

        // Write the updated content back to the file
        Files.write(Paths.get(filePath), lines);
    }

    // Helper method to update the location
    private static String locationUpdateHelper(String line, String newLocation) {
        String[] parts = line.split(",");
        if (parts.length > 2) {
            parts[2] = newLocation; // Replace the third item (location)
            return String.join(",", parts);
        }
        return line;
    }

    // Method to update the availability
    public static void availabilityUpdate(String filePath, String newAvailability, int lineNumber) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        // Check if the specified line number is within the file's line count
        if (lineNumber >= 0 && lineNumber < lines.size()) {
            lines.set(lineNumber, availabilityUpdateHelper(lines.get(lineNumber), newAvailability));
        }

        // Write the updated content back to the file
        Files.write(Paths.get(filePath), lines);
    }

    // Helper method to update the availability
    private static String availabilityUpdateHelper(String line, String newAvailability) {
        String[] parts = line.split(",");
        if (parts.length > 3) {
            parts[3] = newAvailability; // Replace the fourth item (availability)
            return String.join(",", parts);
        }
        return line;
    }

    //Generates list of cars that have x number of seats or more
    public static List<String> getSeats(String filePath, int minSeats) throws IOException {
        return Files.lines(Paths.get(filePath))
                .skip(1) // Skip the header line
                .filter(line -> {
                    String[] parts = line.split(",");
                    if (parts.length > 0) {
                        try {
                            int seats = Integer.parseInt(parts[parts.length - 1].trim());
                            return seats >= minSeats;
                        } catch (NumberFormatException e) {
                            return false; // In case the number of seats is not a valid integer
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    //Finds the closest car by ASCII value
    private static CarData findClosestCarByAsciiDifference(ArrayList<CarData> cars, String userInput) {
        int userInputAsciiValue = calculateAsciiValue(userInput);
        CarData closestCar = null;
        int smallestDifference = Integer.MAX_VALUE;

        for (CarData car : cars) {
            int carLocationAsciiValue = calculateAsciiValue(car.getLocation());
            int difference = Math.abs(carLocationAsciiValue - userInputAsciiValue);

            if (difference < smallestDifference) {
                smallestDifference = difference;
                closestCar = car;
            }
        }

        return closestCar;
    }

    //Calculates ASCII values
    private static int calculateAsciiValue(String str) {
        int total = 0;
        for (char c : str.toCharArray()) {
            total += (int) c;
        }
        return total;
    }

    //Get cars above certain charge level
    private static ArrayList<CarData> getCarsAboveCharge(ArrayList<CarData> cars, double chargeThreshold) {
        ArrayList<CarData> carsAboveCharge = new ArrayList<>();
        for (CarData car : cars) {
            if (car.getCharge() > chargeThreshold) {
                carsAboveCharge.add(car);
            }
        }
        return carsAboveCharge;
    }

    //Gets cars of a certain type
    private static ArrayList<CarData> getCarsOfType(ArrayList<CarData> cars, String carType) {
        ArrayList<CarData> carsOfType = new ArrayList<>();
        for (CarData car : cars) {
            if (car.getType().equalsIgnoreCase(carType)) {
                carsOfType.add(car);
            }
        }
        return carsOfType;
    }

}
