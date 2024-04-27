package myRPC.Dao;

import myRPC.common.StudentGrade;
import myRPC.service.StudentGradeService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentGradeDAO extends UnicastRemoteObject implements StudentGradeService {

    private Connection connection;

    public StudentGradeDAO(Connection connection) throws RemoteException {
        this.connection = connection;
    }

    public int addStudentGrade(String studentId, String courseId, int grade) {
        String query = "INSERT INTO student_grades (student_id, course_id, grade) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, studentId);
            statement.setString(2, courseId);
            statement.setInt(3, grade);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0 ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public int updateStudentGrade(String studentId, String courseId, int afterGrade) {
        String query = "UPDATE student_grades SET grade = ? WHERE student_id = ? AND course_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, afterGrade);
            statement.setString(2, studentId);
            statement.setString(3, courseId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0 ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int deleteStudentGrade(String studentId, String courseId) {
        String query = "DELETE FROM student_grades WHERE student_id = ? AND course_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, studentId);
            statement.setString(2, courseId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0 ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public StudentGrade getStudentGrade(String studentId, String courseId) {
        String query = "SELECT * FROM student_grades WHERE student_id = ? AND course_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, studentId);
            statement.setString(2, courseId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String courseName = resultSet.getString("course_name");
                int grade = resultSet.getInt("grade");
                return new StudentGrade(studentId, name, courseId, courseName, grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<StudentGrade> getAllStudentGrades() {
        List<StudentGrade> studentGrades = new ArrayList<>();
        String query = "SELECT * FROM student_grades";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String studentId = resultSet.getString("student_id");
                String name = resultSet.getString("name");
                String courseId = resultSet.getString("course_id");
                String courseName = resultSet.getString("course_name");
                int grade = resultSet.getInt("grade");

                StudentGrade studentGrade = new StudentGrade(studentId, name, courseId, courseName, grade);
                studentGrades.add(studentGrade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentGrades;
    }
}
