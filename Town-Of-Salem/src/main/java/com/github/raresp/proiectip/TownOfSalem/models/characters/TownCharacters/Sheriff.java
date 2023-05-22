package com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.VisitingInteraction;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Sheriff extends TownCharacter {
    public Sheriff(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.None;
        this.defense = DefenseTypes.None;
        this.immunity = ImmunityTypes.None;
        this.actionText = "Investigate";
    }

    protected Sheriff() {
        super();
    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.None;
    }

    @Override
    public void act(List<Character> listOfTargets) {

    }

    @Override
    public Interaction createInteraction() {
        if(targets.isEmpty())
            return null;
        return new VisitingInteraction(this, targets, 4);
    }

    @Override
    public void act() {
        if(targets.isEmpty())
            return;
        Character target = this.targets.get(0);
        this.AddNightResult("You decided to investigate " + target.getPlayerUsername() + " !");
        this.AddNightResult("Your target seems " + ((target.IsInnocent() || target.isFramed())?"Innocent!":"Suspicious!"));
        target.setFramed(false);
    }


}