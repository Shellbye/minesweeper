package UserInterface;
/**
 * @author Hephaest
 * @since 3/21/2019 8:41 PM
 * This class provides a way to count the total number of bombs which surrounds the given square.
 * 这个类提供了一种计算围绕给定方块的炸弹总数的方法。
 */
public class CheckSquare {
    /**
     * The GameBoard instance
     **/
    //棋盘定义
    private GameBoard board;

    /**
     * The height of this GameBoard instance
     **/
    //这个GameBoard实例的高度
    private int boardHeight;
    /**
     * The width of this GameBoard instance
     **/
    //这个GameBoard实例的高度
    private int boardWidth;

    private static final int[] distantX = {-1, 0, 1};
    private static final int[] distantY = {-1, 0, 1};

    /**
     * Create a CheckSquare instance contained with the given board.
     * 创建包含给定板的CheckSquare实例。
     *
     * @param board the GameBoard upon which user clicks on.
     *              board用户点击的GameBoard。
     */
    public CheckSquare(GameBoard board) {
        this.board = board;
        // Both height and width of the board should remove its padding values.
        //板的高度和宽度都应该删除其填充值。
        boardHeight = (board.getHeight() - 100) / 20;
        boardWidth = (board.getWidth() - 20) / 20;
    }

    /**
     * Returns the check result of the given position.
     * 返回给定位置的检查结果。
     *
     * @param x the x co-ordinate of the given square.
     *          给定正方形的X坐标。
     * @param y the y co-ordinate of the given square.
     *          给定正方形的Y坐标。
     * @return a boolean value of the check result.
     * 检查结果的布尔值
     */
    private boolean hasKickedBoundary(int x, int y) {
        return x < 0 || x >= boardWidth || y < 0 || y >= boardHeight;
    }

    /**
     * Returns the check result whether user has found out all bombs.
     * 返回用户是否找到所有炸弹的检查结果。
     *
     * @return a boolean value of the check result.
     */
    protected int isSuccess() {

        int res1 = GameBoard.getScoreOne() - GameBoard.getScoreTwo();
        int res2 = GameBoard.getScoreTwo() - GameBoard.getScoreOne();

        if (res1 >= ProduceBombs.bombsNum) {
            return 1;
        } else if (res2 >=  ProduceBombs.bombsNum) {
            return 2;
        }
        if ( ProduceBombs.bombsNum == 0 ) {
            int x = GameBoard.getError1();
            int y = GameBoard.getError2();
            if (x == y) {
                return 3;
            } else if (GameBoard.getError1() > GameBoard.getError2()) {
                return 1;
            } else if (GameBoard.getError2() > GameBoard.getError1()) {
                return 2;
            } else {
                return 0;
            }
        }
        return -1;
    }



    /**
     * This method reveals all bombs on the board, examine the square where user guesses it has a bomb
     * 这个方法显示了所有的炸弹在板上，检查用户猜测它有一个炸弹的正方形
     * @param currentX the x co-ordinate of the given square.
     *                 currentX给定方块的x坐标。
     * @param currentY the y co-ordinate of the given square.
     *                 当前给定正方形的y坐标。
     */
    protected void showBomb(int currentX, int currentY)
    {
        /**for (int y = 0; y < boardHeight; y++)
        {
            for (int x = 0; x < boardWidth; x++)
            {
                if (currentX == x && currentY == y){}
                else if (((SmartSquare) board.getSquareAt(x, y)).getBombExist())
                    board.getSquareAt(x, y).setImage(CheckSquare.class.getResource("/bomb.png"));
                else if(((SmartSquare) board.getSquareAt(x, y)).getGuessThisSquareIsBomb())
                    board.getSquareAt(x, y).setImage(CheckSquare.class.getResource("/flagWrong.png")); // Wrong guess!
            }
        }*/
        if (((SmartSquare) board.getSquareAt(currentX, currentY)).getBombExist())
        board.getSquareAt(currentX, currentY).setImage(CheckSquare.class.getResource("/bomb.png"));
        else if(((SmartSquare) board.getSquareAt(currentX, currentY)).getGuessThisSquareIsBomb())
        board.getSquareAt(currentX, currentY).setImage(CheckSquare.class.getResource("/flagWrong.png")); // Wrong guess!
    }
    /**
     * This method counts the total number of bombs which surrounds the given square.
     * 该方法计算围绕给定正方形的炸弹总数。
     * If there is no bombs surrounds the square, paint this square as blank then expand its surrounding squares
     * 如果没有炸弹包围这个正方形，将这个正方形画成空白，然后扩展它周围的正方形
     * util find bombs of the surrounding squares are not empty. This method is implemented by recursion algorithm.
     * Util发现炸弹周围的广场不是空的。该方法采用递归算法实现。
     *  currentX the x co-ordinate of the given square.
     *  currentY the y co-ordinate of the given square.
     */
    protected void countBomb(int currentX, int currentY)
    {
        // Ensure count start at 0 once this method is invoked.
        //一旦调用此方法，确保count从0开始。
        int count = 0;
        SmartSquare currentObject;

        if (hasKickedBoundary(currentX, currentY))
            return; //Skip iteration.
        else if(((SmartSquare)board.getSquareAt(currentX, currentY)).getTraverse())
            return; //Skip iteration.
        else {
            // Declare a SmartSquare instance.
            SmartSquare squareObject;

            // Get the current square.
            currentObject = (SmartSquare)board.getSquareAt(currentX, currentY);
            currentObject.setTraverse(true);

            /*
             * Check surrounding 8 squares:
             * 检查周围8个方格:
             * If the square has touch the boundary, skip to next iteration of the loop.
             * 如果方块触及边界，则跳到循环的下一次迭代。
             * Else if the square is itself, then it's unnecessary to count. Just skip to next iteration of the loop.
             * 否则，如果正方形是它本身，就没有必要计算了。只需跳到循环的下一个迭代。
             * Else check whether this surrounding square contains a bomb. If it does, count accumulation.
             * 否则检查周围的广场是否有炸弹。如果是的话，计算累积。
             */
            for (int x : distantX)
            {
                for (int y: distantY)
                {
                    if (hasKickedBoundary(currentX + x, currentY + y)){}
                    else if (x == 0 && y == 0){}
                    else{
                       squareObject = (SmartSquare)board.getSquareAt(currentX + x, currentY + y);
                        count = squareObject.getBombExist() ? count + 1 : count;
                    }
                }
            }
        }

        /**
         * If count result is zero, then replace this square with its surrounding
         * 如果count result为零，则用其周围的方块替换该方块
         * squares and invoke itself to find the bombs again.
         * 然后调用自己再次找到炸弹。
         */
        if (count != 0) {
            currentObject.setImage(CheckSquare.class.getResource("/" + count + ".png"));
        }
        else {
            // Paint current square as blank.
            currentObject.setImage(CheckSquare.class.getResource("/0.png"));
            countBomb(currentX - 1, currentY -1); // Upper left
            countBomb(currentX, currentY -1); // Above
            countBomb(currentX + 1, currentY -1); // Upper right
            countBomb(currentX - 1, currentY); // Left side
            countBomb(currentX + 1, currentY); // Right side
            countBomb(currentX - 1, currentY + 1); // Lower left
            countBomb(currentX, currentY + 1); // Below
            countBomb(currentX + 1, currentY + 1); // Lower right
        }
    }
}
