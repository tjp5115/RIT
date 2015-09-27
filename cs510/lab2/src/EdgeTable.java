import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.ArrayList;

/**
 * Created by Crystal on 9/26/2015.
 */
public class EdgeTable {
    private Hashtable<Integer, ArrayList<Bucket>> et;
    public EdgeTable(int n, int x[], int y[]){
        et = new Hashtable<>();
        createTable(n, x, y);
    }

    public void createTable(int n, int x[], int y[]){

        if (!et.isEmpty()){
            et.clear();
        }
        int x0,y0;
        x0 = x[0];
        y0 = y[0];

        for (int i = 1; i < n;++i){
            putEdge(x0,y0,x[i],y[i]);
            x0 = x[i];
            y0 = y[i];
        }
        n = n-1;
        //put the last edge in.
        putEdge(x[n], y[n], x[0], y[0]);
    }

    public void putEdge(int x0, int y0, int x1, int y1){
        int key = y0 < y1 ? y0 : y1;
        if(et.containsKey(key)){
            et.get(key).add(new Bucket(x0,y0,x1,y1));
            sort(key);
        }else{
            et.put(key,new ArrayList<>());
            et.get(key).add(new Bucket(x0,y0,x1,y1));
        }
    }

    public void sort(int key){
        if(!et.containsKey(key)) {
            System.out.println("Attempt to sort edgetable on key that does not exists.");
            throw new NullPointerException();
        }

        Collections.sort(et.get(key));
    }

    @Override
    public String toString() {
        String str = new String();
        for (Integer key : et.keySet()){
           str += key +" = "+et.get(key)+"\n";
        }
        return str;
    }

    public boolean isEmpty(){
        return et.isEmpty();
    }

    public int getScanlineMin(){
        return Collections.min(et.keySet());
    }

    public ArrayList<Bucket> getScanline(int key){
        return et.get(key);
    }

    public void remove(int key){
        et.remove(key);
        System.out.println("removed: "+et);
    }
}
