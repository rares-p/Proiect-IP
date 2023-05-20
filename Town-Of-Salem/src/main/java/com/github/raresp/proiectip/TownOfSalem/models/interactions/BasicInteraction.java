package com.github.raresp.proiectip.TownOfSalem.models.interactions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

import java.util.List;

public class BasicInteraction extends Interaction {
    public BasicInteraction(Character actioner, List<Character> targets, int priority) {
        super(actioner, targets, priority);
    }

    @Override
    public boolean isValid(){
        if(actioner.roleBlocked) return false;
        return true;
    }
}
