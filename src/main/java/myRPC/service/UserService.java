package myRPC.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import myRPC.common.User;

public interface UserService extends Remote {
    public int addUser(String username, String password, String role) throws RemoteException;

    public int updateUser(String username, String afterUsername, String afterPassword, String afterLevel) throws RemoteException;

    public int deleteUser(String username) throws RemoteException;

    public User getUserByUsername(String username) throws RemoteException;

    public boolean userExists(String username) throws RemoteException;

    public User loginUser(String username, String password) throws RemoteException;

    public User registerUser(String username, String password, String level) throws RemoteException;

    public String getUserRole(String username) throws RemoteException;

    public ArrayList<User> getAllUsers() throws RemoteException;
}
