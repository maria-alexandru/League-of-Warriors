package Entities;

import Spells.Spell;
import jdk.nashorn.internal.objects.annotations.Getter;

public abstract class Character extends Entity {
    private String name;
    private int XP;
    private float strength;
    private float charisma;
    private float dexterity;
    private int level;
    private int killedEnemies;

    public Character(String name, int XP, int level, boolean fireImmune, boolean EarthImmune,
                     boolean IceImmune, float strength, float charisma, float dexterity, int maxHealth,
                     int maxMana, int damage) {
        super(maxHealth, maxMana, damage, fireImmune, EarthImmune, IceImmune);
        this.name = name;
        this.XP = XP;
        this.level = level;
        this.strength = strength;
        this.charisma = charisma;
        this.dexterity = dexterity;
        killedEnemies = 0;
        setUsedAbility(null);
    }

    public void addXP(int XP) {
        this.XP += XP;
    }

    public void endLevel() {
        this.XP += level * 5;
        level++;
        strength += 0.05f;
        charisma += 0.05f;
        dexterity += 0.05f;
        setDamageVal(getDamageVal() + 2);
        setMaxHealth(getMaxHealth() + 20);
        resetHealthAndMana();
    }

    public String toString() {
        return "\n      Name: " + name + ", XP: " + XP + ", Level: " + level;
    }

    public String getStats() {
         return "Health: " + getCrtHealth() + ", Mana: " + getCrtMana() + ", XP: " + XP;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }

    public float getXP() {
        return XP;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }

    public float getStrength() {
        return strength;
    }

    public void setCharisma(float charisma) {
        this.charisma = charisma;
    }

    public float getCharisma() {
        return charisma;
    }

    public void setDexterity(float dexterity) {
        this.dexterity = dexterity;
    }

    public float getDexterity() {
        return dexterity;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setKilledEnemies(int killedEnemies) {
        this.killedEnemies = killedEnemies;
    }

    public void increaseKilledEnemies() {
        killedEnemies++;
    }

    public int getKilledEnemies() {
        return killedEnemies;
    }
}
