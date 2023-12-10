import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

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

    }


}
