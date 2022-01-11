package com.junling.rpc.common.bean;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class RpcResponse<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -2242632244347602527L;

    private  Integer code;
    private  String msg;
    private  T data;

    public static <T> RpcResponse<T> success(T data) {
        RpcResponse<T> rpcResponse = new RpcResponse();
        rpcResponse.setCode(1);
        rpcResponse.setMsg("success");
        rpcResponse.setData(data);
        return rpcResponse;
    }

    public static <T> RpcResponse<T> fail() {
        RpcResponse<T> rpcResponse = new RpcResponse();
        rpcResponse.setCode(0);
        rpcResponse.setMsg("fail");
        return rpcResponse;
    }
}
