/**
 * The triangle that holds all of the pieces. This object uses the monitor to control the value of the piece.
 * @author  Tyler Paulsen
 * @version 9/5/2015.
 */
public class PascalTriangle{
    private int a,b,s;
    private MonitorPasTri monitor;

    /**
     * Creates all the Triangel Pieces needed for the triangle along with one monitor to control them.
     * @param n - size of triangle -- how many rows
     * @param a - A variable to the Pascal's Triangle
     * @param b - B variable to the Pascal's Triangle
     * @param s - S variable to the Pascal's Triangle
     */
    public PascalTriangle(String n,String a,String b,String s){
        int size = 0;
        try {
            this.a = Integer.parseInt(a);
            this.b = Integer.parseInt(b);
            this.s = Integer.parseInt(s);
            size = Integer.parseInt(n);
        }catch (NumberFormatException e){
            System.out.println("Unable to parse Integer. Please give integer values.");
            System.exit(0);
        }
        if ( this.a < 0 || this.b < 0 || this.s < 0 || size < 0) {
            System.out.println("can not have a negative A B or S value. Please check input. Exiting.");
            System.exit(0);
        }
        monitor = new MonitorPasTri(size);

    }

    /**
     * Starts the Triangle piece threads in reverse.
     */
    public void startReverse(){
        for(int r = monitor.rows()-1;r >= 0;--r) {
            for(int c = r;c >= 0;--c) {
                //System.out.println("Starting Thread: (" + r + "," + c + ")");
                new PasTriPiece(r,c,this).start();
            }
        }
    }
    /**
     * @return MonitorPasTri - monitor for the triangle pieces.
     */
    public MonitorPasTri getMonitor(){
        return monitor;
    }
    /**
     * @return int - a value for triangle
     */
    public int getA(){return a;}
    /**
     * @return int - s value for triangle
     */
    public int getS(){return s;}
    /**
     * @return int - b value for triangle
     */
    public int getB(){return b;}
}
