package com.junling.rpc.common.bean;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class RpcRequest implements Serializable {


    @Serial
    private static final long serialVersionUID = -1937181973543100462L;

    private String interfaceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] parameterTypes;
}
