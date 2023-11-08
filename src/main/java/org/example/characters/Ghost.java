package org.example.characters;

import org.example.Board;

import javax.swing.*;
import java.awt.*;

import static org.example.Board.BLOCK_SIZE;
import static org.example.Board.inGame;

public class Ghost {
    private static int N_GHOSTS = 4;
    private Image ghost = new ImageIcon("src/main/resources/images/ghost.png").getImage();
    protected int x, y, dx, dy, speed;
    protected final int ATTACK = 280;
    protected final int SCATTER = 100;
    protected int countdownTimer = 0;
    protected boolean isAttacking = false;
    private int lastDirectionY = 0;
    private int lastDirectionX = 0;
    public void moveGhost(Graphics2D g2d, Pacman pacman, Board board) {


        if (x % BLOCK_SIZE == 0 && y % BLOCK_SIZE == 0) {
            getAIMove(pacman);
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

    public boolean moveIsAllowed() {
//        System.out.println(Board.screenData[(x / BLOCK_SIZE + dx)][(y / BLOCK_SIZE + dy)]);
        return ((x/ BLOCK_SIZE + dx >= 0 && (x / BLOCK_SIZE + dx)  < Board.screenData.length) &&
                (y / BLOCK_SIZE+ dy >= 0 && (y / BLOCK_SIZE + dy)  < Board.screenData.length)
                && Board.screenData[(x / BLOCK_SIZE + dx)][(y / BLOCK_SIZE + dy)] != 2);
    }

    protected void flipAttack(){
        if(isAttacking){
            countdownTimer = SCATTER;
        } else{
            countdownTimer = ATTACK;
        }
        isAttacking = !isAttacking;
    }

    protected void getAIMove(Pacman pm)
    {
        // as of now, this ghost just tries to get to you as fast as possible
        // with some work, it could end up being very smart
        // so for now this is just an example for one way of doing this

        int curX = this.x;
        int curY = this.y;
        int targetX, targetY;
        if(!isAttacking){
//            System.out.println("!isAttacking");
            targetX = Board.screenData.length*BLOCK_SIZE - pm.getX();
            targetY = Board.screenData.length*BLOCK_SIZE - pm.getY();
            countdownTimer --;
        } else {
//            System.out.println("Attacking!!!");
            targetX = pm.getX();
            targetY = pm.getY();
            countdownTimer --;
        }

            if(countdownTimer <= 0){
                flipAttack();
            }

            tryMove(curX, curY, targetX, targetY);
        lastDirectionX = dx;
        lastDirectionY = dy;
    }


    protected void tryMove(int curX, int curY, int targetX, int targetY){
        int horizontalDifference = curX - targetX;
        int verticalDifference = curY - targetY;
        int preferredHorizontal = horizontalDifference > 0 ? -1 : 1;
        int preferredVertical = verticalDifference > 0 ? -1 : 1;
//        System.out.println(Math.abs(verticalDifference) - Math.abs(horizontalDifference));
        boolean verticalMoreImportant = Math.abs(verticalDifference) > Math.abs(horizontalDifference);
        if (verticalMoreImportant) {
            dy = preferredVertical;
            dx = 0;
        }
        else {
            dx = preferredHorizontal;
            dy = 0;
        }
        if (!this.moveIsAllowed()) {
            if (verticalMoreImportant) {
//                System.out.println("Vertical");
                if (lastDirectionX == -1 || lastDirectionX == 1) {
                    dx = lastDirectionX;
                    dy = 0;
                    if (!this.moveIsAllowed())
                        dx = dx == -1 ? 1 : -1;
                } else {
//                    System.out.println("Else 1");
                    dx = preferredHorizontal;
                    dy = 0;
                    if (!this.moveIsAllowed()) {
                        dx = preferredHorizontal == -1 ? 1 : -1;
                        if (!this.moveIsAllowed()) {
                            dy = preferredVertical == -1 ? 1 : -1;
                            dx = 0;
                        }
                    }
                }
            } else {
//                System.out.println("Horizontal");
                if ((lastDirectionY == -1) || (lastDirectionY == 1)) {
                    dy = lastDirectionY;
                    dx = 0;
                    if (!this.moveIsAllowed())
                        dy = dy == -1 ? 1 : -1;
                } else {
//                    System.out.println("Else 2");
                    dy = preferredVertical;
                    dx = 0;
                    if (!this.moveIsAllowed()) {
                        dy = preferredVertical == -1 ? 1 : -1;
                        if (!this.moveIsAllowed()) {
                            dx = preferredHorizontal == -1 ? 1 : -1;
                            dy = 0;
                        }
                    }
                }
            }
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
