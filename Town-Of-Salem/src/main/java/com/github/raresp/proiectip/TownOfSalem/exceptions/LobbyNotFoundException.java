package com.github.raresp.proiectip.TownOfSalem.exceptions;

public class LobbyNotFoundException extends Exception {
    public LobbyNotFoundException(String id){
        super("Could not find lobby " + id);
    }
}
