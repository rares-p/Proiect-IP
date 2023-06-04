package com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.*;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.miscellaneousinteractions.WerewolfSetTargetInteraction;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Werewolf extends NeutralCharacter implements PassiveActing {
    //teoretic daca esti jailed si nu esti executat, omori jailerul dar tbh e complicat
    private boolean isFullMoon;
    public Werewolf(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.Powerful;
        this.defense = DefenseTypes.Basic;
        this.immunity = ImmunityTypes.None;
        this.actionText = "Rampage";
    }

    protected Werewolf() {
        super();
    }
    @Override
    public void resetDefense() {
        this.defense = DefenseTypes.Basic;
    }
    @Override
    public void act(){
        for(var target : targets){
            if(!target.healed){
                //din nou, problema e pt ca sunt mai multe targeturi, pana acum era un sg target si se verifica deja
                //validitatea in isValid
                target.setAlive(false);
                target.AddNightResult("You were mauled by a Werewolf!");
            }
        }
    }

    @Override
    public Interaction createInteraction() {
        if(!isFullMoon)
            return null;
        return new WerewolfSetTargetInteraction(this, targets);
//        if(targets.isEmpty())
//            return new WerewolfRampageHomeInteraction(this);
//        return new WerewolfRampageTargetInteraction(this, targets);
    }

    @Override
    public void passiveAction(List<Character> characters) {
        if(visitors.size()>0)
            this.AddNightResult("You killed your visitors last night!");
        for (var visitor : this.visitors)
        {
            visitor.setAlive(false);
        }
    }

    public void setFullMoon(boolean fullMoon) {
        isFullMoon = fullMoon;
    }

    public boolean isFullMoon() {
        return isFullMoon;
    }

    @Override
    public void setPossibleTargets(List<Character> characters) {

    }
}
