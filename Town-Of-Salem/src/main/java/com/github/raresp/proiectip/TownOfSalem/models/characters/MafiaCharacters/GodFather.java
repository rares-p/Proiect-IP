package com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.AttackInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.util.List;

@Entity
public class GodFather extends MafiaCharacter {
    @OneToOne
    @JoinColumn(name = "mafioso_godfather_id", nullable = true)
    public Character mafioso = null;

    public GodFather(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.Basic;
        this.defense = DefenseTypes.Basic;
        this.immunity = ImmunityTypes.DetectionImmunity;
        this.innocent = true;
    }

    protected GodFather() {
        super();
    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.Basic;
    }

    @Override
    public void act(List<Character> listOfTargets) {

    }

    @Override
    public Interaction createInteraction() {
        return new AttackInteraction(this, targets, 5);
    }

    @Override
    public void act() {
        Character target = targets.get(0);
        this.AddNightResult("You attacked " + target.getPlayerUsername() + "!");
        target.AddNightResult("You were attacked last night. You died");
        target.setIsAlive(false);

    }
}