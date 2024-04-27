package myRPC.Dao;

import myRPC.common.Class1;
import myRPC.service.Class1Service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Class1DAO extends UnicastRemoteObject implements Class1Service {
    private Connection connection;

    public Class1DAO(Connection connection) throws RemoteException {
        this.connection = connection;
    }

    public int addClass(String classId, String className, String departmentId) {
        String query = "INSERT INTO class1 (classId, className, departmentId) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, classId);
            preparedStatement.setString(2, className);
            preparedStatement.setString(3, departmentId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public List<Class1> getAllClasses() {
        List<Class1> classes = new ArrayList<>();
        String query = "SELECT * FROM class1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Class1 class1 = new Class1();
                class1.setClassId(resultSet.getString("classId"));
                class1.setClassName(resultSet.getString("className"));
                class1.setDepartmentId(resultSet.getString("departmentId"));
                classes.add(class1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classes;
    }

    public int updateClass(String clno, String after_clno, String after_clname, String after_dno) {
        String query = "UPDATE class1 SET clno = ?, className = ?, departmentId = ? WHERE clno = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, after_clno);
            preparedStatement.setString(2, after_clname);
            preparedStatement.setString(3, after_dno);
            preparedStatement.setString(4, clno);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public int deleteClass(String classId) {
        String query = "DELETE FROM class1 WHERE classId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, classId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Class1 getClassById(String classId) {
        String query = "SELECT * FROM class1 WHERE classId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, classId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Class1 class1 = new Class1();
                    class1.setClassId(resultSet.getString("classId"));
                    class1.setClassName(resultSet.getString("className"));
                    class1.setDepartmentId(resultSet.getString("departmentId"));
                    return class1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
