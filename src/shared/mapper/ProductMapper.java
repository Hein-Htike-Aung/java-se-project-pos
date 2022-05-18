package shared.mapper;

import entities.Brand;
import entities.Category;
import entities.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper {
    public Product mapToProduct(Product product, ResultSet rs) {
        try {
            product.setId(rs.getInt("product_id"));
            product.setName(rs.getString("productName"));
            product.setQuantity(rs.getInt("quantity"));
            product.setPrice(rs.getInt("price"));
            Category category = new Category();
            category.setId(rs.getInt("category_id"));
            category.setName(rs.getString("categoryName"));
            Brand brand = new Brand();
            brand.setId(rs.getInt("brand_id"));
            brand.setName(rs.getString("brandName"));
            product.setBrand(brand);
            product.setCategory(category);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;

    }
}
