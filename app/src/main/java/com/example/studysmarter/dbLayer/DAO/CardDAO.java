package com.example.studysmarter.dbLayer.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.studysmarter.dbLayer.tables.Cards;
import com.example.studysmarter.dbLayer.tables.Decks;
import com.example.studysmarter.dbLayer.tuples.CardTuple;

import java.util.List;

@Dao
public interface CardDAO {
    @Insert
    void insertCards(Cards... cards);

    @Insert
    void insertCard(Cards card);

    @Update
    void updateCards(Decks... cards);

    @Update
    void updateCards(Cards card);

    @Delete
    void deleteCards(Cards... cards);

    @Delete
    void deleteDeck(Cards card);

    @Query("SELECT * FROM cards WHERE deckID == :deckID ORDER BY `index` ASC")
    List<Cards> getFullCards(int deckID);

    @Query("SELECT * FROM cards WHERE deckID == :deckID ORDER BY `index` ASC")
    List<CardTuple> getFrontBack(int deckID);

    @Query("SELECT * FROM cards WHERE deckID == :deckID AND cardID == :cardID")
    Cards getSpecificCard(int deckID, int cardID);

    @Query("SELECT MAX(cardID) FROM cards WHERE deckID == :deckID AND ")
    int getHighestCardID(int deckID);
}
