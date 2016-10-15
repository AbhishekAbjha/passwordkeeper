package com.coronate.passwordkeeper.passwordaccount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.coronate.passwordkeeper.R;

import java.util.ArrayList;

public class PasswordAccountAdapter extends ArrayAdapter<PasswordAccount>{

    public PasswordAccountAdapter(Context context,ArrayList<PasswordAccount> passwordAccounts){
        super(context,0, passwordAccounts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        PasswordAccount passwordAccount = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.password_account_list, parent, false);
        }

        // Lookup view for data population
        TextView tv_password_account_name = (TextView) convertView.findViewById(R.id.password_account_name);
        TextView tv_password_account_id = (TextView) convertView.findViewById(R.id.password_account_id);

        // Populate the data into the template view using the data object
        tv_password_account_name.setText(passwordAccount.getAccountName());
        tv_password_account_id.setText(String.valueOf(passwordAccount.getAccountId()));

        // Return the completed view to render on screen
        return convertView;
    }
}
