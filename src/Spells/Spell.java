package Spells;

import Entities.CharacterFactory;
import Entities.Entity;
import Entities.Visitor;
import Entities.Character;

public abstract class Spell implements Visitor {
    private String name;
    private int damage;
    private int manaCost;

    public Spell() {
        this("", 0, 0);
    }

    public Spell(String name, int damage, int manaCost) {
        this.name = name;
        this.damage = damage;
        this.manaCost = manaCost;
    }

    @Override
    public void visit(Entity entity) {
        if (name.equals("Earth") && entity.isEarthImmune()) {
            if (entity instanceof Character)
                entity.setBattleMessage("Character is immune to earth. No damage applied.");
            else
                entity.setBattleMessage("Enemy is immune to earth. No damage applied.");

            entity.setReceivedDamage(0);
        }

        else if (name.equals("Fire") && entity.isFireImmune()) {
            if (entity instanceof Character)
                entity.setBattleMessage("Character is immune to fire. No damage applied.");
            else
                entity.setBattleMessage("Enemy is immune to fire. No damage applied.");

            entity.setReceivedDamage(0);
        }

        else if (name.equals("Ice") && entity.isIceImmune()) {
            if (entity instanceof Character)
                entity.setBattleMessage("Character is immune to ice. No damage applied.");
            else
                entity.setBattleMessage("Enemy is immune to ice. No damage applied.");

            entity.setReceivedDamage(0);
        }

        else {
            entity.setReceivedDamage(entity.getReceivedDamage() + damage);
            entity.setBattleMessage("-" + (int)(entity.getReceivedDamage() + damage) + " damage");
        }
    }

public int getManaCost() {
    return manaCost;
}

public void setManaCost(int manaCost) {
    this.manaCost = manaCost;
}

public int getDamageVal() {
    return damage;
}

public void setDamageVal(int damage) {
    this.damage = damage;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String toString() {
    return name + ": damage = " + damage + " , mana cost = " + manaCost;
}
}
