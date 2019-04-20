package com.example.studysmarter.dbLayer.DAL;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.studysmarter.dbLayer.database.CardsDatabase;
import com.example.studysmarter.dbLayer.tables.Decks;

import java.util.List;


public class DataAccessLayerHelper {
    public static void insertCards(CardsDatabase cd, int deckID, List<String> terms, List<String> definitions) {
        // find max index of deck
        // create card objects with that starting index
        // insert cards into deck
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
