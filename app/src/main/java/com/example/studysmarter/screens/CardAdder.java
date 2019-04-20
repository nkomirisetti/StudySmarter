package com.example.studysmarter.screens;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.studysmarter.R;
import com.example.studysmarter.dbLayer.DAL.DataAccessLayerHelper;

import java.util.ArrayList;
import java.util.List;

public class CardAdder extends AppCompatActivity {

    int currentDeck;
    int currentStartIndex;
    List<String> term, definition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_adder);

        term = new ArrayList<>();
        definition = new ArrayList<>();
    }

    public void addCard(View view) {
        EditText term = findViewById(R.id.term_entry);
        EditText def = findViewById(R.id.definition_entry);

        String newTerm = term.getText().toString();
        String newDef = def.getText().toString();

        if (newTerm.isEmpty()) {
            Snackbar.make(view, "The term is empty, you can't add that to the deck", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }

        if (newDef.isEmpty()) {
            Snackbar.make(view, "The definition is empty, you can't add that to the deck", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }

        this.term.add(newTerm);
        definition.add(newDef);


        if (definition.size() == 1) {
            Snackbar.make(view, "1 card made so far!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            Snackbar.make(view, definition.size() + " cards made so far!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        term.setText("");
        def.setText("");
    }

    @Override
    public void onBackPressed() {

    }

    public void saveCards() {
        DataAccessLayerHelper.insertCards(this, currentDeck, term, definition);
        term = new ArrayList<>();
        definition = new ArrayList<>();
    }
}
