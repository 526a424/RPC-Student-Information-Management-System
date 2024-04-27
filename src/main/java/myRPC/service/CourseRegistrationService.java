package myRPC.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CourseRegistrationService extends Remote {
    int registerCourse(String studentId, String courseId) throws RemoteException;
    int unregisterCourse(String studentId, String courseId) throws RemoteException;
    boolean isRegistered(String studentId, String courseId) throws RemoteException;
}
