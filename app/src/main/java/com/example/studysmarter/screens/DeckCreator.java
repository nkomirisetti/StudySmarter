package com.example.studysmarter.screens;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.studysmarter.R;
import com.example.studysmarter.dbLayer.DAL.DataAccessLayerHelper;
import com.example.studysmarter.dbLayer.database.CardsDatabase;
import com.example.studysmarter.dbLayer.tables.Decks;

import java.util.Calendar;
import java.util.Date;

public class DeckCreator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_creator2);
    }

    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int choice) {
                switch (choice) {
                    case DialogInterface.BUTTON_POSITIVE:
                        DeckCreator.super.onBackPressed();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you wanna leave without making a deck?")
                .setPositiveButton("yup", dialogClickListener)
                .setNegativeButton("nope", dialogClickListener).show();
    }

    public void createDeck(View view) {
        EditText et = findViewById(R.id.deck_title_entry);
        DatePicker dp = findViewById(R.id.datePicker1);
        Calendar cal = Calendar.getInstance();
        cal.set(dp.getYear(),dp.getMonth(),dp.getDayOfMonth());
        Date newDate = cal.getTime();
        if (et.getText().toString().isEmpty()) {
            Snackbar.make(view, "The title is empty, you can't make a deck with an empty title", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        } else if (newDate.before(new Date())){
            Snackbar.make(view, "You can't set the due date to something in the past...", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }

        Decks newDeck = new Decks();
        newDeck.initialize(et.getText().toString());

        CardsDatabase cd = DataAccessLayerHelper.buildDatabaseConnection(this);

        // TODO: generate code for 3 midpoints and insert them into the proficiency table

        cd.getDeckDAO().insertDeck(newDeck);

        Intent openDD = new Intent(this, DeckDesigner.class);
        openDD.putExtra("DECK_ID", cd.getDeckDAO().getHighestDeckID());
        startActivity(openDD);
    }
}
