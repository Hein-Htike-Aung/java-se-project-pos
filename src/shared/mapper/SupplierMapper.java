package shared.mapper;

import entities.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierMapper {

    public Supplier mapToSupplier(Supplier supplier, ResultSet rs) {

        try {
            supplier.setId(rs.getInt("sup_id"));
            supplier.setName(rs.getString("sup_name"));
            supplier.setAddress(rs.getString("sup_address"));
            supplier.setEmail(rs.getString("sup_email"));
            supplier.setPhone(rs.getString("sup_phone"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplier;
    }
}
