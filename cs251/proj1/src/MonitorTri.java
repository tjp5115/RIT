/**
 * Created by Crystal on 9/5/2015.
 */
public class MonitorTri {
    private PascalTriangle pasTri;

    public MonitorTri(PascalTriangle pasTri){
        this.pasTri = pasTri;
    }

    public synchronized int rows(){
        return 0;
    }

    public synchronized  void putValue(int r, int c, int value){

    }

    public synchronized int getValue( int r, int c){
        return 0;
    }
}
