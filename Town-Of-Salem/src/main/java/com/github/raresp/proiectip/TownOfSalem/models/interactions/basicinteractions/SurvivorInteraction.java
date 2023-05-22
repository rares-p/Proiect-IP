package com.github.raresp.proiectip.TownOfSalem.models.interactions.basicinteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.DefenseTypes;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.Survivor;

public class SurvivorInteraction extends BasicInteraction{
    Survivor survivor = (Survivor) actioner;
    public SurvivorInteraction(Character actioner) {
        super(actioner, 4);
    }

    @Override
    public void act() {
        survivor.bulletVestsCount--;
        survivor.setDefense(DefenseTypes.Basic);
    }
}
