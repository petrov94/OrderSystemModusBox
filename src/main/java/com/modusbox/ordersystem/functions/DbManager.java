package com.modusbox.ordersystem.functions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.modusbox.ordersystem.functions.models.Item;
import com.modusbox.ordersystem.functions.models.Order;

public class DbManager {

	private final String driver = "org.postgresql.Driver";
	private static final int LoginTimeout = 10;
	private static LambdaLogger logger;
	private static volatile Connection connection;
	private SimpleDateFormat dateFormat;

	public DbManager(LambdaLogger logger) {
		if (DbManager.logger == null)
			DbManager.logger = logger;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	}

	public Connection createConnection() throws IOException, ClassNotFoundException, SQLException {
		if (connection == null) {
			Class.forName(driver);
			DriverManager.setLoginTimeout(LoginTimeout);
			connection = DriverManager.getConnection(QueryConfiguration.host, QueryConfiguration.username, QueryConfiguration.password);
			connection.setAutoCommit(false);
		}
		return connection;
	}

    public List<Order> getAllOrdersByDate(String placementDate) {
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = createConnection().prepareStatement(QueryConfiguration.queryFindAllOrderByDate);
            statement.setDate(1, new java.sql.Date(dateFormat.parse(placementDate).getTime()));
            resultSet = statement.executeQuery();
        } catch (IOException | ClassNotFoundException | SQLException | ParseException ex) {
        	logger.log(ex.getMessage());
        	throw new RuntimeException(ex.getMessage());
        }
        return consumeQueryResult(resultSet);
    }

    public List<Order> getAllOrdersByCustomer(String customer) {
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = createConnection().prepareStatement(QueryConfiguration.queryFindAllOrderByCustomer);
            statement.setString(1, customer);
            resultSet = statement.executeQuery();
        } catch (IOException | ClassNotFoundException | SQLException ex) {
        	logger.log(ex.getMessage());
        	throw new RuntimeException(ex.getMessage());
        }
        return consumeQueryResult(resultSet);
    }
    
    public List<Order> getAllOrdersById(String id) {
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = createConnection().prepareStatement(QueryConfiguration.queryFindById);
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            if(resultSet == null) throw new RuntimeException("There are no records.");
        } catch (IOException | ClassNotFoundException | SQLException ex) {
        	logger.log(ex.getMessage());
        	throw new RuntimeException(ex.getMessage());
        }
        return consumeQueryResult(resultSet);
    }
    
    public List<Order> getAllOrdersByCustomerAndDate (String customer, String placementDate) {
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = createConnection().prepareStatement(QueryConfiguration.queryFindByCustomerAndDate);
            statement.setDate(1, new java.sql.Date(dateFormat.parse(placementDate).getTime()));
            statement.setString(2, customer);
            resultSet = statement.executeQuery();
        } catch (IOException | ClassNotFoundException | SQLException | ParseException ex) {
        	logger.log(ex.getMessage());
        	throw new RuntimeException(ex.getMessage());
        }
        return consumeQueryResult(resultSet);
    }
    
    public List<Order> getAllOrders() {
        ResultSet resultSet = null;
        try {
            Statement statement = createConnection().createStatement();
            resultSet = statement.executeQuery(QueryConfiguration.queryFindAllOrders);
        } catch (IOException | ClassNotFoundException | SQLException ex) {
        	logger.log(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
        return consumeQueryResult(resultSet);
    }

    private List<Order> consumeQueryResult(ResultSet resultSet){
        String id = null;
        List<Order> allOrders = new ArrayList<Order>();
        Order order = null;
        try {
            while (resultSet.next()) {
                if (id == null || !id.equalsIgnoreCase(resultSet.getString("OrderID"))) {
                    order = new Order();
                    order.setId(resultSet.getString("OrderID"));
                    order.setPlacementDate(resultSet.getDate("PlacementDate"));
                    order.setCustomerName(resultSet.getString("CustomerName"));
                    order.addProduct(new Item(resultSet.getString("ItemName"), Double.valueOf(resultSet.getString("ItemCost")), Integer.valueOf(resultSet.getString("ItemCount"))));
                    id = resultSet.getString("OrderID");
                    allOrders.add(order);
                } else {
                    order.getProducts().add(new Item(resultSet.getString("ItemName"), Double.valueOf(resultSet.getString("ItemCost")), Integer.valueOf(resultSet.getString("ItemCount"))));
                    id = resultSet.getString("OrderID");
                }
            }
        } catch (SQLException ex) {
        	logger.log(ex.getMessage());
        	throw new RuntimeException(ex.getMessage());
        }
        return allOrders;
    }

}
