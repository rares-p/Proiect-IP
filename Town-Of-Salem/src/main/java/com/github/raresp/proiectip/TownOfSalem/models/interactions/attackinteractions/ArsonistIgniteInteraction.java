package com.github.raresp.proiectip.TownOfSalem.models.interactions.attackinteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.Arsonist;

import java.util.List;

public class ArsonistIgniteInteraction extends AttackInteraction{
    Arsonist arsonist = (Arsonist) actioner;
    public ArsonistIgniteInteraction(Character actioner, List<Character> targets) {
        super(actioner, targets, 5);
    }

    @Override
    public void act() {
        if(targets.isEmpty())
            return;
        for (Character dousedPlayer : Arsonist.dousedPlayers){
            dousedPlayer.setAlive(false);
            dousedPlayer.AddNightResult("You were set on fire by an Arsonist!");
        }
    }
}
