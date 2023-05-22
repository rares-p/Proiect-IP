package com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.miscellaneousinteractions.BodyguardAttackInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.basicinteractions.BodyguardProtectSelfInteraction;

import java.util.List;

public class Bodyguard extends TownCharacter{
    private boolean hasProtectedHimself = false;

    public Bodyguard(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.Powerful;
        this.defense = DefenseTypes.None;
        this.immunity = ImmunityTypes.None;
        this.actionText = "Heal";
    }

    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.None;
    }

    @Override
    public Interaction createInteraction() {
        if(targets.isEmpty())
            return null;
        if (targets.get(0) == this)
            return new BodyguardProtectSelfInteraction(this);
        return new BodyguardAttackInteraction(this, targets); //prioritatea 4 ca sa fie dupa heal-uri
    }

    public void setHasProtectedHimself(boolean hasProtectedHimself) {
        this.hasProtectedHimself = hasProtectedHimself;
    }
}

