package com.github.raresp.proiectip.TownOfSalem.API.projections;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface IndividualGame {
    List<PublicCharacter> getCharacters();
    Long getId();

    @Value("#{null}")
    Character getCharacter();

//    PublicCharacter getMafiaCharacter();

}
