package pkgData;

import pkgModels.*;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

/**
 * Created by sammerm on 21/04/2017.
 */
public class Database {
    private static String connStr="jdbc:oracle:thin:@192.168.128.152:1521:ora11g";//Externe ip jdbc:oracle:thin:@212.152.179.117:1521:ora11g192.168.128.152
    private static String user = "d5b12";
    private static String passwd = "d5b";

    //PLF SQL
    private static String selectProducts = "SELECT * FROM PRODUCTS";
    private static String selectOrders = "SELECT * FROM ORDERS";
    private static String selectCustomers = "SELECT * FROM CUSTOMERS";
    private static String insertOrder = "Insert blablab  ";


    private Connection conn = null;
    private static Database _database = new Database();

    public static Database getInstance() {
        if(_database == null) {
            _database = new Database();
        }
        return _database;
    }

    private Database() {
    }

    private void createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.OracleDriver");
        conn = DriverManager.getConnection(connStr,user,passwd);
    }

    public Vector<Order> getOrders() throws Exception {
        createConnection();
        Vector<OrderDTO> vecOrdersTemp = new Vector<OrderDTO>();
        PreparedStatement state = conn.prepareStatement(selectOrders, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = state.executeQuery();
        while(rs.next())   {
            vecOrdersTemp.add(new OrderDTO(rs.getInt("id"),rs.getInt("id_cust"),rs.getInt("id_prod"),rs.getDate("orderdate").toString(),rs.getInt("quantity")));
        }
        conn.close();
        Vector<Customer> vecCustomers = getCustomers();
        Vector<Product> vecProducts = getProducts();
        Vector<Order> vecOrders = new Vector<Order>();

        for (OrderDTO o: vecOrdersTemp) {
            Order o2 = new Order();
            o2.setId(o.getId());
            o2.setQuantity(o.getQuantity());
            o2.setOrderdate(o.getOrderdate());
            o2.setCustomer(findCustomerById(vecCustomers, o.getCustomerId()).get());
            o2.setProduct(findProductById(vecProducts, o.getProductId()).get());
            vecOrders.add(o2);
        }
        return vecOrders;
    }


    public Vector<Product> getProducts() throws Exception {
        createConnection();
        Vector<Product> vecProducts = new Vector<Product>();
        PreparedStatement state = conn.prepareStatement(selectProducts, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = state.executeQuery();
        while(rs.next())   {
            vecProducts.add(new Product(rs.getInt("id"),rs.getString("name"), rs.getInt("on_stock"),rs.getInt("critical_level"), new ProductPrice(rs.getBigDecimal("price").toString())));
        }
        conn.close();
        return vecProducts;
    }

    public Vector<Customer> getCustomers() throws Exception {
        createConnection();
        Vector<Customer> vecCustomers = new Vector<Customer>();
        PreparedStatement state = conn.prepareStatement(selectCustomers, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = state.executeQuery();
        while(rs.next()) {
            vecCustomers.add(new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("address")));
        }
        conn.close();
        return vecCustomers;
    }

    public Optional<Product> findProductById(final Vector<Product> list, final int id) {
        return list.stream()
                .filter(p -> p.getId() == id).findAny();
    }

    public Optional<Customer> findCustomerById(final Vector<Customer> list, final int id) {
        return list.stream()
                .filter(p -> p.getId() == id).findAny();
    }
}

