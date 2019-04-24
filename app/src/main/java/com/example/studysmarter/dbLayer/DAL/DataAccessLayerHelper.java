package com.example.studysmarter.dbLayer.DAL;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.studysmarter.dbLayer.dataBuilder.BuildCards;
import com.example.studysmarter.dbLayer.database.CardsDatabase;
import com.example.studysmarter.dbLayer.tables.Cards;
import com.example.studysmarter.dbLayer.tables.Decks;
import com.example.studysmarter.dbLayer.tables.Proficiency;

import java.util.Date;
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

    public static List<Cards> getCards(CardsDatabase cd, int deckID) {
        return cd.getCardDAO().getFullCards(deckID);
    }

    public static void insertProficiencies(CardsDatabase cd, int deckID, Date[] dates) {
        int[] profValues = {25, 50, 75};
        Proficiency[] profs = new Proficiency[3];
        for (int i = 0; i < 3; i++) {
            Proficiency p = new Proficiency();
            p.deadline = dates[i];
            p.deckID = deckID;
            p.stage = profValues[i];
            p.stageID = i + 1;
            profs[i] = p;
        }
        cd.getProficiencyDAO().insertProfs(profs);
    }
}

