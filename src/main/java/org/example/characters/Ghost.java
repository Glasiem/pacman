package org.example.characters;

import org.example.Board;

import javax.swing.*;
import java.awt.*;

import static org.example.Board.BLOCK_SIZE;
import static org.example.Board.inGame;

public class Ghost {
    private static int N_GHOSTS = 4;
    private Image ghost = new ImageIcon("src/main/resources/images/ghost.png").getImage();
    private int x, y, dx, dy, speed;
    public void moveGhost(Graphics2D g2d, Pacman pacman, Board board) {
        short i;
        int posx;
        int posy;
        int count;

        int[] dx_temp = new int[4], dy_temp = new int[4];

        if (x % BLOCK_SIZE == 0 && y % BLOCK_SIZE == 0) {
            posx = x / BLOCK_SIZE;
            posy = y / BLOCK_SIZE;
            count = 0;

            if ((posx != 0 && Board.screenData[posx - 1][posy] != 2) && dx != 1) {
                dx_temp[count] = -1;
                dy_temp[count] = 0;
                count++;
            }

            if ((posy != 0 && Board.screenData[posx][posy - 1] != 2) && dy != 1) {
                dx_temp[count] = 0;
                dy_temp[count] = -1;
                count++;
            }

            if ((posx != Board.screenData.length - 1 && Board.screenData[posx + 1][posy] != 2) && dx != -1) {
                dx_temp[count] = 1;
                dy_temp[count] = 0;
                count++;
            }

            if ((posy != Board.screenData.length - 1 && Board.screenData[posx][posy + 1] != 2) && dy != -1) {
                dx_temp[count] = 0;
                dy_temp[count] = 1;
                count++;
            }

            if (count == 0) {
                dx = -dx;
                dy = -dy;
            } else {

                count = (int) (Math.random() * count);

                if (count > 3) {
                    count = 3;
                }

                dx = dx_temp[count];
                dy = dy_temp[count];
            }
        }


        x = x + (dx * speed);
        y = y + (dy * speed);
        drawGhost(g2d, x + 1, y + 1, board);

        if (pacman.getX() > (x - 12) && pacman.getX() < (x + 12)
                && pacman.getY() > (y - 12) && pacman.getY() < (y + 12)
                && inGame) {

            pacman.setDying(true);
        }
    }

    public void drawGhost(Graphics2D g2d, int x, int y, Board board) {

        g2d.drawImage(ghost, x, y, board);
    }

    public static int getnGhosts() {
        return N_GHOSTS;
    }

    public Image getGhost() {
        return ghost;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int getSpeed() {
        return speed;
    }

    public static void setnGhosts(int nGhosts) {
        N_GHOSTS = nGhosts;
    }

    public void setGhost(Image ghost) {
        this.ghost = ghost;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
