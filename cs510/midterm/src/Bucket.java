/**
 * Created by Tyler on 9/26/2015.
 */
/**
 * Bucket
 * Imitates the bucket for a line segment.
 */
public class Bucket implements Comparable{
    // public variables used in the bucket. manipulated by other classes.
    int yMax,x,dx,dy,sum,flag;

    /**
     * @param x0 int - first x value
     * @param y0 int - first y value
     * @param x1 int - second x value
     * @param y1 int - second y value
     */
    Bucket(int x0, int y0, int x1, int y1){
        // get the yMax
        yMax = y0 > y1 ? y0 : y1;
        // use the x value with the lowest y
        x = y0 < y1 ? x0 : x1;
        dx = Math.abs(x0 - x1);
        dy = Math.abs(y0 - y1);
        sum = 0;
        // flag for slope. add -1 for negative slope. add 1 for positive slope. Used in update.
        flag = (x0-x1) < 0 ^ (y0-y1) < 0 ? -1 : 1;
    }

    /**
     * @param o - Bucket to compare
     * @return int - how the objects compared.
     */
    @Override
    public int compareTo(Object o) {
        Bucket temp = ((Bucket)o);
        if(temp.x == x){
            // compare on the slope.
            return Integer.compare(dx-dy,temp.dx-temp.dy);
        }else {
            return Integer.compare(x, temp.x);
        }
    }

    /**
     * updates the bucket.
     * does an imitation of floating point arithmetic with integers.
     */
    public void update(){
        if(dy == 0){
            return;
        }
        sum += dx;
        while(sum >= dy ){
            sum -= dy;
            // add the flag to x -- either -1 or 1
            x += flag;
        }
    }
}
