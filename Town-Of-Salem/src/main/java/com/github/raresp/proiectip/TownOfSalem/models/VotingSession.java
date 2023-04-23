package com.github.raresp.proiectip.TownOfSalem.models;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import org.springframework.stereotype.Service;

public class VotingSession {
    Character judgedCharacter;
    Map<Character, VoteType> playerVotes = new HashMap<>();
    ArrayList<String> votingLog = new ArrayList<>();
    List<Character> players = new ArrayList<>();
    boolean isOver = false;

    public VotingSession(Character judgedCharacter, List<Character> characters) {
        this.judgedCharacter = judgedCharacter;
        this.players = characters;
        for(Character c : players)
            if(c.targets.size()>0)
                if(c.targets.get(0).equals(judgedCharacter))
                    playerVotes.put(c, VoteType.Guilty);
                else playerVotes.put(c, VoteType.Innocent);
    }

    public void addVote(Character character, VoteType vote) {
        if (playerVotes.get(character) == vote)
            playerVotes.remove(character);
        else
            playerVotes.put(character, vote);
    }

    public boolean calculateOutcome(){
        isOver = true;
        int guiltyVotes = 0;
        int innocentVotes = 0;

        for (Character player : players) {
            //System.out.println(player.getPlayerUsername() + " " + player.lastVote);
            if (playerVotes.get(player) == VoteType.Innocent) {
                innocentVotes++;
                votingLog.add(player.getPlayerUsername() + " has voted " + playerVotes.get(player) + ".");
            }
            else if (playerVotes.get(player) == VoteType.Guilty) {
                guiltyVotes++;
                votingLog.add(player.getPlayerUsername() + " has voted " + playerVotes.get(player) + ".");
            }
            else
                votingLog.add(player.getPlayerUsername() + " has abstained.");
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