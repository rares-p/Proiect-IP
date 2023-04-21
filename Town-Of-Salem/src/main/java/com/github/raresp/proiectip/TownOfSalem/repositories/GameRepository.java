package com.github.raresp.proiectip.TownOfSalem.repositories;

import com.github.raresp.proiectip.TownOfSalem.API.projections.IndividualGame;
import com.github.raresp.proiectip.TownOfSalem.API.projections.PublicGame;
import com.github.raresp.proiectip.TownOfSalem.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface GameRepository extends JpaRepository<Game, Long> {
    PublicGame findPublicGameById(Long id);
}
