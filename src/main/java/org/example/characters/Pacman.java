package org.example.characters;

import org.example.Board;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static org.example.Board.*;

public class Pacman {
    private final Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
    private final Image pacman3up, pacman3down, pacman3left, pacman3right;
    private final Image pacman4up, pacman4down, pacman4left, pacman4right;

    private final int PAC_ANIM_DELAY = 2;
    private final int PACMAN_ANIM_COUNT = 4;

    private final int PACMAN_SPEED = 6;

    private int pacAnimCount = PAC_ANIM_DELAY;
    private int pacAnimDir = 1;
    private int pacmanAnimPos = 0;

    private boolean dying = false;

    private int req_dx, req_dy, view_dx, view_dy;
    private int x, y, d_x, d_y;
    private int pacsLeft;
    public Pacman(int x, int y, int d_x, int d_y) {
        this.pacsLeft = 3;
        this.x = x;
        this.y = y;
        this.d_x = d_x;
        this.d_y = d_y;
        pacman1 = new ImageIcon("src/main/resources/images/pacman.png").getImage();
        pacman2up = new ImageIcon("src/main/resources/images/up1.png").getImage();
        pacman3up = new ImageIcon("src/main/resources/images/up2.png").getImage();
        pacman4up = new ImageIcon("src/main/resources/images/up3.png").getImage();
        pacman2down = new ImageIcon("src/main/resources/images/down1.png").getImage();
        pacman3down = new ImageIcon("src/main/resources/images/down2.png").getImage();
        pacman4down = new ImageIcon("src/main/resources/images/down3.png").getImage();
        pacman2left = new ImageIcon("src/main/resources/images/left1.png").getImage();
        pacman3left = new ImageIcon("src/main/resources/images/left2.png").getImage();
        pacman4left = new ImageIcon("src/main/resources/images/left3.png").getImage();
        pacman2right = new ImageIcon("src/main/resources/images/right1.png").getImage();
        pacman3right = new ImageIcon("src/main/resources/images/right2.png").getImage();
        pacman4right = new ImageIcon("src/main/resources/images/right3.png").getImage();
    }
    public void movePacman() {

        int posx,posy;

        if (req_dx == -d_x && req_dy == -d_y) {
            d_x = req_dx;
            d_y = req_dy;
            view_dx = d_x;
            view_dy = d_y;
        }

        if (x % Board.BLOCK_SIZE == 0 && y % Board.BLOCK_SIZE == 0) {
            posx = x / Board.BLOCK_SIZE;
            posy = y / Board.BLOCK_SIZE;

            if (screenData[posx][posy] == 1) {
                screenData[posx][posy] = 0;
                score++;
                levelScore++;
            }

            if (req_dx != 0 || req_dy != 0) {
                if (!((req_dx == -1 && req_dy == 0 && (posx == 0 || screenData[posx-1][posy] == 2))
                        || (req_dx == 1 && req_dy == 0 && (posx == screenData.length-1 || screenData[posx+1][posy] == 2))
                        || (req_dx == 0 && req_dy == -1 && (posy == 0 || screenData[posx][posy-1] == 2))
                        || (req_dx == 0 && req_dy == 1 && (posy == screenData.length-1 || screenData[posx][posy+1] == 2)))) {
                    d_x = req_dx;
                    d_y = req_dy;
                    view_dx = d_x;
                    view_dy = d_y;
                }
            }

            // Check for standstill
            if ((d_x == -1 && d_y == 0 && (posx == 0 || screenData[posx-1][posy] == 2))
                    || (d_x == 1 && d_y == 0 && (posx == screenData.length-1 || screenData[posx+1][posy] == 2))
                    || (d_x == 0 && d_y == -1 && (posy == 0 || screenData[posx][posy-1] == 2))
                    || (d_x == 0 && d_y == 1 && (posy == screenData.length-1 || screenData[posx][posy+1] == 2))) {
                d_x = 0;
                d_y = 0;
            }
        }
        x = x + PACMAN_SPEED * d_x;
        y = y + PACMAN_SPEED * d_y;
    }

    public void death(List<Ghost> ghosts) {

        pacsLeft--;
        levelScore = 0;

        if (pacsLeft == 0) {
            Board.inGame = false;
        }

        Board.continueLevel(ghosts, this);
    }

    public void doAnim() {

        pacAnimCount--;

        if (pacAnimCount <= 0) {
            pacAnimCount = PAC_ANIM_DELAY;
            pacmanAnimPos = pacmanAnimPos + pacAnimDir;

            if (pacmanAnimPos == (PACMAN_ANIM_COUNT - 1) || pacmanAnimPos == 0) {
                pacAnimDir = -pacAnimDir;
            }
        }
    }

