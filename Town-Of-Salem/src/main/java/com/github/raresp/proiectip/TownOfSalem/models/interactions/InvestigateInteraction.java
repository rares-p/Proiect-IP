package com.github.raresp.proiectip.TownOfSalem.models.interactions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Interaction;

import java.util.List;

public class InvestigateInteraction extends Interaction {
    public InvestigateInteraction(Character actioner, List<Character> targets, int priority) {
        super(actioner, targets, priority);
    }

    @Override
    public boolean isValid(){
        if(actioner.roleBlocked) return false;
        return true;
    }
}
