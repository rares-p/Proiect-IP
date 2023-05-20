package com.github.raresp.proiectip.TownOfSalem.models;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacter;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters.Blackmailer;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters.Framer;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters.GodFather;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacters.Mafioso;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.Arsonist;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.SerialKiller;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.Werewolf;
import com.github.raresp.proiectip.TownOfSalem.models.characters.PassiveActing;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.Bodyguard;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.Doctor;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.Lookout;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.Spy;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.AttackInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.PassiveInteraction;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.VisitingInteraction;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class TurnInteractions {
    PriorityQueue<Interaction> interactions;
    List<Interaction> validInteractions = new ArrayList<>();

    public TurnInteractions(List<Character> characters) {
        interactions = characters.stream().map(Character::createInteraction).collect(Collectors.toCollection(PriorityQueue::new));
    }

    public void addInteraction(Interaction interaction) {
        interactions.add(interaction);
    }

    public void deleteInteraction(Interaction interaction){
        interactions.remove(interaction);
    }
    public PriorityQueue<Interaction> getInteractions(){
        return interactions;
    }
    public void computeInteractionsOutcome() {
        for (int p = 1; p <= 6; p++) {
            if (p == 6)
                computeSpyMessage();
            if (p == 5) {
                computeMafiosoInteraction();
                computeWerewolfInteraction();
            }
            if (p == 4)
                computeLookoutMessage();
            if (p == 3)
                computeBodyguardInteraction();
            int priority = p;

            validInteractions = interactions.stream()
                    .filter(i -> i.getPriority() == priority)
                    .filter(i -> i.actioner.isAlive())
                    .filter(Interaction::isValid)
                    .collect(Collectors.toList());

            for (Interaction interaction : validInteractions)
                if (interaction instanceof PassiveInteraction && interaction.actioner instanceof PassiveActing actioner)
                    actioner.passiveAction(interaction.targets);
                else {//nu e passive action
                    if (interaction.actioner instanceof Werewolf)//pt ca tb un argument la act, nu pot apela fara parametri
                        interaction.actioner.act(interaction.targets);
                    else
                        interaction.actioner.act();
                }
        }
        computeDoctorMessage();
    }

    private void computeMafiosoInteraction() {
        Interaction mafiosoInteraction = interactions.stream().filter(i -> i.actioner instanceof Mafioso).filter(i -> i.actioner.isAlive()).findFirst().orElse(null);
        Interaction godfatherInteraction = interactions.stream().filter(i -> i.actioner instanceof GodFather).filter(i -> i.actioner.isAlive()).findFirst().orElse(null);

        if (mafiosoInteraction == null || godfatherInteraction == null) return;

        interactions.remove(mafiosoInteraction);
        interactions.remove(godfatherInteraction);

        Mafioso mafioso = (Mafioso) mafiosoInteraction.actioner;
        GodFather godFather = (GodFather) godfatherInteraction.actioner;

        if (godFather.roleBlocked) {
            if (!mafioso.roleBlocked)
                interactions.add(mafiosoInteraction);
        } else if (mafioso.roleBlocked)
            interactions.add(godfatherInteraction);
        else if (godfatherInteraction.targets.isEmpty())  ///godfather nu a selectat pe nimeni
            interactions.add(mafiosoInteraction);
        else {
            interactions.add(new AttackInteraction(mafioso, godfatherInteraction.targets, 5));
            mafioso.AddNightResult("The Godfather ordered you to kill " + godfatherInteraction.targets.get(0).getPlayerUsername() + "!");
            godFather.AddNightResult("The mafioso attacked the target you selected.");
        }
    }

    private void computeDoctorMessage() {
        List<Character> healedCharacters = interactions.stream().map(i -> i.actioner)
                .filter(c -> c.healed).toList();
        for (Character character : healedCharacters) {
            if (interactions.stream().anyMatch(i -> i instanceof AttackInteraction || i instanceof PassiveInteraction)) {
                List<Interaction> healingInteractions = interactions.stream()
                        .filter(i -> i.targets.get(0).getPlayerUsername().equals(character.getPlayerUsername()))
                        .toList();
                for (Interaction interaction : healingInteractions)
                    interaction.actioner.AddNightResult("Your target was attacked last Night!");
            }
        }
    }

    private void computeBodyguardInteraction() {
        var bodyguardInteractions = interactions.stream()
                .filter(i -> i.actioner instanceof Bodyguard && i instanceof VisitingInteraction).toList();

        for (var bodyguardInteraction : bodyguardInteractions) {
            //caut targeturile
            var targets = bodyguardInteraction.targets;
            //pt fiecare target, vad daca are vizitatori care l-au atacat
            for (var target : targets) {
                var attackers = interactions.stream()
                        .filter(interaction -> interaction instanceof AttackInteraction && interaction.targets.get(0) == target)
                        .map(Interaction::getActioner)
                        .toList();
                //omor primul atacator din lista (doar daca nu a fost healed),
                //deci fac un attack interaction care sa fie verificat dupa ce a dat medicul heal
                interactions.add(new AttackInteraction(bodyguardInteraction.actioner, new ArrayList<>() {{
                    add(attackers.get(0));
                }}, 5));
            }
        }

    }

    private void computeSpyMessage() {
        //vad daca exista vreun spy si care e targetul lui
        var spyInteractions = interactions.stream().filter(i -> i.actioner instanceof Spy).toList();
        for (var spyInteraction : spyInteractions) {
            //get spy
            var spy = spyInteraction.actioner;
            //get target
            var target = spyInteraction.targets.get(0);
            //see if they were roleblocked
            if (target.roleBlocked)
                spy.AddNightResult("Someone occupied your target's night. They were role blocked!");
            //see if they were attacked
            var attackers = interactions.stream().filter(i -> i.targets.get(0) == target && i instanceof AttackInteraction).map(Interaction::getActioner).toList();
            //mafia
            if (attackers.stream().filter(a -> a instanceof MafiaCharacter).toList().size() > 0)
                spy.AddNightResult("Your target was attacked by a member of the Mafia!");
            //sk
            if (attackers.stream().filter(a -> a instanceof SerialKiller).toList().size() > 0)
                spy.AddNightResult("Your target was attacked by a Serial Killer!");

            //bodyguard
            if (attackers.stream().filter(a -> a instanceof SerialKiller).toList().size() > 0)
                spy.AddNightResult("Your target was attacked by a Bodyguard!");

            //burned
            if (attackers.stream().filter(a -> a instanceof Arsonist).toList().size() > 0)
                spy.AddNightResult("Your target was set on fire by an arsonist!");

            //visiting actions

            //blackmailed
            var visitors = interactions.stream().filter(i -> i.targets.get(0) == target && i instanceof VisitingInteraction).map(Interaction::getActioner).toList();

            //doctor
            if (visitors.stream().filter(a -> a instanceof Doctor).toList().size() > 0)
                spy.AddNightResult("Your target was healed by a doctor");

            //framer
            if (visitors.stream().filter(a -> a instanceof Framer).toList().size() > 0)
                spy.AddNightResult("Your target was framed!");

            //blackmailer
            if (visitors.stream().filter(a -> a instanceof Blackmailer).toList().size() > 0)
                spy.AddNightResult("Your target was blackmailed!");

        }
    }

    void computeLookoutMessage() {
        //fac sa vada pur si simplu toti oamenii care viziteaza pe cnv seara
        //mi-e neclar daca vede usernameul sau rolul, o sa pun usernameul
        var lookoutInteractions = interactions.stream().filter(i -> i.actioner instanceof Lookout).toList();

        //iau pe rand
        for (var lookoutInteraction : lookoutInteractions) {
            var lookout = lookoutInteraction.actioner;
            //iau targetul
            var target = lookoutInteraction.targets.get(0);
            //vad daca mai mult de 3 oameni viziteaza
            var visitors = target.visitors;

            if (visitors.size() == 0) {
                lookout.AddNightResult("Your target was not visited by anyone.");
                continue;
            }
            if (visitors.size() > 3) {
                lookout.AddNightResult("Your target was visited by more than 3 people last night! You could only identify 3");
            }
            for (var visitor : visitors) {
                lookout.AddNightResult("Your target was visited by " + visitor.getPlayerUsername() + " last night");
            }
        }
    }

    private void computeWerewolfInteraction() {
        //iau interactiunea
        var werewolfInteraction = interactions.stream()
                .filter(i -> i.actioner instanceof Werewolf).toList().get(0);

        //iau targetul
        var target = werewolfInteraction.targets.get(0);
        //iau werewolful
        var werewolf = werewolfInteraction.actioner;

        //vad cine a mai vizitat si adaug attack interaction: mai bine le adaug separat
        //  interactions.add(new AttackInteraction(werewolf, target.visitors, 5));
        target.visitors.forEach(visitor -> interactions.add(new AttackInteraction(werewolf, List.of(visitor), 5)));

        //adaug in lista de targets
        werewolf.targets.addAll(target.visitors);


    }

}