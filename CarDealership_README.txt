
Car Dealership Management System – README

Project Overview
----------------
This application simulates a car dealership management system, allowing users to perform core CRUD operations and simulate vehicle purchases. It offers both:

- A console-based interface (CarManagementApp.java)
- A GUI-based application (CarManagementGUI.java)

Users can:
- Add new cars
- Remove or update existing cars
- View the current inventory
- Purchase a vehicle (with tax & discount options)
- Load cars from a file or an SQLite database

Technologies Used
-----------------
- Java SE (Swing for GUI)
- SQLite (optional database support)
- IntelliJ IDEA
- JUnit (for unit testing)

Features & Functionalities

| Feature            | Console App | GUI App |
|--------------------|-------------|---------|
| Add Car            | Yes         | Yes     |
| Remove Car         | Yes         | Yes     |
| Update Car         | Yes         | Yes     |
| View Inventory     | Yes         | Yes     |
| Buy Car            | Yes         | Yes     |
| Load from File     | Yes         | Yes     |
| Connect SQLite DB  | No          | Yes     |

Getting Started
---------------
Requirements:
- Java JDK 11 or above
- IntelliJ IDEA (or similar IDE)
- SQLite JDBC JAR (already included if built properly)

Running the Console App:
1. Open CarManagementApp.java
2. Run the file.
3. Use the menu to interact with the system via numeric inputs.

Running the GUI App:
1. Open CarManagementGUI.java
2. Run the file.
3. When prompted, enter the path to your SQLite database file, or cancel to continue in-memory.
4. Use GUI buttons to manage the inventory.

SQLite Integration (GUI Only):
1. Ensure sqlite-jdbc is in your project libraries.
2. At runtime, provide the .db file path when prompted.
3. Make sure your DB schema includes:

CREATE TABLE cars (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  year INTEGER,
  make TEXT,
  model TEXT,
  price REAL,
  features TEXT,
  status TEXT DEFAULT 'Available'
);

Unit Tests
----------
JUnit tests in CarInventoryTest.java cover:
- File loading
- Car addition/removal
- Update functionality
- Simulated purchases

File Input Format
-----------------
To load cars from a text file, use this format:
2020, Toyota, Camry, 25000, Bluetooth
2018, Honda, Civic, 22000, Navigation

Developer Notes
---------------
- All classes are documented with Javadoc.
- GUI includes background image support (optional).
- Input validation prevents crashes from invalid entries.
- Console app checks for inventory before allowing update/delete/purchase.
