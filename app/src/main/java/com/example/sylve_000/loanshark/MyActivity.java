package com.example.sylve_000.loanshark;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;

import com.parse.ParseObject;
import com.parse.Parse;

public class MyActivity extends Activity {

    private EditText mTaskInput;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Parse.initialize(this, "ubmj6oAAyAAD7ARhLkuaTiOkvEpbqjWhi13NEIia", "R7xHxk1EAS24kVUiGuxzJDHTASRI6MXyWBGl7Wnk");
        ParseObject.registerSubclass(Task.class);

        mTaskInput = (EditText) findViewById(R.id.task_input);
        mListView = (ListView) findViewById(R.id.task_list);
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

    public void createTask(View v) {
        if (mTaskInput.getText().length() > 0){
            Task t = new Task();
            t.setDescription(mTaskInput.getText().toString());
            t.setCompleted(false);
            //t.saveEventually();
            mTaskInput.setText("");
        }
    }
}
