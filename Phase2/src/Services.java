import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// CarInventory class manages all car-related operations
class CarInventory {
    private ArrayList<Car> cars = new ArrayList<>();

    public boolean addCar(Car car) {
        return cars.add(car);
    }

    public boolean removeCar(int index) {
        if (index >= 0 && index < cars.size()) {
            cars.remove(index);
            return true;
        }
        return false;
    }

    public boolean updateCar(int index, float price, String features) {
        if (index >= 0 && index < cars.size()) {
            cars.get(index).setPrice(price);
            cars.get(index).setFeatures(features);
            return true;
        }
        return false;
    }

    public List<Car> getCars() {
        return new ArrayList<>(cars);
    }

    public Optional<Car> buyCar(int index, float taxRate, float discount, String confirmation) {
        if (index >= 0 && index < cars.size() && confirmation.equalsIgnoreCase("yes")) {
            Car car = cars.get(index);
            car.markAsSold(); // Ensure the car is marked as sold
            return Optional.of(car);
        }
        return Optional.empty();
    }



    public boolean loadCarsFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] details = line.split("\\s*,\\s*", 4); // Fix: Features field allows commas
                if (details.length == 4) {
                    try {
                        int year = Integer.parseInt(details[0]);
                        String make = details[1];
                        String model = details[2];
                        String[] priceAndFeatures = details[3].split("\\s*,\\s*", 2);
                        float price = Float.parseFloat(priceAndFeatures[0]);
                        String features = priceAndFeatures.length > 1 ? priceAndFeatures[1] : "";

                        addCar(new Car(year, make, model, price, features));
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }



}