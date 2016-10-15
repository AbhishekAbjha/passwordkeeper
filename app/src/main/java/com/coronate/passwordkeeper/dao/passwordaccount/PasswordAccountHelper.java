package com.coronate.passwordkeeper.dao.passwordaccount;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.coronate.passwordkeeper.dao.PasswordKeeperDBHelper;
import com.coronate.passwordkeeper.passwordaccount.PasswordAccount;


import java.util.ArrayList;

public class PasswordAccountHelper implements IPasswordAccountHelper{

    private PasswordKeeperDBHelper passwordAccountHelper;
    private SQLiteDatabase passwordAccountDB;


    public PasswordAccountHelper(Context context){
        passwordAccountHelper = PasswordKeeperDBHelper.getInstance(context);
    }

    public PasswordAccountHelper open(){
        passwordAccountDB = passwordAccountHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        passwordAccountDB.close();
    }

    @Override
    public long insertPasswordAccount(PasswordAccount passwordAccount){
        long rowId = 0;
        try {
            if(passwordAccount == null){
                return rowId;
            }
            ContentValues values = new ContentValues();
            values.put(PasswordAccountContract.PasswordAccount.COLUMN_NAME_ACCOUNT_NAME, passwordAccount.getAccountName()); // Account Name
            values.put(PasswordAccountContract.PasswordAccount.COLUMN_NAME_USERNAME, passwordAccount.getUsername()); //Username
            values.put(PasswordAccountContract.PasswordAccount.COLUMN_NAME_PASSWORD, passwordAccount.getPassword()); //Password
            values.put(PasswordAccountContract.PasswordAccount.COLUMN_NAME_NOTES, passwordAccount.getNotes()); //Notes
            values.put(PasswordAccountContract.PasswordAccount.COLUMN_NAME_IS_DELETED, passwordAccount.getIsDeleted()); //Deleted
            values.put(PasswordAccountContract.PasswordAccount.COLUMN_NAME_CREATED_DATE, passwordAccount.getDateCreated()); //Created Date
            values.put(PasswordAccountContract.PasswordAccount.COLUMN_NAME_LAST_UPDATED, passwordAccount.getLastUpdated()); //Last Updated Date

            rowId = passwordAccountDB.insert(PasswordAccountContract.PasswordAccount.TABLE_NAME, null, values);
        }catch(Exception ex){
            Log.e("Error while inserting", ex.toString());
        }

        return rowId;
    }

    @Override
    public ArrayList<PasswordAccount> fetchAllPasswordAccount(){
        ArrayList<PasswordAccount> passwordAccountList = new ArrayList<PasswordAccount>();

        try {
            String query = "select * from " + PasswordAccountContract.PasswordAccount.TABLE_NAME +
                    " where "+ PasswordAccountContract.PasswordAccount.COLUMN_NAME_IS_DELETED + " =0";

            Cursor res = passwordAccountDB.rawQuery(query, null);
            res.moveToFirst();

            while (res.isAfterLast() == false) {
                passwordAccountList.add(new PasswordAccount(
                        res.getLong(res.getColumnIndex(PasswordAccountContract.PasswordAccount.COLUMN_NAME_ACCOUNT_ID)),
                        res.getString(res.getColumnIndex(PasswordAccountContract.PasswordAccount.COLUMN_NAME_ACCOUNT_NAME)),
                        res.getString(res.getColumnIndex(PasswordAccountContract.PasswordAccount.COLUMN_NAME_USERNAME)),
                        res.getString(res.getColumnIndex(PasswordAccountContract.PasswordAccount.COLUMN_NAME_PASSWORD)),
                        res.getString(res.getColumnIndex(PasswordAccountContract.PasswordAccount.COLUMN_NAME_NOTES)),
                        res.getInt(res.getColumnIndex(PasswordAccountContract.PasswordAccount.COLUMN_NAME_IS_DELETED)),
                        res.getString(res.getColumnIndex(PasswordAccountContract.PasswordAccount.COLUMN_NAME_CREATED_DATE)),
                        res.getString(res.getColumnIndex(PasswordAccountContract.PasswordAccount.COLUMN_NAME_LAST_UPDATED))));
                res.moveToNext();
            }
        }catch(Exception ex){
            Log.e("Error fetching Records", ex.toString());
        }

        return passwordAccountList;
    }

