package io.github.imsmobile.fahrplan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ch.schoeb.opendatatransport.model.Connection;
import io.github.imsmobile.fahrplan.adapter.ConnectionAdapter;
import io.github.imsmobile.fahrplan.model.FavoriteModel;
import io.github.imsmobile.fahrplan.task.ConnectionSearchTask;
import io.github.imsmobile.fahrplan.ui.ProgressDialogUI;

public class SearchResultActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private String from;
    private String to;
    private FavoriteModel favorites;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.search_result_title));
        favorites = new FavoriteModel(this);

        Intent intent = getIntent();
        from = intent.getStringExtra(MainActivity.FROM_MESSAGE);
        to = intent.getStringExtra(MainActivity.TO_MESSAGE);
        String arrivalTime = intent.getStringExtra(MainActivity.IS_ARRIVAL_TIME_MESSAGE);
        String dateTime = intent.getStringExtra(MainActivity.DATETIME_MESSAGE);
        startSearch(from, to, arrivalTime, dateTime);
    }

    private void startSearch(String from, String to, String arrivalTime, String dateTime) {
        TextView fromView = (TextView) this.findViewById(R.id.result_from);
        fromView.setText(from);
        TextView toView = (TextView) this.findViewById(R.id.result_to);
        toView.setText(to);
        new ConnectionSearchTask(this).execute(from, to, arrivalTime, dateTime);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(favorites.contains(from, to)) {
            inflater.inflate(R.menu.favorite_on, menu);
        } else {
            inflater.inflate(R.menu.favorite_off, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite_add:
                favorites.add(from, to);
                return true;
            case R.id.action_favorite_remove:
                favorites.remove(from, to);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setResult(List<Connection> connections) {
        ListView listView = (ListView)findViewById(R.id.result_list);

        ConnectionAdapter adapter = new ConnectionAdapter(this, connections);
        listView.setAdapter(adapter);
    }

    public void startProgressDialog() {
        dialog = new ProgressDialogUI(this);
        dialog.show();
    }

    public void stopProcessDialog() {
        dialog.dismiss();
    }
}
