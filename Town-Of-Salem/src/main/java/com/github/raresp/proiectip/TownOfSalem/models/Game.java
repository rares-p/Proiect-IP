package com.github.raresp.proiectip.TownOfSalem.models;

import com.github.raresp.proiectip.TownOfSalem.exceptions.CharacterNotFoundException;
import com.github.raresp.proiectip.TownOfSalem.exceptions.InvalidCharacterException;
import com.github.raresp.proiectip.TownOfSalem.models.characters.Character;
import com.github.raresp.proiectip.TownOfSalem.utils.GameRunner;
import com.github.raresp.proiectip.TownOfSalem.models.characters.MafiaCharacter;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.Sheriff;

import java.util.*;

@Entity
public class Game implements Runnable{
    //@Transient
    //@Autowired
    //private GameService gameService;

    GameRunner gameRunner;
    public final int discussionTime = 30;
    public final int selectionTime = 30;
    public final int votingTime = 15;
    public final int nightTime = 30;
    public Calendar timeOfCurrentState;
    @ManyToMany
    public Map<Character, Character> selections = new HashMap<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "game_characters")
    private List<Character> characters;

    public GameState gameState;
    public GameState getGameState() {
        return gameState;
    }
    //@Transactional
    public void setGameState(GameState gameState){
        //gameService = new GameService();
        this.gameState = gameState;
        //gameService.updateGameState(getId(), gameState);
        //gameRunner = new GameRunner(this);
    }

    protected Game() {}

    public Game(List<Character> characters) {
        this.characters = characters;
    }

    public Character getCharacterByName(String name) throws CharacterNotFoundException {
        for(Character c : characters)
            if(Objects.equals(c.getPlayerUsername(), name))
                return c;
        throw new CharacterNotFoundException();
    }

    public void removeCharacter(Character c) throws InvalidCharacterException {
        if(!characters.contains(c))
            throw new InvalidCharacterException();
        characters.remove(c);
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public Long getId() {
        return id;
    }

    public List<MafiaCharacter> getMafiaCharacters() {
        return characters.stream()
                .filter(c -> c instanceof MafiaCharacter)
                .map(c -> (MafiaCharacter) c)
                .toList();
    }

    public List<MafiaCharacter> getMafiaCharactersIfCharacterIsMafia(Character c) {
        if(c instanceof MafiaCharacter && characters.contains(c))
            return getMafiaCharacters();
        return new ArrayList<>();
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    public void StartGame()
    {
        while(true)
        {
            System.out.println("A INCEPUT ");
            this.gameState = GameState.Discussion;
            setGameState(GameState.Discussion);
            timeOfCurrentState = getCurrentUtcTime(discussionTime);
            while(getCurrentUtcTime().compareTo(timeOfCurrentState) < 0);   //wait for discussion to finish

            System.out.println("E selection");
            gameState = GameState.Selection;
            setGameState(GameState.Selection);
            timeOfCurrentState = getCurrentUtcTime(selectionTime);
            while(getCurrentUtcTime().compareTo(timeOfCurrentState) < 0) {   //wait for selection to finish
                //perform voting checks and instantiate voting session
                if(selections.values().stream().filter(v -> v.equals(new Sheriff("test"))).count() > 2) {    //voting session
                    VotingSession votingSession = new VotingSession(new Sheriff("test"), characters);

                    gameState = GameState.Voting;
                    timeOfCurrentState = getCurrentUtcTime(nightTime);
                    long selectionRemainingTime = timeOfCurrentState.getTimeInMillis() - getCurrentUtcTime().getTimeInMillis();
                    while(getCurrentUtcTime().compareTo(timeOfCurrentState) < 0);   //wait for voting to finish
                    Object o = votingSession.calculateOutcome();    //get voting result
                    timeOfCurrentState = getCurrentUtcTime(selectionTime * 1000);
                }
            }

            gameState = GameState.Night;
            setGameState(GameState.Night);
            timeOfCurrentState = getCurrentUtcTime(nightTime);
            while(getCurrentUtcTime().compareTo(timeOfCurrentState) < 0) {   //wait for night to finish
                //get targets
            }

            TurnInteractions turnInteractions = new TurnInteractions(characters);
            turnInteractions.computeInteractionsOutcome();
            for(Character c : characters)
                c.resetStats();
            //evaluate night actions
        }
    }

    public Calendar getCurrentUtcTime()
    {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return calendar;
    }

    public Calendar getCurrentUtcTime(int secondsDelay)
    {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.add(Calendar.SECOND, secondsDelay);
        return calendar;
    }

    @Override
    public void run() {
        StartGame();
    }
}
