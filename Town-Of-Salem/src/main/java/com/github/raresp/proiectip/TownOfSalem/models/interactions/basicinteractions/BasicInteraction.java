package com.github.raresp.proiectip.TownOfSalem.models.interactions.basicinteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;

import java.util.List;

public abstract class BasicInteraction extends Interaction {
    /////asta in principiu e pt actiuni in care personajul se targeteaza pe sine, isi activeaza o abilitate
    /// de exemplu, veteranul se pune onAlert
    public BasicInteraction(Character actioner, int priority) {
        this.actioner = actioner;
        this.priority = priority;
    }

    @Override
    public boolean isValid(){
        if(actioner.roleBlocked) return false;
        return true;
    }
}
