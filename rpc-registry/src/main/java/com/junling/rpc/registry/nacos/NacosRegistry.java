package com.junling.rpc.registry.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ListView;
import com.alibaba.nacos.api.naming.pojo.ServiceInfo;
import com.junling.rpc.registry.ServiceRegistry;
import com.junling.rpc.registry.loadbalance.RpcLoadBalancer;

import java.util.List;

public class NacosRegistry implements ServiceRegistry {

    private static String serverAddr = "localhost:8848";
    private static NamingService namingService;
    private RpcLoadBalancer rpcLoadBalancer;

    public NacosRegistry(RpcLoadBalancer rpcLoadBalancer) {
        this.rpcLoadBalancer = rpcLoadBalancer;
    }

    static {
        try{
            namingService = NamingFactory.createNamingService(serverAddr);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void register(String serviceName, String serviceAddress) {
        System.out.println("register>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
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
            if (allInstances.size() < 1) {
                throw new Exception("nacos is not started");
            }


            Instance instance = null;
            if (rpcLoadBalancer != null) {
                instance = rpcLoadBalancer.getInstance(allInstances);
            }else {
                instance = allInstances.get(0);
            }
            String ip = instance.getIp();
            Integer port = instance.getPort();
            return ip+":"+port;
        } catch (NacosException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * TODO: need to implement the deregister to server side.
     * @throws NacosException
     */
    public void deregister() throws NacosException {

        ListView<String> services = namingService.getServicesOfServer(1, 100);
        List<String> data = services.getData();

        for (String service: data) {
            List<Instance> allInstances = namingService.getAllInstances(service);
            for (Instance instance: allInstances) {
                namingService.deregisterInstance(service, instance);
            }
        }

    }
}
