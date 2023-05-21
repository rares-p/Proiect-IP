package com.github.raresp.proiectip.TownOfSalem.models.interactions.attackinteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.Arsonist;

import java.util.List;

public class ArsonistIgniteInteraction extends AttackInteraction{
    Arsonist arsonist = (Arsonist) actioner;
    public ArsonistIgniteInteraction(Character actioner, List<Character> targets, int priority) {
        super(actioner, targets, priority);
    }

    @Override
    public void act() {
        if(targets.isEmpty())
            return;
        Character target = this.targets.get(0);
        if (target.getPlayerUsername().equals(arsonist.getPlayerUsername())) {
            for (Character dousedPlayer : Arsonist.dousedPlayers){
                dousedPlayer.setAlive(false);
                dousedPlayer.AddNightResult("You were set on fire by an Arsonist!");
            }
        }
    }
}
