package UserInterface;

import java.io.*;

public class ReadTheFile {
    private  int b[][];
    public ReadTheFile(int x, int y){
        try{
            b = new int[x][y];
            BufferedReader bw2 = new BufferedReader(new FileReader(new File("shu.txt")));

            for(int i = 0; i < x; i++){
                for(int j = 0; j < y; j++){
                    b[i][j] = bw2.read() - 48;
                }
            }
            bw2.close();
        }catch (FileNotFoundException fne){
            fne.printStackTrace();;
        }catch (IOException ioe){
            ioe.printStackTrace();
        }catch (Exception E) {
            E.printStackTrace();
        }
    }
    public int[][] getB(){
        return b;
    }
}
