package com.github.raresp.proiectip.TownOfSalem.models.interactions.visitinginteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;

import java.util.List;

public abstract class VisitingInteraction extends Interaction {
    //asta e pt cand un personaj targeteaza pe altcnv si merge la el acasa
    //ex doctor, sheriff, etc.
    public VisitingInteraction(Character actioner, List<Character> targets, int priority) {
        this.actioner = actioner;
        this.targets = targets;
        this.priority = priority;
    }

    @Override
    public boolean isValid(){
        if (this.targets.isEmpty()) {
            actioner.AddNightResult("You decided to stay at home.");
            return false;
        }
        if(actioner.roleBlocked) return false;
        //targets.get(0).visitors.add(actioner);
        return true;
    }
}
