package myRPC.Dao;

import myRPC.service.CourseRegistrationService;
import myRPC.service.DepartmentService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseRegistrationDAO extends UnicastRemoteObject implements CourseRegistrationService {
    private Connection connection;

    public CourseRegistrationDAO(Connection connection) throws RemoteException {
        this.connection = connection;
    }

    public int registerCourse(String studentId, String courseId) {
        String query = "INSERT INTO course_registration (studentId, courseId) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, courseId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int unregisterCourse(String studentId, String courseId) {
        String query = "DELETE FROM course_registration WHERE studentId = ? AND courseId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, courseId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean isRegistered(String studentId, String courseId) {
        String query = "SELECT * FROM course_registration WHERE studentId = ? AND courseId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, courseId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
