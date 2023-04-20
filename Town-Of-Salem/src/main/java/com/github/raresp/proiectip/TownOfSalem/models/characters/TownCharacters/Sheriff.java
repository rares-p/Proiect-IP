package com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

import java.util.List;

public class Sheriff extends TownCharacter {
    public Sheriff(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.None;
        this.defense = DefenseTypes.None;
        this.immunity = ImmunityTypes.None;
        this.role = Roles.Sheriff;
    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.None;
    }

    @Override
    public void act(List<Character> listOfTargets) {

    }

    @Override
    public void act() {
        Character target = this.targets.get(0);
        if(roleBlocked)
            this.AddNightResult("Someone occupied your night. You were role blocked!");
        else {
            this.AddNightResult("You decided to investigate " + target.playerUsername + " !");
            this.AddNightResult("Your target seems " + ((target.IsInnocent())?"Innocent!":"Suspicious!"));
        }
    }


}