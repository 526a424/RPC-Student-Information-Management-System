package myRPC.Dao;

import myRPC.common.Course;
import myRPC.common.Course_avg;
import myRPC.common.Course_fail_rate;
import myRPC.common.Course_ranking;
import myRPC.service.CourseService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO extends UnicastRemoteObject implements CourseService {
    private Connection connection;

    public CourseDAO(Connection connection) throws RemoteException {
        this.connection = connection;
    }

    public int addCourse(String courseId, String courseName, String teacherName, int credit) {
        String query = "INSERT INTO course (courseId, courseName, teacherName, credit) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, courseId);
            preparedStatement.setString(2, courseName);
            preparedStatement.setString(3, teacherName);
            preparedStatement.setInt(4, credit);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int deleteCourseById(String courseId) {
        String query = "DELETE FROM course WHERE courseId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, courseId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateCourse(String cno, String after_cno, String after_cname, String after_cteacher, int after_ccredit) {
        String query = "UPDATE course SET courseId = ?, courseName = ?, teacherName = ?, credit = ? WHERE courseId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, after_cno);
            preparedStatement.setString(2, after_cname);
            preparedStatement.setString(3, after_cteacher);
            preparedStatement.setInt(4, after_ccredit);
            preparedStatement.setString(5, cno);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM course";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Course course = new Course();
                course.setCourseId(resultSet.getString("courseId"));
                course.setCourseName(resultSet.getString("courseName"));
                course.setTeacherName(resultSet.getString("teacherName"));
                course.setCredit(resultSet.getInt("credit"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public Course getCourseById(String courseId) {
        String query = "SELECT * FROM course WHERE courseId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, courseId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Course course = new Course();
                    course.setCourseId(resultSet.getString("courseId"));
                    course.setCourseName(resultSet.getString("courseName"));
                    course.setTeacherName(resultSet.getString("teacherName"));
                    course.setCredit(resultSet.getInt("credit"));
                    return course;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 查询课程平均分信息，返回一个ArrayList集合
    public ArrayList<Course_avg> course_avg() {
        ArrayList<Course_avg> courseAvgs = new ArrayList<>();
        String query = "SELECT courseId, courseName, AVG(grade) AS avg FROM course_grades GROUP BY courseId, courseName";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String courseId = resultSet.getString("courseId");
                String courseName = resultSet.getString("courseName");
                int avg = resultSet.getInt("avg");

                Course_avg courseAvg = new Course_avg(courseId, courseName, avg);
                courseAvgs.add(courseAvg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseAvgs;
    }

    // 查询课程不及格率，返回一个ArrayList集合
    public ArrayList<Course_fail_rate> fail_rate() {
        ArrayList<Course_fail_rate> failRates = new ArrayList<>();
        String query = "SELECT courseId, courseName, COUNT(*) AS failCount, COUNT(*) / (SELECT COUNT(*) FROM course_grades WHERE grade < 60 AND courseId = course_grades.courseId) AS failRate FROM course_grades WHERE grade < 60 GROUP BY courseId, courseName";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String courseId = resultSet.getString("courseId");
                String courseName = resultSet.getString("courseName");
                int failCount = resultSet.getInt("failCount");
                double failRate = resultSet.getDouble("failRate");

                Course_fail_rate courseFailRate = new Course_fail_rate(courseId, courseName, failCount, failRate);
                failRates.add(courseFailRate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return failRates;
    }

    // 查询课程排名情况，返回一个ArrayList集合
    public ArrayList<Course_ranking> course_ranking(String cno) {
        ArrayList<Course_ranking> courseRankings = new ArrayList<>();
        String query = "SELECT studentId, departmentName,courseName, name, gender,age, grade ,RANK() OVER (PARTITION BY courseId ORDER BY grade DESC) AS ranking FROM StudentGrade WHERE courseId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, cno);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String studentId = resultSet.getString("studentId");
                    String departmentName = resultSet.getString("departmentName");
                    String courseName = resultSet.getString("courseName");
                    String name = resultSet.getString("name");
                    String gender = resultSet.getString("gender");
                    int age = resultSet.getInt("age");
                    int grade = resultSet.getInt("grade");
                    int ranking = resultSet.getInt("ranking");

                    Course_ranking courseRanking = new Course_ranking(studentId, departmentName,courseName, name,gender, age, grade , ranking);
                    courseRankings.add(courseRanking);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseRankings;
    }
}
