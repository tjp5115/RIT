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
    class BoundryEdge{
        private Point e1,e2;
        private Point LL,UR;
        BoundryEdge(Point p1,Point p2,Point LL, Point UR) {
            setEdge(p1,p2);
            this.LL = LL;
            this.UR = UR;
            e1 = p1;
            e2 = p2;
        }
        public void setEdge(Point p1, Point p2){
            e1 = p1;
            e2 = p2;
        }
        public Point intersect(Point p1, Point p2) {
            if(inside(p2)){
                Point temp = p1;
                p1 = p2;
                p2 = temp;
            }
            float x,y;
            float m = p1.x != p2.x ? (p1.y - p2.y)/(p1.x - p2.x) : 0;
            float b = p2.y - m * p2.x;

            if(e1.x == e2.x ){
                x = e1.x;
                y = m * x + b;
            }else{
                y = e1.y;
                x = m != 0.0 ? (y - b) / m : p1.x;
            }
            return new Point(x,y);
        }

        private boolean inside(Point p){
            return (e1.x == e2.x && (e1.x == UR.x && p.x <= e1.x))  ||
                    (e1.x == e2.x && (e1.x == LL.x && p.x >= e1.x)) ||
                    (e1.y == e2.y && (e1.y == UR.y && p.y <= e1.y)) ||
                    (e1.y == e2.y && (e1.y == LL.y && p.y >= e1.y));
            /*
            if(e1.x == e2.x){
                if(e1.x == UR.x){
                    return p.x <= e1.x;
                }else{
                    return p.x >= e1.x;
                }
            }else{
                if(e1.y == UR.y){
                    return p.y <= e1.y;
                }else{
                    return p.y >= e1.y;
                }
            }
            */
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

        edge.setEdge(new Point(x0,y0), new Point(x1,y0));
        float[] outx3 = new float[outx.length];
        float[] outy3 = new float[outx.length];
        outLength = SHPC( outx2, outy2, outLength, outx, outy,  edge );

        return outLength; // should return number of vertices in clipped poly.
    }
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


    public void output(Point p, float[] x, float[] y, MutableInt i){
        x[i.val] = p.x;
        y[i.val] = p.y;
        i.val += 1;
    }
}
