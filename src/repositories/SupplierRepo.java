package repositories;

import entities.Supplier;

import java.util.List;

public interface SupplierRepo {

    void createSupplier(Supplier supplier);

    void updateSupplier(String id, Supplier supplier);

    Supplier findById(String id);

    List<Supplier> findAllSuppliers();

    void deleteSupplier(String id);
}
