package com.example.studysmarter.screens;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.studysmarter.R;
import com.example.studysmarter.dbLayer.DAL.DataAccessLayerHelper;
import com.example.studysmarter.dbLayer.database.CardsDatabase;
import com.example.studysmarter.dbLayer.tables.Proficiency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CalendarScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_screen);
        initializeListView();
    }

    void initializeListView() {
        ListView lv = findViewById(R.id.proficiency_list);
        String[] from = {"deck_name", "proficiency_deadline_name", "proficiency_deadline"};
        int[] to = {R.id.deck_name, R.id.proficiency_deadline_name, R.id.proficiency_deadline};

        List<HashMap<String, String>> mappingList = new ArrayList<>();

        CardsDatabase cd = DataAccessLayerHelper.buildDatabaseConnection(this);

        for (Proficiency p : DataAccessLayerHelper.getProficiencies(this)) {
            HashMap<String, String> innerMap = new HashMap<>();

            innerMap.put("deck_name", DataAccessLayerHelper.getDeckName(cd, p.deckID));
            innerMap.put("proficiency_deadline_name", p.stage + "% Proficient");
            innerMap.put("proficiency_deadline", "Finish by " + p.deadline.toString());

            mappingList.add(innerMap);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), mappingList, R.layout.proficiency_item, from, to);
        SimpleAdapter.ViewBinder binder = new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                return false;
            }
        };

        simpleAdapter.setViewBinder(binder);
        lv.setAdapter(simpleAdapter);
    }
}
