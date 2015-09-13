/**
 * Thread monitor for the PascalTriangle.
 * @author  Tyler Paulsen
 * @version 9/5/2015.
 */
public class MonitorPasTri {
    private PascalTriangle triangle;
    private int size;

    /**
     * @param triangle - triangle to monitor.
     * @param size - size of the triangle.
     */
    public MonitorPasTri(PascalTriangle triangle,int size){
        this.triangle = triangle;
        this.size = size;
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
        triangle.setPiece(r,c,value);
    }

    /**
     * will retrun the value of the piece if it has one. If no value is present, it will force the thread
     * to wait until one is present.
     * @param r - row of the piece
     * @param c - column of the piece
     * @return int - value of the piece
     */
    public synchronized int getValue( int r, int c){
        try {
            //System.out.println("Calculating Thread: (" + r + "," + c + ")");
            while(!triangle.getPiece(r,c).isValueSet()) {
                if (r == 0 && c == 0) {
                    putValue(r, c, triangle.getS());
                    //System.out.println("Value Set: (" + r + "," + c + ") = " + triangle.getPiece(r, c));
                }else if (c == 0 && triangle.pieceHasValue(r - 1, c)) {
                    int val = triangle.getA() + triangle.getPiece(r - 1, c).getValue();
                    putValue(r, c, val);
                    //System.out.println("Value Set: (" + r + "," + c + ") = "+triangle.getPiece(r,c));
                } else if (r==c && triangle.pieceHasValue(r - 1, c - 1)) {
                    int val = triangle.getPiece(r -1, c - 1).getValue() + triangle.getB();
                    putValue(r, c, val);
                    //System.out.println("Value Set: (" + r + "," + c + ") = "+triangle.getPiece(r,c));
                } else if (c != 0 && triangle.getPiece(r - 1, c - 1).isValueSet() && triangle.getPiece(r - 1, c).isValueSet()) {
                    int val = triangle.getPiece(r - 1, c - 1).getValue() + triangle.getPiece(r - 1, c).getValue();
                    putValue(r, c, val);
                    //System.out.println("Value Set: (" + r + "," + c + ") = "+triangle.getPiece(r,c));
                } else {
                    wait();
                }
            }
        }catch(InterruptedException e){
            System.out.println("Interrupt Exception Caught.");
        }catch(Exception e){
            System.out.println("**Caught Exception for Thread: (" + r + "," + c + ")");
            System.out.println("Value:"+triangle.getPiece(r,c));
            System.out.println("isValueSet:"+triangle.getPiece(r,c).isValueSet());
            System.out.println(e.toString());
        }
        notifyAll();
        return triangle.getPiece(r,c).getValue();
    }
}
