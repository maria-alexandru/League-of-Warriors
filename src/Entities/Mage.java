package Entities;

import Spells.Spell;

import java.util.Random;

public class Mage extends Character {
    public Mage(String name, int XP, int level) {
        super(name, XP, level, false, false, true, 0.11f,
                0.35f, 0.23f, 100, 100, 15);
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
        if (nr == 1 && getStrength() * getDexterity() >= 0.14)
            setReceivedDamage(getReceivedDamage() / 2);

        setCrtHealth(getCrtHealth() - getReceivedDamage());
        if (getReceivedDamage() != 0)
            setBattleMessage("-" + getReceivedDamage() + " damage");
    }

    @Override
    public int getDamage() {
        int newDamage = (int)(getDamageVal() * getDexterity() + getDamageVal() * getStrength() + getCharisma() * getDamageVal() * 3);

        Random rand = new Random();
        if (getCharisma() > 0.85f && rand.nextBoolean())
            newDamage *= 2;

        return newDamage;
    }
}
