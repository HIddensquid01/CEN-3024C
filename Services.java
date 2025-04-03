import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * CarInventory manages an in-memory list of Car objects.
 * It provides methods to add, remove, update, view, and simulate purchasing of cars.
 */
class CarInventory {
    private ArrayList<Car> cars = new ArrayList<>();
    /**
     * Adds a car to the inventory.
     *
     * @param car the Car object to add
     * @return true if the car is successfully added
     */
    public boolean addCar(Car car) {
        return cars.add(car);
    }
    /**
     * Removes a car from inventory by its index.
     *
     * @param index the index of the car in the list
     * @return true if the car was successfully removed
     */
    public boolean removeCar(int index) {
        if (index >= 0 && index < cars.size()) {
            cars.remove(index);
            return true;
        }
        return false;
    }
    /**
     * Updates the price and features of a car at the specified index.
     *
     * @param index the index of the car in the list
     * @param price the new price
     * @param features the new features
     * @return true if the update was successful
     */
    public boolean updateCar(int index, float price, String features) {
        if (index >= 0 && index < cars.size()) {
            cars.get(index).setPrice(price);
            cars.get(index).setFeatures(features);
            return true;
        }
        return false;
    }
    /**
     * Retrieves a copy of the current inventory list.
     *
     * @return a list of Car objects
     */
    public List<Car> getCars() {
        return new ArrayList<>(cars);
    }
    /**
     * Processes a car purchase by marking it as sold.
     *
     * @param index the index of the car to purchase
     * @param confirmation the user's confirmation input ("yes")
     * @return an Optional containing the purchased Car if successful
     */
    public Optional<Car> buyCar(int index, String confirmation) {
        if (index >= 0 && index < cars.size() && confirmation.equalsIgnoreCase("yes")) {
            Car car = cars.get(index);
            car.markAsSold(); // Ensure the car is marked as sold
            return Optional.of(car);
        }
        return Optional.empty();
    }
    /**
     * Loads car data from a comma-separated text file.
     *
     * @param filename the name of the file to load
     * @return true if the file was loaded successfully, false if an error occurred
     */
    public boolean loadCarsFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] details = line.split("\\s*,\\s*"); // Split by commas with optional spaces
                if (details.length >= 5) { // Ensure all fields are present
                    try {
                        int year = Integer.parseInt(details[0]);
                        String make = details[1];
                        String model = details[2];
                        float price = Float.parseFloat(details[3]);
                        String features = details[4];

                        addCar(new Car(year, make, model, price, features));
                    } catch (NumberFormatException e) {
                        return false; // Invalid number format
                    }
                } else {
                    return false; // Invalid file format
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Placeholder for future enhanced car purchase method with tax and discount.
     *
     * @param indexToBuy index of the car to buy
     * @param taxRate applicable tax rate
     * @param discount discount amount
     * @param confirmation purchase confirmation ("yes")
     * @return an Optional that currently returns empty (not implemented)
     */
    public Optional<Car> buyCar(int indexToBuy, float taxRate, float discount, String confirmation) {
        return Optional.empty();
    }
}



