package com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import java.util.List;

public class Mafioso extends MafiaCharacter {

    //cannot be initialized in the constructor because there is a circular
    //dependency from Mafioso to Godfather and you don't know who "is creates" first.
    //in the end when all the characters are created, the godfather and the mafioso fields will be assigned

    private GodFather godFather;
    public Mafioso(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.Basic;
        this.defense = DefenseTypes.None;
        this.immunity = ImmunityTypes.None;
        this.innocent = false;
        this.role = Roles.Mafioso;
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
        else if(target.defense.ordinal() >= this.attack.ordinal()) {
            this.AddNightResult("You tried to attack " + target.playerUsername + " but his defense was too strong!");
            target.AddNightResult("Someone attacked you last night but your defense was too strong!");
        }
        else {
            this.AddNightResult("You attacked " + target.playerUsername + " !");
            if(target.healed)
                target.AddNightResult("You were attacked last night but someone nursed you back to health");
            else
                target.AddNightResult("You were attacked last night. You died");
        }
    }
}