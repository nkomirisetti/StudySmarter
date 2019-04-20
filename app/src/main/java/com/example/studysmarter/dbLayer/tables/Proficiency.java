package com.example.studysmarter.dbLayer.tables;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;


import java.util.Date;

@Entity(tableName = "proficiency", foreignKeys = @ForeignKey(entity = Decks.class,
        parentColumns = "deckID", childColumns = "deckID"))
public class Proficiency {
    @PrimaryKey(autoGenerate = true)
    public int stageID;

    public int stage;
    public int deckID;
    public Date deadline;
}