package com.coronate.passwordkeeper;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.coronate.passwordkeeper.dao.PasswordKeeperDBHelper;
import com.coronate.passwordkeeper.dao.userkey.IUserKeyHelper;
import com.coronate.passwordkeeper.dao.userkey.UserKeyHelper;
import com.coronate.passwordkeeper.util.Crypt;

public class UserKeyActivity extends AppCompatActivity {

    private IUserKeyHelper userKeyHelper;
    private EditText et_user_key;
    private EditText et_confirm_user_key;
    private CheckBox cb_show_user_key;
    private TextView tv_error;
    private Button btn_login;
    private boolean isFirstRun = false;
    private final String ENCRYPTION_KEY_LOGIN = "sdhfhjijhdko7837";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting main page
        setContentView(R.layout.user_key);

        //Get view elements
        btn_login = (Button) findViewById(R.id.btn_login);
        et_user_key = (EditText) findViewById(R.id.et_user_key);
        et_confirm_user_key = (EditText) findViewById(R.id.et_confirm_user_key);
        cb_show_user_key = (CheckBox) findViewById(R.id.cb_show_user_key);
        tv_error = (TextView) findViewById(R.id.tv_error);

        //Initialize helper instance and keep it's single instance across application.
        PasswordKeeperDBHelper.getInstance(getApplicationContext());

        //Create instance of user key helper
        userKeyHelper = new UserKeyHelper(getApplicationContext());
        userKeyHelper.open();
        if(userKeyHelper.fetchUserKey() == null){
            isFirstRun = true;
        }else{
            et_confirm_user_key.setVisibility(View.GONE);
            isFirstRun = false;

        }
        userKeyHelper.close();

        //On click event of login button
        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                try {
                    if(et_user_key.getText().length() != 8){
                        errorNotification("User key should be of 8 characters only.", true);
                        return;
                    }

                    String userKey = et_user_key.getText().toString();
                    String confirmUserKey = et_confirm_user_key.getText().toString();

                    if(!userKey.equals(confirmUserKey) && isFirstRun){
                        errorNotification("Please confirm user key.", true);
                        return;
                    }

                    if(isFirstRun) { //If first run
                        userKeyHelper.open();
                        String encryptedUserKey = Crypt.encrypt(ENCRYPTION_KEY_LOGIN, et_user_key.getText().toString());
                        userKeyHelper.insertUserKey(encryptedUserKey);
                        userKeyHelper.close();

                        Intent userKeyIntent = new Intent(getApplicationContext(), UserKeyActivity.class);
                        startActivity(userKeyIntent);
                        finish();
                    }else { //If not first run
                        userKeyHelper.open();
                        String userKeyFromDatabase = Crypt.decrypt(ENCRYPTION_KEY_LOGIN, userKeyHelper.fetchUserKey());
                        userKeyHelper.close();
                        if(userKeyFromDatabase.equals(userKey)){
                            Intent pwdKeeperIntent = new Intent(getApplicationContext(), PasswordKeeperActivity.class);
                            startActivity(pwdKeeperIntent);
                            finish();
                        }else{
                            errorNotification("Incorrect user key.", true);
                        }
                    }
                } catch (Exception ex) {
                    Log.e(getClass().getSimpleName(), ex.toString());
                }
            }
        });

        et_user_key.addTextChangedListener(new TextWatcher() {
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

        et_confirm_user_key.addTextChangedListener(new TextWatcher() {
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

        //On change event of show password
        cb_show_user_key.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    et_user_key.setInputType(InputType.TYPE_CLASS_TEXT);
                    et_confirm_user_key.setInputType(InputType.TYPE_CLASS_TEXT);
                }else{
                    et_user_key.setInputType(129);
                    et_confirm_user_key.setInputType(129);
                }
            }
        });
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
