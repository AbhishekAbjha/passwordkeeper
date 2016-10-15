package com.coronate.passwordkeeper.dao.passwordaccount;


public class PasswordAccountContract{

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    public PasswordAccountContract(){}

    public static abstract class PasswordAccount{
        public static final String TABLE_NAME = "password_account";
        public static final String COLUMN_NAME_ACCOUNT_ID = "account_id";
        public static final String COLUMN_NAME_ACCOUNT_NAME = "account_name";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_NOTES = "notes";
        public static final String COLUMN_NAME_IS_DELETED = "is_deleted";
        public static final String COLUMN_NAME_CREATED_DATE = "created_date";
        public static final String COLUMN_NAME_LAST_UPDATED = "last_updated";
    }

    public static final String SQL_CREATE_PASSWORDACCOUNT_TABLE =
                "CREATE TABLE " + PasswordAccount.TABLE_NAME + " (" +
                        PasswordAccount.COLUMN_NAME_ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                        PasswordAccount.COLUMN_NAME_ACCOUNT_NAME + TEXT_TYPE + COMMA_SEP +
                        PasswordAccount.COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP +
                        PasswordAccount.COLUMN_NAME_PASSWORD + TEXT_TYPE + COMMA_SEP +
                        PasswordAccount.COLUMN_NAME_NOTES + TEXT_TYPE + COMMA_SEP +
                        PasswordAccount.COLUMN_NAME_IS_DELETED + TEXT_TYPE + COMMA_SEP +
                        PasswordAccount.COLUMN_NAME_CREATED_DATE + TEXT_TYPE + COMMA_SEP +
                        PasswordAccount.COLUMN_NAME_LAST_UPDATED + TEXT_TYPE + " )";

    public static final String SQL_DELETE_PASSWORDACCOUNT_TABLE =
            "DROP TABLE IF EXISTS " + PasswordAccount.TABLE_NAME;
}
