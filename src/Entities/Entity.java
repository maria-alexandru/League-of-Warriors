package Entities;

import Spells.Earth;
import Spells.Fire;
import Spells.Ice;
import Spells.Spell;

import java.util.ArrayList;
import java.util.Random;

public abstract class Entity implements Battle, Element {
    private ArrayList<Spell> abilities;
    private int crtHealth, maxHealth = 100;
    private int crtMana, maxMana = 100;
    private boolean fireImmune, earthImmune, iceImmune;
    private int damage;
    private int receivedDamage;
    private Spell usedAbility;
    private String battleMessage;

    public Entity() {
        abilities = new ArrayList<Spell>();
        resetHealthAndMana();
        battleMessage = null;
    }

    public Entity(int maxHealth, int maxMana, int damage, boolean fireImmune, boolean earthImmune, boolean iceImmune) {
        abilities = new ArrayList<Spell>();
        this.maxHealth = maxHealth;
        this.maxMana = maxMana;
        this.damage = damage;
        this.fireImmune = fireImmune;
        this.earthImmune = earthImmune;
        this.iceImmune = iceImmune;
        resetHealthAndMana();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void regenerateHealth(int health) {
        int oldHealth = crtHealth;

        if (crtHealth <= 0) {
            crtHealth = 10;
            health = 10;
        }

        if (crtHealth + health >= maxHealth)
            crtHealth = maxHealth;
        else
            crtHealth = crtHealth + health;
    }

    public void regenerateMana(int mana) {
        int oldMana = crtMana;

        if (crtMana + mana >= maxMana)
            crtMana = maxMana;
        else
            crtMana = crtMana + mana;
    }

    public void resetHealthAndMana() {
        crtHealth = maxHealth;
        crtMana = maxMana;
    }

    public boolean canUseAbility(Spell ability, Entity target) {
        if (crtMana < ability.getManaCost()) {
            return false;
        }

        crtMana -= ability.getManaCost();
        abilities.remove(ability);
        setUsedAbility(ability);
        target.receiveDamage(getDamage(), getUsedAbility());
        return true;
    }

    public void addAbilities() {
        Random rand = new Random();
        Spell spell;
        spell = new Fire("Fire", rand.nextInt(25) + 5, rand.nextInt(25) + 5);
        abilities.add(spell);
        spell = new Ice("Ice", rand.nextInt(14) + 5, rand.nextInt(15) + 5);
        abilities.add(spell);
        spell = new Earth("Earth", rand.nextInt(40) + 5, rand.nextInt(45) + 5);
        abilities.add(spell);

        int nr = rand.nextInt(4);
        for (int i = 0; i < nr; i++) {
            int opt = rand.nextInt(3);
            if (opt == 0)
                spell = new Fire("Fire", rand.nextInt(32) + 5, rand.nextInt(30) + 5);
            else if (opt == 1)
                spell = new Ice("Ice", rand.nextInt(23) + 5, rand.nextInt(24) + 5);
            else
                spell = new Earth("Earth", rand.nextInt(24) + 5, rand.nextInt(40) + 5);
            abilities.add(spell);
        }
    }

    public ArrayList<Spell> getAbilities() {
        return abilities;
    }

    public void setAbilities(ArrayList<Spell> abilities) {
        this.abilities = abilities;
    }

    public int getCrtHealth() {
        return crtHealth;
    }

    public void setCrtHealth(int crtHealth) {
        this.crtHealth = crtHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCrtMana() {
        return crtMana;
    }

    public void setCrtMana(int crtMana) {
        this.crtMana = crtMana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public boolean isFireImmune() {
        return fireImmune;
    }

    public boolean isEarthImmune() {
        return earthImmune;
    }

    public boolean isIceImmune() {
        return iceImmune;
    }

    public int getDamageVal() {
        return damage;
    }

    public void setDamageVal(int damage) {
        this.damage = damage;
    }

    public void setUsedAbility(Spell usedAbility) {
        this.usedAbility = usedAbility;
    }

    public Spell getUsedAbility() {
        return usedAbility;
    }

    public int getReceivedDamage() {
        return receivedDamage;
    }

    public void setReceivedDamage(int receivedDamage) {
        this.receivedDamage = receivedDamage;
    }

    public void setBattleMessage(String battleMessage) {
        this.battleMessage = battleMessage;
    }

    public String getBattleMessage() {
        return battleMessage;
    }
}
