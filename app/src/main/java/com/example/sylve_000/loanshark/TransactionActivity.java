package com.example.sylve_000.loanshark;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by sylve_000 on 2014-12-11.
 */
public class TransactionActivity extends Activity{

    private String friendID;
    private String currentUserId;
    private ListView transactionList;
    private EditText amountField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        Intent intent = getIntent();
        friendID = intent.getStringExtra("FRIEND_ID");

        currentUserId = ParseUser.getCurrentUser().getObjectId();

        transactionList = (ListView) findViewById(R.id.listTransactions);

        amountField = (EditText) findViewById(R.id.loanAmount);

        findViewById(R.id.issueLoanButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                issueLoan();
            }
        });

        findViewById(R.id.receiveLoanButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                receiveLoan();
            }
        });
    }

    private void issueLoan(){
        final ProgressDialog dialog = new ProgressDialog(TransactionActivity.this);
        dialog.setMessage(getString(R.string.progress_post));
        dialog.show();

        Transaction post = new Transaction();

        double amount = Double.parseDouble(amountField.getText().toString());

        post.setAmount(amount);
        post.setType(false);
        post.setFriend(friendID);
        post.setUser(ParseUser.getCurrentUser());

        ParseACL acl = new ParseACL();

        acl.setPublicReadAccess(true);
        post.setACL(acl);

        post.saveInBackground(new SaveCallback(){
            @Override
            public void done(ParseException e){
                dialog.dismiss();
                finish();
            }
        });
    }

    private void receiveLoan(){
        final ProgressDialog dialog = new ProgressDialog(TransactionActivity.this);
        dialog.setMessage(getString(R.string.progress_post));
        dialog.show();

        Transaction post = new Transaction();

        double amount = Double.parseDouble(amountField.getText().toString());

        post.setAmount(amount);
        post.setType(true);
        post.setFriend(friendID);
        post.setUser(ParseUser.getCurrentUser());

        ParseACL acl = new ParseACL();

        acl.setPublicReadAccess(true);
        post.setACL(acl);

        post.saveInBackground(new SaveCallback(){
            @Override
            public void done(ParseException e){
                dialog.dismiss();
                finish();
            }
        });
    }
}
