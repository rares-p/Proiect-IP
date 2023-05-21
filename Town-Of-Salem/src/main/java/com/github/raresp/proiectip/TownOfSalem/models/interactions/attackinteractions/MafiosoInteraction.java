package com.github.raresp.proiectip.TownOfSalem.models.interactions.attackinteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

import java.util.List;

public class MafiosoInteraction extends AttackInteraction{
    public MafiosoInteraction(Character actioner, List<Character> targets, int priority) {
        super(actioner, targets, priority);
    }

    @Override
    public void act() {
        if(targets.isEmpty())
            return;

        Character target = this.targets.get(0);
        actioner.AddNightResult("You attacked " + target.getPlayerUsername() + "!");
        target.AddNightResult("You were attacked last night. You died");
        target.setIsAlive(false);

        //e promovat la godfather daca moare
    }
}
