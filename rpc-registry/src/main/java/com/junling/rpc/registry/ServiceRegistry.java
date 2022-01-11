package com.junling.rpc.registry;

public interface ServiceRegistry {

    void register(String serviceName, String serviceAddress);
    String discover(String serviceName);
}
