package com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters;

import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.VisitingInteraction;

import java.util.List;

public class Blackmailer extends MafiaCharacter {

    public Blackmailer(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.None;
        this.defense = DefenseTypes.None;
        this.immunity = ImmunityTypes.None;
        this.innocent = false;
        this.actionText = "Blackmail";
    }

    public Blackmailer() {

    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.None;
    }

    @Override
    public Interaction createInteraction() {
        return new VisitingInteraction(this, targets, 4);
    }

    @Override
    public void act(List<Character> listOfTargets) {
        Character target = this.targets.get(0);
        target.canSpeak = false;
    }
}
