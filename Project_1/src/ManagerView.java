// import javax.smartcardio.Card;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManagerView extends JFrame {
    private JTextField txtProductID  = new JTextField(10);
    private JTextField txtProductName  = new JTextField(30);
    private JTextField txtProductPrice  = new JTextField(10);
    private JTextField txtProductQuantity  = new JTextField(10);

    private JTextField txtBuyerID  = new JTextField(10);
    private JTextField txtBuyerName  = new JTextField(30);
    private JTextField txtPhoneNumber  = new JTextField(10);
    private JTextField txtEmail  = new JTextField(30);
    private JTextField txtAddress  = new JTextField(30);

    private JTextField txtOrderDetailOrderID  = new JTextField(10);
    // private JTextField txtOrderDetailProductID  = new JTextField(10);
    private JButton btnSearchOrderDetail = new JButton("Order Details");

    private JButton btnUpdateProduct = new JButton("Update");
    private JButton btnSearchProduct = new JButton("Search"); 

    // Check database
    private JButton btnProducts = new JButton("Products");
    private JButton btnOrders = new JButton("Orders");

    // Prepare orders 
    private JButton btnSearchBuyer = new JButton("Customers");

    // Update inventory
    private JButton btnManageProduct = new JButton("Manage Inventory");

    private DefaultTableModel products = new DefaultTableModel();
    private JTable tblProducts = new JTable();

    private DefaultTableModel orders = new DefaultTableModel();
    private JTable tblOrders = new JTable();

    private DefaultTableModel orderDetails = new DefaultTableModel();
    private JTable tblOrderDetails = new JTable();

    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    private JPanel panelProducts = new JPanel(); 
    private JPanel panelOrders = new JPanel(); 
    private JPanel panelSaveProduct = new JPanel();
    private JPanel panelBuyers = new JPanel();
    private JPanel panelOrderDetails = new JPanel(); 

    

    // private JButton btnAddBuyer = new JButton("Add");
    private JButton btnLoadBuyer = new JButton("Search"); 
    private JButton btnLoadOrderDetail = new JButton("Search"); 

    public ManagerView() {
        this.setTitle("Store Management");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setSize(800, 500);

        // Button panel
        JPanel panelButton = new JPanel();
        panelButton.add(btnProducts); // done
        panelButton.add(btnOrders); // done
        panelButton.add(btnSearchBuyer); // show form 
        panelButton.add(btnSearchOrderDetail); // also show table 
        panelButton.add(btnManageProduct); // done 
        this.getContentPane().add(panelButton);

        // Products inventory 
        products.addColumn("Product ID");
        products.addColumn("Name");
        products.addColumn("Price");
        products.addColumn("Quantity");

        panelProducts.setPreferredSize(new Dimension(400, 450));
        panelProducts.setLayout(new BoxLayout(panelProducts, BoxLayout.PAGE_AXIS));
        tblProducts.setBounds(0, 0, 400, 350);
        panelProducts.add(tblProducts.getTableHeader());
        panelProducts.add(tblProducts);
        panelProducts.setVisible(false);

        // Orders info  
        orders.addColumn("Order ID");
        orders.addColumn("Buyer ID");
        orders.addColumn("Total Cost");
        orders.addColumn("Total Tax");
        orders.addColumn("Date");
        orders.addColumn("Payment Status");

        panelOrders.setPreferredSize(new Dimension(400, 450));
        panelOrders.setLayout(new BoxLayout(panelOrders, BoxLayout.PAGE_AXIS));
        tblOrders.setBounds(0, 0, 400, 350);
        panelOrders.add(tblOrders.getTableHeader());
        panelOrders.add(tblOrders);
        panelOrders.setVisible(false);

        // Order details panel 
        panelOrderDetails.setPreferredSize(new Dimension(400, 450));
        // panelOrderDetails.setLayout(new BoxLayout(panelOrderDetails, BoxLayout.Y_AXIS));
        panelOrderDetails.setLayout(new BorderLayout());

        JPanel panelOrderDetailsID = new JPanel();
        panelOrderDetailsID.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelOrderDetailsID.add(new JLabel("Order ID: "));
        panelOrderDetailsID.add(txtOrderDetailOrderID);
        panelOrderDetailsID.add(btnLoadOrderDetail);

        // txtOrderDetailOrderID.setHorizontalAlignment(JTextField.RIGHT);
        
        orderDetails.addColumn("Order ID");
        orderDetails.addColumn("Product ID");
        orderDetails.addColumn("Quantity");
        orderDetails.addColumn("Cost");
        JPanel panelOrderDetailsTable = new JPanel();
        panelOrderDetailsTable.setLayout(new BorderLayout());
        panelOrderDetailsTable.add(tblOrderDetails.getTableHeader(), BorderLayout.NORTH);
        panelOrderDetailsTable.add(tblOrderDetails, BorderLayout.CENTER);

        panelOrderDetails.add(panelOrderDetailsID, BorderLayout.NORTH);
        panelOrderDetails.add(panelOrderDetailsTable, BorderLayout.CENTER);

        // panelOrderDetails.add(tblOrderDetails.getTableHeader());
        // panelOrderDetails.add(tblOrderDetails, BorderLayout.CENTER);

        // Buyers panel 
        JPanel panelBuyerID = new JPanel();
        panelBuyerID.add(new JLabel("Customer ID: "));
        panelBuyerID.add(txtBuyerID);
        txtBuyerID.setHorizontalAlignment(JTextField.RIGHT);

        JPanel panelBuyerName = new JPanel();
        panelBuyerName.add(new JLabel("Name: "));
        panelBuyerName.add(txtBuyerName);
        txtBuyerName.setHorizontalAlignment(JTextField.RIGHT);

        JPanel panelBuyerInfo = new JPanel();
        panelBuyerInfo.add(new JLabel("Phone Number: "));
        panelBuyerInfo.add(txtPhoneNumber);
        txtPhoneNumber.setHorizontalAlignment(JTextField.RIGHT);

        panelBuyerInfo.add(new JLabel("Email: "));
        panelBuyerInfo.add(txtEmail);
        txtEmail.setHorizontalAlignment(JTextField.RIGHT);

        JPanel panelAddress = new JPanel();
        panelAddress.add(new JLabel("Address: "));
        panelAddress.add(txtAddress);
        txtAddress.setHorizontalAlignment(JTextField.RIGHT);
        
        JPanel panelBuyerButtons = new JPanel();
        // panelBuyerButtons.add(btnAddBuyer, BorderLayout.CENTER);
        panelBuyerButtons.add(btnLoadBuyer, BorderLayout.CENTER);

        panelBuyers.add(panelBuyerID, BorderLayout.CENTER);
        panelBuyers.add(panelBuyerName, BorderLayout.CENTER);
        panelBuyers.add(panelBuyerInfo, BorderLayout.CENTER);
        panelBuyers.add(panelAddress, BorderLayout.SOUTH);
        panelBuyers.add(Box.createVerticalStrut(15)); // Add some space
        panelBuyerButtons.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); 
        panelBuyers.add(panelBuyerButtons);


        // Update inventory panel
        JPanel panelProductID = new JPanel();
        panelProductID.add(new JLabel("Product ID: "));
        panelProductID.add(txtProductID);
        txtProductID.setHorizontalAlignment(JTextField.RIGHT);
        panelSaveProduct.add(panelProductID);

        JPanel panelProductName = new JPanel();
        panelProductName.add(new JLabel("Product Name: "));
        panelProductName.add(txtProductName);
        panelSaveProduct.add(panelProductName);

        JPanel panelProductInfo = new JPanel();
        panelProductInfo.add(new JLabel("Price: "));
        panelProductInfo.add(txtProductPrice);
        txtProductPrice.setHorizontalAlignment(JTextField.RIGHT);
        panelProductInfo.add(new JLabel("Quantity: "));
        panelProductInfo.add(txtProductQuantity);
        txtProductQuantity.setHorizontalAlignment(JTextField.RIGHT);

        panelSaveProduct.add(panelProductInfo);
        panelSaveProduct.add(btnUpdateProduct, BorderLayout.CENTER);
        panelSaveProduct.add(btnSearchProduct, BorderLayout.CENTER);

        mainPanel.add(panelSaveProduct, "Save");
        mainPanel.add(panelOrders, "Orders");
        mainPanel.add(panelProducts, "Products"); 
        mainPanel.add(panelBuyers, "Buyers"); 
        mainPanel.add(panelOrderDetails, "Order Details");
        mainPanel.setVisible(false);
        this.getContentPane().add(mainPanel);
    }

    public JButton getBtnProducts() {
        return btnProducts;
    }
    public JButton getBtnManageProduct() {
        return btnManageProduct;
    }
    public JButton getBtnOrders() {
        return btnOrders;
    }
    public JButton getBtnSearchBuyer() {
        return btnSearchBuyer;
    }
    public JButton getBtnSearchOrderDetail() {
        return btnSearchOrderDetail;
    }

    public DefaultTableModel getTblModelProduct() {
        return products; 
    }
    public JTable getTblProducts() {
        return tblProducts;
    }
    public DefaultTableModel getTblModelOrder() {
        return orders; 
    }
    public JTable getTblOrders() {
        return tblOrders;
    }

    // product
    public JTextField getTxtProductID() {
        return txtProductID;
    }
    public JTextField getTxtProductName() {
        return txtProductName;
    }
    public JTextField getTxtProductPrice() {
        return txtProductPrice;
    }
    public JTextField getTxtProductQuantity() {
        return txtProductQuantity;
    }
    public JButton getBtnUpdateProduct() {
        return btnUpdateProduct;
    }
    public JButton getBtnSearchProduct() {
        return btnSearchProduct;
    }

    // buyer
    public JButton getBtnLoadBuyer() {
        return btnLoadBuyer;
    }
    public JTextField getTxtBuyerID() {
        return txtBuyerID;
    }
    public JTextField getTxtBuyerName() {
        return txtBuyerName;
    }
    public JTextField getTxtPhoneNumber() {
        return txtPhoneNumber;
    }
    public JTextField getTxtEmail() {
        return txtEmail;
    }
    public JTextField getTxtAddress() {
        return txtAddress;
    }
    
    // order details 
    public DefaultTableModel getTblModelOrderDetails() {
        return orderDetails;
    }
    public JTable getTbleOrderDetails() {
        return tblOrderDetails;
    }
    public JTextField getTxtOrderDetailOrderID() {
        return txtOrderDetailOrderID;
    }
    public JButton getBtnLoadOrderDetail() {
        return btnLoadOrderDetail;
    }
    public CardLayout getCardLayout() {
        return cardLayout;
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
