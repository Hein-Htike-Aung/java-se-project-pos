package repositories;

import entities.Brand;

import java.util.List;

public interface BrandRepo {
    void saveBrand(Brand brand);

    void updateBrand(String id, Brand brand);

    List<Brand> findAllBrands();

    Brand findById(String id);

    void delete(String id);
}
