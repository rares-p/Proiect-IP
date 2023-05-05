package com.github.raresp.proiectip.TownOfSalem.models;

import com.github.raresp.proiectip.TownOfSalem.exceptions.CharacterNotFoundException;
import com.github.raresp.proiectip.TownOfSalem.exceptions.InvalidCharacterException;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacter;
import com.github.raresp.proiectip.TownOfSalem.models.characters.SelectionSession;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.Jailor;
import com.github.raresp.proiectip.TownOfSalem.utils.GameManager;
import jakarta.persistence.*;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.Sheriff;

import java.util.*;

@Entity
public class Game {
    private UUID lobbyId;
    public final int discussionTime = 30;
    public final int selectionTime = 30;
    public final int votingTime = 15;
    public final int nightTime = 15;
    public final int dayEndingTime = 5;
    public final int nightEndingTime = 5;

    public Character werewolfRampage;
    @Temporal(TemporalType.TIMESTAMP)
    public Instant timeOfCurrentState;
    @ManyToMany
    public Map<Character, Character> selections = new HashMap<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "game_characters")
    private List<Character> characters;

    public GameState gameState;

    @OneToOne
    public Character selectedCharacter;
    @ElementCollection
    public List<String> votingLog = new ArrayList<>();

    public int nightCounter = 0;

    public GameState getGameState() {
        return gameState;
    }
    //@Transactional
    public void setGameState(GameState gameState){
        this.gameState = gameState;
    }

    protected Game() {
        //this.lobbyId = lobbyId;
    }

    public Game(List<Character> characters, UUID lobbyID) {
        this.characters = characters;
        this.timeOfCurrentState = Instant.now().plusSeconds(discussionTime);
        this.gameState = GameState.Discussion;
        this.lobbyId = lobbyID;
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

    public Instant getCurrentUtcTime(int secondsDelay)
    {
        return Instant.now().plusSeconds(secondsDelay);
    }


    public Instant getTimeOfCurrentState() {
        return timeOfCurrentState;
    }

    public void setTimeOfCurrentState(Instant timeOfCurrentState) {
        this.timeOfCurrentState = timeOfCurrentState;
    }

    @Transient
    public Instant getTimeOfState() {
        if (gameState == GameState.Discussion)
            return Instant.now().plusSeconds(discussionTime);
        if (gameState == GameState.Selection)
            return Instant.now().plusSeconds(selectionTime);
        if (gameState == GameState.Voting)
            return Instant.now().plusSeconds(votingTime);
        if (gameState == GameState.Night)
            return Instant.now().plusSeconds(nightTime);
        if (gameState == GameState.DayEnding)
            return Instant.now().plusSeconds(dayEndingTime);
        if (gameState == GameState.NightEnding)
            return Instant.now().plusSeconds(nightEndingTime);
        return null;
    }

    public boolean isOver(){
        return false;
    }

    public List<Character> getDeadPlayers() {
        return characters.stream()
                .filter(c -> !c.isAlive())
                .toList();
    }

    public List<? extends Character> getPeers(Character character) {
        if(this.gameState == GameState.Discussion)
            return characters.stream()
                    .filter(c -> !c.equals(character))
                    .toList();
        if(character.isJailed())
            return characters.stream()
                    .filter(c -> c instanceof Jailor)
                    .toList();
        if(character instanceof MafiaCharacter)
            return getMafiaCharacters();
        return new ArrayList<>();
    }

//    @Override
//    public void run() {
//        StartGame();
//    }
}
