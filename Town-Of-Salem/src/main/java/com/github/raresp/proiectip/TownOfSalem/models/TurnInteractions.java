package com.github.raresp.proiectip.TownOfSalem.models;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters.GodFather;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters.Mafioso;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class TurnInteractions {
    PriorityQueue<Character> charactersQueue = new PriorityQueue<>();

    public TurnInteractions(List<Character> characters){
        this.charactersQueue.addAll(characters);
        Mafioso mafioso = null;
        GodFather godfather = null;
        for(Character c : characters)
            if(c instanceof Mafioso)
                mafioso = (Mafioso)c;
            else if(c instanceof GodFather)
                godfather = (GodFather) c;

        if(godfather != null)
            godfather.mafioso = mafioso;
    }

    public void computeInteractionsOutcome() {
        System.out.println("calculeaza outcome");
        for (Character c : charactersQueue)
            c.act();
    }
}