package com.github.raresp.proiectip.TownOfSalem.API.responses;

import com.github.raresp.proiectip.TownOfSalem.API.projections.PublicLobbyListProjection;
import com.github.raresp.proiectip.TownOfSalem.models.Lobby;

import java.util.ArrayList;
import java.util.List;

public class AllLobbiesResponse {
    public List<LobbyResponse> lobbies;
    public AllLobbiesResponse(List<PublicLobbyListProjection> lobbies) {
        this.lobbies = new ArrayList<>();
        for(PublicLobbyListProjection lobby : lobbies) {
            this.lobbies.add(new LobbyResponse(lobby));
        }
    }
    public class LobbyResponse {
        public String joinCode;
        public int noUsers;
        LobbyResponse(PublicLobbyListProjection lobby) {
            this.joinCode = lobby.getJoinCode();
            this.noUsers = lobby.getWaitingToJoin().size();
        }
    }
}
