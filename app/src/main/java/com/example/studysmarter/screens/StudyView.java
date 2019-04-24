package com.example.studysmarter.screens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.studysmarter.R;
import com.example.studysmarter.dbLayer.DAL.DataAccessLayerHelper;
import com.example.studysmarter.dbLayer.tables.Cards;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import org.w3c.dom.Text;

import java.util.List;

public class StudyView extends AppCompatActivity {

    int deckID;
    List<Cards> cardsList;
    int currentCard;
    int iKnowCount, iDontKnowCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_view);

        EasyFlipView easyFlipView = findViewById(R.id.easyFlipView);
        easyFlipView.setFlipDuration(1000);
        easyFlipView.setFlipEnabled(true);

        deckID = getIntent().getIntExtra("DECK_ID", -1);

        cardsList = DataAccessLayerHelper.getCards(this, deckID);
        currentCard = 0;

        iKnowCount = 0;
        iDontKnowCount = 0;

        Button iKnow = findViewById(R.id.btn_i_know);
        iKnow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                iKnowClicked();
            }
        });

        Button iDontKnow = findViewById(R.id.btn_i_dont_know);
        iDontKnow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                iDontKnowClicked();
            }
        });

        refresh();
    }

    void refresh(){
        if (currentCard == cardsList.size()){
            // TODO summary screen window call
            // TODO save progress into database
            return;
        }

        TextView indexLabel = findViewById(R.id.card_view_front_index);
        TextView termLabel = findViewById(R.id.card_view_front_name);

        TextView defLabel = findViewById(R.id.card_view_back_defintion);
        TextView indexLabelBack = findViewById(R.id.card_view_back_index);

        indexLabel.setText("Card " + (currentCard + 1));
        indexLabelBack.setText("Card " + (currentCard + 1));

        termLabel.setText(cardsList.get(currentCard).term);
        defLabel.setText(cardsList.get(currentCard).definition);
    }

    void iKnowClicked(){
        iKnowCount++;
        currentCard++;
        refresh();
    }

    void iDontKnowClicked() {
        iDontKnowCount++;
        currentCard++;
        refresh();
    }
}