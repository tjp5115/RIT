//
//  Clipper.java
//
//  Created by Joe Geigel on 1/21/10.
//  Copyright 2010 Rochester Institute of Technology. All rights reserved.
//
//  Contributor:  Tyler Paulsen
//
import java.lang.Number;
///
// Object for performing clipping
//
///

public class clipper {
    class MutableInt{
        public int val;
        MutableInt(int val){
            this.val = val;
        }
    }
    class Point{
        public float x,y;
        Point(float x, float y){
            setPoint(x,y);
        }
        public void setPoint(float x, float y){
            this.x = x;
            this.y = y;
        }
    }
    class Boundary{
        Point LL,UR,UL,LR;
        Boundary(float x0, float y0, float x1, float y1){
            LL = new Point(x0,y0); // Lower left corner
            UR = new Point(x1,y1); // Upper right corner
            UL = new Point(x0,y1); // Upper left corner
            LR = new Point(x1,y0); // Lower right corner
        }

        public boolean inside(Point p){
            return LL.x <= p.x && LL.y <= p.y && p.x <= UR.x && p.y <= UR.y;
        }
        public Point intersect(Point p, Point s){
            Point c;
            if(!inside(p)){
                //dea with p
                return intersectionPoint(p);
            }else{
                //deal with s
                return intersectionPoint(s);
            }

        }
        private Point intersectionPoint(Point c){
            
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
        MutableInt outLength = new MutableInt(0);
        Boundary  boundry = new Boundary(x0,y0,x1,y1) ;
        int inLength = inx.length;
        Point p, s = new Point(0,0); // init just point s;

        p = new Point(inx[inLength - 1],iny[inLength - 1]);

        for(int i = 0; i < inLength; ++i){
           s.setPoint(inx[i],iny[i]);

            if( boundry.inside(s) ){
                if( boundry.inside(p) ){
                   output(s, outx, outy, outLength);
                }else{
                    output(boundry.intersect(p,s), outx, outy, outLength);
                    output(s, outx, outy, outLength);
                }

            }else{
                if( boundry.inside(p) ){
                    output(boundry.intersect(p,s),outx, outy, outLength);
                }
            }

            p = s;
        }

        return outLength.val; // should return number of vertices in clipped poly.
    }

    public void output(Point p, float[] x, float[] y, MutableInt i){
        x[i.val] = p.x;
        y[i.val] = p.y;
        i.val += 1;
    }
}
