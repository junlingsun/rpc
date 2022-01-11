package com.junling.rpc.client;

import com.junling.rpc.common.bean.RpcRequest;
import com.junling.rpc.common.bean.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RpcClientProxy implements InvocationHandler {

    private String host;
    private Integer port;
    private RpcClient rpcClient;

    public RpcClientProxy(String host, Integer port, RpcClient rpcClient){
        this.host = host;
        this.port = port;
        this.rpcClient = rpcClient;
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
        Object object = rpcClient.send(host, port, rpcRequest);

        RpcResponse response = (RpcResponse) rpcClient.send(host, port, rpcRequest);
        return response.getData();
    }
}
