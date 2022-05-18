package entities;

public class SaleDetails {

	private int id;
	
	private Product product;
	
	private int quantity;
	
	private Sale sale;
	
	public SaleDetails() {
		super();
	}

	public SaleDetails(int id, Product product, int quantity, Sale sale) {
		super();
		this.id = id;
		this.product = product;
		this.quantity = quantity;
		this.sale = sale;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}
	
}
