package com.github.raresp.proiectip.TownOfSalem.models.interactions.attackinteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacter;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.Vigilante;

import java.util.List;

public class VigilanteKillInteraction extends AttackInteraction{
    Vigilante vigilante = (Vigilante) actioner;
    public VigilanteKillInteraction(Character actioner, List<Character> targets) {
        super(actioner, targets, 5);
    }
    @Override
    public void act() {
        if(targets.isEmpty())
            return;
        vigilante.bulletsLeft--;

        Character target = targets.get(0);

        target.setIsAlive(false);
        vigilante.AddNightResult("You attacked " + target.getPlayerUsername() + "!");
        target.AddNightResult("You were attacked last night. You died!");

        if (target instanceof TownCharacter) {
            vigilante.canAct = false; //?
            vigilante.setWillCommitSuicide(true);//urmatoarea noapte adaugam un VigilanteSuicideInteraction
        }

    }
}
