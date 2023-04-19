package com.github.raresp.proiectip.TownOfSalem.models;

import com.github.raresp.proiectip.TownOfSalem.exceptions.InvalidCharacterException;
import com.github.raresp.proiectip.TownOfSalem.exceptions.InvalidLobbyException;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.utils.GameUtils;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Lobby {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    private Game game;

    @ElementCollection
    private List<String> waitingToJoin;
    private LobbyState state;
    public static int MINIMUM_PLAYERS = 3;

    public Lobby() {
        this.waitingToJoin = new ArrayList<>();
        this.game = null;
    }

    public void addPlayerInLobby(String player) throws InvalidLobbyException {
        if(state != LobbyState.WAITING_PLAYERS)
            throw new InvalidLobbyException();
        if(waitingToJoin.contains(player))
            throw new InvalidLobbyException();
        waitingToJoin.add(player);
    }

    public void removePlayerFromLobby(String player) throws InvalidCharacterException, InvalidLobbyException {
        if(!waitingToJoin.contains(player))
            throw new InvalidLobbyException();
        if(state == LobbyState.STARTED)
            this.game.removeCharacter(this.game.getCharacterByName(player));
        waitingToJoin.remove(player);
    }

    public List<String> getWaitingToJoin() {
        return waitingToJoin;
    }

    public void startGame() throws InvalidLobbyException {
        if(waitingToJoin.size() < MINIMUM_PLAYERS)
            throw new InvalidLobbyException();
        List<Character> characters = GameUtils.generateCharacters(waitingToJoin);
        this.game = new Game(characters);
    }

    public Game getGame() {
        return game;
    }

    public static int getMinimumPlayers() {
        return MINIMUM_PLAYERS;
    }

    public LobbyState getState() {
        return state;
    }

    public UUID getId() {
        return id;
    }
}
