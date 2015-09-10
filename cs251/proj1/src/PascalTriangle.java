/**
 * Created by Crystal on 9/5/2015.
 */
public class PascalTriangle {
    private int a,b,s;
    private TriRow[] triangle;
    private MonitorTri monitor;
    public PascalTriangle(String n,String a,String b,String s){
        this(Integer.parseInt(n), Integer.parseInt(a), Integer.parseInt(b), Integer.parseInt(s));
    }
    public PascalTriangle(int size,int a,int b,int s){
        this.a = a;
        this.b = b;
        this.s = s;
        triangle = new TriRow[size];
        monitor = new MonitorTri(this,size);
        for(int i = 0;i<size;++i){
            triangle[i] = new TriRow(i,monitor);
        }
    }
    public void startReverse(){
        for(int i = monitor.rows()-1;i>=0;--i) {
            triangle[i].start();
        }
    }
    public TriRow getRow(int r){
        return triangle[r];
    }
    public TriPiece getPiece(int r,int c){
        return triangle[r].getPiece(c);
    }
    public void setPiece(int r, int c, int value){
        triangle[r].setPiece(c,value);
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
    public boolean pieceHasValue(int r, int c){
        return triangle[r].getPiece(c).isValueSet();
    }
    public int getSize(){
        return monitor.rows();
    }

    public boolean isRowComplete(int r){
        return triangle[r].isComplete();
    }
}
