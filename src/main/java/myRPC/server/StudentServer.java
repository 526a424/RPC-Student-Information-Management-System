package myRPC.server;

import myRPC.Dao.*;
import myRPC.service.*;

import java.rmi.RemoteException;
import java.sql.*;

public class StudentServer {
    private static Connection connection;

    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/rpcdemo",
                    "root", "your-password");

            ServiceProvider serviceProvider = new ServiceProvider();

            // 将各个服务接口与其实现类关联起来
            UserService userService = new UserDAO(connection);
            serviceProvider.provideServiceInterface(userService);

            DepartmentService departmentService = new DepartmentDAO(connection);
            serviceProvider.provideServiceInterface(departmentService);

            StudentService studentService = new StudentDAO(connection);
            serviceProvider.provideServiceInterface(studentService);

            Class1Service class1Service = new Class1DAO(connection);
            serviceProvider.provideServiceInterface(class1Service);

            CourseService courseService = new CourseDAO(connection);
            serviceProvider.provideServiceInterface(courseService);

            CourseRegistrationService courseRegistrationService = new CourseRegistrationDAO(connection);
            serviceProvider.provideServiceInterface(courseRegistrationService);

            StudentGradeService studentGradeService = new StudentGradeDAO(connection);
            serviceProvider.provideServiceInterface(studentGradeService);

            Server RPCServer = new NettyRPCServer(serviceProvider);
            RPCServer.start(1099);
        } catch (SQLException | RemoteException e) {
            e.printStackTrace();
        }
    }
}
