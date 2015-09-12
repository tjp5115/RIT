/**
 * Created by Crystal on 9/5/2015.
 */
public class MonitorPasTri {
    private PascalTriangle triangle;
    private int size;
    public MonitorPasTri(PascalTriangle triangle,int size){
        this.triangle = triangle;
        this.size = size;
    }

    public synchronized int rows(){
        return size;
    }

    public synchronized  void putValue(int r, int c, int value){
        triangle.setPiece(r,c,value);
    }

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
