import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ManageController implements ActionListener {
    private ManagerView managerView;
    private DataAdapter dataAdapter; // to save and load product information

    public ManageController(ManagerView managerView, DataAdapter dataAdapter) {
        this.dataAdapter = dataAdapter;
        this.managerView = managerView;

        managerView.getBtnProducts().addActionListener(this);
        managerView.getBtnManageProduct().addActionListener(this);
        managerView.getBtnOrders().addActionListener(this);
        managerView.getBtnUpdateProduct().addActionListener(this);
        managerView.getBtnSearchProduct().addActionListener(this);
        managerView.getBtnSearchBuyer().addActionListener(this);    
        managerView.getBtnSearchOrderDetail().addActionListener(this);  
        managerView.getBtnLoadBuyer().addActionListener(this);      
        managerView.getBtnLoadOrderDetail().addActionListener(this);      

    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == managerView.getBtnProducts())
            showProducts();
        else if (e.getSource() == managerView.getBtnOrders())
            showOrders();

        else if (e.getSource() == managerView.getBtnManageProduct()) {
            managerView.getCardLayout().show(managerView.getMainPanel(), "Save");
            managerView.getMainPanel().setVisible(true);
        }
        else if (e.getSource() == managerView.getBtnUpdateProduct())
            saveProduct();
        else if (e.getSource() == managerView.getBtnSearchProduct())
            loadProduct();
        
        else if (e.getSource() == managerView.getBtnSearchBuyer()) {
            managerView.getCardLayout().show(managerView.getMainPanel(), "Buyers");
            managerView.getMainPanel().setVisible(true);
        }
        else if (e.getSource() == managerView.getBtnSearchOrderDetail()) {
            managerView.getCardLayout().show(managerView.getMainPanel(), "Order Details");
            managerView.getMainPanel().setVisible(true);
        }
        else if (e.getSource() == managerView.getBtnLoadOrderDetail())
            loadOrderDetails();
        else if (e.getSource() == managerView.getBtnLoadBuyer()) {
            loadBuyer();
        }
    }

    private void showProducts() {
        managerView.getTblModelProduct().setRowCount(0);
        Object[][] productsData = dataAdapter.showProducts(); 
        for (Object[] productRow : productsData) {
            managerView.getTblModelProduct().addRow(productRow);  // Add each row to the products model
        }
        managerView.getTblProducts().setModel(managerView.getTblModelProduct());
        managerView.getCardLayout().show(managerView.getMainPanel(), "Products");
        managerView.getMainPanel().setVisible(true);
        System.out.println("Show Products Inventory");
    }

    private void saveProduct() {
        int productID;
        try {
            productID = Integer.parseInt(managerView.getTxtProductID().getText());
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid product ID! Please provide a valid product ID!");
            return;
        }

        double productPrice;
        try {
            productPrice = Double.parseDouble(managerView.getTxtProductPrice().getText());
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid product price! Please provide a valid product price!");
            return;
        }

        double productQuantity;
        try {
            productQuantity = Double.parseDouble(managerView.getTxtProductQuantity().getText());
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid product quantity! Please provide a valid product quantity!");
            return;
        }

        String productName = managerView.getTxtProductName().getText().trim();

        if (productName.length() == 0) {
            JOptionPane.showMessageDialog(null, "Invalid product name! Please provide a non-empty product name!");
            return;
        }

        // Done all validations! Make an object for this product!

        ModelProduct product = new ModelProduct();
        product.setID(productID);
        product.setName(productName);
        product.setPrice(productPrice);
        product.setQuantity(productQuantity);

        // Store the product to the database

        boolean status = dataAdapter.saveProduct(product);
        System.out.println(status);
        if (status == true) {
            JOptionPane.showMessageDialog(null, "Successfully update products!");
            return;
        }
        else {
            JOptionPane.showMessageDialog(null, "Fail to update products!");
            return;
        }
    }

    private void loadProduct() {
        int productID = 0;
        try {
            productID = Integer.parseInt(managerView.getTxtProductID().getText());
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid product ID! Please provide a valid product ID!");
            return;
        }

        ModelProduct product = dataAdapter.loadProduct(productID);

        if (product == null) {
            JOptionPane.showMessageDialog(null, "This product ID does not exist in the database!");
            return;
        }

        managerView.getTxtProductName().setText(product.getName());
        managerView.getTxtProductPrice().setText(String.valueOf(product.getPrice()));
        managerView.getTxtProductQuantity().setText(String.valueOf(product.getQuantity()));
    }

    private void showOrders() {
        managerView.getTblModelOrder().setRowCount(0);
        Object[][] ordersData = dataAdapter.showOrders(); 
        for (Object[] orderRow : ordersData) {
            managerView.getTblModelOrder().addRow(orderRow);  // Add each row to the products model
        }
        managerView.getTblOrders().setModel(managerView.getTblModelOrder());
        managerView.getCardLayout().show(managerView.getMainPanel(), "Orders");
        managerView.getMainPanel().setVisible(true);
        System.out.println("Show Orders");
    }

    private void loadBuyer() {
        int buyerID = 0;
        try {
            buyerID = Integer.parseInt(managerView.getTxtBuyerID().getText());
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid customer ID! Please provide a valid customer ID!");
            return;
        }

        ModelBuyer buyer = dataAdapter.loadBuyer(buyerID);

        if (buyer == null) {
            JOptionPane.showMessageDialog(null, "This customer ID does not exist in the database!");
            return;
        }

        managerView.getTxtBuyerName().setText(buyer.getName());
        managerView.getTxtPhoneNumber().setText(buyer.getPhone());
        managerView.getTxtEmail().setText(buyer.getEmail());
        managerView.getTxtAddress().setText(buyer.getAddress());

    }

    private void loadOrderDetails() {
        managerView.getTblModelOrderDetails().setRowCount(0);

        int orderID = 0;
        try {
            orderID = Integer.parseInt(managerView.getTxtOrderDetailOrderID().getText());
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid order ID! Please provide a valid order ID!");
            return;
        }
        
        ModelOrder order = dataAdapter.loadOrder(orderID); 

        if (order == null) {
            JOptionPane.showMessageDialog(null, "This order ID does not exist in the database!");
            return;
        }

        managerView.getTblModelOrder().setRowCount(0);
        for (ModelOrderLine line : order.getLines()) {
            Object[] orderRow = {
                line.getOrderID(),        // Example: getter for the ID
                line.getProductID(),      // Example: getter for the Name
                line.getQuantity(),  // Example: getter for the Quantity
                line.getCost()      // Example: getter for the Price
            };
            managerView.getTblModelOrderDetails().addRow(orderRow);  // Add each row to the order model
        }
        managerView.getTbleOrderDetails().setModel(managerView.getTblModelOrderDetails());
        managerView.getCardLayout().show(managerView.getMainPanel(), "Order Details");
        managerView.getMainPanel().setVisible(true);
        System.out.println("Show Order Details");
    }
}
