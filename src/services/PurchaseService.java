package services;

import config.DBConfig;
import entities.Employee;
import entities.Purchase;
import entities.PurchaseDetails;
import entities.Supplier;
import repositories.ProductRepo;
import repositories.PurchaseRepo;
import shared.mapper.PurchaseMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PurchaseService implements PurchaseRepo {

    private final DBConfig dbConfig = new DBConfig();
    private PurchaseMapper purchaseMapper;

    public void setPurchaseMapper(PurchaseMapper purchaseMapper) {
        this.purchaseMapper = purchaseMapper;
        this.purchaseMapper.setPurchaseRepo(new PurchaseService());
        this.purchaseMapper.setProductRepo(new ProductService());
    }

    public void createPurchase(Purchase purchase) {
        try {

            PreparedStatement ps = this.dbConfig.getConnection()
                    .prepareStatement("INSERT INTO purchase (purchaseDate, description, employee_id, supplier_id) VALUES (?, ?, ?, ?)");

            ps.setString(1, String.valueOf(purchase.getPurchaseDate()));
            ps.setString(2, String.valueOf(purchase.getDescription()));
            ps.setString(3, String.valueOf(purchase.getEmployee().getId()));
            ps.setString(4, String.valueOf(purchase.getSupplier().getId()));
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createPurchaseDetails(PurchaseDetails purchaseDetails) {
        try {

            PreparedStatement ps = this.dbConfig.getConnection()
                    .prepareStatement("INSERT INTO purchase_details (quantity, product_id, purchase_id) VALUES (?, ?, ?)");

            ps.setInt(1, purchaseDetails.getQuantity());
            ps.setInt(2, purchaseDetails.getProduct().getId());
            ps.setInt(3, purchaseDetails.getPurchase().getId());
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<PurchaseDetails> findPurcahseDetialsListByProductId(String productId) {
        List<PurchaseDetails> purchaseDetailsList = new ArrayList<>();
        try (Statement st = this.dbConfig.getConnection().createStatement()) {

            String query = "SELECT * FROM purchase_detials WHERE product_id = " + productId + ";";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                PurchaseDetails purchaseDetails = new PurchaseDetails();
                purchaseDetailsList.add(this.purchaseMapper.mapToPurchaseDetails(purchaseDetails, rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return purchaseDetailsList;
    }

    public Purchase findPurchaseById(String id) {
        Purchase purchase = new Purchase();

        try (Statement st = this.dbConfig.getConnection().createStatement()) {

            String query = "SELECT * FROM purchase\n" +
                    "INNER JOIN employee\n" +
                    "on employee.emp_id = purchase.employee_id\n" +
                    "INNER JOIN supplier\n" +
                    "ON supplier.sup_id = purchase.supplier_id\n" +
                    "WHERE purchase_id=" + id + ";";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                this.purchaseMapper.mapToPurchase(purchase, rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return purchase;
    }

    public List<Purchase> findAllPurchases() {

        List<Purchase> purchaseList = new ArrayList<>();

        try (Statement st = this.dbConfig.getConnection().createStatement()) {

            String query = "SELECT * FROM purchase\n" +
                    "INNER JOIN employee\n" +
                    "on employee.emp_id = purchase.employee_id\n" +
                    "INNER JOIN supplier\n" +
                    "ON supplier.sup_id = purchase.supplier_id\n";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Purchase purchase = new Purchase();
                purchaseList.add(this.purchaseMapper.mapToPurchase(purchase, rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return purchaseList;
    }

    @Override
    public List<Purchase> findPurchaseListBySupplierId(String supplierId) {

        List<Purchase> purchaseList = new ArrayList<>();

        try (Statement st = this.dbConfig.getConnection().createStatement()) {

            String query = "SELECT * FROM purchase\n" +
                    "INNER JOIN employee\n" +
                    "on employee.emp_id = purchase.employee_id\n" +
                    "INNER JOIN supplier\n" +
                    "ON supplier.sup_id = purchase.supplier_id\n" +
                    "WHERE supplier_id=" + supplierId + ";";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Purchase purchase = new Purchase();

                purchaseList.add(this.purchaseMapper.mapToPurchase(purchase, rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return purchaseList;
    }

    public List<Purchase> findPurchaseListByEmployeeId(String employeeId) {
        List<Purchase> purchaseList = new ArrayList<>();

        try (Statement st = this.dbConfig.getConnection().createStatement()) {
            String query = "SELECT * FROM purchase\n" +
                    "INNER JOIN employee\n" +
                    "on employee.emp_id = purchase.employee_id\n" +
                    "INNER JOIN supplier\n" +
                    "ON supplier.sup_id = purchase.supplier_id\n" +
                    "WHERE employee_id=" + employeeId + ";";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Purchase purchase = new Purchase();

                purchaseList.add(this.purchaseMapper.mapToPurchase(purchase, rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return purchaseList;
    }
}
