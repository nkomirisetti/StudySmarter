package com.example.studysmarter.dbLayer.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.studysmarter.dbLayer.tables.Decks;

import java.util.List;

@Dao
public interface DeckDAO {
    @Insert
    void insertDecks(Decks... decks);

    @Insert
    void insertDeck(Decks deck);

    @Update
    void updateDecks(Decks... decks);

    @Update
    void updateDeck(Decks deck);

    @Delete
    void deleteDecks(Decks... decks);

    @Delete
    void deleteDeck(Decks deck);

    @Query("SELECT * FROM decks WHERE deckID == :id")
    List<Decks> findDeckWithID(int id);

    @Query("SELECT * FROM decks ORDER BY lastOpened")
    List<Decks> getDecksByLastStudied();

    @Query("SELECT d.proficiency from decks d WHERE d.deckID == :deckID")
    double getSpecificProficiency(int deckID);

    @Query("SELECT * FROM decks WHERE isFavorite == 'true'")
    List<Decks> getFavoritedDecks();

    @Query("SELECT MAX(deckID) FROM decks")
    int getHighestDeckID();

    @Query("DELETE FROM decks")
    void deleteAllDecks();
}
