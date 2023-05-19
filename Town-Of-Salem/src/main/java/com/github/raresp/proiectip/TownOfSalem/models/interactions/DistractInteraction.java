package com.github.raresp.proiectip.TownOfSalem.models.interactions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.ImmunityTypes;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.SerialKiller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DistractInteraction extends Interaction {
    public DistractInteraction(Character actioner, List<Character> targets, int priority) {
        super(actioner, targets, priority);
    }

    @Override
    public boolean isValid() {
        if (this.targets.isEmpty()) {
            actioner.AddNightResult("You decided to stay at home.");
            return false;
        }
        Character target = targets.get(0);
        target.visitors.add(actioner);
        if(target instanceof SerialKiller) {
            getTurnInteractions().addInteraction(new PassiveInteraction(target, new ArrayList<>(Arrays.asList(actioner)), 5)); //nush exact care-i prioritatea, dupa heal
            return false;
        }
        if(target.getImmunity() == ImmunityTypes.Roleblock) {
            actioner.AddNightResult("You tried to distract " + target.getPlayerUsername() + " but were unsuccessful!");
            target.AddNightResult("Someone tried to role block you, but you are immune!");
            return false;
        }
        return true;
    }
}
