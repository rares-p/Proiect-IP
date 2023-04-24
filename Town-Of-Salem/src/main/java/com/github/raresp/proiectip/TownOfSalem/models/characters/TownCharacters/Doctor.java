package com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import jakarta.persistence.Entity;
import org.springframework.data.repository.cdi.Eager;

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
        this.defense = DefenseTypes.Powerful;
    }

    @Override
    public void act(List<Character> listOfTargets) {
        /* when acting, he gives temporary powerful defense*/
    }

    @Override
    public void act() {
        if(this.targets.isEmpty())
        {
            this.AddNightResult("You decided to stay at home.");
            return;
        }

        Character target = this.targets.get(0);
        if(roleBlocked)
            this.AddNightResult("Someone occupied your night. You were role blocked!");
        else {
            target.healed = true;
            this.AddNightResult("You decided to heal " + target.getPlayerUsername() + " tonight!");
        }
    }

}