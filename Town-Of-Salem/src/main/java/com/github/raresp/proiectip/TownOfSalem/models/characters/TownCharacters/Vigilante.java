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
        this.actionText = "Shoot";
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
    public Interaction createInteraction() {
        return null;
    }

    @Override
    public void act() {
        /*performs action;chooses another player to kill*/;
        bulletsLeft--;
        Character target = targets.get(0);
        if(target.getDefense().ordinal() >= this.attack.ordinal()) {
            this.AddNightResult("You tried to attack " + target.getPlayerUsername() + " but his defense was too strong!");
            target.AddNightResult("Someone attacked you last night but your defense was too strong!");
        }
        else {
            this.AddNightResult("You attacked " + target.getPlayerUsername() + "!");
            if(!target.healed) {
                target.setIsAlive(false);
                if(target instanceof TownCharacter)
                    canAct = false;
            }
        }
    }

    @Override
    public void act(List<Character> listOfTargets) {

    }


    @Override
    public void checkIfCanAct() {
        if(!canAct)
            return;
        if(bulletsLeft == 0)
            canAct = false;
    }
}