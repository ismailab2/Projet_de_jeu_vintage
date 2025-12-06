package fr.ubordeaux.ao.project.view;

import javax.swing.*;
import java.awt.*;
import fr.ubordeaux.ao.project.model.logic.Rules;

public class GameOver extends JFrame {

    public GameOver(Rules rules) {
        setTitle("Game Over");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("GAME OVER", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 40));
        JLabel label1 = new JLabel("Score : " + rules.getPlayerPoint(), SwingConstants.CENTER);
        label1.setFont(new Font("Arial", Font.BOLD, 20));
        label1.setForeground(Color.YELLOW);

        add(label);
        add(label1);
        setVisible(false);
    }
}
