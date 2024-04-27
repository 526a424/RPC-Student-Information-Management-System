package myRPC.service;

import myRPC.common.Class1;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Class1Service extends Remote {
    int addClass(String classId, String className, String departmentId) throws RemoteException;
    int updateClass(String clno, String after_clno, String after_clname, String after_dno) throws RemoteException;
    int deleteClass(String classId) throws RemoteException;
    Class1 getClassById(String classId) throws RemoteException;
    List<Class1> getAllClasses() throws RemoteException;
}
