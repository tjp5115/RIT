/**
 * One piece in the triangle. Each piece in the Triangle is a thread, and will be started by the object that
 * holds all of the pieces. Uses a monitor to control what the thread does during execution.
 * @author  Tyler Paulsen
 * @version 9/5/2015.
 */
import java.lang.Thread;
public class PasTriPiece extends Thread{
    private int r,c;
    private PascalTriangle triangle;

    /**
     * Constructor
     * @param r - row
     * @param c - column
     * @param triangle - monitor and a,b,s values for the triangle piece.
     */
    PasTriPiece(int r,int c, PascalTriangle triangle){
       // System.out.println("Creating Thread: ("+r+","+c+")");
        this.r = r;
        this.c = c;
        this.triangle = triangle;
    }

    /**
     * run method. Uses the monitor to get the piece, and to control the wait\notify
     */
    public void run(){
        int val;
        if (r == 0 && c == 0) {
            triangle.getMonitor().putValue(r, c, triangle.getS());
            //System.out.println("Value Set: (" + r + "," + c + ") = " + triangle.getPiece(r, c));
        }else if (c == 0  ) {
            val = triangle.getA() + triangle.getMonitor().getValue(r - 1, c);
            triangle.getMonitor().putValue(r, c, val);
            //System.out.println("Value Set: (" + r + "," + c + ") = "+triangle.getPiece(r,c));
        } else if (r==c) {
            val = triangle.getMonitor().getValue(r -1, c - 1) + triangle.getB();
            triangle.getMonitor().putValue(r, c, val);
            //System.out.println("Value Set: (" + r + "," + c + ") = "+triangle.getPiece(r,c));
        } else if (c != 0) {
            val = triangle.getMonitor().getValue(r - 1, c - 1) + triangle.getMonitor().getValue(r - 1, c);
            triangle.getMonitor().putValue(r, c, val);
            //System.out.println("Value Set: (" + r + "," + c + ") = "+triangle.getPiece(r,c));
        }
    }
}
