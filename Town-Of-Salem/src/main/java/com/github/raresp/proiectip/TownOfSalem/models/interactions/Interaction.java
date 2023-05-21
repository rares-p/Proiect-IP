package com.github.raresp.proiectip.TownOfSalem.models.interactions;

import com.github.raresp.proiectip.TownOfSalem.models.TurnInteractions;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import jakarta.persistence.*;

import java.util.List;

@Entity
public abstract class Interaction implements Comparable<Interaction>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    public Character actioner;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "interaction_target")
    public List<Character> targets;
    @Transient
    private TurnInteractions turnInteractions;
    protected int priority;
    public TurnInteractions getTurnInteractions() {
        return turnInteractions;
    }
    protected Interaction() {
    }
    public abstract boolean isValid();
    public abstract void act();

    public Character getActioner(){ return actioner;}
    public List<Character> getTargets(){ return targets;}

    public int getPriority() {
        return priority;
    }
    @Override
    public int compareTo(Interaction other) {
        return Integer.compare(this.priority, other.priority);
    }
}
