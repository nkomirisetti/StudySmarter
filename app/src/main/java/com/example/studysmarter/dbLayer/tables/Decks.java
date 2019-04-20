package com.example.studysmarter.dbLayer.tables;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "decks")
public class Decks {
    public String name;
    public Date lastOpened;
    @PrimaryKey(autoGenerate = true)
    public int deckID;
    public double proficiency;
    public String isFavorite;

    public void initialize(String name){
        this.name = name;
        this.lastOpened = new Date();
        this.proficiency = 0;
    }
}
