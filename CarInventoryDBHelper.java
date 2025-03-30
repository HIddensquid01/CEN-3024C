import javax.swing.*;
import java.sql.*;
import java.util.*;

public class CarInventoryDBHelper {
    private Connection connection;

    /**
     * Connect to the SQLite database file
     */
    public boolean connect(String dbPath) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            return true;
        } catch (Exception e) {
            System.out.println("Database connection error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Disconnect from the database
     */
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a new car to the database
     */
    public boolean addCar(int year, String make, String model, float price, String features) {
        String sql = "INSERT INTO cars (year, make, model, price, features) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, year);
            pstmt.setString(2, make);
            pstmt.setString(3, model);
            pstmt.setFloat(4, price);
            pstmt.setString(5, features);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Remove a car from the database using ID
     */
    public boolean removeCar(int id) {
        String sql = "DELETE FROM cars WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Update a car's price and features using ID
     */
    public boolean updateCar(int id, float price, String features) {
        String sql = "UPDATE cars SET price = ?, features = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setFloat(1, price);
            pstmt.setString(2, features);
            pstmt.setInt(3, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Fetch all cars in the inventory
     */
    public List<String> getAllCars() {
        List<String> carList = new ArrayList<>();
        String sql = "SELECT * FROM cars";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String car = rs.getInt("id") + ": " + rs.getInt("year") + " " + rs.getString("make") + " " + rs.getString("model") +
                        " - $" + rs.getFloat("price") + " - " + rs.getString("status") + " - Features: " + rs.getString("features");
                carList.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carList;
    }

    /**
     * Mark a car as sold using ID
     */
    public boolean markCarAsSold(int id) {
        String sql = "UPDATE cars SET status = 'Sold' WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Calculate final price and optionally mark as sold
     */
    public void processCarPurchase(int id) {
        String sql = "SELECT price FROM cars WHERE id = ? AND status != 'Sold'";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                float basePrice = rs.getFloat("price");

                String taxInput = JOptionPane.showInputDialog(null, "Enter sales tax rate (as %):");
                String discountInput = JOptionPane.showInputDialog(null, "Enter discount amount:");

                float taxRate = Float.parseFloat(taxInput);
                float discount = Float.parseFloat(discountInput);

                float taxedPrice = basePrice + (basePrice * taxRate / 100);
                float finalPrice = taxedPrice - discount;

                int confirm = JOptionPane.showConfirmDialog(null,
                        "Final Purchase Price: $" + finalPrice + "\nDo you want to proceed?",
                        "Confirm Purchase",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    markCarAsSold(id);
                    JOptionPane.showMessageDialog(null, "Car purchased successfully! Final price: $" + finalPrice);
                } else {
                    JOptionPane.showMessageDialog(null, "Purchase cancelled.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Car not available or already sold.");
            }
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error during purchase: " + e.getMessage());
        }
    }
}

