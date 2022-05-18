package services;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import entities.Brand;
import entities.Category;
import entities.Product;
import repositories.BrandRepo;
import config.DBConfig;
import repositories.ProductRepo;
import shared.exception.AppException;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BrandService implements BrandRepo {

    private final DBConfig dbConfig = new DBConfig();
    private ProductRepo productRepo;

    public void setProductRepo(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public void saveBrand(Brand brand) {
        try {

            PreparedStatement ps = this.dbConfig.getConnection()
                    .prepareStatement("INSERT INTO brand (brandName) VALUES (?);");

            ps.setString(1, brand.getName());
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            if (e instanceof MySQLIntegrityConstraintViolationException) {
                JOptionPane.showMessageDialog(null, "Already Exists");
            }
        }

    }

    public void updateBrand(String id, Brand brand) {
        try {

            PreparedStatement ps = this.dbConfig.getConnection()
                    .prepareStatement("UPDATE brand SET brandName = ? WHERE brand_id = ?");

            ps.setString(1, brand.getName());
            ps.setString(2, id);
            ps.execute();

            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Brand> findAllBrands() {
        List<Brand> brandList = new ArrayList<>();

        try (Statement st = this.dbConfig.getConnection().createStatement()) {

            String query = "SELECT * FROM brand";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Brand brand = new Brand();
                brand.setId(rs.getInt("brand_id"));
                brand.setName(rs.getString("brandName"));
                brandList.add(brand);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brandList;
    }

    public Brand findById(String id) {
        Brand brand = new Brand();

        try (Statement st = this.dbConfig.getConnection().createStatement()) {

            String query = "SELECT * FROM brand WHERE brand_id = " + id + ";";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                brand.setId(rs.getInt("brand_id"));
                brand.setName(rs.getString("brandName"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return brand;
    }

    public void delete(String id) {
        try {

            List<Product> productsByCategoryId = this.productRepo.findProductsByBrandId(id);

            if (productsByCategoryId.size() > 0) {
                throw new AppException("This brand cannot be deleted");
            }

            String query = "DELETE FROM brand WHERE brand_id = ?";

            PreparedStatement ps = this.dbConfig.getConnection().prepareStatement(query);
            ps.setString(1, id);

            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
