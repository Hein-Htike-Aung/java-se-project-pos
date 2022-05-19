package entities;

import java.util.Objects;

public class PurchaseDetails {

	private int id;
	
	private Purchase purchase;
	
	private Product product;

	private int price;
	
	private int quantity;

	public PurchaseDetails() {
	}

	public PurchaseDetails(int id, Purchase purchase, Product product, int quantity, int price) {
		super();
		this.id = id;
		this.purchase = purchase;
		this.product = product;
		this.quantity = quantity;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PurchaseDetails that = (PurchaseDetails) o;
		return Objects.equals(product, that.product);
	}

	@Override
	public int hashCode() {
		return Objects.hash(product);
	}
}
