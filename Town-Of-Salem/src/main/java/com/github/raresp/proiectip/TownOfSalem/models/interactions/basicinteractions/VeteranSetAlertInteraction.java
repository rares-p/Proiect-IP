package com.github.raresp.proiectip.TownOfSalem.models.interactions.basicinteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.DefenseTypes;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.Veteran;

public class VeteranSetAlertInteraction extends BasicInteraction{
    Veteran veteran = (Veteran) actioner;
    public VeteranSetAlertInteraction(Character actioner) {
        super(actioner, 1);
    }

    @Override
    public void act() {
        veteran.alerts--;
        veteran.onAlert = true;
        veteran.setDefense(DefenseTypes.Basic);
        veteran.AddNightResult("You decided to go on alert.");
        veteran.AddNightResult("You have " + veteran.alerts + " alerts left.");
    }
}
