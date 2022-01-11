package com.junling.rpc.common.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceProvider {

    private static Map<String, Object> serviceMap = new ConcurrentHashMap<>();
//    private static Set<String> serviceSet = serviceMap.keySet();

    public synchronized <T> void serviceRegister(T service){
        String serviceName = service.getClass().getName();
        serviceMap.put(serviceName, service);

        Class<?>[] interfaces = service.getClass().getInterfaces();
        for (Class iface: interfaces) {
            serviceMap.put(iface.getName(), service);
        }
    }

    public <T> T getService(String serviceName) {
        return (T)serviceMap.get(serviceName);
    }
}
