package myRPC.server;

import java.util.HashMap;
import java.util.Map;

/**
 * 存放服务接口名与服务端对应的实现类
 * 服务启动时要暴露其相关的实现类
 * 根据request中的interface调用服务端中相关实现类
 */

/*
    provideServiceInterface(Object service)方法：
    该方法用于将一个实现类的服务接口添加到interfaceProvider中。
    它首先每获取实现类的所有接口，然后将个接口名和对应的实现类对象存储在interfaceProvider中的Map中。

    getService(String interfaceName)方法：
    该方法用于根据接口名从interfaceProvider中获取对应的实现类对象。
    通过传入接口名，可以获取到实现了该接口的具体服务对象。

通过这两个方法，ServiceProvider类实现了将实现类的服务接口提供给其他类使用的功能。
这样其他类可以通过接口名获取到对应的服务对象，从而实现服务的调用和使用。
 */
public class ServiceProvider {
    /**
     * 一个实现类可能实现多个接口
     */
    private Map<String, Object> interfaceProvider;

    public ServiceProvider(){
        this.interfaceProvider = new HashMap<>();
    }

    public void provideServiceInterface(Object service){
        Class<?>[] interfaces = service.getClass().getInterfaces();

        for(Class clazz : interfaces){
            interfaceProvider.put(clazz.getName(),service);
        }

    }

    public Object getService(String interfaceName){
        return interfaceProvider.get(interfaceName);
    }
}
