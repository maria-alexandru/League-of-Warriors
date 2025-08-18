package GUI;

import App.Cell;
import App.CellEntityType;
import App.Grid;
import Entities.Character;
import Exceptions.ImpossibleMove;
import GUI.Elements.MyButton;
import GUI.Elements.Variables;

import App.Game;
import Spells.Spell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;


public class Map implements ActionListener {
    private JFrame frame;
    private ArrayList<JLabel> cellLabel;
    private JPanel mapPanel;
    private MyButton north;
    private MyButton east;
    private MyButton south;
    private MyButton west;
    private ImageIcon unvisited;
    private ImageIcon player;
    private MyButton name;
    private MyButton level;
    private MyButton experience;
    private MyButton health;
    private MyButton mana;
    private JPanel characterInfo;
    private JPanel sanctuaryMessage;
    private MyButton closeSanctuaryMessage;
    private JPanel buttons;
    private JTextArea message;
    private JTextArea healthMessage;
    private JTextArea manaMessage;
    private JPanel attackPanel;
    private JLabel enemyImage;
    private MyButton attackButton;
    private MyButton abilityButton;
    private JLabel characterName;
    private MyButton enemyHealth, enemyMana;
    private MyButton characterHealth, characterMana;
    private MyButton showMapButton;
    private JPanel abilitiesPanel;
    private ArrayList<MyButton> selectAbility;
    private MyButton normalAttackButton;
    private MyButton returnMenuButton;
    private MyButton characterAttackMessage, enemyAttackMessage;
    private JPanel endLevelPanel;
    private MyButton exit;
    private MyButton nextLevel;

    private Grid grid;

    public Map(JFrame frame) {
        this.frame = frame;

        for (Component component : frame.getContentPane().getComponents())
            frame.remove(component);

        createAttackPanel();

        grid = Game.getInstance().getGrid();
        int cols = grid.getColumns();
        int rows = grid.getRows();

        mapPanel = new JPanel(new GridLayout(rows, cols, 10, 10));
        mapPanel.setBounds(300, 20, 400, 500);

        unvisited = new ImageIcon("src/Images/question.png");
        player = new ImageIcon("src/Images/swordsman.png");
        int img_size = Integer.min(mapPanel.getWidth() / rows, mapPanel.getHeight() / cols) - 35;
        if (img_size < 24)
            img_size = 24;
        Image resizedImage = player.getImage().getScaledInstance(img_size, img_size, Image.SCALE_SMOOTH);
        player = new ImageIcon(resizedImage);
        resizedImage = unvisited.getImage().getScaledInstance(img_size, img_size, Image.SCALE_SMOOTH);
        unvisited = new ImageIcon(resizedImage);

        mapPanel.addKeyListener(new KeyAdapter() {
                                    @Override
                                    public void keyPressed(KeyEvent e) {
                                        int keyCode = e.getKeyCode();
                                        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP)
                                            actionPerformed(new ActionEvent(north, ActionEvent.ACTION_PERFORMED, "click"));

                                        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN)
                                            actionPerformed(new ActionEvent(south, ActionEvent.ACTION_PERFORMED, "click"));

                                        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT)
                                            actionPerformed(new ActionEvent(west, ActionEvent.ACTION_PERFORMED, "click"));

                                        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT)
                                            actionPerformed(new ActionEvent(east, ActionEvent.ACTION_PERFORMED, "click"));

