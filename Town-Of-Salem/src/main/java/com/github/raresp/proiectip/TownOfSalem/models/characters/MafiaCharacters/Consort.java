package com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters;

import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.SerialKiller;
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
    public void act() {

    }

    @Override
    public void act(List<Character> listOfTargets) {
        if(this.targets.isEmpty()) {
            this.AddNightResult("You decided to stay at home.");
            return;
        }
        Character target = this.targets.get(0);

        if(target instanceof SerialKiller){
            //this.isAlive = false;
            //this.AddNightResult("You were murdered by the Serial Killer you visited.");
            //target.AddNightResult("Someone tried to role block you. You murdered them!");
            target.AddNightResult("Someone tried to role block you, but you are immune");
        }
//        else if(target.getImmunity() == ImmunityTypes.Roleblock) {
//            this.AddNightResult("You tried to distract " + target.getPlayerUsername() + " but were unsuccessful!");
//            target.AddNightResult("Someone tried to role block you, but you are immune!");
//        }
        else{
            target.setRoleBlocked(true);
            target.AddNightResult("Someone occupied your night. You were role blocked!");
            this.AddNightResult("You kept " + target.getPlayerUsername() + " occupied last night. They did not use their ability!");
        }
        //devine mafiot
    }
}
