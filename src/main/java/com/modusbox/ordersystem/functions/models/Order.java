package com.modusbox.ordersystem.functions.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

	private String id;
	private Date placementDate;
	private String customerName;
	private List<Item> products;

	public Order() {
		products = new ArrayList<Item>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Item> getProducts() {
		return products;
	}

	public void addProduct(Item product) {
		this.products.add(product);
	}

	public Date getPlacementDate() {
		return placementDate;
	}

	public void setPlacementDate(Date placementDate) {
		this.placementDate = placementDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

}
