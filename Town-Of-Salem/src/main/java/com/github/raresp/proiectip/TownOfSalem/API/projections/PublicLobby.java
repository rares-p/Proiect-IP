package com.github.raresp.proiectip.TownOfSalem.API.projections;

import com.github.raresp.proiectip.TownOfSalem.models.LobbyState;

import java.util.List;
import java.util.UUID;

public interface PublicLobby {
    UUID getId();
    PublicGame getGame();
    List<String> getWaitingToJoin();
    LobbyState getState();
    String getJoinCode();
}
