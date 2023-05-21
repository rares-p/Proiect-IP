package com.github.raresp.proiectip.TownOfSalem.models.interactions.basicinteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.DefenseTypes;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.Bodyguard;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.basicinteractions.BasicInteraction;

public class BodyguardProtectSelfInteraction extends BasicInteraction {
    Bodyguard bodyguard = (Bodyguard) actioner;
    public BodyguardProtectSelfInteraction(Character actioner, int priority) {
        super(actioner, priority);
    }

    @Override
    public void act() {
        bodyguard.setDefense(DefenseTypes.Basic);
        bodyguard.setHasProtectedHimself(true);

    }
}
