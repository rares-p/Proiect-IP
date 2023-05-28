package com.github.raresp.proiectip.TownOfSalem.models.interactions.basicinteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.DefenseTypes;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.Bodyguard;

public class BodyguardProtectSelfInteraction extends BasicInteraction {
    Bodyguard bodyguard = (Bodyguard) actioner;
    public BodyguardProtectSelfInteraction(Character actioner) {
        super(actioner, 3);
    }

    @Override
    public void act() {
        bodyguard.setDefense(DefenseTypes.Basic);
        bodyguard.setHasProtectedHimself(true);
        bodyguard.AddNightResult("You decided to protect yourself.");
        //ar tb sa-i apara mesaj "You were attacked but your bulletproof vest saved you!"
    }
}
