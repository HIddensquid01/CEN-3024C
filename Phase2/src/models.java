// Car Class represents a single car object
class Car {
    private int year;
    private String make;
    private String model;
    private boolean status; // false = not sold, true = sold
    private float price;
    private String features;

    public Car(int year, String make, String model, float price, String features) {
        this.year = year;
        this.make = make;
        this.model = model;
        this.price = price;
        this.features = features;
        this.status = false; // Default: not sold
    }

    public Car(int year, String honda, String civic, String s, int i) {

    }

    public int getYear() { return year; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public boolean isSold() { return status; }
    public float getPrice() { return price; }
    public String getFeatures() { return features; }

    public void setPrice(float price) { this.price = price; }
    public void setFeatures(String features) { this.features = features; }
    public void markAsSold() { this.status = true; }

    @Override
    public String toString() {
        return year + " " + make + " " + model + " - $" + price + " - " + (status ? "Sold" : "Available") + " - Features: " + features;
    }
}
