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
    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.Basic;
    }

    @Override
    public void act(List<Character> listOfTargets) {

    }
}