package com.github.raresp.proiectip.TownOfSalem.models.characters;

import jakarta.persistence.*;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Character implements Comparable<Character> {

    protected Roles role;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    public boolean isAlive = true;
    public boolean roleBlocked = false;
    protected boolean innocent;
    public boolean framed = false;
    public boolean healed = false;
    public String playerUsername;
    @OneToMany
    protected List<Character> targets = new ArrayList<>();

    @OneToOne
    public Interaction lastInteraction;
    public DefenseTypes defense;
    public AttackTypes attack;
    public ImmunityTypes immunity;

    @OneToMany
    @JoinColumn(name = "night_results")
    private ArrayList<NightResult> nightResults;

    public Character(String playerUsername) {
        this.playerUsername = playerUsername;
    }

    public Character() {

    }
    public Roles getRole() {
        return role;
    }
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

    @Override
    public int compareTo(Character other) {
        return role.compareTo(other.getRole());
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
