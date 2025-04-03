/**
 * The Car class represents a car object in the dealership system.
 * It stores information such as year, make, model, price, features, and status.
 */
class Car {
    private int year;
    private String make;
    private String model;
    private boolean status; // false = not sold, true = sold
    private float price;
    private String features;
    /**
     * Constructs a new Car object.
     *
     * @param year the manufacturing year of the car
     * @param make the brand or manufacturer of the car
     * @param model the specific model of the car
     * @param price the price of the car
     * @param features a description of the car's features
     */
    public Car(int year, String make, String model, float price, String features) {
        this.year = year;
        this.make = make;
        this.model = model;
        this.price = price;
        this.features = features;
        this.status = false; // Default: not sold
    }
    /**
     * @return the manufacturing year of the car
     */
    public int getYear() { return year; }
    /**
     * @return the make of the car
     */
    public String getMake() { return make; }

    /**
     * @return the model of the car
     */
    public String getModel() { return model; }

    /**
     * @return true if the car is sold, false otherwise
     */
    public boolean isSold() { return status; }

    /**
     * @return the price of the car
     */
    public float getPrice() { return price; }

    /**
     * @return the features of the car
     */
    public String getFeatures() { return features; }

    /**
     * Sets the price of the car.
     *
     * @param price the new price of the car
     */
    public void setPrice(float price) { this.price = price; }

    /**
     * Sets the features of the car.
     *
     * @param features the new features description
     */
    public void setFeatures(String features) { this.features = features; }

    /**
     * Marks the car as sold.
     */
    public void markAsSold() { this.status = true; }

    /**
     * @return a formatted string representing the car's information
     */
    @Override
    public String toString() {
        return year + " " + make + " " + model + " - $" + price + " - " + (status ? "Sold" : "Available") + " - Features: " + features;
    }
}
