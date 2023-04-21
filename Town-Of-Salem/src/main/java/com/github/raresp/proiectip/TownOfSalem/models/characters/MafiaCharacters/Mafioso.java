package com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Mafioso extends MafiaCharacter {
    @OneToOne(mappedBy = "mafioso", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private GodFather godFather;

    public Mafioso(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.Basic;
        this.defense = DefenseTypes.None;
        this.immunity = ImmunityTypes.None;
        this.innocent = false;
    }

    protected Mafioso() {
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
    public void act() {
        Character target = this.targets.get(0);
        if(roleBlocked)
            this.AddNightResult("Someone occupied your night. You were role blocked!");
        else if(target.getDefense().ordinal() >= this.attack.ordinal()) {
            this.AddNightResult("You tried to attack " + target.getPlayerUsername() + " but his defense was too strong!");
            target.AddNightResult("Someone attacked you last night but your defense was too strong!");
        }
        else {
            this.AddNightResult("You attacked " + target.getPlayerUsername() + " !");
            if(target.healed)
                target.AddNightResult("You were attacked last night but someone nursed you back to health");
            else
                target.AddNightResult("You were attacked last night. You died");
        }
    }
}