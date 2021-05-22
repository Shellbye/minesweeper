package UserInterface;

import Library.Bomb;

import javax.swing.*;
import java.util.Random;
/**
 * This class provides a method to generate bombs on the board.
 * This class mainly uses recursion algorithm to put bombs on different squares.
 * @version V1.0
 * @author Hephaest
 * @since 2019-03-12 20:18
 */
public class ProduceBombs extends Bomb
{
    /**
     * Create a ProduceBombs instance to generate bombs at the given board.
     * Using recursion algorithm to avoid generating more than one bomb on the same square.
     * @param board the GameBoard upon which user clicks on.
     * @param number the total number of bombs.
     *创建一个ProduceBombs实例在给定的板生成炸弹。
     *使用递归算法避免在同一个正方形上产生多个炸弹。
     *board GameBoard上的用户点击。
     *number炸弹总数。
     */
    public static int bombsNum = 0;
    public static int w, h, s;
    public static int bomb[][];
    public ProduceBombs(GameBoard board, int number)
    {
        super(board);
        bombsNum = number;
        w = board.getWidth();
        h = board.getHeight();
        s = board.getStepN();
        bomb = new int [boardHeight][boardWidth];
        for(int i = 0; i < boardHeight; i++){
            for(int j = 0; j < boardWidth; j++){
                bomb[i][j] = 0;
            }
        }
        int count =0;
        do {
            reproduceBomb();
            count++;
        }while (count < number);
    }
    public ProduceBombs(GameBoard board, int number, int[][] map)
    {
        super(board);
        bombsNum = number;
        w = board.getWidth();
        h = board.getHeight();
        s = board.getStepN();
        bomb = new int [boardHeight][boardWidth];
        for(int x = 0; x < boardHeight; x++){
            for(int y = 0; y < boardWidth; y++){
                bomb[x][y] = map[x][y];
            }
        }
        reproduceBomb2();
    }
    /**
     * This method produce bombs on random square. If the assigned square has already contained a bomb, then
     * reassign a square to receive this bomb by invoking itself.
     */
    public void reproduceBomb2(){
        int i = 0;
        int j = 0;
        try {
            while (i < boardHeight) {
               j = 0;
                while (j <  boardWidth) {
                    SmartSquare square = (SmartSquare) board.getSquareAt(j, i);
                    if (bomb[i][j] == 1) {
                        square.setBombExist(true);
                        square.setTraverse(true);
                    }
                    j++;
                }
                i++;
            }
        }catch (NullPointerException e){
            System.out.println(e);
            board.dispose();
        }
}
    public void reproduceBomb()
    {
        Random r = new Random();

        int xLocation = r.nextInt(boardWidth);
        int yLocation = r.nextInt(boardHeight);
        SmartSquare square = (SmartSquare) board.getSquareAt(xLocation, yLocation);

        if (!square.getBombExist()) {
            bomb[yLocation][xLocation] = 1;
            // mark this square as it has a bomb and been traversed.
            //标记这个广场，因为它有一个炸弹和被穿过。
            square.setBombExist(true);
            square.setTraverse(true);
        } else {
            bomb[yLocation][xLocation] = 0;
            reproduceBomb();
        }
    }

    public static int[][] getBomb(){
        return bomb;
    }
}
