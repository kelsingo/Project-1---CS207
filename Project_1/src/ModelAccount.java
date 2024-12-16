

public class ModelAccount {
    private String username; 
    private String password;
    private int buyerID;
    private boolean isManager; 

    public int getBuyerID() {
        return buyerID;
    }
    public boolean getType() {
        return isManager; 
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public void setCustomerID(int buyerID) {
        this.buyerID = buyerID;
    }
    public void setType(boolean isManager) {
        this.isManager = isManager; 
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

