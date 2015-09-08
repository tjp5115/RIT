import java.lang.Thread;
/**
 * Created by Crystal on 9/5/2015.
 */
public class TriPiece extends Thread{
    private int r;
    private int c;
    private int val;
    TriPiece(int r, int c){
       this.r = r;
        this.c = c;
    }
    public int getValue(){
        return val;
    }
    public void run(){

    }
}
