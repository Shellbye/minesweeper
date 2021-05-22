package UserInterface;

import javax.swing.*;
import java.net.URL;
import javax.swing.JButton;

/**
 * This class provides a representation of a single game square.
 * The class is abstract, and should be extended to provide domain
 * specific functionality.
 * @author joe finney
 */
public abstract class GameSquare extends JButton
{
	/** The x co-ordinate of this square. **/
	//这个正方形x的坐标
	protected int xLocation;

	/** The y co-ordinate of this square. **/
	//这个正方形y的坐标
	protected int yLocation;
	/** The GameBoard upon which this GameSquare resides. **/
	//这个游戏正方形所在的面板
	protected GameBoard board;

	/**
	 * Create a new GameSquare, which can be placed on a GameBoard.
	 * @param x the x co-ordinate of this square on the game board.
	 * @param y the y co-ordinate of this square on the game board.
	 * @param filename file name.
	 * @param board the GameBoard upon which this square resides.
	 */
	/**
	 *
	 * 创建一个新的GameSquare，可以放置在GameBoard上。
	 * x这个方块在棋盘上的x坐标。
	 * y这个方块在棋盘上的y坐标。
	 * filename文件名。
	 * board这个方块所在的游戏板。
	 */
	public GameSquare(int x, int y, URL filename, GameBoard board)
	{
		super(new ImageIcon(filename));
		this.board = board;
		xLocation = x;
		yLocation = y;
	}

	/**
	 * Change the image displayed by this square to the given bitmap.
	 * 将该方块显示的图像更改为给定的位图。
	 *
	 * @param filename the filename of the image to display.
	 *                 文件名要显示的图像的文件名。
	 */
	public void setImage(URL filename)
	{

		this.setIcon(new ImageIcon(filename));
	}

	/**
	 * A method that is invoked when a user clicks on this square.
	 *
	 */
	public abstract void clicked();
}
