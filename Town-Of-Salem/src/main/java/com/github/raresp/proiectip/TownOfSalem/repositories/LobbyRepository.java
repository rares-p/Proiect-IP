package com.github.raresp.proiectip.TownOfSalem.repositories;

import com.github.raresp.proiectip.TownOfSalem.API.projections.PublicLobby;
import com.github.raresp.proiectip.TownOfSalem.API.projections.PublicLobbyListProjection;
import com.github.raresp.proiectip.TownOfSalem.models.Lobby;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LobbyRepository extends JpaRepository<Lobby, UUID> {
    PublicLobby findPublicLobbyById(UUID id);
    List<PublicLobbyListProjection> findBy();
}
