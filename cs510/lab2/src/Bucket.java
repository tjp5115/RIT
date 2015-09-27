/**
 * Created by Crystal on 9/26/2015.
 */
public class Bucket implements Comparable{
    int yMax,x,dx,dy,sum,flag;
    Bucket(int x0, int y0, int x1, int y1){
        yMax = y0 > y1 ? y0 : y1;
        x = y0 < y1 ? x0 : x1;
        dx = Math.abs(x0 - x1);
        dy = Math.abs(y0 - y1);
        sum = 0;
        flag = (x0-x1) < 0 ^ (y0-y1) < 0 ? -1 : 1;
    }

    public String toString(){

        return  "| ymax="+ yMax +" | x="+x+" | dx="+dx+" | dy="+dy+" | sum="+sum+" | flag="+flag+" |";

    }

    @Override
    public int compareTo(Object o) {
        Bucket temp = ((Bucket)o);
        if(temp.x == x){
            //this may be wrong.
            return Integer.compare(dx-dy,temp.dx-temp.dy);
        }else {
            return Integer.compare(x, temp.x);
        }
    }
    public void update(){
        if(dy == 0){
            return;
        }
        sum += dx;
        while(sum >= dy ){
            sum -= dy;
            x += flag;
        }
    }
}
