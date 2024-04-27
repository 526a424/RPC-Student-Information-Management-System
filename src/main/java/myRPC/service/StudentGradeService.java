package myRPC.service;

import myRPC.common.StudentGrade;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface StudentGradeService extends Remote {

    int addStudentGrade(String studentId, String courseId, int grade) throws RemoteException;

    int updateStudentGrade(String studentId, String courseId, int afterGrade) throws RemoteException;

    int deleteStudentGrade(String studentId, String courseId) throws RemoteException;

    StudentGrade getStudentGrade(String studentId, String courseId) throws RemoteException;

    List<StudentGrade> getAllStudentGrades() throws RemoteException;
}
