package Characters;

public class Sheriff extends Character {
    public Sheriff(String playerUsername) {
        super(playerUsername);
        this.attack = AttackTypes.None;
        this.defense = DefenseTypes.None;
        this.innocent = true;
    }
}
