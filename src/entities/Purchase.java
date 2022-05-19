package entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Purchase {

	private int id;
	
	private Employee employee;
	
	private Supplier supplier;
	
	private LocalDateTime purchaseDate;
	
	private String description;
	
	private List<Purchase> purchases = new ArrayList<>();

	public Purchase() {
	}

	public Purchase(int id, Employee employee, Supplier supplier, LocalDateTime purchaseDate, String description,
			List<Purchase> purchases) {
		super();
		this.id = id;
		this.employee = employee;
		this.supplier = supplier;
		this.purchaseDate = purchaseDate;
		this.description = description;
		this.purchases = purchases;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public LocalDateTime getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDateTime purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}
	
}
