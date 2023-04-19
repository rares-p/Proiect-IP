package com.github.raresp.proiectip.TownOfSalem.models.characters;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public boolean isAlive = true;
    public boolean roleBlocked = false;
    protected boolean innocent;
    public boolean framed = false;
    public String playerUsername;
    @OneToOne
    public Interaction lastInteraction;
    public DefenseTypes defense;
    public AttackTypes attack;

    @OneToMany
    @JoinColumn(name = "night_results")
    private ArrayList<NightResult> nightResults;

    public Character(String playerUsername) {
        this.playerUsername = playerUsername;
    }

    public Character() {

    }

    public void AddNightResult(String ...messages) {
        for (String message : messages)
            nightResults.add(new NightResult(message));
    }
    public void ResetNightResults() {
        nightResults.clear();
    }

    public boolean IsInnocent() {
        return innocent;
    }

    public void ResetEffects() {
        framed = false;
        roleBlocked = false;
        lastInteraction = null;
    }


}
