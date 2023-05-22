package com.github.raresp.proiectip.TownOfSalem.models.interactions.basicinteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.basicinteractions.BasicInteraction;

import java.util.List;

public class ArsonistCleanSelfInteraction extends BasicInteraction {
    public ArsonistCleanSelfInteraction(Character actioner) {
        super(actioner, 4);
    }

    @Override
    public void act() {

    }
}
