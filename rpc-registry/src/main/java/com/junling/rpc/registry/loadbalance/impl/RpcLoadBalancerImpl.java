package com.junling.rpc.registry.loadbalance.impl;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.junling.rpc.registry.loadbalance.RpcLoadBalancer;

import java.util.List;

public class RpcLoadBalancerImpl implements RpcLoadBalancer {
    private int index = 0;

    @Override
    public Instance getInstance(List<Instance> instances) {

        if (index > instances.size()) {
            index = index%instances.size();
        }
       return instances.get(index++);
    }
}
