package com.example.studysmarter.dbLayer.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.studysmarter.dbLayer.DAO.CardDAO;
import com.example.studysmarter.dbLayer.DAO.DeckDAO;
import com.example.studysmarter.dbLayer.DAO.ProficiencyDAO;
import com.example.studysmarter.dbLayer.DateConverter;
import com.example.studysmarter.dbLayer.tables.Cards;
import com.example.studysmarter.dbLayer.tables.Decks;
import com.example.studysmarter.dbLayer.tables.Proficiency;

@Database(entities = {Decks.class, Cards.class, Proficiency.class}, version = 2,exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class CardsDatabase extends RoomDatabase {
    public abstract DeckDAO getDeckDAO();
    public abstract CardDAO getCardDAO();
    public abstract ProficiencyDAO getProficiencyDAO();
}
