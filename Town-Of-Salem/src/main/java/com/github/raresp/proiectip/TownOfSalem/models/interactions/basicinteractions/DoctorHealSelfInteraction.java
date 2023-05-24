package com.github.raresp.proiectip.TownOfSalem.models.interactions.basicinteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.DefenseTypes;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.Doctor;

public class DoctorHealSelfInteraction extends BasicInteraction{
    Doctor doctor = (Doctor) actioner;
    public DoctorHealSelfInteraction(Character actioner) {
        super(actioner, 3);
    }

    @Override
    public void act() {
        doctor.setDefense(DefenseTypes.Powerful);
        doctor.healed = true;
        doctor.hasHealedHimself = true;
        doctor.AddNightResult("You decided to heal yourself tonight!");
    }
}
