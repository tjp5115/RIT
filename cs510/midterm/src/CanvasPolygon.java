/**
 * Created by Tyler on 9/26/2015.
 */
/**
 * A canvas polygon. The class is used to store a polygon added to the canvas via the addPoly() method.
 */
class CanvasPolygon{
    float[] x;
    float[] y;
    int[] intX;
    int[] intY;
    int size;

    /**
     * @param x x array to copy
     * @param y y array to copy
     * @param size - size of array
     */
    CanvasPolygon(float []x, float []y,int size){
        this.x = x.clone();
        this.y = y.clone();
        this.size = size;
        intX = new int[x.length];
        intY = new int[y.length];
        for (int i = 0; i < size;i++){
            intX[i] = Math.round(x[i]);
            intY[i] = Math.round(y[i]);
        }
    }

    /**
     * copies an existing polygon into a new one.
     * @param poly - polygon to copy
     */
    CanvasPolygon(CanvasPolygon poly){
        x = poly.x.clone();
        y = poly.y.clone();
        intX = poly.intX.clone();
        intY = poly.intY.clone();
        size = poly.size;
    }
}
