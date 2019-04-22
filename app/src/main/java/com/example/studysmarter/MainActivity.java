package com.example.studysmarter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.studysmarter.dbLayer.DAL.DataAccessLayerHelper;
import com.example.studysmarter.dbLayer.database.CardsDatabase;
import com.example.studysmarter.dbLayer.tables.Decks;
import com.example.studysmarter.screens.DeckCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    CardsDatabase cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cd = DataAccessLayerHelper.buildDatabaseConnection(this);
        initializeToolbar();
        initializeListView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_deck:
                openDeckCreator();
                return true;
            case R.id.cal_view:
                Log.i("wassup", "cal pushed");
                return true;
            case R.id.delete_deck:
                Log.i("wassup", "del pushed");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openDeckCreator() {
        Intent newDeck = new Intent(this, DeckCreator.class);
        startActivity(newDeck);
    }

    private void initializeToolbar() {
        Toolbar homeToolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(homeToolbar);
    }

    private void initializeListView() {
        ListView lv = findViewById(R.id.decks_list);
        String[] from = {"deck_title", "last_accessed_date", "percent"};
        int[] to = {R.id.deck_title, R.id.last_accessed_date, R.id.percent};

        List<HashMap<String, String>> mappingList = new ArrayList<>();


        for (Decks deck : DataAccessLayerHelper.getAllDecks(cd)) {
            HashMap<String, String> innerMap = new HashMap<>();
            innerMap.put("deck_title", deck.name);
            innerMap.put("last_accessed_date", deck.lastOpened.toString());
            innerMap.put("percent", Double.toString(deck.proficiency));
            mappingList.add(innerMap);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), mappingList, R.layout.deck_item, from, to);
        SimpleAdapter.ViewBinder binder = new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                return false;
            }
        };

        simpleAdapter.setViewBinder(binder);
        lv.setAdapter(simpleAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeListView();
    }
}