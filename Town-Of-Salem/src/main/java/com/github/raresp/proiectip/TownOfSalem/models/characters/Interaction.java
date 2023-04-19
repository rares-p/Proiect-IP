package com.github.raresp.proiectip.TownOfSalem.models.characters;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Interaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    public Character actioner;
    @OneToMany
    @JoinColumn(name = "interaction_target")
    public List<Character> target;

}
