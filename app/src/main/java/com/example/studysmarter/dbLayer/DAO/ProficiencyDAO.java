package com.example.studysmarter.dbLayer.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.widget.LinearLayout;

import com.example.studysmarter.dbLayer.tables.Decks;
import com.example.studysmarter.dbLayer.tables.Proficiency;

import java.util.List;

@Dao
public interface ProficiencyDAO {
    @Insert
    void insertProfs(Proficiency... profs);

    @Insert
    void insertProf(Proficiency proficiency);

    @Update
    void updateProfs(Proficiency... proficiencies);

    @Update
    void updateProfs(Proficiency proficiency);

    @Delete
    void deleteProf(Proficiency... proficiencies);

    @Delete
    void deleteProf(Proficiency proficiency);

    @Query("SELECT * FROM proficiency WHERE deckID == :deckID ORDER BY deadline DESC")
    List<Proficiency> getByDeck(int deckID);

    @Query("SELECT * FROM proficiency ORDER BY deadline DESC")
    List<Proficiency> getAllProfs();

    @Query("SELECT * FROM proficiency WHERE stage == :stage ORDER BY deadline DESC")
    List<Proficiency> getByCurrentStage(int stage);
}
