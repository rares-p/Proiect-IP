package com.github.raresp.proiectip.TownOfSalem.models.characters;
import java.util.ArrayList;
import java.util.List;

public abstract class MafiaCharacter extends Character{

    //every mafia character will know who the other mafia characters are
    //also the list is static meaning that all mafia characters share it
    //when you create the first element of this class, the character list is also initialized
    private static List<MafiaCharacter> mafiaCharacterList;

    static{
        mafiaCharacterList = new ArrayList<>();
    }
    public MafiaCharacter(String playerUsername) {
        super(playerUsername);
        MafiaCharacter.mafiaCharacterList.add(this);
    }
    public List<MafiaCharacter> getMafiaCharacterList() {
        return mafiaCharacterList;
    }

    public void setMafiaCharacterList(List<MafiaCharacter> mafiaCharacterList) {
        MafiaCharacter.mafiaCharacterList = mafiaCharacterList;
    }


    public abstract void act(List<Character> listOfTargets);
}