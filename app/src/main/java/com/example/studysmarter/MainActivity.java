package com.example.studysmarter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.studysmarter.dbLayer.DAL.DataAccessLayerHelper;
import com.example.studysmarter.dbLayer.database.CardsDatabase;
import com.example.studysmarter.dbLayer.tables.Decks;
import com.example.studysmarter.screens.CalendarScreen;
import com.example.studysmarter.screens.DeckCreator;
import com.example.studysmarter.screens.StudyView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    CardsDatabase cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cd = DataAccessLayerHelper.buildDatabaseConnection(this);

//        cd.getProficiencyDAO().deleteAll();
//        cd.getCardDAO().deleteAll();
//        cd.getDeckDAO().deleteAllDecks();

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
                openCalendarView();
                return true;
            case R.id.delete_deck:
                // TODO add delete deck view
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openCalendarView() {
        Intent newStudy = new Intent(this, CalendarScreen.class);
        startActivity(newStudy);
    }

    private void openStudyView(int deckID) {
        Intent newStudy = new Intent(this, StudyView.class);
        newStudy.putExtra("DECK_ID", deckID);
        startActivity(newStudy);
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

        DecimalFormat df2 = new DecimalFormat("#.##");

        List<HashMap<String, String>> mappingList = new ArrayList<>();

        for (Decks deck : DataAccessLayerHelper.getAllDecks(cd)) {
            HashMap<String, String> innerMap = new HashMap<>();
            innerMap.put("deck_title", deck.name);
            innerMap.put("last_accessed_date", deck.lastOpened.toString());
            innerMap.put("percent", df2.format(deck.proficiency));
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

        AdapterView.OnItemClickListener messageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                openStudyView(getDeckID(position));
            }
        };

        lv.setOnItemClickListener(messageClickedHandler);
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeListView();
    }

    int getDeckID(int position) {
        return (DataAccessLayerHelper.getAllDecks(cd).get(position)).deckID;
    }
}