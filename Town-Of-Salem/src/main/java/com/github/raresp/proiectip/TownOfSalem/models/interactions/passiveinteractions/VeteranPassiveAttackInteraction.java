package com.github.raresp.proiectip.TownOfSalem.models.interactions.passiveinteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

import java.util.List;

public class VeteranPassiveAttackInteraction extends PassiveAttackInteraction{
    public VeteranPassiveAttackInteraction(Character actioner, List<Character> targets) {
        super(actioner, targets, 4);
    }

    @Override
    public void act() {
        Character target = targets.get(0);
        target.setAlive(false);
        target.AddNightResult("You were murdered by the Veteran you visited.");
        actioner.AddNightResult("Someone visited you last night. You shot them!");
    }
}
