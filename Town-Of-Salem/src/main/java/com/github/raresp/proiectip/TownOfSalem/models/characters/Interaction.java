package com.github.raresp.proiectip.TownOfSalem.models.characters;

import com.github.raresp.proiectip.TownOfSalem.models.TurnInteractions;
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
    private int priority;
    public TurnInteractions getTurnInteractions() {
        return turnInteractions;
    }
    protected Interaction() {
    }

    public Interaction(Character actioner, List<Character> targets, int priority) {
        this.actioner = actioner;
        this.targets = targets;
        this.priority = priority;
    }

    @Override
    public int compareTo(Interaction other) {
        return Integer.compare(this.priority, other.priority);
    }
    
    public abstract boolean isValid();


    public int getPriority() {
        return priority;
    }
}
