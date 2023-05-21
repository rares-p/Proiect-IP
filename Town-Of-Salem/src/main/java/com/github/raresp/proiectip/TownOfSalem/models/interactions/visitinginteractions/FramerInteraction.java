package com.github.raresp.proiectip.TownOfSalem.models.interactions.visitinginteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

import java.util.List;

public class FramerInteraction extends VisitingInteraction{
    public FramerInteraction(Character actioner, List<Character> targets, int priority) {
        super(actioner, targets, priority);
    }

    @Override
    public void act() {
        if(targets.isEmpty())
            return;

        Character target = this.targets.get(0);
        actioner.AddNightResult("You framed " + target.getPlayerUsername() + "!");
        target.setFramed(true);  //dispare framed de pe target cand e investigat !!
        //devine mafiot cand moare mafia killing
    }
}
