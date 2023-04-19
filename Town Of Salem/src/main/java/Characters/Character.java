package Characters;

import java.util.ArrayList;

public abstract class Character {
    public boolean isAlive = true;
    public boolean roleBlocked = false;
    protected boolean innocent;
    public boolean framed = false;
    public String playerUsername;
    public Interaction lastInteraction;
    public DefenseTypes defense;
    public AttackTypes attack;



    private ArrayList<String> nightResults;

    public Character(String playerUsername) {
        this.playerUsername = playerUsername;
    }

    public void AddNightResult(String ...messages) {
        for (String message : messages)
            nightResults.add(message);
    }
    public void ResetNightResults() {
        nightResults.clear();
    }

    public boolean IsInnocent() {
        return innocent;
    }

    public void ResetEffects() {
        framed = false;
        roleBlocked = false;
        lastInteraction = null;
    }


}
