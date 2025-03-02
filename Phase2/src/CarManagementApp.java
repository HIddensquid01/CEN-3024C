import java.util.List;
import java.util.Optional;
import java.util.Scanner;

// CarManagementApp class for handling user interactions
public class CarManagementApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CarInventory inventory = new CarInventory();

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = getUserChoice();
            handleUserChoice(choice);
        }
    }

    private static void displayMenu() {
        System.out.println("\nCar Management System");
        System.out.println("1. Add Car");
        System.out.println("2. Remove Car");
        System.out.println("3. Update Car");
        System.out.println("4. View Cars");
        System.out.println("5. Buy Car");
        System.out.println("6. Load Cars from File");
        System.out.println("7. Exit");
        System.out.print("Choose an option: ");
    }

    private static int getUserChoice() {
        return getValidIntInput();
    }

    private static int getValidIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }

    private static float getValidFloatInput() {
        while (true) {
            try {
                return Float.parseFloat(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }
    private static void displayInventory() {
        List<Car> cars = inventory.getCars();
        if (cars.isEmpty()) {
            System.out.println("No cars in inventory.");
        } else {
            System.out.println("\nCurrent Cars in Inventory:");
            for (int i = 0; i < cars.size(); i++) {
                System.out.println(i + ": " + cars.get(i));
            }
        }
    }


    private static void handleUserChoice(int choice) {
        scanner.nextLine(); // Consume newline
        switch (choice) {
            case 2:
            case 3:
            case 5:
                if (inventory.getCars().isEmpty()) {
                    System.out.println("No cars in inventory. Returning to main menu.");
                    return;
                }
                break;
        }
        switch (choice) {
            case 1:
                System.out.print("Enter Year: ");
                int year = getValidIntInput();
                System.out.print("Enter Make: ");
                String make = scanner.nextLine();
                System.out.print("Enter Model: ");
                String model = scanner.nextLine();
                System.out.print("Enter Price: ");
                float price = getValidFloatInput();
                System.out.print("Enter Features: ");
                String features = scanner.nextLine();
                inventory.addCar(new Car(year, make, model, price, features));
                break;
            case 2:
                displayInventory();
                System.out.print("Enter car index to remove: ");
                if (!inventory.removeCar(getValidIntInput())) {
                    System.out.println("Invalid index.");
                }
                break;
            case 3:
                displayInventory();
                System.out.print("Enter car index to update: ");
                int index = getValidIntInput();
                System.out.print("Enter new price: ");
                float newPrice = getValidFloatInput();
                System.out.print("Enter new features: ");
                String newFeatures = scanner.nextLine();
                inventory.updateCar(index, newPrice, newFeatures);
                break;
            case 4:
                displayInventory();
                break;
            case 5:
                displayInventory();
                System.out.print("Enter car index to buy: ");
                int indexToBuy = getUserChoice();
                System.out.print("Enter tax rate (%): ");
                float taxRate = scanner.nextFloat();
                System.out.print("Enter discount amount: ");
                float discount = scanner.nextFloat();
                scanner.nextLine(); // Consume newline
                System.out.print("Do you want to proceed with the purchase? (yes/no): ");
                String confirmation = scanner.nextLine().trim().toLowerCase();
                Optional<Car> purchasedCar = inventory.buyCar(indexToBuy, taxRate, discount, confirmation);
                if (purchasedCar.isPresent()) {
                    System.out.println("Car purchased successfully!");
                } else {
                    System.out.println("Purchase cancelled or invalid selection.");
                }
                break;
            case 6:
                System.out.print("Enter file name: ");
                if (!inventory.loadCarsFromFile(scanner.nextLine())) {
                    System.out.println("Error loading file.");
                }
                break;
            case 7:
                System.exit(0);
            default:
                System.out.println("Invalid choice, try again.");
        }
    }

}


