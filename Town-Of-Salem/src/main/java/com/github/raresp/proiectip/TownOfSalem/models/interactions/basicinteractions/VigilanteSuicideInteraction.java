package com.github.raresp.proiectip.TownOfSalem.models.interactions.basicinteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacter;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.Vigilante;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;

public class VigilanteSuicideInteraction extends BasicInteraction {
    public VigilanteSuicideInteraction(Character actioner){
        super(actioner, 5);
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void act() {
        actioner.AddNightResult("You killed yourself because of the guilt! ");
        actioner.setAlive(false);
    }
}
