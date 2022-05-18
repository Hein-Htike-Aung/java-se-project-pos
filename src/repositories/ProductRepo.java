package repositories;

import entities.Product;

import java.util.List;

public interface ProductRepo {
    List<Product> findProductsByCategoryId(String categoryId);

    List<Product> findProductsByBrandId(String brandId);

    void createProduct(Product product);

    void updateProduct(String id, Product product);

    void deleteProduct(String id);

    Product findById(String productId);

    List<Product> findAllProducts();
}
