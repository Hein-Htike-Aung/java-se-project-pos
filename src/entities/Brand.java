package entities;

import java.util.ArrayList;
import java.util.List;

public class Brand {

	private int id;
	
	private String name;
	
	private List<Product> products = new ArrayList<>();

	public Brand() {
	}

	public Brand(int id, String name, List<Product> products) {
		super();
		this.id = id;
		this.name = name;
		this.products = products;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	
}
