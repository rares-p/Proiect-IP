package com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.VisitingInteraction;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Doctor extends TownCharacter {
    //will be set true when he heals himself
    private boolean hasHealedHimself = false;

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
    public void act(List<Character> listOfTargets) {
        /* when acting, he gives temporary powerful defense*/
    }

    @Override
    public Interaction createInteraction() {
        return new VisitingInteraction(this, targets, 3);
    }


    @Override
    public void act() {
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

}