                                        if (keyCode == KeyEvent.VK_C || keyCode == KeyEvent.VK_ENTER)
                                            actionPerformed(new ActionEvent(closeSanctuaryMessage, ActionEvent.ACTION_PERFORMED, "click"));
                                    }
                                });
        mapPanel.setFocusable(true);
        mapPanel.requestFocusInWindow();

        cellLabel = new ArrayList<>();
        createMap();
        mapPanel.setVisible(true);

        createMoveButtons();
        createCharacterInfo();
        createSanctuaryMessage();
        createAttackPanel();

        frame.add(sanctuaryMessage);
        frame.add(buttons);
        frame.add(mapPanel);
        frame.add(characterInfo);


        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                mapPanel.requestFocusInWindow();
            }
        });

        frame.revalidate();
        frame.repaint();
    }

    public void createEndLevelPanel() {
        mapPanel.setVisible(false);
        buttons.setVisible(false);
        characterInfo.setVisible(false);
        endLevelPanel = new JPanel(new GridBagLayout());
        endLevelPanel.setBounds(frame.getWidth() / 2 - 175, 0, 350, frame.getHeight() - 50);

        GridBagConstraints constraints = new GridBagConstraints();
        Insets insets = new Insets(5, 5, 5, 5);
        constraints.insets = insets;
        constraints.ipadx = 10;
        constraints.ipady = 10;

        Character character = Game.getInstance().getSelectedCharacter();

        player = new ImageIcon("src/Images/swordsman.png");
        Image resizedImage = player.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        player = new ImageIcon(resizedImage);

        MyButton message = new MyButton("Congratulations! You ended this level! ");
        MyButton name = new MyButton("Name: " + character.getName());
        MyButton role = new MyButton("Role: " + character.getClass().getSimpleName());
        MyButton level = new MyButton("Level: " + character.getLevel());
        MyButton experience = new MyButton("Experience: " + character.getXP());
        MyButton killedEnemies = new MyButton("Killed enemies: " + character.getKilledEnemies());
        name.setAllColors(Variables.purple);
        message.setAllColors(Variables.purple);
        role.setAllColors(Variables.purple);
        level.setAllColors(Variables.purple);
        experience.setAllColors(Variables.purple);
        killedEnemies.setAllColors(Variables.purple);

        message.setForeground(Color.black);
        name.setForeground(Color.black);
        role.setForeground(Color.black);
        level.setForeground(Color.black);
        experience.setForeground(Color.black);
        killedEnemies.setForeground(Color.black);

        JPanel exitAndNextLevel = new JPanel(new GridLayout(1, 2, 10, 10));
        nextLevel = new MyButton("Go to next level!");
        exitAndNextLevel.add(exit);
        exitAndNextLevel.add(nextLevel);
        nextLevel.addActionListener(this);

        constraints.gridx = 0;
        constraints.gridy = 0;
        endLevelPanel.add(message, constraints);

        constraints.gridy = 1;
        endLevelPanel.add(new JLabel(player), constraints);

        constraints.gridy = 2;
        endLevelPanel.add(name, constraints);

        constraints.gridy = 3;
        endLevelPanel.add(role, constraints);

        constraints.gridy = 4;
        endLevelPanel.add(level, constraints);

        constraints.gridy = 5;
        endLevelPanel.add(experience, constraints);

        constraints.gridy = 6;
        endLevelPanel.add(killedEnemies, constraints);

        constraints.gridy = 7;
        endLevelPanel.add(exitAndNextLevel, constraints);

        frame.add(endLevelPanel);
    }

    public void abilityUseErrorPanel(String messageText) {
        int x = frame.getWidth() / 2 - 175;
        int y = frame.getHeight() / 2 - 130;

        abilitiesPanel = new JPanel(new GridLayout(3, 1, 10, 20));
        abilitiesPanel.setBounds(x, y, 350, 200);
        abilitiesPanel.setVisible(true);
        frame.add(abilitiesPanel);

        MyButton message = new MyButton(messageText);
        MyButton message2 = new MyButton("Start normal attack!");
        message.setAllColors(Variables.purple);
        message.setForeground(Color.black);
        message.setFont(new Font(Variables.font, Font.BOLD, Variables.fontSize + 5));
        message2.setAllColors(Variables.purple);
        message2.setForeground(Color.black);
        message2.setFont(new Font(Variables.font, Font.BOLD, Variables.fontSize + 5));

        normalAttackButton = new MyButton("Attack");
        normalAttackButton.addActionListener(this);
        abilitiesPanel.add(message);
        abilitiesPanel.add(message2);

        JPanel aux = new JPanel(new GridLayout(1, 3, 10, 10));
        aux.add(new JPanel());
        aux.add(normalAttackButton);
        aux.add(new JPanel());
        abilitiesPanel.add(aux);
        frame.revalidate();
        frame.repaint();
    }

    public void createAbilitiesPanel() {
        attackPanel.setVisible(false);

        ArrayList<Spell> abilities = Game.getInstance().getSelectedCharacter().getAbilities();
        int nr = abilities.size();
        selectAbility = new ArrayList<>();

        if (nr == 0) {
           abilityUseErrorPanel("No abilities!");
            return;
        }

        abilitiesPanel = new JPanel(new GridLayout(1, nr, 10, 10));
        abilitiesPanel.setBounds(30, 50, frame.getWidth() - 80, frame.getHeight() - 150);
        abilitiesPanel.setVisible(true);
        frame.add(abilitiesPanel);

        int img_size = (frame.getWidth() - 200) / nr;
        if (img_size > 200)
            img_size = 200;

        for (Spell ability : abilities) {
            JPanel abilityPanel = new JPanel(new GridBagLayout());
            abilityPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
            abilityPanel.setBorder(BorderFactory.createCompoundBorder(abilityPanel.getBorder(),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));

            ImageIcon abilityIcon = new ImageIcon("src/Images/" + ability.getName().toLowerCase() + ".png");
            Image resizedImage = abilityIcon.getImage().getScaledInstance(img_size, img_size, Image.SCALE_SMOOTH);
            abilityIcon = new ImageIcon(resizedImage);

            JLabel abilityImage = new JLabel(abilityIcon);
            abilityImage.setHorizontalAlignment(SwingConstants.CENTER);
            abilityImage.setVerticalAlignment(SwingConstants.TOP);

            GridBagConstraints constraints = new GridBagConstraints();
            Insets insets = new Insets(10, 10, 10, 10);
            constraints.insets = insets;
            constraints.ipadx = 10;
            constraints.ipady = 10;

            MyButton name = new MyButton(ability.getName());
            name.setAllColors(Variables.purple);
            name.setForeground(Color.black);
            JPanel namePanel = new JPanel();
            namePanel.add(name);

            MyButton manaCost = new MyButton("Mana cost: " + ability.getManaCost());
            manaCost.setAllColors(Variables.purple);
            manaCost.setForeground(Color.black);
            if (nr == 6)
                manaCost.setFontSize(9);

            MyButton damage = new MyButton("Damage: " + ability.getDamageVal());
            damage.setAllColors(Variables.purple);
            damage.setForeground(Color.black);
            if (nr == 6)
                damage.setFontSize(9);

            MyButton select = new MyButton("Select");
            select.addActionListener(this);
            selectAbility.add(select);

            constraints.gridx = 0;
            constraints.gridy = 0;
            abilityPanel.add(name, constraints);

            constraints.gridy = 1;
            abilityPanel.add(abilityImage, constraints);

            constraints.gridy = 2;
            abilityPanel.add(manaCost, constraints);

            constraints.gridy = 3;
            abilityPanel.add(damage, constraints);

            constraints.gridy = 4;
            abilityPanel.add(select, constraints);

            abilitiesPanel.add(abilityPanel);
        }
    }

    public void deadPanel() {
        attackPanel.setVisible(false);
        MyButton dead = new MyButton("The character is dead!");
        MyButton lost = new MyButton("You lost!");
        dead.setFont(new Font(Variables.font, Font.BOLD, Variables.fontSize + 5));
        dead.setAllColors(new Color(149, 37, 46));
        lost.setFont(new Font(Variables.font, Font.BOLD, Variables.fontSize + 5));
        lost.setAllColors(new Color(149, 37, 46));
        dead.setForeground(Color.black);
        lost.setForeground(Color.black);

        int x = frame.getWidth() / 2 - 150;
        int y = frame.getHeight() / 2 - 200;
        dead.setBounds(x, y, 300, 60);

        y += 80;
        lost.setBounds(x, y, 300, 60);

        returnMenuButton = new MyButton("Return to menu");
        returnMenuButton.addActionListener(this);

        y += 90;
        returnMenuButton.setBounds(frame.getWidth() / 2 - 100, y, 200, 50);
        frame.add(dead);
        frame.add(lost);
        frame.add(returnMenuButton);
    }

    public void wonPanel() {
        attackPanel.setVisible(false);
        MyButton dead = new MyButton("The enemy is dead!");
        MyButton won = new MyButton("Congratulations! You won!");
        dead.setFont(new Font(Variables.font, Font.BOLD, Variables.fontSize + 5));
        dead.setAllColors(Variables.purple);
        won.setFont(new Font(Variables.font, Font.BOLD, Variables.fontSize + 3));
        won.setAllColors(Variables.purple);
        dead.setForeground(Color.black);
        won.setForeground(Color.black);

        int x = frame.getWidth() / 2 - 150;
        int y = frame.getHeight() / 2 - 200;
        dead.setBounds(x, y, 300, 60);

        y += 80;
        won.setBounds(x, y, 300, 60);

        y += 90;
        showMapButton = new MyButton("Return to map");
        showMapButton.addActionListener(this);
        showMapButton.setBounds(frame.getWidth() / 2 - 100, y, 200, 50);
        frame.add(dead);
        frame.add(won);
        frame.add(showMapButton);
    }

    public void createAttackPanel() {
        attackPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        attackPanel.setBounds(50, 10, frame.getWidth() - 100, frame.getHeight() - 100);

        ImageIcon characterIcon = new ImageIcon("src/Images/character.png");
        Image resizedImage = characterIcon.getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH);
        characterIcon = new ImageIcon(resizedImage);
        JLabel characterImage = new JLabel(characterIcon);

        characterImage.setBounds(40, 30, frame.getWidth() / 2 - 50, 200);

        characterName = new JLabel("NUmele meu");//Game.getInstance().getSelectedCharacter().getName());
        characterName.setFont(new Font(Variables.font, Font.BOLD, Variables.fontSize + 3));

        JPanel character = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        Insets insets = new Insets(5, 5, 5, 5);
        constraints.insets = insets;
        constraints.ipadx = 10;
        constraints.ipady = 10;
        character.setBounds(40, 30, frame.getWidth() / 2 - 150, 150);
        characterImage.setHorizontalAlignment(SwingConstants.CENTER);
        characterImage.setVerticalAlignment(SwingConstants.TOP);

        characterHealth =  new MyButton("Health: ");
        characterMana =  new MyButton("Mana: ");
        characterAttackMessage = new MyButton("");
        characterAttackMessage.setVisible(false);
        characterAttackMessage.setAllColors(new Color(73, 38, 45));
        characterAttackMessage.setFontSize(10);
        characterHealth.setAllColors(Variables.purple);
        characterHealth.setForeground(Color.black);
        characterMana.setAllColors(Variables.purple);
        characterMana.setForeground(Color.black);


        constraints.gridx = 0;
        constraints.gridy = 0;
        JPanel aux = new JPanel(new GridLayout(1, 2, 10, 10));
        aux.setBounds(0, 0, 150, 40);
        MyButton auxButton = new MyButton(".");
        aux.add(auxButton);
        auxButton.setVisible(false);
        character.add(aux, constraints);

        constraints.gridy = 1;
        character.add(characterImage, constraints);

        constraints.gridy = 2;
        character.add(characterName, constraints);

        constraints.gridy = 3;
        character.add(characterHealth, constraints);

        constraints.gridy = 4;
        character.add(characterMana, constraints);

        constraints.gridy = 5;
        character.add(characterAttackMessage, constraints);

        JPanel attackButtons = new JPanel(new GridLayout(1, 2, 10, 15));
        attackButton = new MyButton("Attack");
        abilityButton = new MyButton("Use ability");
        attackButton.addActionListener(this);
        abilityButton.addActionListener(this);
        attackButtons.add(attackButton);
        attackButtons.add(abilityButton);
        attackButton.setColorClick(Variables.darkGreen);
        abilityButton.setColorClick(Variables.darkGreen);
        attackButtons.setBounds(frame.getWidth() / 2, 0, 150, 40);

        ImageIcon enemyIcon = new ImageIcon("src/Images/enemy0.png");
        enemyImage = new JLabel(enemyIcon);
        enemyImage.setBounds(frame.getWidth() / 2, 30, frame.getWidth() / 2 - 50, 200);
        enemyImage.setHorizontalAlignment(SwingConstants.CENTER);
        enemyImage.setVerticalAlignment(SwingConstants.TOP);

        JLabel enemyName = new JLabel("Enemy");
        enemyName.setFont(new Font(Variables.font, Font.BOLD, Variables.fontSize + 3));

        enemyHealth =  new MyButton("Health: ");
        enemyMana =  new MyButton("Mana: ");
        enemyAttackMessage = new MyButton("");
        enemyAttackMessage.setVisible(false);
        enemyAttackMessage.setAllColors(new Color(73, 38, 45));
        enemyAttackMessage.setFontSize(10);
        enemyHealth.setAllColors(Variables.purple);
        enemyHealth.setForeground(Color.black);
        enemyMana.setAllColors(Variables.purple);
        enemyMana.setForeground(Color.black);

        JPanel enemy = new JPanel(new GridBagLayout());
        enemy.setBounds(frame.getWidth() / 2, 0, frame.getWidth() / 2 - 150, 200);

        constraints.gridx = 0;
        constraints.gridy = 0;
        enemy.add(attackButtons, constraints);

        constraints.gridy = 1;
        enemy.add(enemyImage, constraints);

        constraints.gridy = 2;
        enemy.add(enemyName, constraints);

        constraints.gridy = 3;
        enemy.add(enemyHealth, constraints);

        constraints.gridy = 4;
        enemy.add(enemyMana, constraints);

        constraints.gridy = 5;
        enemy.add(enemyAttackMessage, constraints);

        attackPanel.add(character);
        attackPanel.add(enemy);
        attackPanel.setVisible(false);

        frame.add(attackPanel);
    }

    public void setEnemyImage() {
        Random rand = new Random();
        int i = rand.nextInt(5);
        ImageIcon enemyIcon = new ImageIcon("src/Images/enemy" + i + ".png");
        Image resizedImage = enemyIcon.getImage().getScaledInstance(210, 210, Image.SCALE_SMOOTH);
        enemyIcon = new ImageIcon(resizedImage);
        enemyImage.setIcon(enemyIcon);
    }

    public void openAttack() {
        mapPanel.setVisible(false);
        characterInfo.setVisible(false);
        buttons.setVisible(false);
        attackPanel.setVisible(true);

        characterName.setText(Game.getInstance().getSelectedCharacter().getName());
        characterMana.setText("Mana: " + Game.getInstance().getSelectedCharacter().getCrtMana());
        characterHealth.setText("Health: " + Game.getInstance().getSelectedCharacter().getCrtHealth());
        enemyMana.setText("Mana: " + Game.getInstance().getEnemy().getCrtMana());
        enemyHealth.setText("Health: " + Game.getInstance().getEnemy().getCrtHealth());

        String characterMessage = Game.getInstance().getSelectedCharacter().getBattleMessage();
        String enemyMessage = Game.getInstance().getEnemy().getBattleMessage();

        enemyAttackMessage.setText(enemyMessage);
        characterAttackMessage.setText(characterMessage);
        if (characterMessage != null)
            characterAttackMessage.setVisible(true);
        if (enemyMessage != null)
            enemyAttackMessage.setVisible(true);

        Timer timer = new Timer(1700, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enemyAttackMessage.setVisible(false);
                characterAttackMessage.setVisible(false);
                Game.getInstance().getSelectedCharacter().setBattleMessage(null);
                Game.getInstance().getEnemy().setBattleMessage(null);
            }
        });

        timer.setRepeats(false);
        timer.start();

    }

    public void createMoveButtons() {
        buttons = new JPanel(new GridLayout(3, 3,10, 10));

        north = new MyButton("North");
        east = new MyButton("East");
        south = new MyButton("South");
        west = new MyButton("West");
        north.addActionListener(this);
        east.addActionListener(this);
        south.addActionListener(this);
        west.addActionListener(this);

        JLabel moveLabel = new JLabel();
        moveLabel.setText("Move");
        moveLabel.setFont(new Font(Variables.font, Font.BOLD, Variables.fontSize + 3));
        moveLabel.setForeground(Color.black);
        moveLabel.setHorizontalAlignment(SwingConstants.CENTER);
        moveLabel.setVerticalAlignment(SwingConstants.CENTER);

        buttons.add(new JPanel());
        buttons.add(north);
        buttons.add(new JPanel());
        buttons.add(west);
        buttons.add(moveLabel);
        buttons.add(east);
        buttons.add(new JPanel());
        buttons.add(south);
        buttons.add(new JPanel());
        buttons.setBounds(40, 30, 230, 125);
    }

    public void createCharacterInfo() {
        exit = new MyButton("Return to menu");
        exit.setBackgroundColor(Variables.red);
        exit.setColorClick(Variables.darkRed);
        exit.setColorOver(Variables.darkRed);
        exit.addActionListener(this);

        characterInfo = new JPanel(new GridLayout(6, 1, 10, 10));
        name = new MyButton("");
        level =  new MyButton("");
        experience =  new MyButton("");
        health =  new MyButton("");
        mana =  new MyButton("");
        updateCharacterInfo();
        name.setAllColors(Variables.purple);
        level.setAllColors(Variables.purple);
        experience.setAllColors(Variables.purple);
        health.setAllColors(Variables.purple);
        mana.setAllColors(Variables.purple);
        name.setForeground(Color.black);
        level.setForeground(Color.black);
        experience.setForeground(Color.black);
        health.setForeground(Color.black);
        mana.setForeground(Color.black);
        
        characterInfo.add(name);
        characterInfo.add(level);
        characterInfo.add(experience);
        characterInfo.add(health);
        characterInfo.add(mana);
        characterInfo.add(exit);
        characterInfo.setBounds(40, 185, 230, 300);
    }

    public void createSanctuaryMessage() {
        int y = frame.getHeight() / 2 - 125;
        int x = 60;

        sanctuaryMessage = new JPanel();
        sanctuaryMessage.setBounds(x, 100, 200, 400);
        sanctuaryMessage.setVisible(false);

        message = new JTextArea("You have arrived at the sanctuary. Your health and mana have been restored!\n");
        message.setEditable(false);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setFont(new Font(Variables.font, Font.BOLD, Variables.fontSize + 4));
        message.setBackground(null);
        message.setBounds(x, y, 200, 250);

        y += message.getHeight() + 20;
        healthMessage = new JTextArea("Health regenerated! Current health: \n" + Game.getInstance().getSelectedCharacter().getCrtHealth() + "\n");
        healthMessage.setEditable(false);
        healthMessage.setLineWrap(true);
        healthMessage.setWrapStyleWord(true);
        healthMessage.setFont(new Font(Variables.font, Font.BOLD, Variables.fontSize + 4));
        healthMessage.setBackground(null);
        healthMessage.setBounds(x, y, 200, 250);

        y += healthMessage.getHeight() + 20;
        manaMessage = new JTextArea("Mana regenerated! Current mana: " + Game.getInstance().getSelectedCharacter().getCrtMana() + "\n");
        manaMessage.setEditable(false);
        manaMessage.setLineWrap(true);
        manaMessage.setWrapStyleWord(true);
        manaMessage.setFont(new Font(Variables.font, Font.BOLD, Variables.fontSize + 4));
        manaMessage.setBackground(null);
        manaMessage.setBounds(x, y, 200, 250);

        y += manaMessage.getHeight() + 30;
        closeSanctuaryMessage = new MyButton("Close");
        closeSanctuaryMessage.addActionListener(this);
        closeSanctuaryMessage.setBackgroundColor(Variables.red);
        closeSanctuaryMessage.setColorClick(Variables.darkRed);
        closeSanctuaryMessage.setColorOver(Variables.darkRed);
        closeSanctuaryMessage.setPreferredSize(new Dimension(80, 40));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        buttonPanel.setBounds(x, y, 70, 40);
        buttonPanel.add(new JPanel());
        buttonPanel.add(closeSanctuaryMessage);
        buttonPanel.add(new JPanel());

        sanctuaryMessage.add(message);
        sanctuaryMessage.add(healthMessage);
        sanctuaryMessage.add(manaMessage);
        sanctuaryMessage.add(buttonPanel);
    }

    public void createMap() {
        int cols = grid.getColumns();
        int rows = grid.getRows();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JLabel label = new JLabel();
                cellLabel.add(label);
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setVerticalAlignment(SwingConstants.CENTER);
                mapPanel.add(label);
            }
        }
        updateMap();
    }

    public void updateMap() {
        int cols = grid.getColumns();
        int rows = grid.getRows();

        int k = 0;
        for (int i = 0; i < rows; i++) {
            ArrayList<Cell> row = grid.get(i);
            for (int j = 0; j < cols; j++) {
                Cell cell = row.get(j);
                JLabel label = cellLabel.get(k);
                k++;
                if (cell.isVisited() && cell != grid.getCrtCell()) {
                    label.setIcon(null);
                    label.setText("");
                }
                else {
                    if (cell.getType() == CellEntityType.Player || grid.getCrtCell() == cell) {
                        label.setText("");
                        label.setIcon(player);
                    }
                    else {
                        label.setIcon(unvisited);
                    }
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == closeSanctuaryMessage) {
                sanctuaryMessage.setVisible(false);
                updateCharacterInfo();
                characterInfo.setVisible(true);
                buttons.setVisible(true);
            }
            else if (e.getSource() == showMapButton) {
                for (Component component : frame.getContentPane().getComponents())
                    if (component.isVisible())
                        frame.remove(component);

                updateCharacterInfo();
                characterInfo.setVisible(true);
                buttons.setVisible(true);
                mapPanel.setVisible(true);
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        mapPanel.requestFocusInWindow();
                    }
                });
            }
            else if (e.getSource() == attackButton || e.getSource() == normalAttackButton) {
                if (e.getSource() == normalAttackButton) {
                    frame.remove(abilitiesPanel);
                    frame.revalidate();
                    frame.repaint();
                }
                Game.getInstance().normalAttack();
            }
            else if (e.getSource() == abilityButton) {
                createAbilitiesPanel();
            }
            else if (selectAbility != null && selectAbility.contains(e.getSource())) {
                frame.remove(abilitiesPanel);
                frame.revalidate();
                frame.repaint();
                int index = selectAbility.indexOf(e.getSource());
                selectAbility.clear();
                Game.getInstance().setCharacterAbility(index);
                Game.getInstance().abilityAttack();
            }
            else if (e.getSource() == returnMenuButton || e.getSource() == exit) {
                for (Component component : frame.getContentPane().getComponents())
                    if (component.isVisible())
                        frame.remove(component);
                Game.getInstance().chooseAnotherCharacter();
            }
            else if (e.getSource() == nextLevel) {
                frame.remove(endLevelPanel);
                Game.getInstance().endLevel();
            }
            else if (!sanctuaryMessage.isVisible() && (e.getSource() == north || e.getSource() == east ||
                    e.getSource() == south || e.getSource() == west)) {
                if (e.getSource() == north)
                    grid.goNorth();
                if (e.getSource() == east)
                    grid.goEast();
                if (e.getSource() == south)
                    grid.goSouth();
                if (e.getSource() == west)
                    grid.goWest();
                updateMap();
                Game.getInstance().checkCellType();
            }
        }
        catch (ImpossibleMove err) {
            System.out.println(err.getMessage());
        }
    }

    public void updateCharacterInfo() {
        name.setText("Name: " + Game.getInstance().getSelectedCharacter().getName());
        level.setText("Level: " + Game.getInstance().getSelectedCharacter().getLevel());
        experience.setText("Experience: " + (int) Game.getInstance().getSelectedCharacter().getXP());
        health.setText("Health: " + Game.getInstance().getSelectedCharacter().getCrtHealth());
        mana.setText("Mana: " + Game.getInstance().getSelectedCharacter().getCrtMana());
    }

    public void displaySanctuaryMessage() {
        characterInfo.setVisible(false);
        buttons.setVisible(false);

        healthMessage.setText("Health regenerated! Current health: " + Game.getInstance().getSelectedCharacter().getCrtHealth() + "\n");
        manaMessage.setText("Mana regenerated! Current mana: " + Game.getInstance().getSelectedCharacter().getCrtMana() + "\n");

        sanctuaryMessage.setVisible(true);
    }
}
