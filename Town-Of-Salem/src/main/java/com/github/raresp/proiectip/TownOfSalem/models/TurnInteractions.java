package com.github.raresp.proiectip.TownOfSalem.models;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class TurnInteractions {
    PriorityQueue<Character> charactersQueue = new PriorityQueue<>();

    TurnInteractions(List<Character> characters){
        this.charactersQueue.addAll(characters);
    }

    public void computeInteractionsOutcome(){
        for (Character c : charactersQueue)
            c.act();
    }
}