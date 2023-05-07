package com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

import java.util.List;

@Entity
public class GodFather extends MafiaCharacter {
    @OneToOne
    @JoinColumn(name = "mafioso_godfather_id", nullable = true)
    public Character mafioso = null;

    public GodFather(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.Basic;
        this.defense = DefenseTypes.Basic;
        this.immunity = ImmunityTypes.DetectionImmunity;
        this.innocent = true;
    }

    protected GodFather() {
        super();
    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.Basic;
    }

    @Override
    public void act(List<Character> listOfTargets) {

    }

    @Override
    public void act() {
        if(roleBlocked) {
            this.AddNightResult("Someone occupied your night. You were role blocked!");
            return;
        }

        if(targets.isEmpty()) {
            this.AddNightResult("You decided to stay at home.");
            return;
        }

        Character target = targets.get(0);
        if(mafioso != null) {
            mafioso.targets.set(0, target);
            mafioso.AddNightResult("The Godfather ordered you to kill " + target.getPlayerUsername() + "!");
        }
        else {
            if(target.getDefense().ordinal() >= this.attack.ordinal()) {
                this.AddNightResult("You tried to attack " + target.getPlayerUsername() + " but his defense was too strong!");
                target.AddNightResult("Someone attacked you last night but your defense was too strong!");
            }
            else {
                this.AddNightResult("You attacked " + target.getPlayerUsername() + "!");
                if(target.healed)
                    target.AddNightResult("You were attacked last night but someone nursed you back to health");
                else{
                    target.AddNightResult("You were attacked last night. You died");
                    target.setIsAlive(false);
                }
            }
        }
    }
}