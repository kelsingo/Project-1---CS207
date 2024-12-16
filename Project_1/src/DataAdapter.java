import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

public class DataAdapter {
    private Connection connection;

    public DataAdapter(Connection connection) {
        this.connection = connection;
    }

    public Object[][] showProducts() {
        try {
            String query = "SELECT * FROM Products";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            java.util.List<Object[]> rows = new java.util.ArrayList<>();
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                rows.add(row);
            }
            return rows.toArray(new Object[0][0]);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving data: " + e.getMessage());
            return new Object[0][0];
        }
    }

    public ModelProduct loadProduct(int id) {
        try {
            String query = "SELECT * FROM Products WHERE ProductID = " + id;

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                ModelProduct product = new ModelProduct();
                product.setID(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setPrice(resultSet.getDouble(3));
                product.setQuantity(resultSet.getDouble(4));
                resultSet.close();
                statement.close();

                return product;
            }
        } catch (SQLException e) {
            System.out.println("Database access error!");
            e.printStackTrace();
        }
        return null;
    }

    public boolean saveProduct(ModelProduct product) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Products WHERE ProductID = ?");
            statement.setInt(1, product.getID());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) { // this product exists, update its fields
                statement = connection.prepareStatement("UPDATE Products SET productName = ?, Price = ?, Quantity = ? WHERE ProductID = ?");
                statement.setString(1, product.getName());
                statement.setDouble(2, product.getPrice());
                statement.setDouble(3, product.getQuantity());
                statement.setInt(4, product.getID());
            }
            else { // this product does not exist, use insert into
                statement = connection.prepareStatement("INSERT INTO Products VALUES (?, ?, ?, ?)");
                statement.setString(2, product.getName());
                statement.setDouble(3, product.getPrice());
                statement.setDouble(4, product.getQuantity());
                statement.setInt(1, product.getID());
            }
            statement.execute();
            resultSet.close();
            statement.close();
            return true;        // save successfully

        } catch (SQLException e) {
            System.out.println("Database access error!");
            e.printStackTrace();
            return false; // cannot save!
        }
    }

    public Object[][] showOrders() {
        try {
            String query = "SELECT * FROM Orders";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            java.util.List<Object[]> rows = new java.util.ArrayList<>();
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                rows.add(row);
            }
            return rows.toArray(new Object[0][0]);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving data: " + e.getMessage());
            return new Object[0][0];
        }
    }

    public ModelOrder loadOrder(int id) {
        try {
            ModelOrder order = null;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Orders WHERE OrderID = " + id);

            if (resultSet.next()) {
                order = new ModelOrder();
                order.setID(resultSet.getInt("OrderID"));
                order.setBuyerID(resultSet.getInt("BuyerID"));
                order.setTotalCost(resultSet.getDouble("TotalCost"));
                order.setDate(resultSet.getString("OrderDate"));
                order.setPaymentStatus(resultSet.getString("paymentStatus").equals("PAID"));

                resultSet.close();
                statement.close();
            }

            // loading the order lines for this order
            resultSet = statement.executeQuery("SELECT * FROM OrderDetails WHERE OrderID = " + id);

            while (resultSet.next()) {
                ModelOrderLine line = new ModelOrderLine();
                line.setOrderID(resultSet.getInt(1));
                line.setProductID(resultSet.getInt(2));
                line.setQuantity(resultSet.getDouble(3));
                line.setCost(resultSet.getDouble(4));
                order.addLine(line);
            }

            return order;

        } catch (SQLException e) {
            System.out.println("Database access error!");
            e.printStackTrace();
            return null;
        }
    }

    public boolean saveOrder(ModelOrder order) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Orders (buyerID, totalCost, totalTax, orderDate, paymentStatus) VALUES (?, ?, ?, DATE('now'), ?)");
            statement.setString(4, (order.getPaymentStatus() == true) ? "PAID" : "NOT PAID");
            statement.setInt(1, order.getBuyerID());
            statement.setDouble(2, order.getTotalCost());
            statement.setDouble(3, order.getTotalTax());

            statement.execute();    // commit to the database;

            ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedOrderID = generatedKeys.getInt(1); // Get the generated orderID
                    order.setID(generatedOrderID); // Optionally set this ID to the order object
                } else {
                    throw new SQLException("Failed to retrieve order ID.");
                }
            statement.close();

            statement = connection.prepareStatement("INSERT INTO OrderDetails VALUES (?, ?, ?, ?)");

            for (ModelOrderLine line: order.getLines()) { // store for each order line!
                statement.setInt(1, order.getID());
                statement.setInt(2, line.getProductID());
                statement.setDouble(3, line.getQuantity());
                statement.setDouble(4, line.getCost());

                statement.execute();    // commit to the database;
            }
            statement.close();
            return true; // save successfully!
        }
        catch (SQLException e) {
            System.out.println("Database access error!");
            e.printStackTrace();
            return false;
        }
    }

    public ModelBuyer loadBuyer(int buyerID) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Buyers WHERE buyerID = ?");
            statement.setInt(1, buyerID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                ModelBuyer buyer = new ModelBuyer();
                // buyer.setID(resultSet.getInt("buyerID"));
                buyer.setName(resultSet.getString("buyerName"));
                buyer.setPhone(resultSet.getString("phoneNumber"));
                buyer.setEmail(resultSet.getString("email"));
                buyer.setAddress(resultSet.getString("address"));
                resultSet.close();
                statement.close();

                return buyer;
            }

        } catch (SQLException e) {
            System.out.println("Database access error!");
            e.printStackTrace();
        }
        return null;
    }

    public ModelAccount loadUser(String username, String password) {
        try {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Accounts WHERE username = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                ModelAccount user = new ModelAccount();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setCustomerID(resultSet.getInt("buyerID"));
                user.setType(resultSet.getString("accountType").equals("STORE_MANAGER"));
                resultSet.close();
                statement.close();

                return user;
            }

        } catch (SQLException e) {
            System.out.println("Database access error!");
            e.printStackTrace();
        }
        return null;
    }


}
