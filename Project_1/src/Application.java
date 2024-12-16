import java.sql.*;

public class Application {

    private static Application instance;   // Singleton pattern

    public static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }
    // Main components of this application

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    private DataAdapter dataAdapter;

    private ModelAccount currentUser = null;
    public ModelAccount getCurrentUser() { return currentUser; }
    public void setCurrentUser(ModelAccount user) {
        this.currentUser = user;
    }

    private ManagerView managerView = new ManagerView();
    private BuyerView buyerView = new BuyerView();
    
    public ManagerView getManagerView() {
        return managerView;
    }
    public BuyerView getbBuyerView() {
        return buyerView;
    }

    public LoginScreen loginScreen = new LoginScreen();

    public LoginScreen getLoginScreen() {
        return loginScreen;
    }
    public LoginController loginController; // = new LoginController(loginScreen, dataAdapter);

    private ManageController manageController;

    public ManageController getManageController() {
        return manageController;
    }

    private CheckoutController checkoutController;

    public CheckoutController getCheckoutController() {
        return checkoutController;
    }

    public DataAdapter getDataAdapter() {
        return dataAdapter;
    }


    private Application() {
        // create SQLite database connection here!
        try {
            Class.forName("org.sqlite.JDBC");

            String url = "jdbc:sqlite:store.db";

            connection = DriverManager.getConnection(url);
            dataAdapter = new DataAdapter(connection);

        }
        catch (ClassNotFoundException ex) {
            System.out.println("SQLite is not installed. System exits with error!");
            ex.printStackTrace();
            System.exit(1);
        }

        catch (SQLException ex) {
            System.out.println("SQLite database is not ready. System exits with error!" + ex.getMessage());

            System.exit(2);
        }

        // Create data adapter here!

        manageController = new ManageController(managerView, dataAdapter);
        checkoutController = new CheckoutController(getbBuyerView(), dataAdapter);
        loginController = new LoginController(loginScreen, dataAdapter);
    }


    public static void main(String[] args) {
        Application.getInstance().getLoginScreen().setVisible(true);
    }
}
