package com.github.raresp.proiectip.TownOfSalem.models;

import com.github.raresp.proiectip.TownOfSalem.exceptions.CharacterNotFoundException;
import com.github.raresp.proiectip.TownOfSalem.exceptions.InvalidCharacterException;
import com.github.raresp.proiectip.TownOfSalem.exceptions.InvalidLobbyException;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.utils.GameRunner;
import com.github.raresp.proiectip.TownOfSalem.utils.GameUtils;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Entity
public class Lobby {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    private Game game;

    @ElementCollection
    private List<String> waitingToJoin;
    private LobbyState state;
    private String joinCode;
    public static int MINIMUM_PLAYERS = 3;

    public Lobby() {
        Random r = new Random();
        this.waitingToJoin = new ArrayList<>();
        this.game = null;
        this.state = LobbyState.WAITING_PLAYERS;
        this.joinCode = String.format("%06d", r.nextInt(1000000));
    }

    public void addPlayerInLobby(String player) throws InvalidLobbyException {
        if(state != LobbyState.WAITING_PLAYERS)
            throw new InvalidLobbyException("The Lobby is not in waiting state");
        if(waitingToJoin.contains(player))
            throw new InvalidLobbyException("The player already exists in the lobby");
        waitingToJoin.add(player);
    }

    public void removePlayerFromLobby(String player) throws InvalidCharacterException, InvalidLobbyException, CharacterNotFoundException {
        if(!waitingToJoin.contains(player))
            throw new InvalidLobbyException("The player does not exist in this Lobby");
        if(state == LobbyState.STARTED)
            this.game.removeCharacter(this.game.getCharacterByName(player));
        waitingToJoin.remove(player);
    }

    public List<String> getWaitingToJoin() {
        return waitingToJoin;
    }

    public void createGame() throws InvalidLobbyException {
        List<Character> characters = GameUtils.generateCharacters(waitingToJoin);
        this.game = new Game(characters, id, joinCode);
        this.state = LobbyState.STARTED;
    }

    public void startGame() throws InvalidLobbyException {
        // if(waitingToJoin.size() < MINIMUM_PLAYERS)
        //     throw new InvalidLobbyException("Not enough players to start the game");
        // if(state != LobbyState.WAITING_PLAYERS)
        //     throw new InvalidLobbyException("The Lobby is not in waiting state");
        List<Character> characters = GameUtils.generateCharacters(waitingToJoin);
        this.game = new Game(characters, id, joinCode);
        this.game.getId();
        this.state = LobbyState.STARTED;
    }

    public Game getGame() {
        return game;
    }

    public int getMinimumPlayers() {
        return MINIMUM_PLAYERS;
    }

    public LobbyState getState() {
        return state;
    }

    public UUID getId() {
        return id;
    }

    public void setJoinCode(String joinCode) {
        this.joinCode = joinCode;
    }

    public String getJoinCode() {
        return joinCode;
    }
}
