import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.util.List;

/**
 * CarManagementGUI is the main graphical user interface for managing car dealership operations.
 * It allows users to add, remove, update, view, and purchase cars as well as load inventory from a SQLite database.
 */
public class CarManagementGUI {
    private CarInventoryDBHelper dbHelper = new CarInventoryDBHelper(); // Database interaction layer
    private JFrame frame; // Main application window
    private JTextArea outputArea; // Display area for showing car inventory and system messages

    /**
     * Constructor: Initializes the GUI components and connects to the database.
     */
    public CarManagementGUI() {
        frame = new JFrame("Car Dealership Management System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Load background image (optional)
        try {
            ImageIcon backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("background.jpg"));
            JLabel background = new JLabel(backgroundImage);
            frame.setContentPane(background);
            background.setLayout(new BorderLayout());
        } catch (Exception e) {
            System.out.println("Background image not found or failed to load.");
        }

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Prompt for database connection at startup
        String dbPath = JOptionPane.showInputDialog("Enter path to SQLite database file:");
        if (!dbHelper.connect(dbPath)) {
            JOptionPane.showMessageDialog(frame, "Failed to connect to database.");
            System.exit(1);
        }

        // Create panel for buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));

        // Define and add buttons for different functionalities
        JButton addButton = new JButton("Add Car");
        JButton removeButton = new JButton("Remove Car");
        JButton updateButton = new JButton("Update Car");
        JButton viewButton = new JButton("View Cars");
        JButton buyButton = new JButton("Buy Car");
        JButton loadButton = new JButton("Load Cars from File");
        JButton exitButton = new JButton("Exit");

        // Assign actions to buttons
        addButton.addActionListener(e -> addCar());
        removeButton.addActionListener(e -> removeCar());
        updateButton.addActionListener(e -> updateCar());
        viewButton.addActionListener(e -> viewCars());
        buyButton.addActionListener(e -> buyCar());
        loadButton.addActionListener(e -> loadCarsFromFile());
        exitButton.addActionListener(e -> exitApplication());

        // Add buttons to the panel
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(updateButton);
        panel.add(viewButton);
        panel.add(buyButton);
        panel.add(loadButton);
        panel.add(exitButton);

        frame.add(panel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    /**
     * Allows user to load a different SQLite database using a file chooser.
     */
    private void loadCarsFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select SQLite Database File");
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            dbHelper.disconnect();
            if (dbHelper.connect(selectedFile.getAbsolutePath())) {
                outputArea.append("Successfully loaded new database from file: " + selectedFile.getName() + "\n");
            } else {
                outputArea.append("Failed to connect to new database.\n");
            }
        } else {
            outputArea.append("File selection cancelled.\n");
        }
    }
    /**
     * Prompts user to enter car details and adds the car to the database.
     */
    private void addCar() {
        try {
            int year = Integer.parseInt(JOptionPane.showInputDialog("Enter Year:"));
            String make = JOptionPane.showInputDialog("Enter Make:");
            String model = JOptionPane.showInputDialog("Enter Model:");
            float price = Float.parseFloat(JOptionPane.showInputDialog("Enter Price:"));
            String features = JOptionPane.showInputDialog("Enter Features:");
            if (dbHelper.addCar(year, make, model, price, features)) {
                outputArea.append("Car added successfully.\n");
            } else {
                outputArea.append("Failed to add car.\n");
            }
        } catch (NumberFormatException e) {
            outputArea.append("Invalid number input.\n");
        }
    }
    /**
     * Removes a car from the inventory using its ID.
     */
    private void removeCar() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Car ID to remove:"));
            if (dbHelper.removeCar(id)) {
                outputArea.append("Car removed successfully.\n");
            } else {
                outputArea.append("Failed to remove car.\n");
            }
        } catch (NumberFormatException e) {
            outputArea.append("Invalid ID.\n");
        }
    }
    /**
     * Updates the price and features of an existing car.
     */
    private void updateCar() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Car ID to update:"));
            float price = Float.parseFloat(JOptionPane.showInputDialog("Enter new Price:"));
            String features = JOptionPane.showInputDialog("Enter new Features:");
            if (dbHelper.updateCar(id, price, features)) {
                outputArea.append("Car updated successfully.\n");
            } else {
                outputArea.append("Update failed.\n");
            }
        } catch (NumberFormatException e) {
            outputArea.append("Invalid input.\n");
        }
    }
    /**
     * Displays the list of cars currently in the inventory.
     */
    private void viewCars() {
        List<String> cars = dbHelper.getAllCars();
        outputArea.setText("");
        if (cars.isEmpty()) {
            outputArea.append("No cars in inventory.\n");
        } else {
            for (String car : cars) {
                outputArea.append(car + "\n");
            }
        }
    }
    /**
     * Allows the user to purchase a car by entering its ID.
     * Calculates tax and discount and confirms purchase.
     */
    private void buyCar() {
        if (dbHelper == null) {
            JOptionPane.showMessageDialog(frame, "No database connected.");
            return;
        }

        String idInput = JOptionPane.showInputDialog("Enter the ID of the car to purchase:");
        if (idInput == null || idInput.isEmpty()) return;

        try {
            int id = Integer.parseInt(idInput);
            dbHelper.processCarPurchase(id); // Executes purchase workflow
            viewCars(); // Refresh car list after purchase
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid ID input.");
        }
    }

    /**
     * Exits the application and disconnects from the database.
     */
    private void exitApplication() {
        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dbHelper.disconnect();
            System.exit(0);
        }
    }

    /**
     * Main method to launch the GUI application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CarManagementGUI::new);
    }
}


