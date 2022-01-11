package com.junling.rpc.client;

import com.junling.rpc.common.bean.RpcRequest;
import com.junling.rpc.common.bean.RpcResponse;
import com.junling.rpc.registry.ServiceRegistry;
import com.junling.rpc.registry.nacos.NacosRegistry;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RpcClientProxy implements InvocationHandler {

//    private String host;
//    private Integer port;
    private ServiceRegistry serviceRegistry;
    private RpcClient rpcClient;

    public RpcClientProxy(ServiceRegistry serviceRegistry, RpcClient rpcClient){
        this.rpcClient = rpcClient;
        this.serviceRegistry = serviceRegistry;
    }

    public <T> T getProxy(Class<T> iface) {
        return (T) Proxy.newProxyInstance(iface.getClassLoader(), new Class<?>[]{iface}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setInterfaceName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParameters(args);
        rpcRequest.setParameterTypes(method.getParameterTypes());

        String address = serviceRegistry.discover(rpcRequest.getInterfaceName());
        String[] arr = address.split(":");

        RpcResponse response = (RpcResponse) rpcClient.send(arr[0], Integer.parseInt(arr[1]), rpcRequest);
        return response.getData();
    }
}
