package com.example.sylve_000.loanshark;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by sylve_000 on 2014-11-30.
 */
public class PostActivity extends Activity {

    private Button postButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_post);

        postButton = (Button) findViewById(R.id.post_button);
        postButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                post();
                //finish();
            }
        });
    }

    private void post(){
        EditText firstEdit = (EditText) findViewById(R.id.post_firstname);
        String firstName = firstEdit.getText().toString().trim();

        EditText lastEdit = (EditText) findViewById(R.id.post_lastname);
        String lastName = lastEdit.getText().toString().trim();

        final ProgressDialog dialog = new ProgressDialog(PostActivity.this);
        dialog.setMessage(getString(R.string.progress_post));
        dialog.show();

        Friends post = new Friends();

        post.setFirstName(firstName);
        post.setLastName(lastName);
        post.setUser(ParseUser.getCurrentUser());
        post.setAmount(0);
        ParseACL acl = new ParseACL(ParseUser.getCurrentUser());
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
