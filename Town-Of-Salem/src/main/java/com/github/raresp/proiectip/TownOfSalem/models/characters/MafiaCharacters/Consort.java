package com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters;

import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.SerialKiller;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.DistractInteraction;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Consort extends MafiaCharacter {
    public Consort(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.None;
        this.defense = DefenseTypes.None;
        this.immunity = ImmunityTypes.Roleblock;
        this.innocent = false;
        this.actionText = "Distract";
    }

    public Consort() {
    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.None;
    }

    @Override
    public Interaction createInteraction() {
        return new DistractInteraction(this, targets, 2);
    }
    @Override
    public void act() {

    }

    @Override
    public void act(List<Character> listOfTargets) {
        Character target = this.targets.get(0);

        target.setRoleBlocked(true);
        target.AddNightResult("Someone occupied your night. You were role blocked!");
        this.AddNightResult("You kept " + target.getPlayerUsername() + " occupied last night. They did not use their ability!");

        //devine mafiot
    }



}
