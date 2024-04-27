package myRPC.Dao;

import myRPC.common.Student;
import myRPC.service.StudentService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO extends UnicastRemoteObject implements StudentService{
    private Connection connection;

    public StudentDAO(Connection connection) throws RemoteException {
        this.connection = connection;
    }

    public int addStudent(String sno, String sname, String ssex, int sage, String clno) {
        String query = "INSERT INTO student (studentId, name, gender, age, classId) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, sno);
            preparedStatement.setString(2, sname);
            preparedStatement.setString(3, ssex);
            preparedStatement.setInt(4, sage);
            preparedStatement.setString(5, clno);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public int updateStudent(String sno, String after_sno, String after_sname, String after_ssex, int after_sage, String after_clno) {
        String query = "UPDATE student SET studentId = ?, name = ?, gender = ?, age = ?, classId = ? WHERE studentId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, after_sno);
            preparedStatement.setString(2, after_sname);
            preparedStatement.setString(3, after_ssex);
            preparedStatement.setInt(4, after_sage);
            preparedStatement.setString(5, after_clno);
            preparedStatement.setString(6, sno);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public int deleteStudent(String studentId) {
        String query = "DELETE FROM student WHERE studentId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, studentId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM student";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getString("studentId"));
                student.setName(resultSet.getString("name"));
                student.setGender(resultSet.getString("gender"));
                student.setAge(resultSet.getInt("age"));
                student.setClassId(resultSet.getString("classId"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public Student getStudentById(String studentId) {
        String query = "SELECT * FROM student WHERE studentId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, studentId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Student student = new Student();
                    student.setStudentId(resultSet.getString("studentId"));
                    student.setName(resultSet.getString("name"));
                    student.setGender(resultSet.getString("gender"));
                    student.setAge(resultSet.getInt("age"));
                    student.setClassId(resultSet.getString("classId"));
                    return student;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
