/**
 * Created by Tyler on 9/26/2015.
 */
/**
 * Point: holder for x,y values to make code more readable.
 */
class CanvasPoint{
    public float x,y;
    /**
     * @param x float - x value for point
     * @param y float - y value for point
     */
    CanvasPoint(float x, float y){
        setPoint(x,y);
    }

    /**
     * @param x int - value to set x as.
     * @param y int - value to set y as.
     */
    public void setPoint(float x, float y){
        this.x = x;
        this.y = y;
    }
}
