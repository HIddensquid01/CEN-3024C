import java.util.Scanner;

// Main application that provides a CLI for the Car Management System
public class CarManagementApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CarInventory inventory = new CarInventory();
        while (true) {
            System.out.println("\nCar Management System");
            System.out.println("1. Add Car");
            System.out.println("2. Remove Car");
            System.out.println("3. Update Car");
            System.out.println("4. View Cars");
            System.out.println("5. Buy Car");
            System.out.println("6. Load Cars from File");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Year: ");
                    int year = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Make: ");
                    String make = scanner.nextLine();
                    System.out.print("Enter Model: ");
                    String model = scanner.nextLine();
                    System.out.print("Enter Price: ");
                    float price = scanner.nextFloat();
                    scanner.nextLine();
                    System.out.print("Enter Features: ");
                    String features = scanner.nextLine();
                    inventory.addCar(new Car(year, make, model, price, features));
                    break;
                case 2:
                    inventory.displayCars();
                    System.out.print("Enter car index to remove: ");
                    int removeIndex = scanner.nextInt();
                    inventory.removeCar(removeIndex);
                    break;
                case 3:
                    inventory.displayCars();
                    System.out.print("Enter car index to update: ");
                    int updateIndex = scanner.nextInt();
                    System.out.print("Enter new price: ");
                    float newPrice = scanner.nextFloat();
                    scanner.nextLine();
                    System.out.print("Enter new features: ");
                    String newFeatures = scanner.nextLine();
                    inventory.updateCar(updateIndex, newPrice, newFeatures);
                    break;
                case 4:
                    inventory.displayCars();
                    break;
                case 5:
                    inventory.displayCars();
                    System.out.print("Enter car index to buy: ");
                    int buyIndex = scanner.nextInt();
                    System.out.print("Enter sales tax rate: ");
                    float taxRate = scanner.nextFloat();
                    System.out.print("Enter discount amount: ");
                    float discount = scanner.nextFloat();
                    inventory.buyCar(buyIndex, taxRate, discount);
                    break;
                case 6:
                    System.out.print("Enter file name: ");
                    String filename = scanner.nextLine();
                    inventory.loadCarsFromFile(filename);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }
}
