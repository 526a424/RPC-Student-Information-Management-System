package myRPC.Dao;

import myRPC.common.Department;
import myRPC.service.DepartmentService;
import myRPC.service.StudentService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO extends UnicastRemoteObject implements DepartmentService {
    private Connection connection;

    public DepartmentDAO(Connection connection) throws RemoteException {
        this.connection = connection;
    }

    public int addDepartment(String departmentId, String departmentName) {
        String query = "INSERT INTO department (departmentId, departmentName) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, departmentId);
            preparedStatement.setString(2, departmentName);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public int updateDepartment(String dno, String after_dno, String after_dname) {
        String query = "UPDATE department SET departmentName = ? WHERE departmentId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, after_dname);
            preparedStatement.setString(2, after_dno);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public int deleteDepartment(String departmentId) {
        String query = "DELETE FROM department WHERE departmentId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, departmentId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<Department> getAllDepartments() {
        ArrayList<Department> departments = new ArrayList<>();
        String query = "SELECT * FROM department";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Department department = new Department();
                department.setDepartmentId(resultSet.getString("departmentId"));
                department.setDepartmentName(resultSet.getString("departmentName"));
                departments.add(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

    public Department getDepartmentById(int departmentId) {
        String query = "SELECT * FROM department WHERE departmentId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, departmentId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Department department = new Department();
                    department.setDepartmentId(resultSet.getString("departmentId"));
                    department.setDepartmentName(resultSet.getString("departmentName"));
                    return department;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
