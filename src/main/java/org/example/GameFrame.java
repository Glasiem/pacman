package org.example;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame() {
        setTitle("Pacman Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 480);
        add(new Board());
        setLocationRelativeTo(null);
    }
}