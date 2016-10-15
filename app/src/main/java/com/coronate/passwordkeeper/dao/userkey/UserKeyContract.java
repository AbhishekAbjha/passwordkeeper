package com.coronate.passwordkeeper.dao.userkey;


public class UserKeyContract {

    private static final String TEXT_TYPE = " TEXT";

    public UserKeyContract(){}

    public static abstract class UserKey{
        public static final String TABLE_NAME = "user_key";
        public static final String COLUMN_NAME_KEY = "key";
    }

    public static final String SQL_CREATE_USERKEY_TABLE =
                "CREATE TABLE " + UserKey.TABLE_NAME + " (" +
                        UserKey.COLUMN_NAME_KEY + TEXT_TYPE + " )";

    public static final String SQL_DELETE_USERKEY_TABLE =
                "DROP TABLE IF EXISTS " + UserKey.TABLE_NAME;
}
