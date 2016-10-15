package com.coronate.passwordkeeper.dao.passwordaccount;

import com.coronate.passwordkeeper.passwordaccount.PasswordAccount;
import java.util.ArrayList;

public interface IPasswordAccountHelper {

    IPasswordAccountHelper open();

    void close();

    long insertPasswordAccount(PasswordAccount passwordAccount);

    ArrayList<PasswordAccount> fetchAllPasswordAccount();

    PasswordAccount fetchPasswordAccount(long passwordAccountId);

    long updatePasswordAccount(PasswordAccount passwordAccount);

    long deletePasswordAccount(PasswordAccount passwordAccount);

}
