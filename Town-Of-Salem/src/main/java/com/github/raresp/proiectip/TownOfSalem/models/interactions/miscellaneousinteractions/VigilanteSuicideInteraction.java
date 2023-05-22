package com.github.raresp.proiectip.TownOfSalem.models.interactions.miscellaneousinteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacter;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.Vigilante;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;

public class VigilanteSuicideInteraction extends Interaction {
    public VigilanteSuicideInteraction(Character actioner){
        this.actioner = actioner;
        this.priority = 5;
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
