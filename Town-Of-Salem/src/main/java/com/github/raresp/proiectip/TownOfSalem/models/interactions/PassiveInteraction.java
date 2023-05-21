package com.github.raresp.proiectip.TownOfSalem.models.interactions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

import java.util.List;

public class PassiveInteraction extends Interaction { //pt veteran si serial killer
//la veteran act-ul e sa se puna on alert, si asta ca omoara pe cine il viziteaza e ceva pasiv
    public PassiveInteraction(Character actioner, List<Character> targets, int priority) {
        super(actioner, targets, priority);
    }

    @Override
    public boolean isValid() {
        Character target = targets.get(0);
        if(target.getDefense().ordinal() >= actioner.getAttack().ordinal()) {
            actioner.AddNightResult("You tried to attack " + target.getPlayerUsername() + " but his defense was too strong!");
            target.AddNightResult("You were attacked by a " + actioner.getClass().getSimpleName() + " but someone nursed you back to health");
            return false;
        }
        return true;
    }
}
