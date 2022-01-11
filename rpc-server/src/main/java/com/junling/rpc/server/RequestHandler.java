package com.junling.rpc.server;

import com.junling.rpc.common.bean.RpcRequest;
import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Data
public class RequestHandler {

    private RpcRequest rpcRequest;
    private Object service;

    public Object handle(){

        String ifaceName = rpcRequest.getInterfaceName();
        String methodName = rpcRequest.getMethodName();
        Object[] parameters = rpcRequest.getParameters();
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();

        try{
            Method method = service.getClass().getMethod(methodName, parameterTypes);
            return method.invoke(service, parameters);
        } catch (NoSuchMethodException e) {

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        return null;
    }
}
