import org.junit.jupiter.api.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class CarInventoryTest {
    private CarInventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new CarInventory();
    }

    @Test
    void testAddCar() {
        Car car = new Car(2022, "Toyota", "Camry", "Bluetooth, Sunroof", 25000);
        boolean result = inventory.addCar(car);
        assertTrue(result, "Car should be added successfully.");
        assertEquals(1, inventory.getCars().size(), "Inventory should contain 1 car.");
    }

    @Test
    void testRemoveCar() {
        Car car = new Car(2021, "Honda", "Civic", "Backup Camera, Heated Seats", 20000);
        inventory.addCar(car);
        boolean result = inventory.removeCar(0);
        assertTrue(result, "Car should be removed successfully.");
        assertEquals(0, inventory.getCars().size(), "Inventory should be empty after removal.");
    }

    @Test
    void testViewCars() {
        inventory.addCar(new Car(2022, "Ford", "Mustang", "Leather seats, Apple CarPlay", 30000));
        assertEquals(1, inventory.getCars().size(), "Inventory should contain 1 car.");
    }

    @Test
    void testBuyCar() {
        Car car = new Car(2023, "Chevrolet", "Malibu", "Navigation, Sunroof", 28000);
        inventory.addCar(car);

        float taxRate = 0;
        float discount
                = 0;
        Optional<Car> purchasedCar = inventory.buyCar(0, taxRate, discount, "yes");

        assertTrue(purchasedCar.isPresent(), "Car should be purchased successfully.");
        assertTrue(inventory.getCars().get(0).isSold(), "Car should be marked as sold.");
    }



    @Test
    void testLoadCarsFromFile() {
        String testFile = "test_cars.txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(testFile))) {
            writer.println("2020,Toyota,Corolla,22000,Bluetooth Sunroof");
            writer.println("2019,Honda,Civic,20000,Backup Camera Heated Seats");
        } catch (IOException e) {
            fail("Setup failed: Could not create test file.");
        }

        boolean result = inventory.loadCarsFromFile(testFile);
        assertTrue(result, "File should load successfully.");

        assertEquals(2, inventory.getCars().size(), "Should contain 2 cars after loading.");
    }

}