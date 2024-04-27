package myRPC.service;

import myRPC.common.Department;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DepartmentService extends Remote {
    int addDepartment(String departmentId, String departmentName) throws RemoteException;
    int updateDepartment(String dno, String after_dno, String after_dname) throws RemoteException;
    int deleteDepartment(String departmentId) throws RemoteException;
    List getAllDepartments() throws RemoteException;
    Department getDepartmentById(int departmentId) throws RemoteException;
}

