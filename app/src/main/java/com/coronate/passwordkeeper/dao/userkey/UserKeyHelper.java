package com.coronate.passwordkeeper.dao.userkey;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.coronate.passwordkeeper.dao.PasswordKeeperDBHelper;
import com.coronate.passwordkeeper.passwordaccount.PasswordAccount;

import java.util.ArrayList;

public class UserKeyHelper implements IUserKeyHelper {

    private PasswordKeeperDBHelper userKeyHelper;
    private SQLiteDatabase userKeyDB;


    public UserKeyHelper(Context context){
        userKeyHelper = PasswordKeeperDBHelper.getInstance(context);
    }

    public UserKeyHelper open(){
        userKeyDB = userKeyHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        userKeyDB.close();
    }

    @Override
    public long insertUserKey(String userKey){
        long rowId = 0;
        try {
            if(userKey == null){
                return rowId;
            }
            ContentValues values = new ContentValues();
            values.put(UserKeyContract.UserKey.COLUMN_NAME_KEY, userKey); // User key

            rowId = userKeyDB.insert(UserKeyContract.UserKey.TABLE_NAME, null, values);
        }catch(Exception ex){
            Log.e("Error while inserting", ex.toString());
        }

        return rowId;
    }

    @Override
    public String fetchUserKey(){
        String userKey = null;

        try {
            String query = "select * from " + UserKeyContract.UserKey.TABLE_NAME;

            Cursor res = userKeyDB.rawQuery(query, null);
            res.moveToFirst();

            while (res.isAfterLast() == false) {
                userKey = res.getString(res.getColumnIndex(UserKeyContract.UserKey.COLUMN_NAME_KEY));
                res.moveToNext();
            }
        }catch(Exception ex){
            Log.e("Error fetching Records", ex.toString());
        }

        return userKey;
    }


    @Override
    public long updateUserKey(String userKey) {
        long rowId = 0;

        if(userKey == null){
            return rowId;
        }

        ContentValues values = new ContentValues();

        values.put(UserKeyContract.UserKey.COLUMN_NAME_KEY, userKey); // user key

        try{
            rowId = userKeyDB.update(UserKeyContract.UserKey.TABLE_NAME,
                    values,
                    null,
                    null);
        }catch(Exception ex){
            Log.i("Updating Record", ex.toString());
        }

        return rowId;
    }

}
