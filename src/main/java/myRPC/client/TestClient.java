package myRPC.client;

import myRPC.common.*;
import myRPC.service.*;

import java.rmi.RemoteException;

public class TestClient {
        public static void main(String[] args) throws RemoteException {
                // 构建一个使用java Socket/ netty/....传输的客户端
                RPCClient rpcClient = new NettyRPCClient("127.0.0.1", 1099);
                // 把这个客户端传入代理客户端
                RPCClientProxy rpcClientProxy = new RPCClientProxy(rpcClient);

                // 代理客户端根据不同的服务，获得一个代理类， 并且这个代理类的方法以或者增强（封装数据，发送请求）
                UserService userService = rpcClientProxy.getProxy(UserService.class);
                // 调用方法
                User getUserByUsername = userService.getUserByUsername("user1");
                System.out.println("从服务端得到的user为：" + getUserByUsername);

                Integer integer = userService.addUser("gyy","123456","admin");
                System.out.println("向服务端用户插入数据："+integer);

                StudentService studentService = rpcClientProxy.getProxy(StudentService.class);
                Student student = studentService.getStudentById("S001");
                System.out.println("从服务端得到的student为：" + student);

        }
}