package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Sale {

	private int id;
	
	private Date slaeDate;
	
	private Customer customer;
	
	private Employee employee;

	private String description;
	
	private List<Sale> sales = new ArrayList<>();

	public Sale() {
		super();
	}

	public Sale(int id, Date slaeDate, Customer customer, Employee employee, String description, List<Sale> sales) {
		super();
		this.id = id;
		this.slaeDate = slaeDate;
		this.customer = customer;
		this.employee = employee;
		this.description = description;
		this.sales = sales;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getSlaeDate() {
		return slaeDate;
	}

	public void setSlaeDate(Date slaeDate) {
		this.slaeDate = slaeDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}
	
}
