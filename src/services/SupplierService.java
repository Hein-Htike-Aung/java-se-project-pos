package services;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import config.DBConfig;
import entities.Purchase;
import entities.Supplier;
import repositories.PurchaseRepo;
import repositories.SupplierRepo;
import shared.exception.AppException;
import shared.mapper.SupplierMapper;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SupplierService implements SupplierRepo {

    private final DBConfig dbConfig;
    private final SupplierMapper supplierMapper;
    private PurchaseRepo purchaseRepo;

    public SupplierService() {
        this.dbConfig = new DBConfig();
        this.supplierMapper = new SupplierMapper();
    }

    public void setPurchaseRepo(PurchaseRepo purchaseRepo) {
        this.purchaseRepo = purchaseRepo;
    }

    public void createSupplier(Supplier supplier) {
        try {
            PreparedStatement ps = this.dbConfig.getConnection()
                    .prepareStatement("INSERT INTO supplier (sup_name, sup_phone, sup_email, sup_address) VALUES (?, ?, ?, ?)");

            ps.setString(1, supplier.getName());
            ps.setString(2, supplier.getPhone());
            ps.setString(3, supplier.getEmail());
            ps.setString(4, supplier.getAddress());
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSupplier(String id, Supplier supplier) {
        try {
            PreparedStatement ps = this.dbConfig.getConnection()
                    .prepareStatement("UPDATE supplier SET sup_name=?, sup_phone=?, sup_email=?, sup_address=? WHERE sup_id=?");

            ps.setString(1, supplier.getName());
            ps.setString(2, supplier.getPhone());
            ps.setString(3, supplier.getAddress());
            ps.setString(4, supplier.getAddress());
            ps.setString(5, id);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Supplier findById(String id) {

        Supplier supplier = new Supplier();

        try (Statement st = this.dbConfig.getConnection().createStatement()) {

            String query = "SELECT * FROM supplier WHERE sup_id = " + id + ";";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                this.supplierMapper.mapToSupplier(supplier, rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return supplier;
    }

    public List<Supplier> findAllSuppliers() {

        List<Supplier> supplierList = new ArrayList<>();

        try (Statement st = this.dbConfig.getConnection().createStatement()) {

            String query = "SELECT * FROM supplier";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Supplier supplier = new Supplier();
                supplierList.add(this.supplierMapper.mapToSupplier(supplier, rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return supplierList;
    }

    public void deleteSupplier(String id) {
        try {

            List<Purchase> purchaseListBySupplierId = this.purchaseRepo.findPurchaseListBySupplierId(id);

            if (purchaseListBySupplierId.size() > 0) {
                throw new AppException("This supplier cannot be deleted");
            }

            PreparedStatement ps = this.dbConfig.getConnection()
                    .prepareStatement("DELETE FROM supplier WHERE sup_id = ?");

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
