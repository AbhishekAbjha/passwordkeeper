package com.coronate.passwordkeeper.passwordaccount;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.coronate.passwordkeeper.PasswordKeeperActivity;
import com.coronate.passwordkeeper.R;
import com.coronate.passwordkeeper.dao.passwordaccount.IPasswordAccountHelper;
import com.coronate.passwordkeeper.dao.passwordaccount.PasswordAccountHelper;

import com.coronate.passwordkeeper.dao.userkey.IUserKeyHelper;
import com.coronate.passwordkeeper.dao.userkey.UserKeyHelper;
import com.coronate.passwordkeeper.util.Crypt;
import com.coronate.passwordkeeper.util.TimeStamp;

import org.w3c.dom.Text;

public class PasswordAccountActivity extends AppCompatActivity {

    private IPasswordAccountHelper passwordAccountHelper;
    private IUserKeyHelper userKeyHelper;
    private EditText et_account_name;
    private EditText et_username;
    private EditText et_password;
    private EditText et_notes;
    private CheckBox cb_show_password;
    private TextView tv_error;
    private Button btn_save_account;
    private Button btn_edit_account;
    private Button btn_delete_account;
    private Button btn_update_account;
    private Button btn_cancel;
    private PasswordAccount passwordAccount;
    private String ENCRYPTION_KEY_SYSTEM = "**********";
    private final String ENCRYPTION_KEY_LOGIN = "**************";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.password_account);
        String mode = getIntent().getExtras().getString("Mode");
        userKeyHelper = new UserKeyHelper(getApplicationContext());
        userKeyHelper.open();
        final String ENCRYPTION_KEY = ENCRYPTION_KEY_SYSTEM + Crypt.decrypt(ENCRYPTION_KEY_LOGIN, userKeyHelper.fetchUserKey());
        userKeyHelper.close();

        passwordAccountHelper = new PasswordAccountHelper(getApplicationContext());

        et_account_name = (EditText) findViewById(R.id.et_account_name);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_notes = (EditText) findViewById(R.id.et_notes);
        btn_save_account = (Button) findViewById(R.id.btn_save_account);
        btn_edit_account = (Button) findViewById(R.id.btn_edit_account);
        btn_delete_account = (Button) findViewById(R.id.btn_delete_account);
        btn_update_account = (Button) findViewById(R.id.btn_update_account);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        cb_show_password = (CheckBox) findViewById(R.id.cb_show_password);
        tv_error = (TextView) findViewById(R.id.tv_error);

        if(mode.equals("Insert")){
            btn_edit_account.setVisibility(View.GONE);
            btn_delete_account.setVisibility(View.GONE);
            btn_update_account.setVisibility(View.GONE);
        }

        if(mode.equals("Update")){
            btn_save_account.setVisibility(View.GONE);
            btn_edit_account.setVisibility(View.GONE);
            btn_delete_account.setVisibility(View.GONE);
        }

        if(mode.equals("View")){
            btn_save_account.setVisibility(View.GONE);
            btn_update_account.setVisibility(View.GONE);
            long passwordAccountId = getIntent().getExtras().getLong("passwordAccountId");

            passwordAccountHelper.open();
            passwordAccount = passwordAccountHelper.fetchPasswordAccount(passwordAccountId);
            passwordAccountHelper.close();

            et_account_name.setText(passwordAccount.getAccountName());
            et_username.setText(passwordAccount.getUsername());
            et_password.setText(Crypt.decrypt(ENCRYPTION_KEY, passwordAccount.getPassword()));
            et_notes.setText(passwordAccount.getNotes());

            et_account_name.setEnabled(false);
            et_username.setEnabled(false);
            et_password.setEnabled(false);
            et_notes.setEnabled(false);
        }

        et_account_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                errorNotification("", false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                errorNotification("", false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                errorNotification("", false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //On click event of save button
        btn_save_account.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if(et_account_name.getText().toString().equals("")){
                    errorNotification("Account name should not be empty", true);
                    return;
                }

                if(et_username.getText().toString().equals("")){
                    errorNotification("Username should not be empty", true);
                    return;
                }

                if(et_password.getText().toString().equals("")){
                    errorNotification("Password should not be empty", true);
                    return;
                }

                try {
                    PasswordAccount passwordAccount = new PasswordAccount(
                            null,
                            et_account_name.getText().toString(),
                            et_username.getText().toString(),
                            Crypt.encrypt(ENCRYPTION_KEY, et_password.getText().toString()),
                            et_notes.getText().toString(),
                            0,
                            TimeStamp.getCurrentTimeStamp(),
                            TimeStamp.getCurrentTimeStamp()
                    );

                    passwordAccountHelper = new PasswordAccountHelper(getApplicationContext());
                    passwordAccountHelper.open();
                    if(passwordAccountHelper.insertPasswordAccount(passwordAccount) == 0){
                        Log.w(getClass().getSimpleName(),"Password Account is null");
                    }
                    passwordAccountHelper.close();

                    startIntent();
                } catch (Exception ex) {
                    Log.e("Save Clicked", ex.toString());
                }
            }
        });

        //On click event of delete button
        btn_delete_account.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                try {
                    PasswordAccount passwordAccountUpdated = new PasswordAccount(
                            passwordAccount.getAccountId(),
                            passwordAccount.getAccountName(),
                            passwordAccount.getUsername(),
                            passwordAccount.getPassword(),
                            passwordAccount.getNotes(),
                            1,
                            null,
                            null
                    );

                    passwordAccountHelper.open();
                    if (passwordAccountHelper.deletePasswordAccount(passwordAccountUpdated) == 0) {
                        Log.w(getClass().getSimpleName(), "Password Account is null");
                    }
                    passwordAccountHelper.close();

                    startIntent();
                }catch(Exception ex){
                    Log.i("Delete Clicked", ex.toString());
                }
            }
        });

        //On click event of edit button
        btn_edit_account.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                try {
                    et_account_name.setEnabled(true);
                    et_username.setEnabled(true);
                    et_password.setEnabled(true);
                    et_notes.setEnabled(true);

                    btn_save_account.setVisibility(View.GONE);
                    btn_edit_account.setVisibility(View.GONE);
                    btn_delete_account.setVisibility(View.GONE);

                    btn_update_account.setVisibility(View.VISIBLE);
                    btn_cancel.setVisibility(View.VISIBLE);
                }catch(Exception ex){
                    Log.i("Edit Clicked", ex.toString());
                }
            }
        });

        //On click event of update button
        btn_update_account.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                try {
                    PasswordAccount passwordAccountUpdated = new PasswordAccount(
                            passwordAccount.getAccountId(),
                            et_account_name.getText().toString(),
                            et_username.getText().toString(),
                            Crypt.encrypt(ENCRYPTION_KEY, et_password.getText().toString()),
                            et_notes.getText().toString(),
                            0,
                            null,
                            null
                    );

                    passwordAccountHelper.open();
                    if (passwordAccountHelper.updatePasswordAccount(passwordAccountUpdated) == 0) {
                        Log.w(getClass().getSimpleName(), "Password Account is null");
                    }
                    passwordAccountHelper.close();

                    startIntent();
                }catch(Exception ex){
                    Log.i("Update Clicked", ex.toString());
                }
            }
        });

        //On click event of cancel button
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                try {
                    startIntent();
                } catch (Exception ex) {
                    Log.e("Cancel Clicked", ex.toString());
                }
            }
        });

        //On change event of show password
        cb_show_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    et_password.setInputType(InputType.TYPE_CLASS_TEXT);
                }else{
                    et_password.setInputType(129);
                }
            }
        });

    }

    //Start activity through intent
    private void startIntent(){
        Intent pwdManagerIntent = new Intent(getApplicationContext(), PasswordKeeperActivity.class);
        startActivity(pwdManagerIntent);
        finish();
    }

    private void errorNotification(String errorText, Boolean showError){
        if(showError) {
            tv_error.setVisibility(View.VISIBLE);
        }else{
            tv_error.setVisibility(View.GONE);
        }
        tv_error.setText(errorText);
    }
}
