public class ModelBuyer {
    private int ID;
    private String name;
    private String phone;
    private String email;
    private String address;

    public int getID() {
        return ID;
    }
    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }
    public String getEmail() {
        return email; 
    }
    public String getAddress() {
        return address; 
    }

    public void setID(int buyerID) {
        this.ID = buyerID;
    }
    public void setName(String buyerName) {
        this.name = buyerName;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setEmail(String email) {
        this.email = email; 
    }
    public void setAddress(String address) {
        this.address = address; 
    }
}