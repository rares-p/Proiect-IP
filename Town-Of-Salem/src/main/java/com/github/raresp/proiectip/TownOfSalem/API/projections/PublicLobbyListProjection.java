package com.github.raresp.proiectip.TownOfSalem.API.projections;

import com.github.raresp.proiectip.TownOfSalem.models.LobbyState;

import java.util.List;
import java.util.UUID;

public interface PublicLobbyListProjection {
    UUID getId();
    public List<String> getWaitingToJoin();
    public LobbyState getState();
    String getJoinCode();
}
