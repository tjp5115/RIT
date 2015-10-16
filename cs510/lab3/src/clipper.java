//
//  Clipper.java
//
//  Created by Joe Geigel on 1/21/10.
//  Copyright 2010 Rochester Institute of Technology. All rights reserved.
//
//  Contributor:  Tyler Paulsen
//

///
// Object for performing clipping
//
///

public class clipper {
    /*
    * MutableInt: wrapper around an int because java does not have pointers.
     */
    class MutableInt{
        public int val;

        /**
         * @param val int - val to be able to mutate
         */
        MutableInt(int val){
            this.val = val;
        }
    }
    /*
    * Point: holder for x,y values to make code more readable.
     */
    class Point{
        public float x,y;

        /**
         * @param x float - x value for point
         * @param y float - y value for point
         */
        Point(float x, float y){
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

    /**
     * BoundryEdge: class to imitate the boundry, and the current edge to calculate for the SHPC algorithm
     */
    class BoundryEdge{
        private Point e1,e2;
        private Point LL,UR;

        /**
         * @param p1 - first point to form an edge
         * @param p2 - second point to form an edge
         * @param LL - lower left corner of the boundry
         * @param UR - upper right corner of the boundry
         */
        BoundryEdge(Point p1,Point p2,Point LL, Point UR) {
            setEdge(p1,p2);
            this.LL = LL;
            this.UR = UR;
        }

        /**
         * @param p1 - point to set the edge at
         * @param p2 - point to set the edge at
         */
        public void setEdge(Point p1, Point p2){
            e1 = p1;
            e2 = p2;
        }

        /**
         * To use this method, there must be an intersection between points and the current edge of the boundary
         * being calculated. If there is no intersection, unintented results will happen.
         * @param p1 - Point to determine intersection with edge.
         * @param p2 - Point to determine intersection with edge
         * @return Point - intersection point
         */
        public Point intersect(Point p1, Point p2) {
            if(inside(p2)){
                Point temp = p1;
                p1 = p2;
                p2 = temp;
            }
            float x,y;
            //java can do division by zero if it is by floats...
            // this ternary expression is to prevent a float value of INF.
            float m = p1.x != p2.x ? (p1.y - p2.y)/(p1.x - p2.x) : 0;
            float b = p2.y - m * p2.x;

            if(e1.x == e2.x ){
                x = e1.x + 1; // don't know why, but it is shifted 1 pixel to the left.
                y = m * x + b;
            }else{
                y = e1.y;
                // if we have a slope as zero, set as the x value of the point.
                // is related to the above ternary expression to deal with vertical lines.
                x = m != 0.0 ? (y - b) / m : p1.x;
            }
            return new Point(x,y);
        }

        /**
         * @param p - point to determine indide of boundary and edge.
         * @return boolean - true if inside.
         */
        private boolean inside(Point p){
            return (e1.x == e2.x && (e1.x == UR.x && p.x <= e1.x))  ||
                    (e1.x == e2.x && (e1.x == LL.x && p.x >= e1.x)) ||
                    (e1.y == e2.y && (e1.y == UR.y && p.y <= e1.y)) ||
                    (e1.y == e2.y && (e1.y == LL.y && p.y >= e1.y));
        }

    }

    ///
    // clipPolygon
    //
    // Clip the polygon with vertex count in and vertices inx/iny
    // against the rectangular clipping region specified by lower-left corner
    // (x0,y0) and upper-right corner (x1,y1). The resulting vertices are
    // placed in outx/outy.
    //
    // The routine should return the the vertex count of the polygon
    // resulting from the clipping.
    //
    // @param in the number of vertices in the polygon to be clipped
    // @param inx - x coords of vertices of polygon to be clipped.
    // @param iny - y coords of vertices of polygon to be clipped.
    // @param outx - x coords of vertices of polygon resulting after clipping.
    // @param outy - y coords of vertices of polygon resulting after clipping.
    // @param x0 - x coord of lower left of clipping rectangle.
    // @param y0 - y coord of lower left of clipping rectangle.
    // @param x1 - x coord of upper right of clipping rectangle.
    // @param y1 - y coord of upper right of clipping rectangle.
    //
    // @return number of vertices in the polygon resulting after clipping
    //
    ///
    public int clipPolygon(int in, float inx[], float iny[],
                  float outx[], float outy[],
                  float x0, float y0, float x1, float y1)
    {
        // YOUR IMPLEMENTATION GOES HERE
        Point LL = new Point(x0,y0); // Lower left corner
        Point UR = new Point(x1,y1); // Upper right corner

        BoundryEdge edge = new BoundryEdge(new Point(x1,y0), new Point(x1,y1),LL,UR);

        float[] outx0 = new float[outx.length];
        float[] outy0 = new float[outx.length];
        int outLength = SHPC( inx, iny, inx.length,  outx0, outy0,  edge);

        edge.setEdge(new Point(x1,y1), new Point(x0,y1));
        float[] outx1 = new float[outx.length];
        float[] outy1 = new float[outx.length];
        outLength = SHPC( outx0, outy0, outLength, outx1, outy1,  edge );

        edge.setEdge(new Point(x0, y1), new Point(x0, y0));
        float[] outx2 = new float[outx.length];
        float[] outy2 = new float[outx.length];
        outLength = SHPC( outx1, outy1, outLength, outx2, outy2, edge );

        // for the last edge, use the out array supplied to the function.
        edge.setEdge(new Point(x0,y0), new Point(x1,y0));
        outLength = SHPC( outx2, outy2, outLength, outx, outy,  edge );

        return outLength; // should return number of vertices in clipped poly.
    }

    /**
     * @param inx - in x cords of polygon
     * @param iny - in y cords of polygon
     * @param inLength - in Length of polygon
     * @param outx - out x cords of polygon
     * @param outy - out y cords of polygon
     * @param edge - edge to clip too.
     * @return int - length of out array
     */
    private int SHPC (float inx[], float iny[], int inLength,
                       float outx[], float outy[],
                       BoundryEdge edge)
    {
        Point p, s = new Point(0,0); // init just point s;
        if(inLength == 0){
            return 0;
        }
        p = new Point(inx[inLength - 1],iny[inLength - 1]);
        MutableInt outLength = new MutableInt(0);
        for(int i = 0; i < inLength; ++i){
            s.setPoint(inx[i],iny[i]);
            if( edge.inside(s) ){
                if( edge.inside(p) ){
                    output(s, outx, outy, outLength);
                }else{
                    output(edge.intersect(p,s), outx, outy, outLength);
                    output(s, outx, outy, outLength);
                }
            }else{
                if( edge.inside(p) ){
                    output(edge.intersect(p,s),outx, outy, outLength);
                }
            }
            p = new Point(s.x,s.y);
        }
        return outLength.val;
    }


    /**
     * @param p point to set x,y values as.
     * @param x - the x out array to set values of
     * @param y - the y out array to set values of
     * @param i - mutable int to keep track of the size of the out array.
     */
    public void output(Point p, float[] x, float[] y, MutableInt i){
        x[i.val] = p.x;
        y[i.val] = p.y;
        i.val += 1;
    }
}
