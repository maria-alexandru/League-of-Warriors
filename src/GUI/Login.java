package GUI;

import Entities.Character;
import GUI.Elements.MyButton;
import GUI.Elements.Variables;
import Player.Account;

import App.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Login extends JFrame implements ActionListener {
    private MyButton goToLogin;
    private MyButton quitGame;

    private JPasswordField passwordField;
    private JTextField emailField;
    private JLabel passwordLabel;
    private JLabel emailLabel;
    private JLabel messageLabel;
    private MyButton titleLabel;
    private JCheckBox showPasswordCheckBox;
    private MyButton button;
    private int fontSize = 15;
    private String font = "sansSerif";
    ArrayList<MyButton> buttons = new ArrayList<>();
    private boolean finished = false;

    public Login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setTitle("League of Warriors");
        ImageIcon icon = new ImageIcon("src/Images/swordsman.png");
        setIconImage(icon.getImage());
        setLayout(null);
        setLocationRelativeTo(null);

        createFirstPage();
        setVisible(true);
    }

    public void createFirstPage() {
        ImageIcon backgroundImage = new ImageIcon("src/Images/background image.png");
        int x = getWidth() / 2 - backgroundImage.getIconWidth() + 140 ;
        int y = getHeight() / 2 - backgroundImage.getIconHeight() / 2 - 80;

        MyButton title = new MyButton("League of Warriors");
        title.setFontSize(Variables.fontSize + 5);
        title.setAllColors(Variables.purple);
        title.setForeground(Color.black);
        title.setBounds(x + 40, y - 30,getWidth() - 100, 80);

        JLabel image = new JLabel(backgroundImage);
        image.setBounds(x, y, getWidth() - 150, getHeight() - 50);

        goToLogin = new MyButton("Go to login");
        quitGame = new MyButton("Quit game");
        quitGame.setAllColors(Variables.darkRed);
        quitGame.setBackgroundColor(Variables.red);
        JPanel buttons = new JPanel(new GridLayout(2, 1, 30, 15));
        buttons.setBounds(getWidth() - 190, getHeight() / 2 - 50,150, 120);
        goToLogin.addActionListener(this);
        quitGame.addActionListener(this);

        buttons.add(goToLogin);
        buttons.add(quitGame);

        add(title);
        add(image);
        add(buttons);
    }

    public void createLogin() {
        int x = getWidth() / 2 - 100;
        int y = (getHeight() - 400) / 2;

        titleLabel = new MyButton("LOGIN");
        titleLabel.setColorOver(Variables.green);
        titleLabel.setColorClick(Variables.green);
        titleLabel.setRadius(10);
        titleLabel.setBounds(x, y, 200, 40);
        titleLabel.setFont(new Font(font, Font.BOLD, fontSize + 3));

        emailLabel = new JLabel("Email");
        emailLabel.setBounds(x, y + 60, 100, 40);
        emailLabel.setFont(new Font(font, Font.BOLD, fontSize));
        emailLabel.setForeground(Color.BLACK);

        emailField = new JTextField();
        emailField.setBounds(x, y + 110, 200, 40);
        emailField.setFont(new Font(font, Font.BOLD, fontSize));
        emailField.setForeground(Color.BLACK);
        emailField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        emailField.setBorder(BorderFactory.createCompoundBorder(emailField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(x, y + 150, 100, 40);
        passwordLabel.setFont(new Font(font, Font.BOLD, fontSize));
        passwordLabel.setForeground(Color.BLACK);

        passwordField = new JPasswordField();
        passwordField.setBounds(x, y + 190, 200, 40);
        passwordField.setFont(new Font(font, Font.BOLD, fontSize));
        passwordField.setForeground(Color.BLACK);
        passwordField.setEchoChar('*');
        passwordField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        passwordField.setBorder(BorderFactory.createCompoundBorder(passwordField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        passwordField.addActionListener(this);

        ImageIcon visibleIcon = new ImageIcon("src/Images/visible.png");
        ImageIcon invisibleIcon = new ImageIcon("src/Images/invisible.png");
        showPasswordCheckBox = new JCheckBox();
        showPasswordCheckBox.setIcon(visibleIcon);
        showPasswordCheckBox.setSelectedIcon(invisibleIcon);
        showPasswordCheckBox.setBounds(x + 220, y - 40, 500, 500);
        showPasswordCheckBox.addActionListener(this);
        showPasswordCheckBox.setFocusable(false);

        button = new MyButton("Login");
        button.setBounds(x, y + 250, 100, 40);
        button.addActionListener(this);

        messageLabel = new JLabel("Message");
        messageLabel.setBounds(x, y + 300, 300, 40);
        messageLabel.setVisible(false);
        messageLabel.setFont(new Font(font, Font.BOLD, fontSize));
        messageLabel.setForeground(Variables.red);

        add(titleLabel);
        add(passwordLabel);
        add(passwordField);
        add(showPasswordCheckBox);
        add(emailLabel);
        add(emailField);
        add(button);
        add(messageLabel);
    }

    public static void main(String[] args) {
        Login l = new Login();
        l.setLoginElementsInvisible();
        l.displayCharacters();
        Map m = new Map(l);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == quitGame) {
            System.exit(0);
        }
        else if (e.getSource() == goToLogin) {
            quitGame.setVisible(false);
            for (Component component : getContentPane().getComponents()) {
                if (component.isVisible())
                    component.setVisible(false);
            }
            createLogin();
        }
        else if (e.getSource() == button || e.getSource() == passwordField) {
            Account account = Game.getInstance().getAccountByEmail(emailField.getText());
            String message;
            if (account != null && Game.getInstance().checkPassword(account, new String(passwordField.getPassword()))) {
                Game.getInstance().setSelectedAccount(account);
                setLoginElementsInvisible();
                displayCharacters();
            } else {
                message = "Invalid email or password!";
                passwordField.setText("");
                messageLabel.setText(message);
                messageLabel.setVisible(true);
            }
        }
        else if (e.getSource() == showPasswordCheckBox) {
            if (showPasswordCheckBox.isSelected())
                passwordField.setEchoChar((char) 0);
            else
                passwordField.setEchoChar('*');
        }
        else {
            JButton button = (JButton) e.getSource();
            if (buttons.contains(button)) {
                int index = buttons.indexOf(e.getSource());
                Game.getInstance().setSelectedCharacter(Game.getInstance().getSelectedAccount().getCharacters().get(index));
                for (Component component : getContentPane().getComponents()) {
                    component.setVisible(false);
                }
                revalidate();
                repaint();
                finished = true;
                Game.getInstance().playGame();
            }
        }
    }

    public void setLoginElementsInvisible() {
        passwordField.setVisible(false);
        emailField.setVisible(false);
        emailLabel.setVisible(false);
        passwordLabel.setVisible(false);
        showPasswordCheckBox.setVisible(false);
        messageLabel.setVisible(false);
        button.setVisible(false);
    }

    public void displayCharacters() {
        Account account = Game.getInstance().getSelectedAccount();
        int startX = 150, startY = 120, width = 550, height = 50;

        startX = (int)(getWidth() / 8);
        startY = getHeight() / 5;
        width = getWidth() - 2 * startX;

        int x = getWidth() / 2 - 150;
        titleLabel.setText("Select a character:");
        titleLabel.setBounds(x, 30, 300, 40);
        add(titleLabel);
        titleLabel.setVisible(true);

        buttons.clear();

        for (Character character : account.getCharacters()) {

            JPanel panel = new JPanel();
            panel.setBounds(startX, startY, width, height);
            panel.setLayout(new GridLayout(1, 5, 30, 10));
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
            panel.setBorder(BorderFactory.createCompoundBorder(panel.getBorder(),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));

            JLabel nameLabel = new JLabel();
            nameLabel.setText(character.getName());
            nameLabel.setVisible(true);
            nameLabel.setForeground(Color.BLACK);
            nameLabel.setFont(new Font(font, Font.BOLD, fontSize - 3));
            panel.add(nameLabel);

            JLabel XPLabel = new JLabel();
            XPLabel.setText("XP: " + character.getXP());
            XPLabel.setForeground(Color.BLACK);
            XPLabel.setFont(new Font(font, Font.BOLD, fontSize - 3));
            panel.add(XPLabel);

            JLabel levelLabel = new JLabel();
            levelLabel.setText("Level: " + character.getLevel());
            levelLabel.setForeground(Color.BLACK);
            levelLabel.setFont(new Font(font, Font.BOLD, fontSize - 3));
            panel.add(levelLabel);

            JLabel healthLabel = new JLabel();
            healthLabel.setText("Health: " + character.getCrtHealth());
            healthLabel.setForeground(Color.BLACK);
            healthLabel.setFont(new Font(font, Font.BOLD, fontSize - 3));
            panel.add(healthLabel);

            MyButton selectButton = new MyButton("Select");
            buttons.add(selectButton);
            panel.add(selectButton);
            selectButton.addActionListener(this);
            startY += height + 30;

            add(panel);
        }
        revalidate();
        repaint();
    }

    public boolean isFinished() {
        return finished;
    }
}
