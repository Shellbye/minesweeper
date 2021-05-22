package UserInterface;

import Library.TimeChecker;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;

/**
 * This class inherits from the GameSquare class.
 * This class implements the methods from ActionListener and MouseListener to respond different click events.
 * Each square has its own unique representation of coordinates and attributes.
 * This class counts the number of its surrounding bombs once user clicks the square.
 * This class shows a pop-up window when the game failed or user succeed.
 * *这个类继承自GameSquare类。
 * *这个类实现了ActionListener和MouseListener的方法来响应不同的点击事件。
 * *每个方块都有自己独特的坐标和属性表示。
 * *当玩家点击这个方块时，这个职业会计算周围炸弹的数量。
 * *当游戏失败或用户成功时，这个类显示一个弹出窗口。
 * @author Hephaest
 * @since 3/21/2019 8:41 PM
 */
public class SmartSquare extends GameSquare implements MouseListener, TimeChecker
{
	/** The bomb existence of this square. **/
	//这个广场的炸弹存在。
	private boolean thisSquareHasBomb;

	/** User sets a red flag on this square. **/
	//用户在这个正方形上设置了一个红旗.
	private boolean guessThisSquareIsBomb;

	/** The traversal state of this square. **/
	//这个方块的遍历状态。
	private boolean thisSquareHasTraversed;
	/** The x co-ordinate of this square. **/
	//这个正方形的x坐标。
	private int xLocation;

	/** The y co-ordinate of this square. **/
	//这个正方形的y坐标。
	private int yLocation;

	/** The y co-ordinate of this square. **/
	private long startTime;
	/**
	 * Create a new SmartSquare instance, which can be placed on a GameBoard.
	 * 创建一个新的SmartSquare实例，它可以放在一个GameBoard上。
	 * @param x the x co-ordinate of this square on the game board.
	 *          这个方块在棋盘上的X坐标。
	 * @param y the y co-ordinate of this square on the game board.
	 *          这个方块在棋盘上的Y坐标。
	 * @param board the GameBoard upon which this square resides.
	 *          棋盘这个方块所在的游戏棋盘。
	 */
	public SmartSquare(int x, int y, GameBoard board)
	{
		// Paint this square as gray block when initialization.
		//初始化时将该正方形绘制为灰色块。
		super(x, y, SmartSquare.class.getResource("/block.png"), board);

		// Assign coordinates to this square.
		//为这个正方形分配坐标。
		xLocation = x;
		yLocation = y;

		// Initialize attributes
		thisSquareHasBomb = false;
		thisSquareHasTraversed = false;
		guessThisSquareIsBomb = false;
		startTime = 0;

		// add right mouse listener
		addMouseListener(this);
	}

	/**
	 * Set bomb existence of the SmartSquare instance as a given result.
	 * 将SmartSquare实例的炸弹存在性设置为给定结果。
	 * @param result the bomb existence of this SmartSquare instance.
	 *         返回这个炸弹存在的SmartSquare实例
	 */
	protected void setBombExist(boolean result)
	{

		thisSquareHasBomb = result;
	}

	/**
	 * Return bomb existence of the SmartSquare instance.
	 * 返回SmartSquare实例的bomb存在。
	 * @return the bomb existence of this SmartSquare instance.
	 * 这个SmartSquare实例的炸弹存在。
	 */
	protected boolean getBombExist()
	{

		return thisSquareHasBomb;
	}
	protected boolean getArchive()
	{

		return thisSquareHasBomb;
	}
	/**
	 * Return traversal state of the SmartSquare instance.
	 * 返回SmartSquare实例的遍历状态。
	 * @return the traversal state of this SmartSquare instance.
	 * 49/5000
	 * SmartSquare实例的遍历状态。
	 */
	protected boolean getTraverse()
	{

		return thisSquareHasTraversed;
	}

	/**
	 * Set traversal state of the SmartSquare instance as a given result.
	 * 将SmartSquare实例的遍历状态设置为给定的结果。
	 * @param result the traversal state of this SmartSquare instance.
	 *      返回SmartSquare实例的遍历状态。
	 */
	protected void setTraverse(boolean result)
	{
		thisSquareHasTraversed = result;
	}

	/**
	 * Return a boolean value whether user sets a red flag in this square or not.
	 *返回一个布尔值，不管用户是否在该方块中设置了一个红色标志。
	 * @return the state whether this square has been marked as a bomb or not.
	 * 问这个广场是否被标记为炸弹。
	 */
	protected boolean getGuessThisSquareIsBomb()
	{

		return guessThisSquareIsBomb;
	}

	/**
	 * Set the start time of the game.
	 * @param time the time presented as milliseconds.
	 */
	protected void setStartTime(long time)
	{

		startTime = time;
	}

	/**
	 * Return the game start time.
	 * @return the time presented as milliseconds.
	 */
	protected long getStartTime()
	{

		return startTime;
	}

