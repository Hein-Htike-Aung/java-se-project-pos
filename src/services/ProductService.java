package services;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import entities.Product;
import entities.PurchaseDetails;
import repositories.ProductRepo;
import config.DBConfig;
import repositories.PurchaseRepo;
import repositories.SaleRepo;
import shared.exception.AppException;
import shared.mapper.ProductMapper;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductService implements ProductRepo {
    private final DBConfig dbConfig;
    private final ProductMapper productMapper;
    private PurchaseRepo purchaseRepo;

    public ProductService() {
        dbConfig = new DBConfig();
        productMapper = new ProductMapper();
    }

    public void setPurchaseRepo(PurchaseRepo purchaseRepo) {
        this.purchaseRepo = purchaseRepo;
    }

    public void createProduct(Product product) {
        try {

            PreparedStatement ps = this.dbConfig.getConnection()
                    .prepareStatement("INSERT INTO product (productName, price, quantity, brand_id, category_id) " +
                            "VALUES (?, ?, ?, ?, ?);");

            ps.setString(1, product.getName());
            ps.setInt(2, product.getPrice());
            ps.setInt(3, product.getQuantity());
            ps.setInt(4, product.getBrand().getId());
            ps.setInt(5, product.getCategory().getId());

            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            if (e instanceof MySQLIntegrityConstraintViolationException)
                JOptionPane.showMessageDialog(null, "Already Exists");
            else e.printStackTrace();
        }
    }

    public void updateProduct(String id, Product product) {
        try {

            PreparedStatement ps = this.dbConfig.getConnection()
                    .prepareStatement("UPDATE product SET productName=?, price=?, quantity=?, brand_id=?, category_id=?  WHERE product_id = ?");

            ps.setString(1, product.getName());
            ps.setString(2, String.valueOf(product.getPrice()));
            ps.setString(3, String.valueOf(product.getQuantity()));
            ps.setString(4, String.valueOf(product.getBrand().getId()));
            ps.setString(5, String.valueOf(product.getCategory().getId()));
            ps.setString(6, String.valueOf(id));

            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(String id) {
        try {

            // TODO: Check Validation In Sale

            List<PurchaseDetails> purcahseDetialsListByProductId = this.purchaseRepo.findPurchaseDetailsListByProductId(id);

            if (purcahseDetialsListByProductId.size() > 0) {
                throw new AppException("This Product cannot be deleted");
            }

            PreparedStatement ps = this.dbConfig.getConnection()
                    .prepareStatement("DELETE FROM product where product_id = ?");

            ps.setString(1, id);
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Product findById(String productId) {
        Product product = new Product();

        try (Statement st = this.dbConfig.getConnection().createStatement()) {

            String query = "SELECT * FROM product\n" +
                    "INNER JOIN category\n" +
                    "ON category.category_id = product.category_id\n" +
                    "INNER JOIN brand\n" +
                    "ON brand.brand_id = product.brand_id\n" +
                    "WHERE product_id = " + productId + ";";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                product = this.productMapper.mapToProduct(product, rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return product;
    }

    public List<Product> findAllProducts() {

        List<Product> productList = new ArrayList<>();

        try (Statement st = this.dbConfig.getConnection().createStatement()) {

            String query = "SELECT * FROM product\n" +
                    "INNER JOIN category\n" +
                    "ON category.category_id = product.category_id\n" +
                    "INNER JOIN brand\n" +
                    "ON brand.brand_id = product.brand_id;";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Product product = new Product();
                productList.add(this.productMapper.mapToProduct(product, rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return productList;
    }

    public List<Product> findProductsByCategoryId(String categoryId) {
        List<Product> productList = new ArrayList<>();

        try (Statement st = this.dbConfig.getConnection().createStatement()) {

            String query = "SELECT * FROM product\n" +
                    "INNER JOIN category\n" +
                    "ON category.category_id = product.category_id\n" +
                    "INNER JOIN brand\n" +
                    "ON brand.brand_id = product.brand_id\n" +
                    "WHERE product.category_id = " + categoryId + ";";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Product product = new Product();
                productList.add(this.productMapper.mapToProduct(product, rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

    public List<Product> findProductsByBrandId(String brandId) {
        List<Product> productList = new ArrayList<>();

        try (Statement st = this.dbConfig.getConnection().createStatement()) {

            String query = "SELECT * FROM product\n" +
                    "INNER JOIN category\n" +
                    "ON category.category_id = product.category_id\n" +
                    "INNER JOIN brand\n" +
                    "ON brand.brand_id = product.brand_id\n" +
                    "WHERE product.brand_id = " + brandId + ";";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Product product = new Product();
                productList.add(this.productMapper.mapToProduct(product, rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }
}
