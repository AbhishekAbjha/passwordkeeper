package com.coronate.passwordkeeper.dao.userkey;

import com.coronate.passwordkeeper.passwordaccount.PasswordAccount;

import java.util.ArrayList;

public interface IUserKeyHelper {

    IUserKeyHelper open();

    void close();

    long insertUserKey(String userKey);

    String fetchUserKey();

    long updateUserKey(String userKey);

}
