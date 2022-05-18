package entities;

public class PurchaseDetails {

	private int id;
	
	private Purchase purchase;
	
	private Product product;
	
	private int quantity;

	public PurchaseDetails() {
	}

	public PurchaseDetails(int id, Purchase purchase, Product product, int quantity) {
		super();
		this.id = id;
		this.purchase = purchase;
		this.product = product;
		this.quantity = quantity;
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

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
