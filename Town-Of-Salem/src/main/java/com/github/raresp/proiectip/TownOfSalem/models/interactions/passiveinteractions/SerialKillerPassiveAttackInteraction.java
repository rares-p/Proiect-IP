package com.github.raresp.proiectip.TownOfSalem.models.interactions.passiveinteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.attackinteractions.AttackInteraction;

import java.util.List;

public class SerialKillerPassiveAttackInteraction extends PassiveAttackInteraction {
    public SerialKillerPassiveAttackInteraction(Character actioner, List<Character> targets, int priority) {
        super(actioner, targets, priority);
    }

    @Override
    public void act() {
        if(targets.isEmpty())
            return;
        Character target = targets.get(0);
        target.setAlive(false);
        target.AddNightResult("You were murdered by the Serial Killer you visited.");
        actioner.AddNightResult("Someone tried to role block you. You murdered them!");
    }
}
