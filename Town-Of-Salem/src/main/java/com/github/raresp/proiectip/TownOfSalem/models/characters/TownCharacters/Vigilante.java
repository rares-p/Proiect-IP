package com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

import java.util.List;

public class Vigilante extends TownCharacter {
    /*if you shoot another townie, you commit suicide because of the guilt*/

    private int bulletsLeft = 3;
    public Vigilante(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.Basic;
        this.defense = DefenseTypes.None;
        this.immunity = ImmunityTypes.None;
    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.None;
    }

    public int getBulletsLeft() {
        return bulletsLeft;
    }

    public void setBulletsLeft(int bulletsLeft) {
        this.bulletsLeft = bulletsLeft;
    }


    @Override
    public void act(List<Character> listOfTargets) {
        /*performs action;chooses another player to kill*/;
        bulletsLeft--;
    }

    @Override
    public void act() {

    }
}