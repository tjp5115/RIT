/**
 * Created by Tyler on 9/26/2015.
 */
/**
 * BoundryEdge: class to imitate the boundary, and the current edge to calculate for the SHPC algorithm
 */
class ClipWindow{
    private CanvasPoint e0,e1;
    public CanvasPoint LL,UR;

    /**
     *  initiates an empty clip window.
     */
    ClipWindow(){
        e0 = new CanvasPoint(0,0);
        e1 = new CanvasPoint(0,0);
        UR = null;
        LL = null;
    }

    /**
     * @param LL - lower left corner of the boundry
     * @param UR - upper right corner of the boundry
     */
    public void setWindow(CanvasPoint LL, CanvasPoint UR){
        this.LL = LL;
        this.UR = UR;
    }


    /**
     * sets the current edge to compute the intersection on.
     * @param x0 float - first x coord
     * @param y0 float - first y coord
     * @param x1 float - second x coord
     * @param y1 float - second y coord.
     */
    public void setCurrentEdge(float x0, float y0, float x1, float y1){
        e0.x = x0;
        e0.y = y0;
        e1.x = x1;
        e1.y = y1;
    }
    /**
     * To use this method, there must be an intersection between points and the current edge of the boundary
     * being calculated. If there is no intersection, unintented results will happen.
     * @param p1 - CanvasPoint to determine intersection with edge.
     * @param p2 - CanvasPoint to determine intersection with edge
     * @return CanvasPoint - intersection point
     */
    public CanvasPoint intersect(CanvasPoint p1, CanvasPoint p2) {
        if(inside(p2)){
            CanvasPoint temp = p1;
            p1 = p2;
            p2 = temp;
        }
        float x,y;
        //java can do division by zero if it is by floats...
        // this ternary expression is to prevent a float value of INF.
        float m = p1.x != p2.x ? (p1.y - p2.y)/(p1.x - p2.x) : 0;
        float b = p2.y - m * p2.x;

        if(e0.x == e1.x ){
            x = e0.x;
            y = m * x + b;
        }else{
            y = e0.y;
            // if we have a slope as zero, set as the x value of the point.
            // is related to the above ternary expression to deal with vertical lines.
            x = m != 0.0 ? (y - b) / m : p1.x;
        }

        return new CanvasPoint(x,y);
    }

    /**
     * @param p - point to determine indide of boundary and edge.
     * @return boolean - true if inside.
     */
    public boolean inside(CanvasPoint p){
        return (e0.x == e1.x && (e0.x == UR.x && p.x <= e0.x))  ||
                (e0.x == e1.x && (e0.x == LL.x && p.x >= e0.x)) ||
                (e0.y == e1.y && (e0.y == UR.y && p.y <= e0.y)) ||
                (e0.y == e1.y && (e0.y == LL.y && p.y >= e0.y));
    }

}
