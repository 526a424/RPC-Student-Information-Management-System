package myRPC.service;

import myRPC.common.Student;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface StudentService extends Remote {
    int addStudent(String sno, String sname, String ssex, int sage, String clno) throws RemoteException;

    int updateStudent(String sno, String after_sno, String after_sname, String after_ssex, int after_sage, String after_clno) throws RemoteException;

    int deleteStudent(String studentId) throws RemoteException;

    List<Student> getAllStudents() throws RemoteException;

    Student getStudentById(String studentId) throws RemoteException;
}
