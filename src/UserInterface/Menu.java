package UserInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


/**
 * This class inherits from the JFrame class.
 * This class implements the methods from ActionListener to respond different click events.
 * This class provides 4 options for user to start a new game.
 * These 4 options are "Beginner", "Intermediate", "Advanced" and "Custom".
 * After clicking "New Game" button, this pop-window is automatically closing.
 * 这个类继承自JFrame类。
 * *这个类实现了ActionListener的方法来响应不同的点击事件。
 * *这个类提供了4个选项让用户开始一个新的游戏。
 * *这四个选项是“初学者”，“中级”，“高级”和“自定义”。
 * *点击“新游戏”按钮后，此弹出窗口自动关闭。
 * @author Hephaest
 * @since 3/21/2019 8:41 PM
 */
public class Menu extends JFrame implements ActionListener
{
    private JButton start;
    private JRadioButton beginner, intermediate, advanced, read, custom;
    private JTextField n, width, height , bm;
    private  int stepNum = 0, boms = 0;
    public static int T = 0;
    private int [][] map;
    public static boolean archive = false;
    /**
     * Create a menu of the given title.（创建给定标题的菜单。）
     * @param title the title string for the window.（标题窗口的标题字符串。）
     */
    public Menu(String title)
    {
        // Create a window title.
        setTitle(title);

        // Create a subtitle（副标题）
        JLabel subtitle = new JLabel("Difficulty");
        subtitle.setBounds(100,10,100,20);
        add(subtitle);

        // Create the "Beginner" radio button
        beginner = new JRadioButton("Beginner");
        beginner.setBounds(40,40,150,20);
        add(beginner);

        // Create the "Beginner" descriptions.
        JLabel bDescFirstLine = new JLabel("10 mines");
        bDescFirstLine.setBounds(70,60,100,20);
        JLabel bDescSecondLine = new JLabel("9 x 9 tile grid");
        bDescSecondLine.setBounds(70,80,100,20);
        add(bDescFirstLine);
        add(bDescSecondLine);

        // Create the "Intermediate" radio button
        intermediate=new JRadioButton("Intermediate");
        intermediate.setBounds(40,100,150,20);
        add(intermediate);

        // Create the "Intermediate" descriptions.
        JLabel iDescFirstLine = new JLabel("40 mines");
        iDescFirstLine.setBounds(70,120,100,20);
        JLabel iDescSecondLine = new JLabel("16 x 16 tile grid");
        iDescSecondLine.setBounds(70,140,100,20);
        add(iDescFirstLine);
        add(iDescSecondLine);

        // Create the "Advanced" radio button
        advanced=new JRadioButton("Advanced");
        advanced.setBounds(40,160,160,20);
        add(advanced);

        // Create the "Advanced" descriptions.
        JLabel aDescFirstLine = new JLabel("100 mines");
        aDescFirstLine.setBounds(70,180,100,20);
        JLabel aDescSecondLine = new JLabel("16 x 30 tile grid");
        aDescSecondLine.setBounds(70,200,100,20);
        add(aDescFirstLine);
        add(aDescSecondLine);

        // Create the "Custom" labels and editable text fields.（创建“自定义”标签和可编辑的文本字段。）
        custom = new JRadioButton("Custom");
        custom.setBounds(40,220,100,20);
        add(custom);

        JLabel widthLabel = new JLabel("Width (10-24):");
        widthLabel.setBounds(70,240,80,20);
        add(widthLabel);

        width = new JTextField();
        width.setBounds(170,240,40,20);
        add(width);

        JLabel heightLabel = new JLabel("height (10-30):");
        heightLabel.setBounds(70,260,90,20);
        add(heightLabel);

        height = new JTextField();
        height.setBounds(170,260,40,20);
        add(height);

        JLabel bmLabel = new JLabel("bombs (10-(height+width)/2):");
        bmLabel.setBounds(70,280,90,20);
        add(bmLabel);

        bm = new JTextField();
        bm.setBounds(170,280,40,20);
        add(bm);

        JLabel stepN = new JLabel("n (1-5):");
        stepN.setBounds(140,300,80,20);
        add(stepN);
        n = new JTextField();
        n.setBounds(180,300,40,20);
        n.setText("1");
        add(n);

        //读取存档
        read = new JRadioButton("读档");
        read.setBounds(40,300,80,20);
        add(read);

        // Create the "New Game" button.
        start = new JButton("New Game");
        start.setBounds(80,320,100,20);
        add(start);
        // Initialize the text fields' edit state.
        //初始化文本字段的编辑状态。
        n.setEditable(true);

        // Add actionListener to all buttons.
        beginner.addActionListener(this);
        intermediate.addActionListener(this);
        advanced.addActionListener(this);
        start.addActionListener(this);
        custom.addActionListener(this);
        read.addActionListener(this);

        // Ensure single choice.
        ButtonGroup group = new ButtonGroup();
        group.add(beginner);
        group.add(intermediate);
        group.add(advanced);
        group.add(read);
        group.add(custom);

        //Initialize this menu instance.
        beginner.setSelected(true);
        setSize(280,400);
        setLayout(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    /**
     * An implementation method of ActionListener interface.
     * @param e the click event.
     */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == start) {
            // If user clicks "New Game" button, get the responding bomb amount, width and height of the board.
            int boardWidth = 0;
            int boardHeight = 0;
            int bombs = 0;
            n.setEditable(true);
            boolean errorFlag = false;
            if(!archive) {
                if (beginner.isSelected()) {
                    boardWidth = 9;
                    boardHeight = 9;
                    bombs = 10;
                    stepNum = Integer.parseInt(n.getText());
                    errorFlag = true;
                    T = 1;
                } else if (intermediate.isSelected()) {
                    boardWidth = 16;
                    boardHeight = 16;
                    bombs = 40;
                    stepNum = Integer.parseInt(n.getText());
                    errorFlag = true;
                    T = 2;
                } else if (advanced.isSelected()) {
                    boardWidth = 16;
                    boardHeight = 30;
                    bombs = 99;
                    stepNum = Integer.parseInt(n.getText());
                    errorFlag = true;
                    T = 3;
                }else if(custom.isSelected()){

                    boardHeight = Integer.parseInt(height.getText());
                    boardWidth = Integer.parseInt(width.getText());
                    bombs = Integer.parseInt(bm.getText());
                    stepNum = Integer.parseInt(n.getText());
                    errorFlag = true;
                    T = 4;
                }
                //初始化
                GameBoard.setError2(0);
                GameBoard.setError1(0);
                GameBoard.setScoreOne(0);
                GameBoard.setScoreTwo(0);
                GameBoard.setUser(1);
                GameBoard.setT(1);
            }
            if (read.isSelected()) {
                archive = true;
                int [] x = new int[10];
                int i =0;
                try{
                    BufferedReader bw = new BufferedReader(new FileReader(new File("T.txt")));
                    while((x[i] = bw.read()) != -1){
                     i++;
                    }
                    T = x[0] - 48;
                    bw.close();
                }catch (FileNotFoundException fne){
                    fne.printStackTrace();;
                }catch (IOException ioe){
                    ioe.printStackTrace();
                }catch (Exception E) {
                    E.printStackTrace();
                }
                if (T == 1) {
                    boardWidth = 9;
                    boardHeight = 9;
                    bombs = 10;
                    stepNum = Integer.parseInt(n.getText());
                }
                if (T == 2) {
                    boardWidth = 16;
                    boardHeight = 16;
                    bombs = 40;
                    stepNum = Integer.parseInt(n.getText());
                }
                if (T == 3) {
                    boardWidth = 16;
                    boardHeight = 30;
                    bombs = 99;
                    stepNum = Integer.parseInt(n.getText());
                }
                if(T == 4){
                    boardWidth = (x[1] - 48) * 10 + x[2] -48;
                    boardHeight = (x[3] -48) * 10 + x[4] -48;
                    i = 5;
                    String sum = " ";
                    while(x[i] != -1){
                       sum += String.valueOf(x[i] - 48);
                       i++;
                    }
                    bombs = Integer.parseInt(sum.trim());
                }
                map = new int[boardHeight][boardWidth];
                ReadTheFile read = new ReadTheFile(boardHeight,boardWidth);
                map = read.getB();
                errorFlag = true;
            }
            if (errorFlag) {
                // Close current window and then display the board that meets the requirements.
                //关闭当前窗口，显示符合要求的单板。
                //关闭窗体
                this.dispose();
                GameBoard b = new GameBoard("Minesweeper", boardWidth, boardHeight, stepNum, bombs);
                if(!archive) new ProduceBombs(b, bombs);
                if(archive) new ProduceBombs(b,bombs,map);
                ((SmartSquare) b.getSquareAt(0, 0)).setStartTime(System.currentTimeMillis());
            }else{
                // If user neither chooses "Custom" nor clicks "New Game" button, these fields cannot be editable.
                width.setEditable(false);
                height.setEditable(false);
                bm.setEditable(false);
            }
        }
    }

    /**
     * Check whether user's inputs from the text fields are all vaild.
     * @param bWidth the width of the board.
     * @param bHeight the height of the board.
     * @param bomb the amount of bombs.
     * @return a boolean value of the check result.
     * *检查用户输入的文本字段是否全部有效。
     * * @param bWidth板的宽度。
     * * @param bHeight板的高度。
     * * @param炸弹的数量。
     * 返回一个布尔值的检查结果。
     */
}
