package com.junling.sample.client;

import com.alibaba.nacos.api.exception.NacosException;
import com.junling.rpc.client.RpcClient;
import com.junling.rpc.client.RpcClientProxy;
import com.junling.rpc.client.netty.NettyClient;
import com.junling.rpc.registry.loadbalance.impl.RpcLoadBalancerImpl;
import com.junling.rpc.registry.nacos.NacosRegistry;
import com.junling.rpc.sample.api.User;
import com.junling.rpc.sample.api.UserService;

public class ClientTest {

    public static void main(String[] args) throws NacosException {

        UserService userService = new RpcClientProxy(new NacosRegistry(new RpcLoadBalancerImpl()), new NettyClient()).getProxy(UserService.class);
        User user = new User();
        user.setUsername("abc");
        String result = userService.saverUser(user);
        System.out.println(result);


    }
}
