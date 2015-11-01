/**
 * Created by Crystal on 10/31/2015.
 */
public class Point {
    float x,y,z;
    Point(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    Point (float x, float y){
        this(x,y,0);
    }
    Point(){
        this(0,0,0);
    }
    public String toString(){
        return "("+x+","+y+","+z+")";
    }
}
