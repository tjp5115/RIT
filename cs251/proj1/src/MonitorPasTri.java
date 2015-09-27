/**
 * Thread monitor for the PascalTriangle.
 * @author  Tyler Paulsen
 * @version 9/5/2015.
 */
public class MonitorPasTri {
    private int size;
    int[][] triangle;

    /**
     * @param size - size of the triangle.
     */
    public MonitorPasTri(int size){
        this.size = size;
        triangle = new int[size][size];
    }

    /**
     * @return  int number of rows in the triangle.
     */
    public synchronized int rows(){
        return size;
    }

    /**
     * @param r - row of the piece
     * @param c - column of the piece
     * @param value - value to set the piece as.
     */
    public synchronized  void putValue(int r, int c, int value){
        if (triangle[r][c] !=0){
            System.out.println("An attempt to put a value already set detected. Monitor Exiting");
            System.exit(0);
        }
        triangle[r][c] = value;
        notifyAll();
    }

    /**
     * will retrun the value of the piece if it has one. If no value is present, it will force the thread
     * to wait until one is present.
     * @param r - row of the piece
     * @param c - column of the piece
     * @return int - value of the piece
     */
    public synchronized int getValue( int r, int c){
        try{
            while (triangle[r][c] == 0) {
                wait();
            }
        }catch(InterruptedException e){
            System.out.println("InterruptedException Caught");
        }
        return triangle[r][c];
    }
}
