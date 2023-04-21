package com.github.raresp.proiectip.TownOfSalem.models.characters;

import jakarta.persistence.*;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class Character implements Comparable<Character> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    protected boolean isAlive;
    protected boolean roleBlocked;
    protected boolean innocent;
    protected boolean framed;
    public boolean healed;
    protected String playerUsername;
    @OneToMany
    protected List<Character> targets = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected Interaction lastInteraction;
    protected DefenseTypes defense;
    protected AttackTypes attack;
    protected ImmunityTypes immunity;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "night_results")
    private List<NightResult> nightResults;

    public Character(String playerUsername) {
        this.playerUsername = playerUsername;
        this.framed = false;
        this.roleBlocked = false;
        this.isAlive = true;
        this.healed = false;
    }

    protected Character() {}

    public void AddNightResult(String ...messages) {
        for (String message : messages)
            nightResults.add(new NightResult(message));
    }
    public void ResetNightResults() {
        nightResults.clear();
    }
    public abstract void resetDefense();
    public abstract void act (List<Character> listOfTargets);
    public abstract void act ();

    public boolean IsInnocent() {
        return innocent;
    }

    public void ResetEffects() {
        framed = false;
        roleBlocked = false;
        lastInteraction = null;
        healed = false;
    }

    public DefenseTypes getDefense() {
        return defense;
    }

    public AttackTypes getAttack() {
        return attack;
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public Interaction getLastInteraction() {
        return lastInteraction;
    }

    public Long getId() {
        return id;
    }

    public List<NightResult> getNightResults() {
        return nightResults;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setAttack(AttackTypes attack) {
        this.attack = attack;
    }

    public void setDefense(DefenseTypes defense) {
        this.defense = defense;
    }

    public void setFramed(boolean framed) {
        this.framed = framed;
    }

    public void setImmunity(ImmunityTypes immunity) {
        this.immunity = immunity;
    }

    public void setInnocent(boolean innocent) {
        this.innocent = innocent;
    }

    public void setLastInteraction(Interaction lastInteraction) {
        this.lastInteraction = lastInteraction;
    }

    public void setPlayerUsername(String playerUsername) {
        this.playerUsername = playerUsername;
    }

    public void setNightResults(ArrayList<NightResult> nightResults) {
        this.nightResults = nightResults;
    }

    public void setRoleBlocked(boolean roleBlocked) {
        this.roleBlocked = roleBlocked;
    }

    public String getRole() {
        return this.getClass().getSimpleName();
    }

    public void resetStats()
    {
        this.roleBlocked = false;
        this.healed = false;
        this.nightResults.clear();
    }

    public void AddTarget(Character c){
        this.targets.add(c);
    }
}
