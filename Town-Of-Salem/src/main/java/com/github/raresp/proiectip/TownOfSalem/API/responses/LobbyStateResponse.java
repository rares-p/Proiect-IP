package com.github.raresp.proiectip.TownOfSalem.API.responses;

import com.github.raresp.proiectip.TownOfSalem.models.Lobby;

import java.util.ArrayList;
import java.util.List;

public class LobbyStateResponse {
    public String state;
    public List<String> users;
    public String joinCode;

    public LobbyStateResponse(Lobby lobby) {
        if(lobby.getGame() == null)
            this.state = "Lobby";
        else
            this.state = lobby.getGame().getGameState().toString();
        this.users = new ArrayList<>(lobby.getWaitingToJoin());
        this.joinCode = lobby.getJoinCode();
    }
}
