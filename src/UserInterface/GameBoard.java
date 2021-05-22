package UserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * This class provides a graphical model of a board game.
 * The class creates a rectangular panel of clickable squares,
 * of type SmartSquare. If a square is clicked by the user, a
 * callback method is invoked upon the corresponding SmartSquare instance.
 * The class is intended to be used as a basis for tile based games.
 * *这个类提供了一个棋盘游戏的图形模型。
 * *类创建一个矩形面板的可点击的正方形，
 * SmartSquare类型的*。如果用户点击一个正方形，则a
 * *回调方法在对应的SmartSquare实例上被调用。
 * *类是用来作为基于tile的游戏的基础。
 * @author joe finney
 */
public class GameBoard extends JFrame implements ActionListener
{
	private JPanel text = new JPanel();
	private JPanel text2 = new JPanel();
	private JPanel bombPanel = new JPanel();
	private JPanel button = new JPanel();
	private JPanel m = new JPanel();
	private JPanel boardPanel = new JPanel();
	private JPanel funPanel = new JPanel();
	private JLabel user1 = new JLabel("User1");
	private JLabel score1 = new JLabel();
	private JLabel score2 = new JLabel();
	private JLabel errorr1 = new JLabel();
	private JLabel errorr2 = new JLabel();
	private JLabel user2 = new JLabel("User2");
	private JLabel bombLabel = new JLabel("bombs");
	private JButton in;
	private JButton out;
	private int boardHeight;//界面框高
	private int boardWidth;//界面框宽
	private GameSquare[][] board;//
	//用户输入切换
	private static int User;
	private static int countStep1 = 0;
	private static int countStep2 = 0;
	public  static int scoreOne;
	public static int scoreTwo;
	private int stepN;
	private static int error1;
	private static int error2;
	//第一次是否踩雷
	private static int T;
	private int bomb;
	/**
	 * Create a new game board of the given size.
	 * As soon as an instance of this class is created, it is visualized
	 * on the screen.
	 *创建一个给定大小的新游戏板。
	 * 一旦这个类的实例被创建，它就被可视化了
	 * *在屏幕上。
	 * title the name printed in the window bar.
	 *  width the width of the game area, in squares.
	 *  height the height of the game area, in squares.
	 * 标题在窗口栏打印的名称。
	 *width游戏区域的宽度，以方格表示。
	 *高度游戏区域的高度，以方格表示。
	 */
	public GameBoard(String title, int width, int height, int stepN, int bombs)
	{
		super();
		this.boardWidth = width;
		this.boardHeight = height;
		this.stepN = stepN;
		bomb = bombs;
		// Create game state
		this.board = new GameSquare[width][height];//创建游戏
		// Set up window
		setTitle(title);
		setSize(20 + width * 20,100 + height * 20);
		boardPanel.setPreferredSize(new Dimension(20 + width * 20,20 + height * 20));
		setContentPane(m);
		m.setLayout(new BorderLayout());
		m.add(boardPanel, BorderLayout.CENTER);
		m.add(funPanel, BorderLayout.SOUTH);
		m.add(bombPanel, BorderLayout.NORTH);
		funPanel.setLayout(new BorderLayout());
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		boardPanel.setLayout(new GridLayout(height+1 ,width));
		bombPanel.setLayout(new BorderLayout());
		button.setLayout(new GridLayout(2 ,1));
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				board[x][y] = new SmartSquare(x, y, this);
				board[x][y].addActionListener(this);
				boardPanel.add(board[x][y]);
			}
		}

		countStep1 = stepN;
		countStep2 = stepN;

		in = new JButton("存图");
		in.addActionListener(this);
		out = new JButton("查雷");
		out.addActionListener(this);
		button.add(in);
		button.add(out);

		text.add(user1);
		text.add(score1);
		text.add(errorr1);
		text2.add(user2);
		text2.add(score2);
		text2.add(errorr2);
		funPanel.add(text, BorderLayout.CENTER);
		funPanel.add(text2,BorderLayout.NORTH);
		funPanel.add(button, BorderLayout.EAST);

		bombLabel.setText("bombs");
		bombPanel.add(bombLabel, BorderLayout.WEST);
		// make our window visible 使窗口可见
		setVisible(true);
	}
	public static void setScoreOne(int n){ scoreOne = n; }
	public static void setScoreTwo(int n){
		scoreTwo = n;
	}
	public static int getScoreOne(){ return scoreOne; }
	public static int getScoreTwo(){
		return scoreTwo;
	}
	public static int getUser(){ return User; }
	public static void setUser(int n){ User = n;}
	public static void setError2(int n){ error2 = n; }
	public static int getError2(){
		return error2;
	}
	public static int getError1(){
		return error1;
	}
	public static void setError1(int n){
		error1 = n;
	}
	public static void setT(int n){ T = n; }
	public static int getT(){ return T;}
	public  int getStepN(){return stepN; }
	public static int getCountStep1(){return countStep1;}
	public static void setCountStep1(int n){countStep1 = n; }
	public static int getCountStep2(){return countStep2;}
	public static void setCountStep2(int n){countStep2 = n;}

	/**
	 * Returns the GameSquare instance at a given location.
	 * x the x co-ordinate of the square requested.
	 * y the y co-ordinate of the square requested.
	 * the GameSquare instance at a given location
	 * if the x and y co-ordinates are valid - null otherwise.
	 */
	/**
	*返回给定位置的GameSquare实例。
	*x请求的正方形的x坐标。
	*y要求的正方形的y坐标。
	*返回给定位置的GameSquare实例
	*如果x和y坐标有效，否则为空。
	*/
	public GameSquare getSquareAt(int x, int y)
	{
		if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight)
			return null;
		return board[x][y];
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == in) {
			//to do
			try{
				int i = 0, j = 0;
				int bo[][] = ProduceBombs.getBomb();
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File("T.txt")));
				BufferedWriter bw2 = new BufferedWriter(new FileWriter(new File("shu.txt")));
				bw.write(String.valueOf(Menu.T));bw.write(String.valueOf(boardWidth));
				bw.write(String.valueOf(boardHeight));bw.write(String.valueOf(bomb));
				while(i < bo.length){
					j = 0;
					while(j < bo[i].length){
						bw2.write(String.valueOf(bo[i][j]));
						j++;
					}
					i++;
				}
				bw.close();
				bw2.close();
			}catch (FileNotFoundException fne){
				fne.printStackTrace();;
			}catch (IOException ioe){
				ioe.printStackTrace();
			}catch (Exception E) {
				E.printStackTrace();
			}
			this.dispose();
			return;
		} else if(out == e.getSource()){
			int i = 0;
			int j = 0;
			CheckSquare cq = new CheckSquare(this);
			while(i < boardHeight) {
				j = 0;
				while(j < boardWidth){
					cq.showBomb(board[j][i].xLocation, board[j][i].yLocation);
					j++;
				}
				i++;
			}
		}
		else if (User == 1 && countStep1 >= 1) {
			if(countStep1 == 1){
				User = 2;
				countStep2 = stepN;
			}
			GameSquare b = (GameSquare)e.getSource();//获取按下的按钮
			b.clicked();
			countStep1--;
		}else if(User == 2 && countStep2 >= 1){
			// The button that has been pressed.
			if(countStep2 == 1){
				User = 1;
				countStep1 = stepN;
			}
			GameSquare b = (GameSquare)e.getSource();//获取按下的按钮
			b.clicked();
			countStep2--;
		}
		T--;
		if(T <= 0){
			T = 0;
		}
		score1.setText( "score" + ":" + GameBoard.getScoreOne());
		errorr1.setText( "error" + ":" + GameBoard.getError1());
		score2.setText( "score" + ":" + GameBoard.getScoreTwo());
		errorr2.setText( "error" + ":" + GameBoard.getError2());
		bombLabel.setText(String.valueOf(ProduceBombs.bombsNum));
	}

}
