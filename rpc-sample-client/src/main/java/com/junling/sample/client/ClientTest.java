package com.junling.sample.client;

import com.junling.rpc.client.RpcClient;
import com.junling.rpc.client.RpcClientProxy;
import com.junling.rpc.client.netty.NettyClient;
import com.junling.rpc.sample.api.User;
import com.junling.rpc.sample.api.UserService;

public class ClientTest {

    public static void main(String[] args) {
        RpcClient rpcClient = new NettyClient();
        UserService userService = new RpcClientProxy("localhost", 8888, rpcClient).getProxy(UserService.class);
        User user = new User();
        user.setUsername("abc");
        String result = userService.saverUser(user);
        System.out.println(result);

    }
}
