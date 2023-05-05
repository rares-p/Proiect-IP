package com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters;

import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.Doctor;

import java.util.List;

public class Consigliere extends MafiaCharacter {

    public Consigliere(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.None;
        this.defense = DefenseTypes.None;
        this.immunity = ImmunityTypes.None;
        this.innocent = false;
        this.actionText = "Investigate";
    }

    public Consigliere() {
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
        switch(target.getClass().getSimpleName()){
            case "Doctor": this.AddNightResult("Your target is a professional surgeon. They must be a Doctor.");
                break;
            case "Escort": this.AddNightResult("Your target is a beautiful person working for the town. They must be an Escort.");
                break;
            case "Sheriff": this.AddNightResult("Your target is a protector of the town. They must be a Sheriff.");
                break;
            case "Vigilante": this.AddNightResult("Your target will bend the law to enact justice. They must be a Vigilante.");
                break;
            case "Consigliere": this.AddNightResult("Your target gathers information for the Mafia. They must be a Consigliere.");
                break;
            case "Consort": this.AddNightResult("Your target is a beautiful person working for the Mafia. They must be a Consort.");
                break;
            case "Forger": this.AddNightResult("Your target is good at forging documents. They must be a Forger.");
                break;
            case "GodFather": this.AddNightResult("Your target is the leader of the Mafia. They must be the Godfather.");
                break;
            case "Mafioso": this.AddNightResult("Your target does the Godfather's dirty work. They must be a Mafioso.");
                break;
        }
        //devine mafiot
    }
}
