package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import entities.Category;
import entities.Product;
import repositories.CategoryRepo;
import repositories.ProductRepo;
import config.DBConfig;
import shared.exception.AppException;

import javax.swing.*;

public class CategoryService implements CategoryRepo {

    private final DBConfig dbConfig = new DBConfig();
    private ProductRepo productRepo;

    public void setProductRepo(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public void saveCategory(Category category) {
        try {

            PreparedStatement ps = this.dbConfig.getConnection()
                    .prepareStatement("INSERT INTO Category (categoryName)  VALUES (?);");

            ps.setString(1, category.getName());
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            if (e instanceof MySQLIntegrityConstraintViolationException) {
                JOptionPane.showMessageDialog(null, "Already Exists");
            }
        }
    }

    public void updateCategory(String id, Category category) {
        try {

            PreparedStatement ps = this.dbConfig.getConnection()
                    .prepareStatement("UPDATE category SET categoryName = ? WHERE category_id = ?");

            ps.setString(1, category.getName());
            ps.setString(2, id);
            ps.execute();

            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Category> findAllCategories() {

        List<Category> categoryList = new ArrayList<>();
        try (Statement st = this.dbConfig.getConnection().createStatement()) {

            String query = "SELECT * FROM category";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Category c = new Category();
                c.setId((rs.getInt("category_id")));
                c.setName(rs.getString("categoryName"));
                categoryList.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryList;
    }

    public Category findById(String id) {
        Category category = new Category();

        try (Statement st = this.dbConfig.getConnection().createStatement()) {

            String query = "SELECT * FROM category WHERE category_id = " + id + ";";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                category.setId(rs.getInt("category_id"));
                category.setName(rs.getString("categoryName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }

    public void deleteCategory(String id) {
        try {

            List<Product> productsByCategoryId = this.productRepo.findProductsByCategoryId(id);

            if (productsByCategoryId.size() > 0) {
                throw new AppException("This category cannot be deleted");
            }


            String query = "DELETE FROM category WHERE category_id= ?;";

            PreparedStatement ps = this.dbConfig.getConnection().prepareStatement(query);

            ps.setString(1, id);
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            if (e instanceof AppException)
                JOptionPane.showMessageDialog(null, e.getMessage());
            else e.printStackTrace();
        }
    }

}
