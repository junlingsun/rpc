package com.junling.rpc.sample.server;

import com.junling.rpc.sample.api.User;
import com.junling.rpc.sample.api.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public String saverUser(User user) {
        String res = user.getUsername() + " was saved successfully";
        return res;
    }
}
