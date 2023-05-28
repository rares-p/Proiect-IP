package com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.basicinteractions.DoctorHealSelfInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.visitinginteractions.DoctorHealTargetInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.visitinginteractions.VisitingInteraction;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Doctor extends TownCharacter {
    //will be set true when he heals himself
    public boolean hasHealedHimself = false;

    public Doctor(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.None;
        this.defense = DefenseTypes.None;
        this.immunity = ImmunityTypes.None;
        this.actionText = "Heal";
    }

    protected Doctor() {
        super();
    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.None;
    }

    @Override
    public Interaction createInteraction() {
        if(targets.isEmpty())
            return null;
        if (targets.get(0).getPlayerUsername().equals(this.playerUsername))
            return new DoctorHealSelfInteraction(this);
        else
            return new DoctorHealTargetInteraction(this, targets);
    }


    @Override
    public void act() {
        if(targets.isEmpty())
            return;
        Character target = this.targets.get(0);
        target.setDefense(DefenseTypes.Powerful);

        if (target.getPlayerUsername().equals(this.playerUsername) && !hasHealedHimself){
            hasHealedHimself = true;
            this.AddNightResult("You decided to heal yourself tonight!");
        }
        else {
            this.AddNightResult("You decided to heal " + target.getPlayerUsername() + " tonight!");
            this.healed = true;
        }

    }


    @Override
    public String nightBeginningMessage() {
        return "You have " + (hasHealedHimself? 0 : 1) + " self heals left";
    }
}