    public void drawPacman(Graphics2D g2d, Board board) {

        if (view_dx == -1) {
            drawPacmanLeft(g2d, board);
        } else if (view_dx == 1) {
            drawPacmanRight(g2d, board);
        } else if (view_dy == -1) {
            drawPacmanUp(g2d, board);
        } else {
            drawPacmanDown(g2d, board);
        }
    }

    private void drawPacmanUp(Graphics2D g2d, Board board) {

        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2up, x + 1, y + 1, board);
                break;
            case 2:
                g2d.drawImage(pacman3up, x + 1, y + 1, board);
                break;
            case 3:
                g2d.drawImage(pacman4up, x + 1, y + 1, board);
                break;
            default:
                g2d.drawImage(pacman1, x + 1, y + 1, board);
                break;
        }
    }

    private void drawPacmanDown(Graphics2D g2d, Board board) {

        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2down, x + 1, y + 1, board);
                break;
            case 2:
                g2d.drawImage(pacman3down, x + 1, y + 1, board);
                break;
            case 3:
                g2d.drawImage(pacman4down, x + 1, y + 1, board);
                break;
            default:
                g2d.drawImage(pacman1, x + 1, y + 1, board);
                break;
        }
    }

    private void drawPacmanLeft(Graphics2D g2d, Board board) {

        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2left, x + 1, y + 1, board);
                break;
            case 2:
                g2d.drawImage(pacman3left, x + 1, y + 1, board);
                break;
            case 3:
                g2d.drawImage(pacman4left, x + 1, y + 1, board);
                break;
            default:
                g2d.drawImage(pacman1, x + 1, y + 1, board);
                break;
        }
    }

    private void drawPacmanRight(Graphics2D g2d, Board board) {

        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2right, x + 1, y + 1, board);
                break;
            case 2:
                g2d.drawImage(pacman3right, x + 1, y + 1, board);
                break;
            case 3:
                g2d.drawImage(pacman4right, x + 1, y + 1, board);
                break;
            default:
                g2d.drawImage(pacman1, x + 1, y + 1, board);
                break;
        }
    }

    public int getReq_dx() {
        return req_dx;
    }

    public int getReq_dy() {
        return req_dy;
    }

    public int getView_dx() {
        return view_dx;
    }

    public int getView_dy() {
        return view_dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getD_x() {
        return d_x;
    }

    public int getD_y() {
        return d_y;
    }

    public int getPacsLeft() {
        return pacsLeft;
    }

    public void setReq_dx(int req_dx) {
        this.req_dx = req_dx;
    }

    public void setReq_dy(int req_dy) {
        this.req_dy = req_dy;
    }

    public void setView_dx(int view_dx) {
        this.view_dx = view_dx;
    }

    public void setView_dy(int view_dy) {
        this.view_dy = view_dy;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setD_x(int d_x) {
        this.d_x = d_x;
    }

    public void setD_y(int d_y) {
        this.d_y = d_y;
    }

    public void setPacsLeft(int pacsLeft) {
        this.pacsLeft = pacsLeft;
    }

    public boolean isDying() {
        return dying;
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }



    public Image getPacman1() {
        return pacman1;
    }

    public Image getPacman2up() {
        return pacman2up;
    }

    public Image getPacman2left() {
        return pacman2left;
    }

    public Image getPacman2right() {
        return pacman2right;
    }

    public Image getPacman2down() {
        return pacman2down;
    }

    public Image getPacman3up() {
        return pacman3up;
    }

    public Image getPacman3down() {
        return pacman3down;
    }

    public Image getPacman3left() {
        return pacman3left;
    }

    public Image getPacman3right() {
        return pacman3right;
    }

    public Image getPacman4up() {
        return pacman4up;
    }

    public Image getPacman4down() {
        return pacman4down;
    }

    public Image getPacman4left() {
        return pacman4left;
    }

    public Image getPacman4right() {
        return pacman4right;
    }

    public int getPAC_ANIM_DELAY() {
        return PAC_ANIM_DELAY;
    }

    public int getPACMAN_ANIM_COUNT() {
        return PACMAN_ANIM_COUNT;
    }

    public int getPACMAN_SPEED() {
        return PACMAN_SPEED;
    }

    public int getPacAnimCount() {
        return pacAnimCount;
    }

    public int getPacAnimDir() {
        return pacAnimDir;
    }

    public int getPacmanAnimPos() {
        return pacmanAnimPos;
    }

}