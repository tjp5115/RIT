/**
 * The triangle that holds all of the pieces. This object uses the monitor to control the value of the piece.
 * @author  Tyler Paulsen
 * @version 9/5/2015.
 */
public class PascalTriangle{
    private int a,b,s;
    private PasTriPiece[][] piece;
    private MonitorPasTri monitor;

    /**
     *
     * @param n - size of triangle
     * @param a - A variable to the Pascal's Triangle
     * @param b - B variable to the Pascal's Triangle
     * @param s - S variable to the Pascal's Triangle
     */
    public PascalTriangle(String n,String a,String b,String s){
         this(Integer.parseInt(n), Integer.parseInt(a), Integer.parseInt(b), Integer.parseInt(s));
    }

    /**
     * Creates all the Triangel Pieces needed for the triangle along with one monitor to control them.
     * @param size - size of triagle -- how many rows
     * @param a - A variable to the Pascal's Triangle
     * @param b - B variable to the Pascal's Triangle
     * @param s - S variable to the Pascal's Triangle
     */
    public PascalTriangle(int size,int a,int b,int s){
        this.a = a;
        this.b = b;
        this.s = s;
        monitor = new MonitorPasTri(this,size);
        piece = new PasTriPiece[size + 1][];

        for(int r = 0;r<size;++r){
            piece[r] = new PasTriPiece[r+1];
            for (int c = 0; c < r+1; ++c) {
                piece[r][c] = new PasTriPiece(r, c, monitor);
            }
        }
    }

    /**
     * Starts the Triangle piece threads in reverse.
     */
    public void startReverse(){
        for(int r = getSize()-1;r >= 0;--r) {
            for(int c = r;c >= 0;--c) {
                //System.out.println("Starting Thread: (" + r + "," + c + ")");
                piece[r][c].start();
            }
        }
    }

    /**
     * @return int - S variable for the Pascal Triangle
     */
    public int getS(){
        return s;
    }
    /**
     * @return int - A variable for the Pascal Triangle
     */
    public int getA(){
        return a;
    }
    /**
     * @return int - B variable for the Pascal Triangle
     */
    public int getB(){
        return b;
    }
    /**
     * @return int - size of the triangle -- number of rows.
     */
    public int getSize(){
        return monitor.rows();
    }

    /**
     * @param r - row of piece
     * @param c - column of piece
     * @return PasTriPiece - Piece of the Triangle
     */
    public PasTriPiece getPiece(int r, int c) {
        return piece[r][c];
    }

    /**
     * @param r - row of piece
     * @param c - column of piece
     * @return int - value of the piece
     */
    public int getPieceValue(int r, int c) {
        return monitor.getValue(r,c);
    }

    /**
     * @param r - row of piece
     * @param c - column of piece
     * @return boolean - return if the piece has a value yet.
     */
    public boolean pieceHasValue(int r, int c){
        return piece[r][c].isValueSet();
    }

    /**
     * @param r - row of piece
     * @param c - column of piece
     * @param value - value to set the piece with
     */
    public void setPiece(int r, int c, int value) {
        piece[r][c].setValue(value);
    }
}