	/**
	 * An implementation method of abstract method (from GameSquare).
	 * Once get click event, detect bombs and expand into empty space.
	 * 抽象方法的实现方法。
	 * *一旦得到点击事件，检测炸弹和扩展到空的空间。
	 */
	public void clicked()
	{

		CheckSquare cq = new CheckSquare(board);

		guessThisSquareIsBomb = false;

		if(thisSquareHasBomb)
		{
			/*
			 * If this square contains a bomb, show the bomb image.
			 * 如果这个方块包含炸弹，显示炸弹图像。
			 * Then display the selection window
			 * 然后显示选择窗口
			 */
			if(GameBoard.getT() == 1){
				JOptionPane.showMessageDialog(board, "第一次踩雷 重新开始", "RE",JOptionPane.WARNING_MESSAGE);
				board.dispose();
				new Menu("Minesweeper");
			}
			//载入炸弹图片
			setImage(SmartSquare.class.getResource("/bombReveal.png"));
			//计算游戏时间
			long costTime = System.currentTimeMillis() - ((SmartSquare) board.getSquareAt(0, 0)).getStartTime();
			//输出炸弹图片
			cq.showBomb(xLocation, yLocation);
			//弹出窗口
			if(GameBoard.getUser() == 1) {
				int n = GameBoard.getScoreOne();
				n = n -1;
				if(n <= 0) n = 0;
				GameBoard.setScoreOne(n);
			}else if(GameBoard.getUser() == 2){
				int n = GameBoard.getScoreTwo();
				n = n - 1;
				if(n <= 0) n = 0;
				GameBoard.setScoreTwo(n);
			}
			gameOver(cq);
			ProduceBombs.bombsNum--;
		} else {
			thisSquareHasTraversed = false;
			/*
			 * If this square doesn't contain a bomb, calculate its surrounding bombs.
			 * 如果这个广场没有炸弹，计算一下它周围的炸弹。
			 * If this square has zero bombs in its surrounding squares,
			 * 如果这个广场周围没有炸弹，
			 * expanding into empty space until the surrounding of the space has at least one bomb
			 * 扩张到空旷的空间，直到周围的空间至少有一颗炸弹
			 * or the space touches the window's boundary.
			 * 或者空间接触到窗户的边界。
			 */
			//计算周国炸弹数量
			if (GameBoard.getUser() == 1) {
				int n = GameBoard.getScoreOne();
				n = n + 1;
				GameBoard.setScoreOne(n);
			} else if (GameBoard.getUser() == 2) {
				int n = GameBoard.getScoreTwo();
				n = n + 1;
				GameBoard.setScoreTwo(n);
			}
			cq.countBomb(xLocation, yLocation);
			gameOver(cq);
		}
	}
	public void gameOver(CheckSquare cq){
		if (cq.isSuccess() == 1) {
			long costTime = System.currentTimeMillis() - ((SmartSquare) board.getSquareAt(0, 0)).getStartTime();
			cq.showBomb(xLocation, yLocation);
			window("User1 win continue game?" + TimeChecker.calculateTime(costTime) ,"Congratulations");
		}
		if (cq.isSuccess() == 2) {
			long costTime = System.currentTimeMillis() - ((SmartSquare) board.getSquareAt(0, 0)).getStartTime();
			cq.showBomb(xLocation, yLocation);
			window("User2 win continue game?" + TimeChecker.calculateTime(costTime),"Congratulations");
		}
		if (cq.isSuccess() == 3) {
			long costTime = System.currentTimeMillis() - ((SmartSquare) board.getSquareAt(0, 0)).getStartTime();
			cq.showBomb(xLocation, yLocation);
			window("Both win continue game? " + TimeChecker.calculateTime(costTime),"Congratulations");
		}
	}

	/**
	 * A method to achieve pop-up window.
	 * 一种实现弹出窗口的方法。
	 * @param msg the message to display on the window.
	 *            MSG消息显示在窗口上。
	 * @param title the title string for the window.
	 *              标题窗口的标题字符串。

	 */
	public void window(String msg, String title)
	{

		int n = JOptionPane.showConfirmDialog(board, msg, title,  JOptionPane.YES_NO_OPTION);
		if(n == JOptionPane.YES_OPTION){
			new Menu("Minesweeper");
		}
			// Close this window after user making a choice
		if(n == JOptionPane.NO_OPTION) {
			board.dispose();
		}
	}

	/**
	 * An implementation method to respond right-click events.
	 * 响应右击事件的实现方法。
	 * @param e the event when user clicks on this square.
	 *          E当用户点击这个方块时的事件。
	 */
	@Override
	public void mouseClicked(MouseEvent e)
	{
		// If user right-click on this square.
		//如果用户右键单击这个方块。
			if (e.getButton() == MouseEvent.BUTTON3) {
				int clickCount = e.getClickCount();
				// Show red flag.
				if (clickCount == 1) {
					if(!thisSquareHasBomb) {
						if(GameBoard.getUser() == 1){
							 int error = GameBoard.getError1();
							 error++;
							 int n = GameBoard.getCountStep1();
							 n = n - 1;
							 if(n <= 1) n = 1;
							 GameBoard.setCountStep1(n);
							 GameBoard.setError1(error);
							JOptionPane.showMessageDialog(board, "User1 标记错误, 失误数+1", "标记",JOptionPane.WARNING_MESSAGE);
						}else if(GameBoard.getUser() == 2){
							int error = GameBoard.getError2();
							error++;
							int n = GameBoard.getCountStep2();
							n = n - 1;
							if(n <= 1) n = 1;
							GameBoard.setCountStep2(n);
							GameBoard.setError2(error);
							JOptionPane.showMessageDialog(board, "User2, 失误数+1", "标记",JOptionPane.WARNING_MESSAGE);
						}
					}
					else{
						if(thisSquareHasBomb){
							if(GameBoard.getUser() == 1) {
								int n = GameBoard.getScoreOne();
								n++;
								GameBoard.setScoreOne(n);
							}else if(GameBoard.getUser() == 2){
								int n = GameBoard.getScoreTwo();
								n ++;
								GameBoard.setScoreTwo(n);
							}
						}
						setImage(SmartSquare.class.getResource("/redFlag.png"));
						ProduceBombs.bombsNum--;
						guessThisSquareIsBomb = true;
					}
				}

				// Show question mark.
				if (clickCount == 2) {
					setImage(SmartSquare.class.getResource("/questionMark.png"));
					guessThisSquareIsBomb = false;
				}
			}
	}


	// The following mouse events are not going to be handled in this class.
	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}