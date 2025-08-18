package Entities;

import Spells.Spell;

public interface Battle {
    public void receiveDamage(int damage, Spell ability);
    public int getDamage();
}
