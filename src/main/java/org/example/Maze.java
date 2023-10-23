package org.example;

import org.example.characters.Ghost;
import org.example.characters.Pacman;

import java.awt.*;
import java.util.List;

import static org.example.Board.*;
public class Maze {
    private final Color dotColor = new Color(192, 104, 6);
    private final Color mazeColor = new Color(59, 67, 73);
    public void drawMaze(Graphics2D g2d) {

        short i = 0;
        short j = 0;
        int x, y;


        for (y = 0; y < Board.SCREEN_SIZE; y += Board.BLOCK_SIZE) {
            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {

                g2d.setColor(mazeColor);
                g2d.setStroke(new BasicStroke(2));

                if (screenData[i][j] == 2) {
//                    g2d.fillRect(x, y, BLOCK_SIZE - 1, BLOCK_SIZE - 1);
                    g2d.fillRect(x, y, BLOCK_SIZE , BLOCK_SIZE );
                }

                if (screenData[i][j] == 1) {
                    g2d.setColor(dotColor);
                    g2d.fillRect(x + 11, y + 11, 2, 2);
                }

                i++;
                if (i == screenData.length){
                    i = 0;
                    j++;
                }
            }
        }
    }

    public void checkMaze(Board board) {

        short i = 0;
        boolean finished = false;

        if (levelScore % winScore == 0){
            finished = true;
        }

        if (finished) {

            levelScore = 0;

            if (Board.currentSpeed < Board.maxSpeed) {
                if (currentSpeed < 5) {
                    currentSpeed++;
                }
            }

            board.initLevel();
        }
    }
}