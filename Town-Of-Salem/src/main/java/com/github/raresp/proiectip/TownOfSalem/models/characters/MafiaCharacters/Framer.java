package com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters;

import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.BasicInteraction;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Framer extends MafiaCharacter {
    boolean isMafioso;

    public Framer(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.None;
        this.defense = DefenseTypes.None;
        this.immunity = ImmunityTypes.None;
        this.innocent = false;
        this.actionText = "Frame";
    }
    protected Framer() {
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
        return new BasicInteraction(this, targets, 3);
    }

    @Override
    public void act() {
        if (this.targets.isEmpty()) {
            this.AddNightResult("You decided to stay at home.");
            return;
        }
        Character target = this.targets.get(0);
        this.AddNightResult("You framed " + target.getPlayerUsername() + "!");
        target.setFramed(true);  //dispare framed de pe target cand e investigat !!
        //devine mafiot cand moare mafia killing
    }
}
