package com.coronate.passwordkeeper.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.coronate.passwordkeeper.dao.passwordaccount.PasswordAccountContract;
import com.coronate.passwordkeeper.dao.userkey.UserKeyContract;

public final class PasswordKeeperDBHelper extends SQLiteOpenHelper{

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PasswordKeeper.db";
    private static PasswordKeeperDBHelper instance;
    private static SQLiteDatabase db;

    private PasswordKeeperDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static PasswordKeeperDBHelper getInstance(Context context){
        if(instance == null){
            synchronized (PasswordKeeperDBHelper.class){
                if(instance == null){
                    instance = new PasswordKeeperDBHelper(context);
                }
            }
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserKeyContract.SQL_CREATE_USERKEY_TABLE);
        db.execSQL(PasswordAccountContract.SQL_CREATE_PASSWORDACCOUNT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserKeyContract.SQL_DELETE_USERKEY_TABLE);
        db.execSQL(PasswordAccountContract.SQL_DELETE_PASSWORDACCOUNT_TABLE);
        onCreate(db);
    }

}
