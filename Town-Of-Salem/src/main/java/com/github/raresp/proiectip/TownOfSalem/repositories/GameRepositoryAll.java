package com.github.raresp.proiectip.TownOfSalem.repositories;

import com.github.raresp.proiectip.TownOfSalem.API.projections.PublicGame;
import com.github.raresp.proiectip.TownOfSalem.models.Game;
import com.github.raresp.proiectip.TownOfSalem.models.GameState;
import org.hibernate.annotations.JdbcType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.JDBCType;

import static java.sql.JDBCType.INTEGER;

@Repository
public interface GameRepositoryAll extends JpaRepository<Game, Long> {
    Game findPublicGameById(Long id);
    //void setGameState(GameState gameState);
}
