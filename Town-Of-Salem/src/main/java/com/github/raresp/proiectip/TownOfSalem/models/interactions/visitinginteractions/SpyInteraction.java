package com.github.raresp.proiectip.TownOfSalem.models.interactions.visitinginteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

import java.util.List;

public class SpyInteraction extends VisitingInteraction{
    public SpyInteraction(Character actioner, List<Character> targets) {
        super(actioner, targets, 6);
    }

    @Override
    public void act() {

    }
}
