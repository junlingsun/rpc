package com.junling.rpc.registry.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ListView;
import com.alibaba.nacos.api.naming.pojo.ServiceInfo;
import com.junling.rpc.registry.ServiceRegistry;

import java.util.List;

public class NacosRegistry implements ServiceRegistry {

    private static String serverAddr = "localhost:8848";
    private static NamingService namingService;

    static {
        try{
            namingService = NamingFactory.createNamingService(serverAddr);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void register(String serviceName, String serviceAddress) {
        String[] arr = serviceAddress.split(":");
        try {
            namingService.registerInstance(serviceName, arr[0], Integer.parseInt(arr[1]));
        } catch (NacosException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String discover(String serviceName) {

        try {
            List<Instance> allInstances = namingService.getAllInstances(serviceName);
            Instance instance = allInstances.get(0);
            String ip = instance.getIp();
            Integer port = instance.getPort();
            return ip+":"+port;
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String deregister() throws NacosException {
        ListView<String> services = namingService.getServicesOfServer(1, 100);
        return null;

    }
}
