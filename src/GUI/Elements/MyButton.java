package GUI.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyButton extends JButton {
    private Color backgroundColor;
    private Color borderColor;
    private Color colorOver;
    private Color colorClick;
    private int radius;
    private Boolean mouseOver;
    int fontSize;

    public MyButton(String text) {
        setFocusable(false);
        setFocusPainted(false);
        setText(text);

        setBackgroundColor(Variables.green);
        setBorderColor(Color.black);
        setColorClick(Variables.lightGreen);
        setColorOver(colorClick);
        setForeground(new Color(248, 246, 246));

        setRadius(20);
        setFontSize(Variables.fontSize);

        setContentAreaFilled(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(colorOver);
                mouseOver = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(backgroundColor);
                mouseOver = false;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(colorClick);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (mouseOver)
                    setBackground(colorOver);
                else
                    setBackground(backgroundColor);
            }
        });
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        setFont(new Font(Variables.font, Font.BOLD, fontSize));
    }

    public void setAllColors(Color color) {
        setBackgroundColor(color);
        setColorClick(color);
        setColorOver(color);
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        setBackground(backgroundColor);
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setColorOver(Color colorOver) {
        this.colorOver = colorOver;
    }

    public void setColorClick(Color colorClick) {
        this.colorClick = colorClick;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(borderColor);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, radius, radius);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {

    }
}
