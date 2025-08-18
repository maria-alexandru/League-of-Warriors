package Entities;

import Spells.Spell;
import Spells.Fire;
import Spells.Ice;
import Spells.Earth;

import java.util.Random;

public class Rogue extends Character {
    public Rogue(String name, int XP, int level) {
        super(name, XP, level, false, true, false, 0.09f,
                0.15f, 0.3f, 100, 100, 20);
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
        if (nr == 1 && getCharisma() * getStrength() >= 0.18)
            setReceivedDamage(getReceivedDamage() / 2);

        setCrtHealth(getCrtHealth() - getReceivedDamage());
        if (getReceivedDamage() != 0)
            setBattleMessage("-" + getReceivedDamage() + " damage");
    }

    @Override
    public int getDamage() {
        int newDamage = (int)(getDamageVal() * getDexterity() / 7 + getDamageVal() * getStrength() * 3 + getCharisma() * getDamageVal());

        Random rand = new Random();
        if (getDexterity() >= 1.55 && rand.nextBoolean())
            newDamage *= 2;

        return newDamage;
    }
}
