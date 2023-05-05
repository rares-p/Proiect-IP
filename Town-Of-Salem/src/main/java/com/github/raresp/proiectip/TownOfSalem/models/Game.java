package com.github.raresp.proiectip.TownOfSalem.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.raresp.proiectip.TownOfSalem.exceptions.CharacterNotFoundException;
import com.github.raresp.proiectip.TownOfSalem.exceptions.InvalidCharacterException;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacter;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.Jailor;
import jakarta.persistence.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
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

    public void StartGame()
    {
        System.out.println("A INCEPUT SESIUNEA DE JOC");
        while(true)
        {
            System.out.println("A INCEPUT ");
            this.gameState = GameState.Discussion;
            setGameState(GameState.Discussion);
            timeOfCurrentState = getCurrentUtcTime(discussionTime);
            while(Instant.now().compareTo(timeOfCurrentState) < 0);   //wait for discussion to finish

            System.out.println("E selection");
            characters.get(0).setRoleBlocked(true);
            gameState = GameState.Selection;
//            gameManager.setGameState(this, GameState.Selection);
            setGameState(GameState.Selection);
            timeOfCurrentState = getCurrentUtcTime(selectionTime);
            while(Instant.now().compareTo(timeOfCurrentState) < 0) {   //wait for selection to finish
                //perform voting checks and instantiate voting session
                if(selections.values().stream().filter(v -> v.equals(new Sheriff("test"))).count() > 2) {    //voting session
                    //VotingSession votingSession = new VotingSession(new Sheriff("test"), characters);

                    gameState = GameState.Voting;
                    timeOfCurrentState = getCurrentUtcTime(nightTime);
                    long selectionRemainingTime = timeOfCurrentState.toEpochMilli() - Instant.now().toEpochMilli();
                    while(Instant.now().compareTo(timeOfCurrentState) < 0);   //wait for voting to finish
                    //Object o = votingSession.calculateOutcome();    //get voting result
                    timeOfCurrentState = getCurrentUtcTime(selectionTime * 1000);
                }
            }

            gameState = GameState.Night;
            setGameState(GameState.Night);
            timeOfCurrentState = getCurrentUtcTime(nightTime);
            while(Instant.now().compareTo(timeOfCurrentState) < 0) {   //wait for night to finish
                //get targets
            }

            TurnInteractions turnInteractions = new TurnInteractions(characters);
            turnInteractions.computeInteractionsOutcome();
            for(Character c : characters)
                c.resetStats();
            //evaluate night actions
        }
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

    public void sendVotingResults(ArrayList<String> votes) {
        List<String> peers = new ArrayList<>();
        for(Character c : characters)
            peers.add(c.getPlayerUsername());

        Map<String, Object> params = new HashMap<>();
        params.put("peers", peers);

        for (String vote : votes) {
            params.put("content", vote);

            String requestBody = null;
            try {
                requestBody = new ObjectMapper().writeValueAsString(params);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("/lobbies/:lobbyId/announce"))
                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .build();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

        }
    }

//    @Override
//    public void run() {
//        StartGame();
//    }
}
