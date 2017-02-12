package io.github.imsmobile.fahrplan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import io.github.imsmobile.fahrplan.adapter.StationAdapter;

public class MainActivity extends AppCompatActivity {
    public final static String FROM_MESSAGE = "io.github.imsmoble.fahrplan.from";
    public final static String TO_MESSAGE = "io.github.imsmoble.fahrplan.to";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        registerSearchButton();

        AutoCompleteTextView inputFrom = (AutoCompleteTextView) findViewById(R.id.input_from);
        inputFrom.setAdapter(new StationAdapter(this, R.layout.autocomplete_item));

        AutoCompleteTextView inputTo= (AutoCompleteTextView) findViewById(R.id.input_to);
        inputTo.setAdapter(new StationAdapter(this, R.layout.autocomplete_item));

    }

    private void registerSearchButton() {
        Button buttonOne = (Button) findViewById(R.id.btn_search);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity();
            }
        });
    }

    private void startActivity() {
        Intent intent = new Intent(this, SearchResultActivity.class);
        EditText fromText = (EditText) findViewById(R.id.input_from);
        String from = fromText.getText().toString();

        EditText toText = (EditText) findViewById(R.id.input_to);
        String to = toText.getText().toString();
        intent.putExtra(FROM_MESSAGE, from);
        intent.putExtra(TO_MESSAGE, to);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startSettingActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startSettingActivity() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
}
