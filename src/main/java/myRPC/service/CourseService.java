package myRPC.service;

import myRPC.common.Course;
import myRPC.common.Course_avg;
import myRPC.common.Course_fail_rate;
import myRPC.common.Course_ranking;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface CourseService extends Remote {
    int addCourse(String courseId, String courseName, String teacherName, int credit) throws RemoteException;
    int deleteCourseById(String courseId) throws RemoteException;
    int updateCourse(String cno, String after_cno, String after_cname, String after_cteacher, int after_ccredit) throws RemoteException;
    List<Course> getAllCourses() throws RemoteException;
    Course getCourseById(String courseId) throws RemoteException;
    ArrayList<Course_avg> course_avg() throws RemoteException;
    ArrayList<Course_fail_rate> fail_rate() throws RemoteException;
    ArrayList<Course_ranking> course_ranking(String cno) throws RemoteException;
}
