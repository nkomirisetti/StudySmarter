package com.example.studysmarter.screens;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.studysmarter.MainActivity;
import com.example.studysmarter.R;
import com.example.studysmarter.dbLayer.DAL.DataAccessLayerHelper;

import java.util.ArrayList;
import java.util.List;

public class CardAdder extends AppCompatActivity {

    int currentDeck;
    List<String> term, definition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_adder);

        term = new ArrayList<>();
        definition = new ArrayList<>();

        currentDeck = getIntent().getIntExtra("DECK_ID", -1);
        if (currentDeck == -1) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int choice) {
                    switch (choice) {
                        case DialogInterface.BUTTON_NEGATIVE:
                            goHome();
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Some critical error happened, returning to the home screen")
                    .setNegativeButton("sad", dialogClickListener).show();
        }
    }

    public void goHome() {
        Intent newDeck = new Intent(this, MainActivity.class);
        startActivity(newDeck);
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
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int choice) {
                switch (choice) {
                    case DialogInterface.BUTTON_POSITIVE:
                        saveCards();
                        CardAdder.super.onBackPressed();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (term.size() > 0) {
            builder.setMessage("Is that all the cards you want to add for now?")
                    .setPositiveButton("yup", dialogClickListener)
                    .setNegativeButton("nope", dialogClickListener).show();
        } else {
            CardAdder.super.onBackPressed();
        }
    }

    public void saveCards() {
        DataAccessLayerHelper.insertCards(this, currentDeck, term, definition);
        term = new ArrayList<>();
        definition = new ArrayList<>();
    }
}