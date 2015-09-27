/**
 * A thread that monitors the output. Will print a piece of the triangle if able. Works from top->down, left->right.
 * @author  Tyler Paulsen
 * @version 9/5/2015.
 */
import java.lang.Thread;
public class PasTriOutput extends Thread{
    private PascalTriangle triangle;

    /**
     * @param triangle - triangle to print.
     */
    public PasTriOutput(PascalTriangle triangle){
        this.triangle = triangle;
    }

    /**
     * thread run method. prints from top->down, left->right. will wait
     * for a piece if it has no value.
     */
    public void run(){
        for(int r = 0;r<triangle.getMonitor().rows();++r){
            int c = 0;
            for(;;){
                System.out.print(triangle.getMonitor().getValue(r,c));
                c++;
                if (c > r){
                    break;
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
