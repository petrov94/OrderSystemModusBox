package com.modusbox.ordersystem.functions;

public class QueryConfiguration {

	public static final String queryFindAllOrderByDate = "SELECT Items.ItemName, cast(Items.ItemCost as numeric), OrderItems.ItemCount, Orders.CustomerName, Orders.PlacementDate, Orders.OrderID FROM Orders INNER JOIN OrderItems ON OrderItems.OrderID = Orders.OrderID INNER JOIN Items ON OrderItems.ItemId = Items.ItemId where Orders.PlacementDate = ? ORDER BY Orders.OrderID, Orders.CustomerName";
	public static final String queryFindAllOrderByCustomer = "SELECT Items.ItemName, cast(Items.ItemCost as numeric), OrderItems.ItemCount, Orders.PlacementDate, Orders.CustomerName, Orders.OrderID FROM Orders INNER JOIN OrderItems ON OrderItems.OrderID = Orders.OrderID INNER JOIN Items ON OrderItems.ItemId = Items.ItemId where Orders.CustomerName = ? ORDER BY Orders.OrderID, Orders.PlacementDate";
	public static final String queryFindAllOrders = "SELECT Items.ItemName, cast(Items.ItemCost as numeric), OrderItems.ItemCount, Orders.PlacementDate, Orders.CustomerName, Orders.OrderID FROM Orders INNER JOIN OrderItems ON OrderItems.OrderID = Orders.OrderID INNER JOIN Items ON OrderItems.ItemId = Items.ItemId";
	public static final String queryFindById = "SELECT Items.ItemName, cast(Items.ItemCost as numeric), OrderItems.ItemCount, Orders.PlacementDate, Orders.CustomerName, Orders.OrderID FROM Orders INNER JOIN OrderItems ON OrderItems.OrderID = Orders.OrderID INNER JOIN Items ON OrderItems.ItemId = Items.ItemId where Orders.OrderID = uuid(?)";
	public static final String queryFindByCustomerAndDate = "SELECT Items.ItemName, cast(Items.ItemCost as numeric), OrderItems.ItemCount, Orders.PlacementDate, Orders.CustomerName, Orders.OrderID FROM Orders INNER JOIN OrderItems ON OrderItems.OrderID = Orders.OrderID INNER JOIN Items ON OrderItems.ItemId = Items.ItemId where Orders.PlacementDate = ? AND Orders.CustomerName = ? ORDER BY Orders.OrderID";
	public static final String host = System.getenv("db_hostname");
	public static final String username = System.getenv("db_username");
	public static final String password = System.getenv("db_password");
}
