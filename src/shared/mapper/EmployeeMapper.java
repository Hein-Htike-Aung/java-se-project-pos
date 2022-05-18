package shared.mapper;

import entities.Employee;

import java.sql.ResultSet;

public class EmployeeMapper {

    public Employee mapToEmployee(Employee employee, ResultSet rs) {
        try {
            employee.setId(rs.getInt("emp_id"));
            employee.setName(rs.getString("emp_name"));
            employee.setPhone(rs.getString("emp_phone"));
            employee.setEmail(rs.getString("emp_email"));
            employee.setAddress(rs.getString("emp_address"));
            employee.setUsername(rs.getString("username"));
            employee.setActive(rs.getBoolean("active"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }
}
