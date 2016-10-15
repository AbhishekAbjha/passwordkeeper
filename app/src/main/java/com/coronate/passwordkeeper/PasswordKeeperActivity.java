package com.coronate.passwordkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.coronate.passwordkeeper.dao.passwordaccount.IPasswordAccountHelper;
import com.coronate.passwordkeeper.dao.passwordaccount.PasswordAccountHelper;

import com.coronate.passwordkeeper.passwordaccount.PasswordAccount;
import com.coronate.passwordkeeper.passwordaccount.PasswordAccountAdapter;
import com.coronate.passwordkeeper.passwordaccount.PasswordAccountActivity;

import java.util.ArrayList;


public class PasswordKeeperActivity extends AppCompatActivity {

    private Button btn_add_account;
    private ListView lv_password_account;
    private ArrayList<String> listOfAccountName = new ArrayList<String>();
    private IPasswordAccountHelper passwordAccountHelper;
    private ArrayList<PasswordAccount> passwordAccountsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting main page
        setContentView(R.layout.password_keeper);

        passwordAccountHelper = new PasswordAccountHelper(getApplicationContext());
        passwordAccountHelper.open();
        passwordAccountsList = passwordAccountHelper.fetchAllPasswordAccount();
        passwordAccountHelper.close();

        PasswordAccountAdapter lvPasswordAccountAdapter = new PasswordAccountAdapter(this, passwordAccountsList);
        lv_password_account = (ListView)findViewById(R.id.lv_password_account);
        lv_password_account.setAdapter(lvPasswordAccountAdapter);

        //On click event of password add button
        btn_add_account = (Button) findViewById(R.id.btn_add_account);
        btn_add_account.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                try {
                    Intent pwdAccountIntent = new Intent(getApplicationContext(), PasswordAccountActivity.class);
                    pwdAccountIntent.putExtra("Mode", "Insert");
                    startActivity(pwdAccountIntent);
                    finish();
                } catch (Exception ex) {
                    Log.e(getClass().getSimpleName(), ex.toString());
                }
            }
        });

        //On click event of list view
        lv_password_account.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_password_account_id = (TextView) view.findViewById(R.id.password_account_id);
                long passwordAccountId = Long.parseLong(tv_password_account_id.getText().toString());

                Intent pwdAccountIntent = new Intent(getApplicationContext(), PasswordAccountActivity.class);
                pwdAccountIntent.putExtra("Mode", "View");
                pwdAccountIntent.putExtra("passwordAccountId", passwordAccountId);
                startActivity(pwdAccountIntent);
                finish();
            }
        });
    }
}
