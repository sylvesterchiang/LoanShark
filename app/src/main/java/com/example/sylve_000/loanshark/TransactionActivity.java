package com.example.sylve_000.loanshark;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;

/**
 * Created by sylve_000 on 2014-12-11.
 */
public class TransactionActivity extends Activity{

    private String friendID;
    private String currentUserId;
    private ListView transactionList;
    private EditText amountField;
    private ParseQueryAdapter <Transaction> postsQueryAdapter;
    public double sum = 0;

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

        ParseQueryAdapter.QueryFactory<Transaction> transactionQueryFactory =
                new ParseQueryAdapter.QueryFactory<Transaction>(){
                    public ParseQuery<Transaction> create(){
                        ParseQuery<Transaction> query = Transaction.getQuery();
                        query.include("user");
                        query.whereEqualTo("friend", friendID);
                        query.orderByDescending("created_at");
                        return query;
                    }
                };

        postsQueryAdapter = new ParseQueryAdapter<Transaction>(this, transactionQueryFactory){
            @Override
            public View getItemView(Transaction post, View view, ViewGroup parent){
                if (view == null) {
                    if (post.getType() == false){
                        view = View.inflate(getContext(), R.layout.message_left, null);
                        sum = sum + post.getAmount();
                    }
                    else{
                        view = View.inflate(getContext(), R.layout.message_right, null);
                        sum -= post.getAmount();
                    }
                }
                //TextView usernameView = (TextView) view.findViewById(R.id.username_view);
                //usernameView.setText(post.getFirstName() + " " + post.getLastName());

                TextView dateView = (TextView) view.findViewById(R.id.date);
                TextView amountView = (TextView) view.findViewById(R.id.amount);

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                dateView.setText(df.format(post.getCreatedAt()));
                sum += post.getAmount();
                amountView.setText(post.getAmount()+"");

                return view;
            }
        };

        ListView transactionList = (ListView) findViewById(R.id.listTransactions);
        transactionList.setAdapter(postsQueryAdapter);

        TextView sumText = (TextView) findViewById(R.id.sum);
        sumText.setText("$ " + sum);
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
