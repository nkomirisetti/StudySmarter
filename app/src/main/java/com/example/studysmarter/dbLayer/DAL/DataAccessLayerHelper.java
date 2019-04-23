package com.example.studysmarter.dbLayer.DAL;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.studysmarter.dbLayer.dataBuilder.BuildCards;
import com.example.studysmarter.dbLayer.database.CardsDatabase;
import com.example.studysmarter.dbLayer.tables.Cards;
import com.example.studysmarter.dbLayer.tables.Decks;

import java.util.List;

public class DataAccessLayerHelper {
    public static CardsDatabase buildDatabaseConnection(Context appContext) {
        return Room.databaseBuilder(appContext, CardsDatabase.class, "db-cards")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    static void insertCards(CardsDatabase cd, int deckID, List<String> terms, List<String> definitions) {
        int startingIndex = cd.getCardDAO().getHighestCardID(deckID);
        List<Cards> cardsList = BuildCards.createCards(deckID, terms, definitions, startingIndex);
        for (Cards card : cardsList) {
            cd.getCardDAO().insertCard(card);
        }
    }

    public static void insertCards(Context appContext, int deckID, List<String> terms, List<String> definitions) {
        insertCards(buildDatabaseConnection(appContext), deckID, terms, definitions);
    }

    public static List<Decks> getAllDecks(CardsDatabase cd) {
        return cd.getDeckDAO().getDecksByLastStudied();
    }


    public static List<Cards> getCards(Context appContext, int deckID) {
        CardsDatabase cd = buildDatabaseConnection(appContext);
        return getCards(cd, deckID);
    }

    static List<Cards> getCards(CardsDatabase cd, int deckID) {
        return cd.getCardDAO().getFullCards(deckID);
    }
}

