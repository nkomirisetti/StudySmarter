package com.example.studysmarter.dbLayer.dataBuilder;

import com.example.studysmarter.dbLayer.tables.Cards;

import java.util.ArrayList;
import java.util.List;

public class BuildCards {
    public static List<Cards> createCards(int deckID, List<String> terms, List<String> definitions, int startIndex){


        List<Cards> cards = new ArrayList<>();
        for (int i = 0; i < terms.size(); i++){
            Cards c = new Cards();
            c.initialize(deckID,i + startIndex,terms.get(i), definitions.get(i));
            cards.add(c);
        }
        return cards;
    }
}
