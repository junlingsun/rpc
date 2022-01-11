package com.junling.rpc.server;

public interface RpcServer {

    <T> void publish(Object service, Class<T> iface);
}
