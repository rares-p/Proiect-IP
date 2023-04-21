package com.github.raresp.proiectip.TownOfSalem.models;

import com.github.raresp.proiectip.TownOfSalem.exceptions.CharacterNotFoundException;
import com.github.raresp.proiectip.TownOfSalem.exceptions.InvalidCharacterException;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacter;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "game_characters")
    private List<Character> characters;

    protected Game() {}

    public Game(List<Character> characters) {
        this.characters = characters;
    }

    public Character getCharacterByName(String name) throws CharacterNotFoundException {
        for(Character c : characters)
            if(Objects.equals(c.getPlayerUsername(), name))
                return c;
        throw new CharacterNotFoundException();
    }

    public void removeCharacter(Character c) throws InvalidCharacterException {
        if(!characters.contains(c))
            throw new InvalidCharacterException();
        characters.remove(c);
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public Long getId() {
        return id;
    }

    public List<MafiaCharacter> getMafiaCharacters() {
        return characters.stream()
                .filter(c -> c instanceof MafiaCharacter)
                .map(c -> (MafiaCharacter) c)
                .toList();
    }

    public List<MafiaCharacter> getMafiaCharactersIfCharacterIsMafia(Character c) {
        if(c instanceof MafiaCharacter && characters.contains(c))
            return getMafiaCharacters();
        return new ArrayList<>();
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }
}
