package myRPC.Dao;

import myRPC.common.User;
import myRPC.service.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO extends UnicastRemoteObject implements UserService {
    private Connection connection;

    public UserDAO(Connection connection) throws RemoteException {
        this.connection = connection;
    }

    public int addUser(String username, String password, String role) {
        String query = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, role);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public int updateUser(String username, String afterUsername, String afterPassword, String afterLevel) {
        String query = "UPDATE user SET username = ?, password = ?, role = ? WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, afterUsername);
            preparedStatement.setString(2, afterPassword);
            preparedStatement.setString(3, afterLevel);
            preparedStatement.setString(4, username);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public int deleteUser(String username) {
        String query = "DELETE FROM user WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public User getUserByUsername(String username) {
        String query = "SELECT * FROM user WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRole(resultSet.getString("role"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 判断用户在数据库中是否存在，存在返回true，不存在返回false
    public boolean userExists(String username) {
        String query = "SELECT * FROM user WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 用户登录，登录成功返回一个含值User对象,如果登录失败返回一个User空对象
    public User loginUser(String username, String password) {
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRole(resultSet.getString("role"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new User(); // 返回一个空的User对象
    }

    // 用户注册，注册成功返回一个含值User对象，如果注册失败返回一个User空对象
    public User registerUser(String username, String password, String level) {
        if (userExists(username)) {
            return new User(); // 用户已存在，返回一个空的User对象
        }
        int result = addUser(username, password, level);
        if (result == 1) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setRole(level);
            return newUser; // 注册成功，返回注册的User对象
        } else {
            return new User(); // 注册失败，返回一个空的User对象
        }
    }


    // 获取用户的权限级别，若存在则返回级别(管理员，普通用户),若不存在返回空
    public String getUserRole(String username) {
        String query = "SELECT role FROM user WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("role");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // 用户不存在，返回空
    }

    // 获取数据库中所有用户的信息，用ArrayList返回
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM user";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
