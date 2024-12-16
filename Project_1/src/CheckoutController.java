import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckoutController implements ActionListener {
    private BuyerView view;
    private DataAdapter dataAdapter; // to save and load product
    private ModelOrder order = null;

    public CheckoutController(BuyerView view, DataAdapter dataAdapter) {
        this.dataAdapter = dataAdapter;
        this.view = view;

        view.getBtnAdd().addActionListener(this);
        view.getBtnPay().addActionListener(this);

        order = new ModelOrder();

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getBtnAdd())
            addProduct();
            
        else
        if (e.getSource() == view.getBtnPay()) {
            makePayment();
            return;
        }
    }

    private void makeOrder() {
        order.setBuyerID(Application.getInstance().getCurrentUser().getBuyerID());
        order.setTotalTax(0);
        dataAdapter.saveOrder(order);

        for (ModelOrderLine line : order.getLines()) {
            ModelProduct product = dataAdapter.loadProduct(line.getProductID());
            
            product.setQuantity(product.getQuantity() - line.getQuantity());
            dataAdapter.saveProduct(product);
        }
    }
    
    private void makePayment() {
        Object[] options = {"Online Payment", "Cash"}; 
        int paymentMethod = JOptionPane.showOptionDialog(null, "Choose your payment method!", "Payment Process", 0, 2,
        null, options, options[0]);
        if (paymentMethod == 0) {
            int isPaid = JOptionPane.showOptionDialog(null, "Please make online payment to this account: 100-000-543. "
            + "Click OK when transaction is done!", "Payment Information", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, 
            null, null, null);
            if (isPaid == JOptionPane.OK_OPTION) {
                JOptionPane.showMessageDialog(null, "Order is made successfully!");
                order.setPaymentStatus(true);
                makeOrder();
                return;
            }           
            else {
                JOptionPane.showMessageDialog(null, "Order fails!");
                return;
            }
        }
        else if (paymentMethod == 1) {
            JOptionPane.showMessageDialog(null, "Order is made successfully!");
            order.setPaymentStatus(false);
            makeOrder();
            return;
        }
    }

    private void addProduct() {
        String id = JOptionPane.showInputDialog("Enter ProductID: ");
        ModelProduct product = dataAdapter.loadProduct(Integer.parseInt(id));
        if (product == null) {
            JOptionPane.showMessageDialog(null, "This product does not exist!");
            return;
        }

        double quantity = Double.parseDouble(JOptionPane.showInputDialog(null,"Enter quantity: "));

        if (quantity < 1 || quantity > product.getQuantity()) {
            JOptionPane.showMessageDialog(null, "This quantity is not valid!");
            return;
        }

        ModelOrderLine line = new ModelOrderLine();
        line.setProductID(product.getID());
        line.setQuantity(quantity);
        line.setCost(quantity * product.getPrice());
        order.getLines().add(line);
        order.setTotalCost(order.getTotalCost() + line.getCost());

        Object[] row = new Object[5];
        row[0] = line.getProductID();
        row[1] = product.getName();
        row[2] = product.getPrice();
        row[3] = line.getQuantity();
        row[4] = line.getCost();

        this.view.addRow(row);
        this.view.getLabTotal().setText("Total: $" + order.getTotalCost());
        this.view.invalidate();
    }
}
