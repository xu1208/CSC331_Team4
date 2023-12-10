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
    public static void main(String[] args) throws IOException {

        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        inputFileNameStream = new FileInputStream("CarDataFile");
        inputFileNameScanner = new Scanner(inputFileNameStream);

        //skip first line of file
        inputFileNameScanner.nextLine();

        ArrayList<Object> workingCars = new ArrayList<Object>();
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

        //TODO:
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






}
