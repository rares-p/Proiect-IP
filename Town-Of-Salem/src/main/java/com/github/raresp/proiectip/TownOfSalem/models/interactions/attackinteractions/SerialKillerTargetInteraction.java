package com.github.raresp.proiectip.TownOfSalem.models.interactions.attackinteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

import java.util.List;

public class SerialKillerTargetInteraction extends AttackInteraction{
    public SerialKillerTargetInteraction(Character actioner, List<Character> targets, int priority) {
        super(actioner, targets, priority);
    }

    @Override
    public void act() {
        if(targets.isEmpty())
            return;

        Character target = this.targets.get(0);
        target.setAlive(false);
        target.AddNightResult("You were attacked by a Serial Killer!");
    }
}
