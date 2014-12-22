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
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by sylve_000 on 2014-12-11.
 */
public class TransactionActivity extends Activity{

    private String friendID;
    private String currentUserId;
    private ListView transactionList;
    private EditText amountField;
    private ParseQueryAdapter <Transaction> postsQueryAdapter;
    ParseQuery<Friends> friendQuery;
    public double sum;
    private TextView sumText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        Intent intent = getIntent();
        friendID = intent.getStringExtra("FRIEND_ID");

        currentUserId = ParseUser.getCurrentUser().getObjectId();

        transactionList = (ListView) findViewById(R.id.listTransactions);

        amountField = (EditText) findViewById(R.id.loanAmount);

        sumText = (TextView) findViewById(R.id.sum);

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
                    }
                    else{
                        view = View.inflate(getContext(), R.layout.message_right, null);
                    }
                }
                if (post.getType() == false){
                    sum += post.getAmount();
                    Log.w(post.getAmount()+"", "TESTING");
                }
                else{
                    sum -= post.getAmount();
                    Log.w(post.getAmount()+"", "TESTING");
                }
                //TextView usernameView = (TextView) view.findViewById(R.id.username_view);
                //usernameView.setText(post.getFirstName() + " " + post.getLastName());

                TextView dateView = (TextView) view.findViewById(R.id.date);
                TextView amountView = (TextView) view.findViewById(R.id.amount);

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                dateView.setText(df.format(post.getCreatedAt()));
                amountView.setText(post.getAmount()+"");

                return view;
            }
        };

        ListView transactionList = (ListView) findViewById(R.id.listTransactions);
        transactionList.setAdapter(postsQueryAdapter);
        updateSum();
    }

    private void updateSum(){
        friendQuery = Friends.getQuery();
        friendQuery.whereEqualTo("objectId", friendID);
        friendQuery.findInBackground(new FindCallback<Friends>() {
            public void done(List<Friends> objects, ParseException e) {
                if (e == null){
                    Log.w("" + objects.size(), "LIST SIZE");
                    sum = objects.get(0).getAmount();
                    sumText.setText("$" + sum);
                }else{
                    //something
                }
            }
        });
    }

    private void changeSum(boolean type, double value){
        ParseObject point = ParseObject.createWithoutData("Friends", friendID);
        if (type == false){
            point.put("amount", sum + value);
        }else{
            point.put("amount", sum - value);
        }

        point.saveInBackground(new SaveCallback(){
            public void done(ParseException e){
                if (e == null){
                    //saved succesfully
                }else{
                    //error
                }
            }
        });
    }

    private void issueLoan(){
        final ProgressDialog dialog = new ProgressDialog(TransactionActivity.this);
        dialog.setMessage(getString(R.string.progress_post));
        dialog.show();

        Transaction post = new Transaction();

        final double amount = Double.parseDouble(amountField.getText().toString());

        changeSum(false, amount);

        post.setAmount(amount);
        post.setType(false);
        post.setFriend(friendID);
        post.setUser(ParseUser.getCurrentUser());

        ParseACL acl = new ParseACL(ParseUser.getCurrentUser());
        post.setACL(acl);

        post.saveInBackground(new SaveCallback(){
            @Override
            public void done(ParseException e){
                dialog.dismiss();
                //finish();
            }
        });
    }

    private void receiveLoan(){
        final ProgressDialog dialog = new ProgressDialog(TransactionActivity.this);
        dialog.setMessage(getString(R.string.progress_post));
        dialog.show();

        Transaction post = new Transaction();

        final double amount = Double.parseDouble(amountField.getText().toString());

        changeSum(true, amount);

        post.setAmount(amount);
        post.setType(true);
        post.setFriend(friendID);
        post.setUser(ParseUser.getCurrentUser());

        ParseACL acl = new ParseACL(ParseUser.getCurrentUser());
        post.setACL(acl);

        post.saveInBackground(new SaveCallback(){
            @Override
            public void done(ParseException e){
                dialog.dismiss();
                //finish();
            }
        });
    }
}
