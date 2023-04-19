package com.github.raresp.proiectip.TownOfSalem.models.characters;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class NightResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String string;

    protected NightResult() {}

    protected NightResult(String string){
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
