package com.github.raresp.proiectip.TownOfSalem.models.characters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectionSession {
    public List<Character> characters = new ArrayList<>();
    public Map<Character, Character> selections = new HashMap<>();

    public SelectionSession(List<Character> characters){
        this.characters = characters;
        for(Character c : characters)
            if(c.targets.size()>0)
                selections.put(c, c.targets.get(0));
    }

    public Character calculateOutcome(){
        return selections.values().stream().filter(this::hasEnoughVotes).findAny().orElse(null);
    }
    private boolean hasEnoughVotes(Character character) {
        return selections.values().stream().filter(c -> c.equals(character)).count() >= characters.size()/2;
    }
}
