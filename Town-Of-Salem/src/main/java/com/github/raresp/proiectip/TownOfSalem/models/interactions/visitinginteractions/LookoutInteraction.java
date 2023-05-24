package com.github.raresp.proiectip.TownOfSalem.models.interactions.visitinginteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

import java.util.List;

public class LookoutInteraction extends VisitingInteraction{
    public LookoutInteraction(Character actioner, List<Character> targets) {
        super(actioner, targets, 4);
    }

    @Override
    public void act() {

    }
}
