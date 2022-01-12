package com.junling.rpc.registry.loadbalance;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

public interface RpcLoadBalancer {
    Instance getInstance(List<Instance> instances);
}
