package services;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import config.DBConfig;
import entities.Employee;
import entities.UserRole;
import repositories.EmployeeRepo;
import shared.mapper.EmployeeMapper;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService implements EmployeeRepo {

    private final EmployeeMapper employeeMapper;
    private final DBConfig dbConfig;

    public EmployeeService() {
        this.employeeMapper = new EmployeeMapper();
        this.dbConfig = new DBConfig();
    }

    public void createEmployee(Employee employee) {
        try {

            PreparedStatement ps = this.dbConfig.getConnection()
                    .prepareStatement("INSERT INTO employee (emp_name, emp_phone, emp_email, emp_address, active, role) VALUES (?, ?, ?, ?, ?, ?)");

            ps.setString(1, employee.getName());
            ps.setString(2, employee.getPhone());
            ps.setString(3, employee.getEmail());
            ps.setString(4, employee.getAddress());
            ps.setBoolean(5, true);
            ps.setString(6, UserRole.USER.toString());
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            if (e instanceof MySQLIntegrityConstraintViolationException) {
                JOptionPane.showMessageDialog(null, "Already Exists");
            }
        }
    }

    public void updateEmployee(String id, Employee employee) {
        try {
            PreparedStatement ps = this.dbConfig.getConnection()
                    .prepareStatement("UPDATE employee SET emp_name=?, emp_phone=?, emp_email=?, emp_address=?, username=?, password=?, active=? WHERE emp_id=?");

            ps.setString(1, employee.getName());
            ps.setString(2, employee.getPhone());
            ps.setString(3, employee.getEmail());
            ps.setString(4, employee.getAddress());
            ps.setString(5, employee.getUsername());
            ps.setString(6, employee.getPassword());
            ps.setBoolean(7, true);
            ps.setString(8, id);

            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            if (e instanceof MySQLIntegrityConstraintViolationException)
                JOptionPane.showMessageDialog(null, "Already Exists");
            else e.printStackTrace();
        }
    }

    public void blockEmployee(String id) {
        try {
            PreparedStatement ps = this.dbConfig.getConnection()
                    .prepareStatement("UPDATE employee SET active=? WHERE emp_id=?");

            ps.setBoolean(1, false);
            ps.setString(2, id);

            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Employee> findAllEmployees() {

        List<Employee> employeeList = new ArrayList<>();
        try (Statement st = this.dbConfig.getConnection().createStatement()) {

            String query = "SELECT * FROM employee";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Employee employee = new Employee();
                employeeList.add(this.employeeMapper.mapToEmployee(employee, rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeList;

    }

    public Employee findEmployeeById(String id) {
        Employee employee = new Employee();

        try (Statement st = this.dbConfig.getConnection().createStatement()) {

            String query = "SELECT * FROM employee WHERE emp_id = " + id + ";";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                this.employeeMapper.mapToEmployee(employee, rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return employee;
    }


}
