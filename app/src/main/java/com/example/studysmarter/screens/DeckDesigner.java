package com.example.studysmarter.screens;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.studysmarter.R;
import com.example.studysmarter.dbLayer.DAL.DataAccessLayerHelper;
import com.example.studysmarter.dbLayer.DAO.CardDAO;
import com.example.studysmarter.dbLayer.database.CardsDatabase;
import com.example.studysmarter.dbLayer.tables.Cards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeckDesigner extends AppCompatActivity {

    int currentDeckId;
    CardsDatabase cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_creator);
        Toolbar toolbar = findViewById(R.id.creator_toolbar);
        setSupportActionBar(toolbar);

        cd = DataAccessLayerHelper.buildDatabaseConnection(this);
        initializeCardView();
        initializeFAB();
    }

    private void initializeFAB() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  createCards();
            }
        });
    }

    private void createCards() {
        Intent newDeck = new Intent(this, CardAdder.class);
        newDeck.putExtra("DECK_ID", currentDeckId);
        startActivity(newDeck);

    }

    private void initializeCardView() {
        currentDeckId = getIntent().getIntExtra("DECK_ID", -1);
        if (currentDeckId != -1) {
            populateCards();
        } else {
            currentDeckId = cd.getDeckDAO().getHighestDeckID() + 1;
        }
    }

    private void populateCards() {
        CardsDatabase cd = Room.databaseBuilder(this, CardsDatabase.class, "db-cards")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        CardDAO cardDAO = cd.getCardDAO();

        ListView lv = findViewById(R.id.cards_list);
        String[] from = {"card_front", "card_back"};
        int[] to = {R.id.deck_title, R.id.last_accessed_date};

        List<HashMap<String, String>> mappingList = new ArrayList<>();


        for (Cards card : cardDAO.getFullCards(currentDeckId)) {
            HashMap<String, String> innerMap = new HashMap<>();
            innerMap.put("card_front", card.term);
            innerMap.put("card_back", card.definition);
            mappingList.add(innerMap);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), mappingList, R.layout.card_item, from, to);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.deck_creator_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_deck:
                // SAVE LOGIC HERE
                return true;
            case R.id.remove_card:
                // REMOVE CARD LOGIC HERE
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
