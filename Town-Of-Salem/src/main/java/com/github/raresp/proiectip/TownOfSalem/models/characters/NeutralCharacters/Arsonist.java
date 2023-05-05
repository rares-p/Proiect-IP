package com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.List;
public class Arsonist extends NeutralCharacter {

    public List<Character> dousedPlayers = new ArrayList<>();

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
    public void act() {
        if(this.targets.isEmpty())
        {
            this.AddNightResult("You decided to stay at home.");
            return;
        }
        Character target = this.targets.get(0);
        if(roleBlocked)
            this.AddNightResult("Someone occupied your night. You were role blocked!");
        else if (target.getPlayerUsername().equals(this.playerUsername)) {
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
