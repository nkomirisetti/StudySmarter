package com.example.studysmarter.screens;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.studysmarter.R;
import com.example.studysmarter.dbLayer.DAL.DataAccessLayerHelper;
import com.example.studysmarter.dbLayer.database.CardsDatabase;
import com.example.studysmarter.dbLayer.tables.Cards;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.List;

public class StudyView extends AppCompatActivity {

    int deckID;
    List<Cards> cardsList;
    int currentCard;
    int iKnowCount, iDontKnowCount;

    long startTime, stopTime;

    CardsDatabase cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_view);

        EasyFlipView easyFlipView = findViewById(R.id.easyFlipView);
        easyFlipView.setFlipDuration(1000);
        easyFlipView.setFlipEnabled(true);

        deckID = getIntent().getIntExtra("DECK_ID", -1);

        cd = DataAccessLayerHelper.buildDatabaseConnection(this);

        cardsList = DataAccessLayerHelper.getCards(cd, deckID);
        currentCard = 0;

        iKnowCount = 0;
        iDontKnowCount = 0;

        Button iKnow = findViewById(R.id.btn_i_know);
        iKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iKnowClicked();
            }
        });

        Button iDontKnow = findViewById(R.id.btn_i_dont_know);
        iDontKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iDontKnowClicked();
            }
        });
        startTime = System.nanoTime();

        TextView indexLabel = findViewById(R.id.card_view_front_index);
        TextView termLabel = findViewById(R.id.card_view_front_name);

        termLabel.setText(cardsList.get(currentCard).term);
        indexLabel.setText("Card " + (currentCard + 1));


        TextView defLabel = findViewById(R.id.card_view_back_defintion);
        TextView indexLabelBack = findViewById(R.id.card_view_back_index);

        indexLabelBack.setText("Card " + (currentCard + 1));
        defLabel.setText(cardsList.get(currentCard).definition);
    }

    void refresh() {
        if (currentCard == cardsList.size()) {
            stopTime = System.nanoTime();

            long secondsPerCard = (stopTime - startTime) / (cardsList.size() * 1000000000);
            Log.i("You took ", secondsPerCard + " and you had " + iKnowCount + " cards you knew and " + iDontKnowCount + " cards you didn't know");
            double rawProficiency = (double)iKnowCount / (iKnowCount + iDontKnowCount);
            double calcProficiency;

            // TODO summary screen window call
            // TODO save progress into database
            return;
        }


        EasyFlipView fv = findViewById(R.id.easyFlipView);
        fv.setOnFlipListener(new EasyFlipView.OnFlipAnimationListener() {
            @Override
            public void onViewFlipCompleted(EasyFlipView easyFlipView, EasyFlipView.FlipState newCurrentSide) {
                updateLabels();
            }
        });

        fv.flipTheView();

        TextView indexLabel = findViewById(R.id.card_view_front_index);
        TextView termLabel = findViewById(R.id.card_view_front_name);
        termLabel.setText(cardsList.get(currentCard).term);
        indexLabel.setText("Card " + (currentCard + 1));

    }

    void iKnowClicked() {
        iKnowCount++;
        currentCard++;
        refresh();
    }

    void iDontKnowClicked() {
        iDontKnowCount++;
        currentCard++;
        refresh();
    }

    void updateLabels() {

        TextView defLabel = findViewById(R.id.card_view_back_defintion);
        TextView indexLabelBack = findViewById(R.id.card_view_back_index);

        indexLabelBack.setText("Card " + (currentCard + 1));
        defLabel.setText(cardsList.get(currentCard).definition);

        EasyFlipView fv = findViewById(R.id.easyFlipView);
        fv.setOnFlipListener(null);
    }
}