package com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.BasicInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.VisitingInteraction;

import java.util.List;

public class Bodyguard extends TownCharacter implements PassiveActing {
    private boolean hasProtectedHimself = false;

    public Bodyguard(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.Powerful;
        this.defense = DefenseTypes.None;
        this.immunity = ImmunityTypes.None;
        this.actionText = "Heal";
    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.None;
    }

    @Override
    public void act(List<Character> listOfTargets) {
        Character target = targets.get(0);
        if (target == this) {
            this.defense = DefenseTypes.Basic;
            hasProtectedHimself = true;
        }
    }

    @Override
    public Interaction createInteraction() {
        if(targets.isEmpty())
            return null;
        if (targets.get(0) == this)
            return new BasicInteraction(this, targets, 3);
        return new VisitingInteraction(this, targets, 3);
    }

    @Override
    public void passiveAction(List<Character> characters) {
        if(targets.isEmpty())
            return;

        Character target = targets.get(0);
        target.setIsAlive(false);
        target.AddNightResult("You were killed by a Bodyguard.");
        this.AddNightResult("You killed " + target.getPlayerUsername() + " while protecting your target.");
        if(!this.healed) {
            this.AddNightResult("You died while protecting your target.");
            this.setAlive(false);
        }
    }
}

