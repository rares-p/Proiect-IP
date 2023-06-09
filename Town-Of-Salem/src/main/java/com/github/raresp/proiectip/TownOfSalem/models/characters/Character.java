package com.github.raresp.proiectip.TownOfSalem.models.characters;

import com.github.raresp.proiectip.TownOfSalem.models.characters.NeutralCharacters.Arsonist;
import com.github.raresp.proiectip.TownOfSalem.models.characters.TownCharacters.Veteran;
import com.github.raresp.proiectip.TownOfSalem.models.interactions.Interaction;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class Character implements Comparable<Character> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    protected boolean isAlive;
    public boolean roleBlocked;
    protected boolean innocent;
    protected boolean framed;
    public boolean healed;
    protected String playerUsername;
    protected String actionText;

    public Boolean canAct = true;
    public Boolean canSpeak = true;
    @ElementCollection(fetch = FetchType.EAGER)
    public List<String> possibleTargets = new ArrayList<>();

    public String getActionText() {
        return actionText;
    }

    public Integer numberOfSelection = 1;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "targets")
    public List<Character> targets = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected Interaction lastInteraction;
    protected DefenseTypes defense;
    protected AttackTypes attack;
    protected ImmunityTypes immunity;
    protected boolean isJailed;
    private String role;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)//asta sau ElementCollection?
    public List<Character> visitors = new ArrayList<>();

    //@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@JoinColumn(name = "night_results")
    @ElementCollection(fetch = FetchType.EAGER)
    //@JoinColumn(name = "night_results")
    public List<String> nightResults = new ArrayList<>();

    public Character(String playerUsername) {
        this.playerUsername = playerUsername;
        this.framed = false;
        this.roleBlocked = false;
        this.isAlive = true;
        this.healed = false;
        this.role = getRole();
    }

    protected Character() {}

    public void AddNightResult(String ...messages) {
        for (String message : messages)
            nightResults.add(message);
    }
    public void ResetNightResults() {
        nightResults.clear();
    }
    public abstract void resetDefense();

    public void act (List<Character> listOfTargets){};
    public abstract Interaction createInteraction();

    //public void addInteraction();
    public void act () {
        if(this.targets.isEmpty())
        {
            return;
        }

        Character target = this.targets.get(0);
        if (target instanceof Arsonist) {
            ((Arsonist)target).dousedPlayers.add(this);
        }
        else if (target instanceof Veteran) {
            if (((Veteran)target).onAlert) {
                this.setAlive(false);
                target.AddNightResult("You shot someone who visited you last night!");
                this.AddNightResult("You were shot by the Veteran you visited!");
            }
        }


    }

    public String nightBeginningMessage() {
        return "";
    }

    public boolean isFramed() {
        return framed;
    }

    public boolean IsInnocent() {
        return innocent;
    }

    public void ResetEffects() {
        resetDefense();
        framed = false;
        roleBlocked = false;
        lastInteraction = null;
        healed = false;
        visitors.clear();
    }

    public DefenseTypes getDefense() {
        return defense;
    }

    public AttackTypes getAttack() {
        return attack;
    }

    public ImmunityTypes getImmunity() {
        return immunity;
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public Interaction getLastInteraction() {
        return lastInteraction;
    }

    public Long getId() {
        return id;
    }

    /*public List<NightResult> getNightResults() {
        return nightResults;
    }*/

    public void setAlive(boolean alive) {
        isAlive = alive;
        if(alive == false)
            canAct = false;
    }

    public void setAttack(AttackTypes attack) {
        this.attack = attack;
    }

    public void setDefense(DefenseTypes defense) {
        this.defense = defense;
    }

    public void setFramed(boolean framed) {
        this.framed = framed;
    }

    public void setImmunity(ImmunityTypes immunity) {
        this.immunity = immunity;
    }

    public void setInnocent(boolean innocent) {
        this.innocent = innocent;
    }

    public void setLastInteraction(Interaction lastInteraction) {
        this.lastInteraction = lastInteraction;
    }

    public void setPlayerUsername(String playerUsername) {
        this.playerUsername = playerUsername;
    }

    /*public void setNightResults(ArrayList<NightResult> nightResults) {
        this.nightResults = nightResults;
    }*/

    public void setRoleBlocked(boolean roleBlocked) {
        this.roleBlocked = roleBlocked;
    }

    public String getRole() {
        return this.getClass().getSimpleName();
    }

    public void resetStats()
    {
        resetDefense();
        this.roleBlocked = false;
        this.healed = false;
        this.nightResults.clear();
        this.resetDefense();
        this.targets.clear();
    }

    @Transactional
    public void AddTarget(Character c){
        this.targets.add(c);
    }

    @Override
    public int compareTo(Character o) {
        return Integer.valueOf(RolePriority.roles.indexOf(this.getRole())).compareTo(Integer.valueOf(RolePriority.roles.indexOf(o.getRole())));
    }

    public Integer getNumberOfSelection() {
        return numberOfSelection;
    }

    public void setNumberOfSelection(Integer numberOfSelection) {
        this.numberOfSelection = numberOfSelection;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public boolean isJailed() {
        return isJailed;
    }

    public void setIsJailed(boolean isJailed) {
        this.isJailed = isJailed;
    }

    public void checkIfCanAct() {

    }

    public void setPossibleTargets(List<Character> characters) {
        this.possibleTargets.clear();
        if(canAct)
            for(Character c : characters)
                if(!Objects.equals(getPlayerUsername(), c.getPlayerUsername()) && c.isAlive)
                    this.possibleTargets.add(c.getPlayerUsername());
    }

    @Override
    public String toString() {
        return "Character{" +
                "isAlive=" + isAlive +
                ", role='" + role + '\'' +
                '}';
    }
}