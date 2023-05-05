package com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Vigilante extends TownCharacter {
    /*if you shoot another townie, you commit suicide because of the guilt*/

    private int bulletsLeft = 3;
    public Vigilante(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.Basic;
        this.defense = DefenseTypes.None;
        this.immunity = ImmunityTypes.None;
    }

    protected Vigilante() {
        super();
    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.None;
    }

    public int getBulletsLeft() {
        return bulletsLeft;
    }

    public void setBulletsLeft(int bulletsLeft) {
        this.bulletsLeft = bulletsLeft;
    }


    @Override
    public void act() {
        if(this.targets.isEmpty()) {
            this.AddNightResult("You decided to stay at home.");
            return;
        }

        /*performs action;chooses another player to kill*/;
        bulletsLeft--;
        Character target = targets.get(0);
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

    @Override
    public void act(List<Character> listOfTargets) {

    }
}