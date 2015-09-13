/**
 * One piece in the triangle. Each piece in the Triangle is a thread, and will be started by the object that
 * holds all of the pieces. Uses a monitor to control what the thread does during execution.
 * @author  Tyler Paulsen
 * @version 9/5/2015.
 */
import java.lang.Thread;
public class PasTriPiece extends Thread{
    private int r,c,value;
    private boolean isValueSet;
    private MonitorPasTri monitor;

    /**
     * Constructor
     * @param r - row
     * @param c - column
     * @param monitor - monitor for object
     */
    PasTriPiece(int r,int c,MonitorPasTri monitor){
       // System.out.println("Creating Thread: ("+r+","+c+")");
        this.r = r;
        this.c = c;
        this.monitor = monitor;
    }

    /**
     * @return boolean if value for piece is set.
     */
    public boolean isValueSet(){
        return isValueSet;
    }

    /**
     * @return int - value of the piece
     */
    public int getValue(){
       return value;
    }

    /**
     * @param value - sets the value and the boolean to true.
     */
    public void setValue(int value){
        this.value = value;
        isValueSet = true;
    }

    /**
     * run method. Uses the monitor to get the piece, and to control the wait\notify
     */
    public void run(){
        monitor.getValue(r, c);
    }

    /**
     * @return String - returns the value as a string.
     */
    public String toString(){
        return Integer.toString(value);
    }
}
