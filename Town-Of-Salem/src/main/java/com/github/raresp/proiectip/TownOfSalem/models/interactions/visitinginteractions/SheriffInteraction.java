package com.github.raresp.proiectip.TownOfSalem.models.interactions.visitinginteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

import java.util.List;

public class SheriffInteraction extends VisitingInteraction{
    public SheriffInteraction(Character actioner, List<Character> targets) {
        super(actioner, targets, 4);
    }

    @Override
    public void act() {
        if(targets.isEmpty())
            return;
        Character target = this.targets.get(0);
        actioner.AddNightResult("You decided to investigate " + target.getPlayerUsername() + " !");
        actioner.AddNightResult("Your target seems " + ((target.IsInnocent() && !target.isFramed())?"Innocent!":"Suspicious!"));
        target.setFramed(false);
    }
}
