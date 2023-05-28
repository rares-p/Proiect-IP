package com.github.raresp.proiectip.TownOfSalem.models.interactions.attackinteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

import java.util.List;

public class GodfatherInteraction extends AttackInteraction{
    public GodfatherInteraction(Character actioner, List<Character> targets) {
        super(actioner, targets, 5);
    }
    @Override
    public void act() {
        if(targets.isEmpty())
            return;

        Character target = targets.get(0);
        actioner.AddNightResult("You attacked " + target.getPlayerUsername() + "!");
        target.AddNightResult("You were attacked last night. You died");
        target.setIsAlive(false);
    }
}
