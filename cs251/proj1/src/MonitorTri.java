/**
 * Created by Crystal on 9/5/2015.
 */
public class MonitorTri {
    private PascalTriangle pasTri;
    private int size;
    public MonitorTri(PascalTriangle pasTri,int size){
        this.pasTri = pasTri;
        this.size = size;
    }

    public synchronized int rows(){
        return size;
    }

    public synchronized  void putValue(int r, int c, int value){
        pasTri.setPiece(r,c,value);
    }

    public synchronized int getValue( int r, int c){
        try {
            while(!pasTri.getPiece(r,c).isValueSet()) {
                if (r == 0 && c == 0) {
                    putValue(r, c, pasTri.getS());
                } else if (c == 0 && pasTri.pieceHasValue(r - 1, c)) {
                    int val = pasTri.getA() + pasTri.getPiece(r - 1, c).getValue();
                    putValue(r, c, val);
                } else if (r == c && pasTri.pieceHasValue(r - 1, c - 1)) {
                    int val = pasTri.getPiece(r - 1, c - 1).getValue() + pasTri.getB();
                    putValue(r, c, val);
                } else if (pasTri.getPiece(r - 1, c - 1).isValueSet() && pasTri.getPiece(r - 1, c).isValueSet()) {
                    int val = pasTri.getPiece(r - 1, c - 1).getValue() + pasTri.getPiece(r - 1, c).getValue();
                    putValue(r, c, val);
                } else {
                    wait();
                }
            }
        }catch(InterruptedException e){
            System.out.println("Interrupt Exception Caught.");
        }
        return pasTri.getPiece(r,c).getValue();
    }
}
