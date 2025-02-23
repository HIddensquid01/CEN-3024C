import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// CarInventory class manages a collection of cars
class CarInventory {
    private ArrayList<Car> cars = new ArrayList<>(); // List to store car objects

    public void addCar(Car car) {
        cars.add(car);
    }

    public void removeCar(int index) {
        if (index >= 0 && index < cars.size()) {
            cars.remove(index);
            System.out.println("Car removed successfully.");
        } else {
            System.out.println("Invalid car index.");
        }
    }

    public void updateCar(int index, float price, String features) {
        if (index >= 0 && index < cars.size()) {
            cars.get(index).setPrice(price);
            cars.get(index).setFeatures(features);
            System.out.println("Car updated successfully.");
        } else {
            System.out.println("Invalid car index.");
        }
    }

    public void displayCars() {
        if (cars.isEmpty()) {
            System.out.println("No cars in inventory.");
        } else {
            for (int i = 0; i < cars.size(); i++) {
                System.out.println(i + ": " + cars.get(i));
            }
        }
    }

    public void buyCar(int index, float taxRate, float discount) {
        if (index >= 0 && index < cars.size()) {
            Car car = cars.get(index);
            float finalPrice = (car.getPrice() - discount) * (1 + taxRate / 100);
            System.out.println("Final Price: $" + finalPrice);
            System.out.print("Confirm Purchase? (yes/no): ");
            Scanner scanner = new Scanner(System.in);
            if (scanner.nextLine().equalsIgnoreCase("yes")) {
                car.markAsSold();
                System.out.println("Car marked as sold!");
            }
        } else {
            System.out.println("Invalid car index.");
        }
    }

    public void loadCarsFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim(); // Remove leading and trailing whitespace
                if (line.isEmpty()) { // Skip empty lines
                    continue;
                }

                // Split the line by commas, ignoring spaces before/after commas
                String[] details = line.split("\\s*,\\s*");

                // Print the number of fields in the current line
                System.out.println("Number of fields in line: " + details.length); // Debugging output

                if (details.length == 5) { // Ensure correct number of fields
                    try {
                        int year = Integer.parseInt(details[0]);
                        String make = details[1];
                        String model = details[2];
                        float price = Float.parseFloat(details[3]);
                        String features = details[4];

                        addCar(new Car(year, make, model, price, features));
                        System.out.println("Added car: " + year + " " + make + " " + model);
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping invalid line (bad number format): " + line);
                    }
                } else {
                    System.out.println("Skipping improperly formatted line: " + line);
                }
            }
            System.out.println("Cars loaded from file successfully.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}