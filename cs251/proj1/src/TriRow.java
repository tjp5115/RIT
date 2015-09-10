/**
 * Created by Crystal on 9/8/2015.
 */
public class TriRow extends Thread{
    private int size;
    private TriPiece[] row;
    public TriRow(int size, MonitorTri monitor){
        this.size = size;
        row = new TriPiece[size+1];
        for(int i=0;i<size+1;++i){
            row[i] = new TriPiece(size,i,monitor);
       }
    }
    public void run(){
        for(int i=size;i>=0;--i){
            row[i].start();
        }
    }
    public TriPiece getPiece(int c){
        return row[c];
    }

    public void setPiece(int c, int value){
        row[c].setValue(value);
    }

    public String toString(){
        String str = new String();
        for(TriPiece p: row){
            str += p.toString() +" ";
        }
        return str.trim();
    }
    public boolean isComplete(){
        for(TriPiece p: row){
            if(!p.isValueSet()){
                return false;
            }
        }
        return true;
    }
}
