package com.github.raresp.proiectip.TownOfSalem.models;

import com.github.raresp.proiectip.TownOfSalem.exceptions.CharacterNotFoundException;
import com.github.raresp.proiectip.TownOfSalem.exceptions.InvalidCharacterException;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacter;
import com.github.raresp.proiectip.TownOfSalem.utils.GameManager;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.Sheriff;

import java.util.*;

@Entity
public class Game {
    public final int discussionTime = 5;
    public final int selectionTime = 5;
    public final int votingTime = 0;
    public final int nightTime = 5;
    @Temporal(TemporalType.TIMESTAMP)
    public LocalDateTime timeOfCurrentState;
    @ManyToMany
    public Map<Character, Character> selections = new HashMap<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "game_characters")
    private List<Character> characters;

    public GameState gameState;
    public GameState getGameState() {
        return gameState;
    }
    //@Transactional
    public void setGameState(GameState gameState){
        this.gameState = gameState;
    }

    protected Game() {}

    public Game(List<Character> characters) {
        this.characters = characters;
        this.timeOfCurrentState = LocalDateTime.now().plusSeconds(discussionTime);
        this.gameState = GameState.Discussion;
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

    public void StartGame()
    {
        System.out.println("A INCEPUT SESIUNEA DE JOC");
        while(true)
        {
            System.out.println("A INCEPUT ");
            this.gameState = GameState.Discussion;
            setGameState(GameState.Discussion);
            timeOfCurrentState = getCurrentUtcTime(discussionTime);
            while(getCurrentUtcTime().compareTo(timeOfCurrentState) < 0);   //wait for discussion to finish

            System.out.println("E selection");
            characters.get(0).setRoleBlocked(true);
            gameState = GameState.Selection;
//            gameManager.setGameState(this, GameState.Selection);
            setGameState(GameState.Selection);
            timeOfCurrentState = getCurrentUtcTime(selectionTime);
            while(getCurrentUtcTime().compareTo(timeOfCurrentState) < 0) {   //wait for selection to finish
                //perform voting checks and instantiate voting session
                if(selections.values().stream().filter(v -> v.equals(new Sheriff("test"))).count() > 2) {    //voting session
                    VotingSession votingSession = new VotingSession(new Sheriff("test"), characters);

                    gameState = GameState.Voting;
                    timeOfCurrentState = getCurrentUtcTime(nightTime);
                    long selectionRemainingTime = timeOfCurrentState.toInstant(ZoneOffset.UTC).toEpochMilli() - getCurrentUtcTime().toInstant(ZoneOffset.UTC).toEpochMilli();
                    while(getCurrentUtcTime().compareTo(timeOfCurrentState) < 0);   //wait for voting to finish
                    Object o = votingSession.calculateOutcome();    //get voting result
                    timeOfCurrentState = getCurrentUtcTime(selectionTime * 1000);
                }
            }

            gameState = GameState.Night;
            setGameState(GameState.Night);
            timeOfCurrentState = getCurrentUtcTime(nightTime);
            while(getCurrentUtcTime().compareTo(timeOfCurrentState) < 0) {   //wait for night to finish
                //get targets
            }

            TurnInteractions turnInteractions = new TurnInteractions(characters);
            turnInteractions.computeInteractionsOutcome();
            for(Character c : characters)
                c.resetStats();
            //evaluate night actions
        }
    }

    public LocalDateTime getCurrentUtcTime()
    {
        return LocalDateTime.now();
    }

    public LocalDateTime getCurrentUtcTime(int secondsDelay)
    {
        return LocalDateTime.now().plusSeconds(secondsDelay);
    }


    public LocalDateTime getTimeOfCurrentState() {
        return timeOfCurrentState;
    }

    public void setTimeOfCurrentState(LocalDateTime timeOfCurrentState) {
        this.timeOfCurrentState = timeOfCurrentState;
    }
    @Transient
    public LocalDateTime getTimeOfState() {
        if (gameState == GameState.Discussion)
            return LocalDateTime.now().plusSeconds(discussionTime);
        if (gameState == GameState.Selection)
            return LocalDateTime.now().plusSeconds(selectionTime);
        if (gameState == GameState.Voting)
            return LocalDateTime.now().plusSeconds(votingTime);
        if (gameState == GameState.Night)
            return LocalDateTime.now().plusSeconds(nightTime);
        return null;
    }

    public boolean isOver(){
        return false;
    }
//    @Override
//    public void run() {
//        StartGame();
//    }
}
