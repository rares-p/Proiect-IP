package com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters;

import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.AttackInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Vigilante extends TownCharacter {
    /*if you shoot another townie, you commit suicide because of the guilt*/

    private int bulletsLeft = 3;
    private boolean willCommitSuicide = false;

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
        return new AttackInteraction(this, targets,5);
    }//din nou, nu tb si aici o interactiune? am adaugat eu

    @Override
    public void act() {
        /*performs action;chooses another player to kill*/
        if(canAct){
            bulletsLeft--;
            Character target = targets.get(0);
            if (target.getDefense().ordinal() >= this.attack.ordinal()) {
                this.AddNightResult("You tried to attack " + target.getPlayerUsername() + " but his defense was too strong!");
                target.AddNightResult("Someone attacked you last night but your defense was too strong!");
            } else {
                this.AddNightResult("You attacked " + target.getPlayerUsername() + "!");
                if (!target.healed) {
                    target.setIsAlive(false);
                    if (target instanceof TownCharacter) {
                        canAct = false;
                        willCommitSuicide = true;//urmatoarea noapte o sa adauge un attackInteraction de la el la el
                    }
                }
            }
            return ;
        }
        if(willCommitSuicide){
            this.AddNightResult("You killed yourself because of the guilt! ");
            this.setAlive(false);
        }


    }

    @Override
    public void act(List<Character> listOfTargets) {

    }


    @Override
    public void checkIfCanAct() {
        if (!canAct)
            return;
        if (bulletsLeft == 0)
            canAct = false;
    }
}