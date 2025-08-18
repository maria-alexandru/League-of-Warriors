package Entities;

import Spells.Spell;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Entity {
    public Enemy(int maxHealth, int maxMana, int damage, boolean fireImmune, boolean earthImmune, boolean iceImmune) {
        super(maxHealth, maxMana, damage, fireImmune, earthImmune, iceImmune);
        setUsedAbility(null);
    }

    @Override
    public void receiveDamage(int damage, Spell ability) {
        setReceivedDamage(damage);
        if (ability != null) {
            accept(ability);
        }

        Random rand = new Random();
        if (rand.nextInt(5) == 1) {
            setReceivedDamage(0);
            setBattleMessage("The enemy avoided the attack! No damage applied.");
        }

        setCrtHealth(getCrtHealth() - getReceivedDamage());
        if (getReceivedDamage() != 0)
            setBattleMessage("-" + getReceivedDamage() + " damage");
    }

    @Override
    public int getDamage() {
        Random rand = new Random();
        int damage = getDamageVal();

        if (rand.nextBoolean())
            damage *= 2;

        return damage;
    }

    @Override
    public String toString() {
        String str = "Health: " + getCrtHealth() + ", Mana: " + getCrtMana();
        if (isFireImmune())
            str += ", is immune to fire";
        if (isEarthImmune())
            str += ", is immune to earth";
        if (isIceImmune())
            str += ", is immune to ice";
        return str;
    }
}
