package Entities;

import Spells.Spell;

import java.util.Random;

public class Warrior extends Character {
    public Warrior(String name, int XP, int level) {
        super(name, XP, level, true, false, false, 0.45f,
                0.18f, 0.25f, 100, 100, 10);
        addAbilities();
    }

    @Override
    public void receiveDamage(int damage, Spell ability) {
        setReceivedDamage(damage);
        if (ability != null) {
            accept(ability);
        }

        Random rand = new Random();
        int nr = rand.nextInt(2);
        if (nr == 1 && getCharisma() * getDexterity() >= 0.35)
            setReceivedDamage(getReceivedDamage() / 2);

        setCrtHealth(getCrtHealth() - getReceivedDamage());
        if (getReceivedDamage() != 0)
            setBattleMessage("-" + getReceivedDamage() + " damage");
    }

    @Override
    public int getDamage() {
        int newDamage = (int)(getDamageVal() * getDexterity() * 2 + getDamageVal() * getStrength() / 3 + getCharisma() * getDamageVal() * 3);

        Random rand = new Random();
        if (getStrength() >= 0.8 && rand.nextBoolean())
            newDamage *= 2;

        return newDamage;
    }
}
