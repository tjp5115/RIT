/**
 * Created by Tyler on 9/26/2015.
 */
import java.util.Collections;
import java.util.Hashtable;
import java.util.ArrayList;

/**
 * EdgeTable
 * The initial structure to fill in a polygon
 * The active List will use this to determine what line(s) are up next to add to the scanline fill
 */
public class EdgeTable {
    private Hashtable<Integer, ArrayList<Bucket>> et;

    /**
     * @param n - number of entries
     * @param x - array of x values
     * @param y - array of y values
     */
    public EdgeTable(int n, int x[], int y[]){
        et = new Hashtable<>();
        createTable(n, x, y);
    }

    /**
     * @param n - number of entries
     * @param x - array of x values
     * @param y - array of y values
     */
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

    /**
     * if the key exists, add to arraylist, else init arraylist and add entry
     * @para - second y valuem x0 - first x value
     * @param y0 - first y value
     * @param x1 - second x value
     * @param y1 - second y value
     */
    public void putEdge(int x0, int y0, int x1, int y1){
        // the key is the lowest y value
        int key = y0 < y1 ? y0 : y1;
        if(et.containsKey(key)){
            et.get(key).add(new Bucket(x0,y0,x1,y1));
            sort(key);
        }else{
            et.put(key,new ArrayList<>());
            et.get(key).add(new Bucket(x0,y0,x1,y1));
        }
    }

    /**
     * sorts the buckets for a certain key -- will use the bucket's sort method.
     * @param key - key to sort in hash table
     */
    public void sort(int key){
        if(!et.containsKey(key)) {
            System.out.println("Attempt to sort edgetable on key that does not exists.");
            throw new NullPointerException();
        }
        Collections.sort(et.get(key));
    }

    /**
     * @return String - string to represent an edge table.
     */
    @Override
    public String toString() {
        String str = new String();
        for (Integer key : et.keySet()){
           str += key +" = "+et.get(key)+"\n";
        }
        return str;
    }

    /**
     * @return int - the minimum scanline ( y value ) in the edge table.
     */
    public int getScanlineMin(){
        return Collections.min(et.keySet());
    }

    /**
     * @param key - scanline ( y ) value to get in the edge table
     * @return -  the bucket for that scanline
     */
    public ArrayList<Bucket> getScanline(int key){
        return et.get(key);
    }
}
