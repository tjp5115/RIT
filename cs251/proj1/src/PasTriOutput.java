/**
 * Created by Crystal on 9/8/2015.
 */
import java.lang.Thread;
public class PasTriOutput extends Thread{
    private PascalTriangle triangle;
    public PasTriOutput(PascalTriangle triangle){
        this.triangle = triangle;
    }
    public void run(){
        printRows();
    }
    public void printRows(){
        for(int r = 0;r<triangle.getSize();++r){
            for (int c = 0; c <= r; ++c) {
                System.out.print(triangle.getPieceValue(r,c)+" ");
            }
            System.out.println();
        }
    }
}
