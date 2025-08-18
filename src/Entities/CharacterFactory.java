package Entities;

public class CharacterFactory {
    public static Character createCharacter(String type, String name, int XP, int level) {
        Character character = null;

        if (type.equals("Warrior"))
            character = new Warrior(name, XP, level);
        if (type.equals("Rogue"))
            character = new Rogue(name, XP, level);
        if (type.equals("Mage"))
            character = new Mage(name, XP, level);

        return character;
    }

}