    @Override
    public PasswordAccount fetchPasswordAccount(long passwordAccountId) {

        PasswordAccount passwordAccount = null;

        try {
            String query = "select * from " + PasswordAccountContract.PasswordAccount.TABLE_NAME + " where " +
                    PasswordAccountContract.PasswordAccount.COLUMN_NAME_ACCOUNT_ID + " = "
                    + String.valueOf(passwordAccountId) + " AND "
                    + PasswordAccountContract.PasswordAccount.COLUMN_NAME_IS_DELETED + " =0";

            Cursor res = passwordAccountDB.rawQuery(query, null);

            res.moveToFirst();

            while (res.isAfterLast() == false) {
                passwordAccount = new PasswordAccount(
                        res.getLong(res.getColumnIndex(PasswordAccountContract.PasswordAccount.COLUMN_NAME_ACCOUNT_ID)),
                        res.getString(res.getColumnIndex(PasswordAccountContract.PasswordAccount.COLUMN_NAME_ACCOUNT_NAME)),
                        res.getString(res.getColumnIndex(PasswordAccountContract.PasswordAccount.COLUMN_NAME_USERNAME)),
                        res.getString(res.getColumnIndex(PasswordAccountContract.PasswordAccount.COLUMN_NAME_PASSWORD)),
                        res.getString(res.getColumnIndex(PasswordAccountContract.PasswordAccount.COLUMN_NAME_NOTES)),
                        res.getInt(res.getColumnIndex(PasswordAccountContract.PasswordAccount.COLUMN_NAME_IS_DELETED)),
                        res.getString(res.getColumnIndex(PasswordAccountContract.PasswordAccount.COLUMN_NAME_CREATED_DATE)),
                        res.getString(res.getColumnIndex(PasswordAccountContract.PasswordAccount.COLUMN_NAME_LAST_UPDATED)));
                res.moveToNext();
            }
        }catch(Exception ex){
            Log.e("Error fetching Records", ex.toString());
        }

        return passwordAccount;
    }

    @Override
    public long updatePasswordAccount(PasswordAccount passwordAccount) {
        long rowId = 0;

        if(passwordAccount == null){
            return rowId;
        }

        String where = PasswordAccountContract.PasswordAccount.COLUMN_NAME_ACCOUNT_ID + " = ?";
        String[] whereArguments = {String.valueOf(passwordAccount.getAccountId())};

        ContentValues values = new ContentValues();

        values.put(PasswordAccountContract.PasswordAccount.COLUMN_NAME_ACCOUNT_NAME, passwordAccount.getAccountName()); // Account Name
        values.put(PasswordAccountContract.PasswordAccount.COLUMN_NAME_USERNAME, passwordAccount.getUsername()); //Username
        values.put(PasswordAccountContract.PasswordAccount.COLUMN_NAME_PASSWORD, passwordAccount.getPassword()); //Password
        values.put(PasswordAccountContract.PasswordAccount.COLUMN_NAME_NOTES, passwordAccount.getNotes()); //Notes
        values.put(PasswordAccountContract.PasswordAccount.COLUMN_NAME_LAST_UPDATED, passwordAccount.getLastUpdated()); //Last updated

        try{
            rowId = passwordAccountDB.update(PasswordAccountContract.PasswordAccount.TABLE_NAME,
                    values,
                    where,
                    whereArguments);
        }catch(Exception ex){
            Log.i("Updating Record", ex.toString());
        }

        return rowId;
    }

    @Override
    public long deletePasswordAccount(PasswordAccount passwordAccount) {
        long rowId = 0;

        if(passwordAccount == null){
            return rowId;
        }

        String where = PasswordAccountContract.PasswordAccount.COLUMN_NAME_ACCOUNT_ID + " = ?";
        String[] whereArguments = {String.valueOf(passwordAccount.getAccountId())};

        ContentValues values = new ContentValues();
        values.put(PasswordAccountContract.PasswordAccount.COLUMN_NAME_IS_DELETED, passwordAccount.getIsDeleted()); //Notes

        try{
            rowId = passwordAccountDB.update(PasswordAccountContract.PasswordAccount.TABLE_NAME,
                    values,
                    where,
                    whereArguments);
        }catch(Exception ex){
            Log.i("Deleting Record", ex.toString());
        }

        return rowId;
    }
}
