public class ModelProduct {
    private int ID;
    private String name;
    private double price;
    private double quantity;

    public int getID() {
        return ID;
    }
    public void setID(int productID) {
        this.ID = productID;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public void setName(String productName) {
        this.name = productName;
    }

    public double getQuantity() {
        return quantity;
    }
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
