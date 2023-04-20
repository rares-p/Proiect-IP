package com.github.raresp.proiectip.TownOfSalem.models;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;

public class VotingSession {
    Character judgedCharacter;
    HashMap<Character, VoteType> playerVotes = new HashMap<>();
    ArrayList<String> votingLog = new ArrayList<>();
    ArrayList<Character> players = new ArrayList<>();
    boolean isOver = false;

    public VotingSession(Character judgedCharacter, List<Character> players) {
        this.judgedCharacter = judgedCharacter;
        this.players = new ArrayList<>(players);
    }

    public void addVote(Character character, VoteType vote) {
        if (playerVotes.get(character) == vote)
            playerVotes.remove(character);
        else
            playerVotes.put(character, vote);
    }

    public Object calculateOutcome(){
        isOver = true;
        int guiltyVotes = 0;
        int innocentVotes = 0;

        for (Character player : players) {
            if (playerVotes.get(player) == VoteType.Innocent) {
                innocentVotes++;
                votingLog.add(player.playerUsername + " has voted " + playerVotes.get(player) + ".");
            }
            else if (playerVotes.get(player) == VoteType.Guilty) {
                guiltyVotes++;
                votingLog.add(player.playerUsername + " has voted " + playerVotes.get(player) + ".");
            }
            else
                votingLog.add(player.playerUsername + " has abstained.");
        }
        return (guiltyVotes > innocentVotes);
    }

    public boolean isOver() {
        return isOver;
    }

    public ArrayList<String> getVotes() {
        return votingLog;
    }
}
