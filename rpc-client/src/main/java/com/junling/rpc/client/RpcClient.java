package com.junling.rpc.client;

import com.junling.rpc.common.bean.RpcRequest;
import lombok.Data;

public interface RpcClient {

    Object send(String host, Integer port, RpcRequest rpcRequest);

}
