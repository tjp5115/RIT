/**
 * Created by Crystal on 9/8/2015.
 */
import java.lang.Thread;
public class PasTriOutput extends Thread{
    private PascalTriangle triangle;
    private int index;
    public PasTriOutput(PascalTriangle triangle){
        this.triangle = triangle;
    }
    public void run(){
        printRows();
    }
    public synchronized void printRows(){
        try {
            while (index < triangle.getSize()) {
                if (triangle.isRowComplete(index)) {
                    System.out.println(triangle.getRow(index));
                    ++index;
                } else {
                    wait();
                }
            }
        }catch(InterruptedException e){
            System.out.println("Interrupt Exception Caught");
        }
    }
}
