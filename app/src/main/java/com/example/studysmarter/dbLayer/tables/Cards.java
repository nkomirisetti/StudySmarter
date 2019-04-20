package com.example.studysmarter.dbLayer.tables;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "cards", foreignKeys = @ForeignKey(entity = Decks.class,
        parentColumns = "deckID", childColumns = "deckID"))
public class Cards {
    public int deckID;
    @PrimaryKey(autoGenerate = true)
    public int cardID;

    public int index;
    public String term;
    public String definition;

    public void initialize(int deckID, int index, String term, String definition) {
        this.deckID = deckID;
        this.index = index;
        this.term = term;
        this.definition = definition;
    }
}
