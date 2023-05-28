package com.github.raresp.proiectip.TownOfSalem.models.interactions.distractinteractions;

import com.github.raresp.proiectip.TownOfSalem.models.TurnInteractions;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.distractinteractions.DistractInteraction;

import java.util.List;

public class ConsortInteraction extends DistractInteraction {
    public ConsortInteraction(Character actioner, List<Character> targets) {
        super(actioner, targets, 2);
    }

    @Override
    public void act() {
        if(targets.isEmpty())
            return;

        Character target = this.targets.get(0);

        target.setRoleBlocked(true);
        target.AddNightResult("Someone occupied your night. You were role blocked!");
        actioner.AddNightResult("You kept " + target.getPlayerUsername() + " occupied last night. They did not use their ability!");

        //devine mafiot
    }

}
