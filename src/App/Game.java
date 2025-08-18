package App;

import Entities.Character;
import Entities.Enemy;
import Exceptions.ImpossibleMove;
import Exceptions.InvalidCommandException;
import GUI.Login;
import GUI.Map;
import Player.Account;
import Spells.Spell;
import sun.rmi.runtime.Log;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private ArrayList<Account> accounts;
    private Grid grid;
    private Account selectedAccount;
    private Character selectedCharacter;
    private Enemy enemy;
    private static Game gameInstance = null;
    private JFrame frame;
    Map map;

    private Game() {
        selectedAccount = null;
        selectedCharacter = null;
        enemy = null;
    }

    public static Game getInstance() {
        if (gameInstance == null)
            gameInstance = new Game();
        return gameInstance;
    }

    public Enemy createEnemy() {
        Random rand = new Random();
        int maxHealth = rand.nextInt(120) + selectedCharacter.getMaxHealth() / 2;
        int maxMana = rand.nextInt(70) + selectedCharacter.getMaxMana() / 3;
        int damage = rand.nextInt(5) + selectedCharacter.getDamageVal() / 3;
        Enemy enemy = new Enemy(maxHealth, maxMana, damage, rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean());
        enemy.addAbilities();
        enemy.setUsedAbility(null);
        map.setEnemyImage();
        return enemy;
    }

    public void enemySelectAbility() {
        Random rand = new Random();
        ArrayList<Spell> abilities = enemy.getAbilities();
        boolean useAbility = rand.nextBoolean();
        if (!abilities.isEmpty() && useAbility) {
            int abilityIdx = rand.nextInt(enemy.getAbilities().size());
            Spell ability = abilities.get(abilityIdx);

            if (enemy.canUseAbility(ability, selectedCharacter)) {
                enemy.setUsedAbility(ability);
            }
        }
    }

    public boolean normalAttack() {
        selectedCharacter.setUsedAbility(null);
        enemy.receiveDamage(selectedCharacter.getDamage(), selectedCharacter.getUsedAbility());

        if (enemy.getCrtHealth() <= 0) {
            map.wonPanel();
            Random rand = new Random();
            selectedCharacter.increaseKilledEnemies();
            selectedCharacter.regenerateHealth(selectedCharacter.getCrtHealth());
            selectedCharacter.regenerateMana(selectedCharacter.getMaxMana());
            selectedCharacter.addXP(rand.nextInt(200) + 10);
            return true;
        }

        enemy.setUsedAbility(null);
        enemySelectAbility();
        if (enemy.getUsedAbility() == null) {
            selectedCharacter.receiveDamage(enemy.getDamage(), null);
        }

        if (selectedCharacter.getCrtHealth() <= 0) {
            map.deadPanel();
            selectedCharacter.setCrtHealth(selectedCharacter.getMaxHealth());
            selectedCharacter.setCrtMana(selectedCharacter.getMaxMana());
            return false;
        }
        map.openAttack();
        return true;
    }

    public void setCharacterAbility(int index) {
        selectedCharacter.setUsedAbility(selectedCharacter.getAbilities().get(index));
    }

    public void chooseAnotherCharacter() {
        ((Login)frame).displayCharacters();
    }

    public boolean abilityAttack() {
        Spell ability = selectedCharacter.getUsedAbility();
        if (selectedCharacter.canUseAbility(ability, enemy)) {

        } else {
            selectedCharacter.setUsedAbility(null);
            map.abilityUseErrorPanel("Can't use ability, not enough mana!");
            return false;
        }

        if (enemy.getCrtHealth() <= 0) {
            Random rand = new Random();
            selectedCharacter.increaseKilledEnemies();
            selectedCharacter.regenerateHealth(selectedCharacter.getCrtHealth());
            selectedCharacter.regenerateMana(selectedCharacter.getMaxMana());
            selectedCharacter.addXP(rand.nextInt(200) + 10);
            map.wonPanel();
            return true;
        }

        enemy.setUsedAbility(null);
        enemySelectAbility();
        if (enemy.getUsedAbility() == null) {
            selectedCharacter.receiveDamage(enemy.getDamage(), enemy.getUsedAbility());
        }
        if (selectedCharacter.getCrtHealth() <= 0) {
            map.deadPanel();
            selectedCharacter.setCrtHealth(selectedCharacter.getMaxHealth());
            selectedCharacter.setCrtMana(selectedCharacter.getMaxMana());
            return false;
        }
        map.openAttack();
        return true;
    }

    public boolean attack() {
        enemy = createEnemy();
        map.openAttack();

        selectedCharacter.setUsedAbility(null);
        enemy.setUsedAbility(null);

        return true;
    }

    public void checkCellType()  {
        Random rand = new Random();

        if(grid.getCrtCell().getType() == CellEntityType.Enemy) {
            selectedCharacter.getAbilities().clear();
            selectedCharacter.addAbilities();
            attack();
        } else if (grid.getCrtCell().getType() == CellEntityType.Sanctuary) {
            selectedCharacter.regenerateHealth(rand.nextInt(selectedCharacter.getMaxHealth()));
            selectedCharacter.regenerateMana(rand.nextInt(selectedCharacter.getMaxMana()));
            map.displaySanctuaryMessage();
        } else if (grid.getCrtCell().getType() == CellEntityType.Portal) {
            selectedCharacter.endLevel();
            map.createEndLevelPanel();
        }
    }

    public void endLevel() {
        Random rand = new Random();
        selectedCharacter.setKilledEnemies(0);
        selectedAccount.setNrPlayedGames(selectedAccount.getNrPlayedGames() + 1);

        selectedCharacter.getAbilities().clear();
        selectedCharacter.addAbilities();

        grid = Grid.generateGrid(rand.nextInt(8) + 3, rand.nextInt(8) + 3);
        map = new Map(frame);
    }

    public void run() {
        accounts = JsonInput.deserializeAccounts();
        selectedAccount = null;

        Login login = new Login();
        while(!login.isFinished()) {

        }
        frame = login;
    }

    public Account getAccountByEmail(String email) {
        accounts = JsonInput.deserializeAccounts();
        for (Account account : accounts) {
            if (account.getInfo().getCredentials().getEmail().equalsIgnoreCase(email)) {
                return account;
            }
        }
        return null;
    }

    public boolean checkPassword(Account account, String password) {
        if (account.getInfo().getCredentials().getPassword().equals(password))
            return true;
        return false;
    }

    public void playGame() {
        grid = Grid.generateExampleGrid();
        map = new Map(frame);
    }

    public void setSelectedAccount(Account selectedAccount) {
        this.selectedAccount = selectedAccount;
    }

    public void setSelectedCharacter(Character selectedCharacter) {
        this.selectedCharacter = selectedCharacter;
    }

    public Character getSelectedCharacter() {
        return selectedCharacter;
    }

    public Account getSelectedAccount() {
        return selectedAccount;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public Grid getGrid() {
        return grid;
    }
}
