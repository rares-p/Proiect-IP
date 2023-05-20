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

    }

    @Override
    public Interaction createInteraction() {
        if (targets.get(0) == this) {
            this.defense = DefenseTypes.Basic;
            return new BasicInteraction(this, targets, 3);
        }
        return new VisitingInteraction(this, targets, 3);
    }

    @Override
    public void passiveAction(List<Character> characters) {
        Character target = targets.get(0);
        target.AddNightResult("You were attacked, but your bulletproof vest saved you!.");
    }
}

