package com.github.raresp.proiectip.TownOfSalem.models.interactions.visitinginteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.DefenseTypes;

import java.util.List;

public class DoctorHealTargetInteraction extends VisitingInteraction{
    public DoctorHealTargetInteraction(Character actioner, List<Character> targets) {
        super(actioner, targets, 3);
    }

    @Override
    public void act() {
        if(targets.isEmpty())
            return;
        Character target = targets.get(0);
        actioner.AddNightResult("You decided to heal " + target.getPlayerUsername() + " tonight!");
        target.setDefense(DefenseTypes.Powerful);
        target.healed = true;
    }
}
