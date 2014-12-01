package com.example.sylve_000.loanshark;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.Parse;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

public class MyActivity extends Activity {

    private ParseQueryAdapter <Friends> postsQueryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Button addFriend = (Button) findViewById(R.id.add_friend);
        addFriend.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MyActivity.this, PostActivity.class);
                startActivity(intent);
            }
        });

        ParseQueryAdapter.QueryFactory<Friends> factory =
                new ParseQueryAdapter.QueryFactory<Friends>(){
                    public ParseQuery<Friends> create(){
                        ParseQuery<Friends> query = Friends.getQuery();
                        query.include("user");
                        query.orderByDescending("updated_at");
                        return query;
                    }
                };

        postsQueryAdapter = new ParseQueryAdapter<Friends>(this, factory){
            @Override
            public View getItemView(Friends post, View view, ViewGroup parent){
                if (view == null) {
                    view = View.inflate(getContext(), R.layout.loanshark_post_item, null);
                }
                //TextView contentView = (TextView) view.findViewById(R.id.content_view);
                TextView usernameView = (TextView) view.findViewById(R.id.username_view);
                usernameView.setText(post.getFirstName());
                return view;
            }
        };

        ListView postsListView = (ListView) findViewById(R.id.friends_list);
        postsListView.setAdapter(postsQueryAdapter);

        //ParseQueryAdapter <Friends> adapter = new ParseQueryAdapter<Friends>(this, Friends.class);
        //adapter.setTextKey("firstName");
        //ListView postsListView = (ListView) this.findViewById(R.id.friends_list);
        //postsListView.setAdapter(postsQueryAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
