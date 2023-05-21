package com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.AttackInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;

import java.util.ArrayList;
import java.util.List;
public class Arsonist extends NeutralCharacter {

    public static List<Character> dousedPlayers = new ArrayList<>();

    public Arsonist(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.Unstoppable;
        this.defense = DefenseTypes.Basic;
        this.immunity = ImmunityTypes.DetectionImmunity;
        this.actionText = "Douse";
    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.Basic;
    }

    public void act(List<Character> listOfTargets) {

    }

    @Override
    public Interaction createInteraction() {
        if(targets.isEmpty())
            return null;

        if (targets.get(0).getPlayerUsername().equals(this.playerUsername)) return new AttackInteraction(this, targets, 5);
        else return new AttackInteraction(this, targets, 3);
        //cum facem diff intre dousing si setting on fire?

    }

    @Override
    public void act() {
        if(targets.isEmpty())
            return;
        Character target = this.targets.get(0);
        if (target.getPlayerUsername().equals(this.playerUsername)) {
            for (Character dousedPlayer : dousedPlayers){
                dousedPlayer.setAlive(false);
                dousedPlayer.AddNightResult("You were set on fire by an Arsonist!");
            }
        }
        else {
            this.AddNightResult("You decided to douse " + target.getPlayerUsername() + " !");
            dousedPlayers.add(target);
        }

    }
}
