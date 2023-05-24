package com.github.raresp.proiectip.TownOfSalem.models.interactions.visitinginteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.Arsonist;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.visitinginteractions.VisitingInteraction;

import java.util.List;

public class ArsonistDouseInteraction extends VisitingInteraction {
    Arsonist arsonist = (Arsonist) actioner;
    public ArsonistDouseInteraction(Character actioner, List<Character> targets) {
        super(actioner, targets, 3);
    }

    @Override
    public void act() {
        if(targets.isEmpty())
            return;
        Character target = arsonist.targets.get(0);
        arsonist.AddNightResult("You decided to douse " + target.getPlayerUsername() + " !");
        Arsonist.dousedPlayers.add(target);
    }
}
