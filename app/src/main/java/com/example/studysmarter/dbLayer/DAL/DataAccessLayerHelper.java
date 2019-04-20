package com.example.studysmarter.dbLayer.DAL;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.studysmarter.dbLayer.dataBuilder.BuildCards;
import com.example.studysmarter.dbLayer.database.CardsDatabase;
import com.example.studysmarter.dbLayer.tables.Cards;
import com.example.studysmarter.dbLayer.tables.Decks;

import java.util.List;


public class DataAccessLayerHelper {
    public static void insertCards(CardsDatabase cd, int deckID, List<String> terms, List<String> definitions) {
        int startingIndex = cd.getCardDAO().getHighestCardID(deckID);
        List<Cards> cardsList = BuildCards.createCards(deckID, terms, definitions, startingIndex);
        for (Cards card: cardsList){
            cd.getCardDAO().insertCard(card);
        }
    }

    public static List<Decks> getAllDecks(CardsDatabase cd) {
        return cd.getDeckDAO().getDecksByLastStudied();
    }

    public static CardsDatabase buildDatabaseConnection(Context appContext) {
        return Room.databaseBuilder(appContext, CardsDatabase.class, "db-cards")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

    }
}
