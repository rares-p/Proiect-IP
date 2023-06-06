package com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters;

import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.attackinteractions.VigilanteKillInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.basicinteractions.VigilanteSuicideInteraction;
import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Vigilante extends TownCharacter {
    /*if you shoot another townie, you commit suicide because of the guilt*/

    public int bulletsLeft = 3;
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

    @Override
    public Interaction createInteraction() {
        if(this.willCommitSuicide)
            return new VigilanteSuicideInteraction(this);
        if(targets.isEmpty())
            return null;
        return new VigilanteKillInteraction(this, targets);
    }

    @Override
    public void checkIfCanAct() {
        if (!canAct)
            return;
        if (bulletsLeft == 0)
            canAct = false;
    }


    public void setWillCommitSuicide(boolean willCommitSuicide) {
        this.willCommitSuicide = willCommitSuicide;
    }

    @Override
    public String nightBeginningMessage() {
        if(willCommitSuicide) {
            return "You decided to put the gun away and suicide because of the guilt";
        }
        return "You have " + bulletsLeft + " bullets left";
    }
}