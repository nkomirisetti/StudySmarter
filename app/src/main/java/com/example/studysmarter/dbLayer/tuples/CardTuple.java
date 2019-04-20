package com.example.studysmarter.dbLayer.tuples;

import android.arch.persistence.room.ColumnInfo;

public class CardTuple {
    @ColumnInfo(name = "term")
    public String term;

    @ColumnInfo(name = "definition")
    public String definition;
}
