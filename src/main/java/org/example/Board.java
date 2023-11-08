package org.example;

import org.example.characters.Ghost;
import org.example.characters.Pacman;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private Dimension d;
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);
    private List<Ghost> ghosts = new ArrayList<Ghost>();
    private Pacman pacman;

    public static final int BLOCK_SIZE = 24;
    public static final int N_BLOCKS = 15;
    public static final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;

    public static int score;
    public static int levelScore;
    public static boolean inGame = false;
    public static final short[][] levelData = {
            {1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 2, 2, 1, 2, 2, 2, 1, 2, 1, 2, 2, 2, 1, 2, 2, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 2, 2, 1, 2, 1, 2, 2, 2, 2, 2, 1, 2, 1, 2, 2, 1},
            {1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1},
            {2, 1, 2, 1, 2, 2, 2, 1, 2, 1, 2, 2, 2, 1, 2, 1, 2},
            {2, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 2},
            {2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2},
            {2, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 2},
            {2, 1, 2, 1, 2, 1, 2, 2, 2, 2, 2, 1, 2, 1, 2, 1, 2},
            {1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 2, 2, 1, 2, 2, 2, 1, 2, 1, 2, 2, 2, 1, 2, 2, 1},
            {1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1},
            {2, 1, 2, 1, 2, 1, 2, 2, 2, 2, 2, 1, 2, 1, 2, 1, 2},
            {1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1},
            {1, 2, 2, 2, 2, 2, 2, 1, 2, 1, 2, 2, 2, 2, 2, 2, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    private static final int[] validSpeeds = {1, 2, 3, 4, 6, 8};
    public static final int maxSpeed = 6;
    public static int currentSpeed = 3;
    public static short[][] screenData;
    public static Timer timer;
    private Maze maze;
    public static int winScore = 0;

    public Board() {
        for (int i = 0; i < levelData.length; i++) {
            for (int j = 0; j < levelData.length; j++) {
                if(levelData[i][j] == 1) winScore++;
            }
        }
        initVariables();
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new Handler());

        setFocusable(true);

        setBackground(Color.black);
    }

    private void initVariables() {

        screenData = new short[N_BLOCKS][N_BLOCKS];
        d = new Dimension(400, 400);
        for (int i = 0; i < Ghost.getnGhosts(); i++) {
            ghosts.add(new Ghost());
        }
        maze = new Maze();
        pacman = new Pacman(7 * BLOCK_SIZE,11 * BLOCK_SIZE, 0, 0);

        timer = new Timer(40, this);
        timer.start();
    }

    @Override
    public void addNotify() {
        super.addNotify();

        initGame();
    }

    private void playGame(Graphics2D g2d) {

        if (pacman.isDying()) {
            pacman.death(ghosts);
        } else {

            pacman.movePacman();
            pacman.drawPacman(g2d, this);
            for (Ghost ghost: ghosts) {
                ghost.moveGhost(g2d,pacman,this);
            }
            maze.checkMaze(this);
        }
    }

    private void showIntroScreen(Graphics2D g2d) {

        g2d.setColor(new Color(0, 32, 48));
        g2d.fillRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);

        String s = "Press SPACE to start.";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, (SCREEN_SIZE - metr.stringWidth(s)) / 2, SCREEN_SIZE / 2);
    }

    private void drawScore(Graphics2D g) {

        int i;
        String s;

        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255));
        s = "Score: " + score;
        g.drawString(s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);

        for (i = 0; i < pacman.getPacsLeft(); i++) {
            g.drawImage(pacman.getPacman3left(), i * 28 + 8, SCREEN_SIZE + 1, this);
        }
    }

    public void initGame() {
        score = 0;
        initLevel();
        currentSpeed = 3;
    }

    public void initLevel() {

        int i;
        for (i = 0; i < N_BLOCKS; i++) {
            for (int j = 0; j < N_BLOCKS; j++) {
                screenData[i][j] = levelData[j][i];
            }
        }

        continueLevel(ghosts,pacman);
    }

    public static void continueLevel(List<Ghost> ghosts, Pacman pacman) {

        short i;
        int dx = 1;
        int random;

        for (Ghost ghost: ghosts) {
            ghost.setY(4 * BLOCK_SIZE);
            ghost.setX(4 * BLOCK_SIZE);
            ghost.setDy(0);
            ghost.setDx(dx);
            dx = -dx;
            random = (int) (Math.random() * (currentSpeed + 1));

            if (random > currentSpeed) {
                random = currentSpeed;
            }

            ghost.setSpeed(validSpeeds[random]);
        }

        pacman.setX(7 * BLOCK_SIZE);
        pacman.setY(11 * BLOCK_SIZE);
        pacman.setD_x(0);
        pacman.setD_y(0);
        pacman.setReq_dx(0);
        pacman.setReq_dy(0);
        pacman.setView_dx(-1);
        pacman.setView_dy(0);
        pacman.setDying(false);
    }

    private void renderBoard(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        maze.drawMaze(g2d);
        drawScore(g2d);
        pacman.doAnim();

        if (inGame) {
            playGame(g2d);
        } else {
            showIntroScreen(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        renderBoard(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }

    public class Handler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (inGame) {
                if (key == KeyEvent.VK_LEFT) {
                    pacman.setReq_dx(-1);
                    pacman.setReq_dy(0);
                } else if (key == KeyEvent.VK_RIGHT) {
                    pacman.setReq_dx(1);
                    pacman.setReq_dy(0);
                } else if (key == KeyEvent.VK_UP) {
                    pacman.setReq_dx(0);
                    pacman.setReq_dy(-1);
                } else if (key == KeyEvent.VK_DOWN) {
                    pacman.setReq_dx(0);
                    pacman.setReq_dy(1);
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                } else if (key == KeyEvent.VK_PAUSE) {
                    if (timer.isRunning()) {
                        timer.stop();
                    } else {
                        timer.start();
                    }
                }
            } else {
                if (key == KeyEvent.VK_SPACE) {
                    inGame = true;
                    initGame();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == Event.LEFT || key == Event.RIGHT
                    || key == Event.UP || key == Event.DOWN) {
                pacman.setReq_dx(0);
                pacman.setReq_dy(0);
            }
        }
    }
}
