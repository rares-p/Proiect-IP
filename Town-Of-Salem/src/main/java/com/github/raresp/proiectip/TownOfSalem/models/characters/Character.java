package com.github.raresp.proiectip.TownOfSalem.models.characters;

import com.github.raresp.proiectip.TownOfSalem.models.VoteType;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

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
    public boolean roleBlocked;
    protected boolean innocent;
    protected boolean framed;
    public boolean healed;
    protected String playerUsername;
    protected String actionText;

    public String getActionText() {
        return actionText;
    }

    public Integer numberOfSelection = 1;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "targets")
    public List<Character> targets = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected Interaction lastInteraction;
    protected DefenseTypes defense;
    protected AttackTypes attack;
    protected ImmunityTypes immunity;
    protected boolean isJailed;

    //@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@JoinColumn(name = "night_results")
    @ElementCollection(fetch = FetchType.EAGER)
    //@JoinColumn(name = "night_results")
    public List<String> nightResults = new ArrayList<>();

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
            nightResults.add(message);
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

    /*public List<NightResult> getNightResults() {
        return nightResults;
    }*/

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

    /*public void setNightResults(ArrayList<NightResult> nightResults) {
        this.nightResults = nightResults;
    }*/

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

    @Transactional
    public void AddTarget(Character c){
        this.targets.add(c);
    }

    @Override
    public int compareTo(Character o) {
        return this.getRole().compareTo(o.getRole());
    }

    public Integer getNumberOfSelection() {
        return numberOfSelection;
    }

    public void setNumberOfSelection(Integer numberOfSelection) {
        this.numberOfSelection = numberOfSelection;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public boolean isJailed() {
        return isJailed;
    }

    public void setIsJailed(boolean isJailed) {
        this.isJailed = isJailed;
    }
}
