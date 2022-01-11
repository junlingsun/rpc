package com.junling.rpc.sample.api;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 806830413325449580L;
    private String username;
}
