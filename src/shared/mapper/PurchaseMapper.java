package shared.mapper;

import entities.Employee;
import entities.Purchase;
import entities.PurchaseDetails;
import entities.Supplier;
import repositories.ProductRepo;
import repositories.PurchaseRepo;
import services.ProductService;
import services.PurchaseService;

import java.sql.ResultSet;

public class PurchaseMapper {

    private ProductRepo productRepo;
    private PurchaseRepo purchaseRepo;

    public void setProductRepo(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public void setPurchaseRepo(PurchaseRepo purchaseRepo) {
        this.purchaseRepo = purchaseRepo;
    }

    public Purchase mapToPurchase(Purchase purchase, ResultSet rs) {
        try {
            purchase.setId(rs.getInt("id"));
            purchase.setPurchaseDate(rs.getDate("purchaseDate"));
            purchase.setDescription(rs.getString("description"));
            Employee employee = new Employee();
            employee.setId(rs.getInt("employee_id"));
            employee.setName(rs.getString("emp_name"));
            employee.setAddress(rs.getString("emp_address"));
            employee.setEmail(rs.getString("emp_email"));
            Supplier supplier = new Supplier();
            supplier.setId(rs.getInt("supplier_id"));
            supplier.setName(rs.getString("sup_name"));
            supplier.setPhone(rs.getString("sup_phone"));
            supplier.setEmail(rs.getString("sup_email"));
            supplier.setAddress(rs.getString("sup_address"));
            purchase.setSupplier(supplier);
            purchase.setEmployee(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return purchase;
    }

    public PurchaseDetails mapToPurchaseDetails(PurchaseDetails purchaseDetails, ResultSet rs) {
        try {
            purchaseDetails.setId(rs.getInt("pd_id"));
            purchaseDetails.setQuantity(rs.getInt("quantity"));
            purchaseDetails.setProduct(
                    this.productRepo.findById(String.valueOf(rs.getInt("product_id")))
            );
            purchaseDetails.setPurchase(
                    this.purchaseRepo.findPurchaseById(String.valueOf(rs.getInt("purchase_id")))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return purchaseDetails;
    }
}
