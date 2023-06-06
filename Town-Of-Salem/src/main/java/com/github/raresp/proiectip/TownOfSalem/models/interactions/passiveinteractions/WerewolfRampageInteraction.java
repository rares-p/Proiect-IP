package com.github.raresp.proiectip.TownOfSalem.models.interactions.passiveinteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.passiveinteractions.PassiveAttackInteraction;

import java.util.List;

public class WerewolfRampageInteraction extends PassiveAttackInteraction {
    public WerewolfRampageInteraction(Character actioner, List<Character> targets) {
        super(actioner, targets, 5);
    }

    @Override
    public void act() {
        if(targets.isEmpty())
            return;
        var target = targets.get(0);
        target.setAlive(false);
        target.AddNightResult("You were mauled by a werewolf");
        actioner.AddNightResult("You mauled " + target.getPlayerUsername());
    }
}
