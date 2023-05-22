package com.github.raresp.proiectip.TownOfSalem.models.interactions.miscellaneousinteractions;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.Bodyguard;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.attackinteractions.AttackInteraction;

import java.util.List;

public class BodyguardAttackInteraction extends Interaction {
    Bodyguard bodyguard = (Bodyguard) actioner;
    public BodyguardAttackInteraction(Character actioner, List<Character> targets) {
        this.actioner = actioner;
        this.targets = targets;
        this.priority = 4;
    }
    @Override
    public boolean isValid() {
        if(targets.isEmpty())
            return false;
        if(bodyguard.roleBlocked)
            return false;
        Character target = targets.get(0);
        target.visitors.add(bodyguard);
        return true;
    }
    @Override
    public void act() {
        var attackInteractions = findAttackInteractions();
        if(attackInteractions == null) return;
        var firstInteraction = attackInteractions.get(0);

        attack(firstInteraction.actioner); //pe wiki zice ca protejeaza doar de un atac
        getTurnInteractions().getInteractions().remove(firstInteraction); //scoatem interactiunea atacatorului din lista de interactiuni

        if(attackInteractions.size() > 1){
            bodyguard.setAlive(false);
            bodyguard.AddNightResult("You died while protecting your target.");
        }
    }

    private void attack(Character attacker){
        if(attacker.getDefense().ordinal() >= actioner.getAttack().ordinal()) {
            actioner.AddNightResult("You tried to attack " + attacker.getPlayerUsername() + " but his defense was too strong!");
            if(attacker.healed) attacker.AddNightResult("You were attacked by a bodyguard but someone nursed you back to health");
            else attacker.AddNightResult("You were attacked by a bodyguard but your defense was too strong");
        }
        else {
            attacker.setIsAlive(false);
            attacker.AddNightResult("You were killed by a Bodyguard.");
            bodyguard.AddNightResult("You killed " + attacker.getPlayerUsername() + " while protecting your target.");
        }

        if(attacker.getAttack().ordinal() >= actioner.getDefense().ordinal()) {
            bodyguard.setAlive(false);
            bodyguard.AddNightResult("You died while protecting your target.");
        }
    }

    private List<Interaction> findAttackInteractions(){
        Character target = targets.get(0);

        return getTurnInteractions().getInteractions().stream()
                .filter(interaction -> interaction instanceof AttackInteraction && interaction.targets.get(0).equals(target))
                .toList();
    }
}
