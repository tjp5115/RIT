/**
 * Created by Crystal on 9/5/2015.
 */
public class PascalTriangle{
    private int a,b,s,size;
    private PasTriPiece[][] piece;
    private MonitorPasTri monitor;
    public PascalTriangle(String n,String a,String b,String s){
        this(Integer.parseInt(n), Integer.parseInt(a), Integer.parseInt(b), Integer.parseInt(s));
    }
    public PascalTriangle(int size,int a,int b,int s){
        this.a = a;
        this.b = b;
        this.s = s;
        this.size = size;
        monitor = new MonitorPasTri(this,size);
        piece = new PasTriPiece[size + 1][];

        for(int r = 0;r<size;++r){
            piece[r] = new PasTriPiece[r+1];
            for (int c = 0; c < r+1; ++c) {
                piece[r][c] = new PasTriPiece(r, c, monitor);
            }
        }
    }
    public void startReverse(){
        for(int r = size-1;r >= 0;--r) {
            for(int c = r;c >= 0;--c) {
                //System.out.println("Starting Thread: (" + r + "," + c + ")");
                piece[r][c].start();
            }
        }
    }

    public int getS(){
        return s;
    }
    public int getA(){
        return a;
    }
    public int getB(){
        return b;
    }
    public int getSize(){
        return size;
    }
    public PasTriPiece[] getRow(int r){
        return piece[r];
    }
    public PasTriPiece getPiece(int r, int c) {
        return piece[r][c];
    }
    public int getPieceValue(int r, int c) {
        return monitor.getValue(r,c);
    }
    public boolean pieceHasValue(int r, int c){
        return piece[r][c].isValueSet();
    }

    public boolean isRowComplete(int r){
        for (PasTriPiece p : piece[r]) {
            System.out.println("Looking up Row:" + r +" -- "+!p.isValueSet());
            System.out.println("Value: "+p.getValue());

            if (!p.isValueSet()) {
                return false;
            }
        }
        return true; 
    }

    public void setPiece(int r, int c, int value) {
        piece[r][c].setValue(value);
    }


}
