package com.github.raresp.proiectip.TownOfSalem.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.raresp.proiectip.TownOfSalem.exceptions.CharacterNotFoundException;
import com.github.raresp.proiectip.TownOfSalem.exceptions.InvalidCharacterException;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacter;
import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.*;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacter;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.Jailor;
import jakarta.persistence.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import java.util.*;

@Entity
public class Game {
    private UUID lobbyId;
    public String lobbyJoinCode;
    public final int discussionTime = 30;
    public final int selectionTime = 30;
    public final int votingTime = 15;
    public final int nightTime = 30;
    public final int dayEndingTime = 5;
    public final int nightEndingTime = 5;
    public final int endTime = 60;
    public int daysSinceLastDeath = 0;
    public int aliveLastNight = 0;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "game_winners")
    public List<Character> winners = new ArrayList<>();
    @Temporal(TemporalType.TIMESTAMP)
    public Instant timeOfCurrentState;

    @Temporal(TemporalType.TIMESTAMP)
    public Instant remainingTimeOfSelection;
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

    public Game(List<Character> characters, UUID lobbyID, String lobbyJoinCode) {
        this.characters = characters;
        this.timeOfCurrentState = Instant.now().plusSeconds(discussionTime);
        this.gameState = GameState.Discussion;
        this.lobbyId = lobbyID;
        this.lobbyJoinCode = lobbyJoinCode;
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

    public List<Character> getTownCharacters() {
        return characters.stream()
                .filter(c -> c instanceof TownCharacter)
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
        if (gameState == GameState.End)
            return Instant.now().plusSeconds(endTime);
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

    public List<Character> getAlivePlayers() {
        return characters.stream()
                .filter(c -> c.isAlive())
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
                        .uri(new URI("https://townofsalem-backend.tedyst.ro/lobbies/" + lobbyJoinCode +"/announce"))
                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .build();
                HttpClient client = HttpClient.newHttpClient();
                client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                        .thenApply(HttpResponse::body);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public boolean isFullMoonNight(){
        if(nightCounter == 1 || nightCounter == 3)
            return false;
        return true;
    }

    public void computeNightBeginningAnnouncements() {
        for(Character c : characters) {
            List<String> peers = new ArrayList<>(){{
                add(c.getPlayerUsername());
            }};

            Map<String, Object> params = new HashMap<>();
            params.put("peers", peers);
            params.put("content", c.nightBeginningMessage());

            String requestBody = null;
            try {
                requestBody = new ObjectMapper().writeValueAsString(params);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("https://townofsalem-backend.tedyst.ro/lobbies/" + lobbyJoinCode +"/announce"))
                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .build();
                HttpClient client = HttpClient.newHttpClient();
                client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                        .thenApply(HttpResponse::body);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean gameEnded() {
        System.out.println(characters);
        if (getAlivePlayers().stream().allMatch(c -> (c instanceof Jester || c instanceof Executioner || c instanceof Survivor))) {
            winners.addAll(getAlivePlayers().stream().filter(c -> c instanceof Survivor).toList());
            return true;
        }
        if (getAlivePlayers().stream().filter(c -> !(c instanceof Jester || c instanceof Executioner || c instanceof Survivor)).allMatch(c -> c instanceof TownCharacter)) {
            winners.addAll(getTownCharacters());
            winners.addAll(getAlivePlayers().stream().filter(c -> c instanceof Survivor).toList());
            return true;
        }
        if (getAlivePlayers().stream().filter(c -> !(c instanceof Jester || c instanceof Executioner || c instanceof Survivor)).allMatch(c -> c instanceof MafiaCharacter)) {
            winners.addAll(getMafiaCharacters());
            winners.addAll(getAlivePlayers().stream().filter(c -> c instanceof Survivor).toList());
            return true;
        }
        if (getAlivePlayers().stream().filter(c -> !(c instanceof Jester || c instanceof Executioner || c instanceof Survivor)).allMatch(c -> c instanceof Arsonist)) {
            winners.addAll(getCharacters().stream().filter(c -> c instanceof Arsonist).toList());
            winners.addAll(getAlivePlayers().stream().filter(c -> c instanceof Survivor).toList());
            return true;
        }
        if (getAlivePlayers().stream().filter(c -> !(c instanceof Jester || c instanceof Executioner || c instanceof Survivor)).allMatch(c -> c instanceof Werewolf)) {
            winners.addAll(getCharacters().stream().filter(c -> c instanceof Werewolf).toList());
            winners.addAll(getAlivePlayers().stream().filter(c -> c instanceof Survivor).toList());
            return true;
        }
        if (getAlivePlayers().stream().filter(c -> !(c instanceof Jester || c instanceof Executioner || c instanceof Survivor)).allMatch(c -> c instanceof SerialKiller)) {
            winners.addAll(getCharacters().stream().filter(c -> c instanceof SerialKiller).toList());
            winners.addAll(getAlivePlayers().stream().filter(c -> c instanceof Survivor).toList());
            return true;
        }
        if (getAlivePlayers().size() == 0)
            return true;
        return false;
    }

//    @Override
//    public void run() {
//        StartGame();
//    }
}
