import java.util.ArrayList;
import java.util.List;

public class ModelOrder {
    private int ID;
    private int buyerID;
    private String date;
    private double totalCost;
    private double totalTax;
    private boolean paymentStatus;

    private List<ModelOrderLine> lines;

    public ModelOrder() {
        lines = new ArrayList<>();
    }

    public int getID() {
        return ID;
    }
    public int getBuyerID() {
        return buyerID;
    }
    public String getDate() {
        return date;
    }
    public double getTotalCost() {
        return totalCost;
    }
    public double getTotalTax() {
        return totalTax;
    }
    public boolean getPaymentStatus() {
        return paymentStatus;
    }

    public void setID(int orderID) {
        this.ID = orderID;
    }
    public void setBuyerID(int buyerID) {
        this.buyerID = buyerID;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
    }
    public void setPaymentStatus(boolean status) {
        this.paymentStatus = status;
    }

    public void addLine(ModelOrderLine line) {
        lines.add(line);
    }
    public void removeLine(ModelOrderLine line) {
        lines.remove(line);
    }
    public List<ModelOrderLine> getLines() {
        return lines;
    }
}

