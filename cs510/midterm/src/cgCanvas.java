//
//  cgCanvas.java
//
//  Created by Joe Geigel on 1/21/10.
//  Copyright 2010 Rochester Institute of Technology. All rights reserved.
//
//  Contributor: Tyler Paulsen
//

///
// This is a simple canvas class for adding functionality for the
// 2D portion of Computer Graphics.
//
///

import Jama.*;
import java.util.*;

public class cgCanvas extends simpleCanvas {


    HashMap<Integer,CanvasPolygon> polygons = new HashMap();
    int key = 0;
    LinkedList<Bucket> al;
    EdgeTable et;
    Matrix transformMatrix = Matrix.identity(3,3);
    Matrix viewMatrix = Matrix.identity(3,3);
    ClipWindow clipWindow;
    ///
    // Constructor
    //
    // @param w width of canvas
    // @param h height of canvas
    ///
    cgCanvas (int w, int h)
    {
        super (w, h);
        // YOUR IMPLEMENTATION HERE if you need to modify the constructor
        CanvasPoint p = new CanvasPoint(0,0);
        clipWindow = new ClipWindow();
    }

    ///
    // addPoly - Adds and stores a polygon to the canvas.  Note that this
    //           method does not draw the polygon, but merely stores it for
    //           later draw.  Drawing is initiated by the draw() method.
    //
    //           Returns a unique integer id for the polygon.
    //
    // @param x - Array of x coords of the vertices of the polygon to be added
    // @param y - Array of y coords of the vertices of the polygin to be added
    // @param n - Number of verticies in polygon
    //
    // @return a unique integer identifier for the polygon
    ///

    public int addPoly (float x[], float y[], int n)
    {
        // YOUR IMPLEMENTATION HERE

        // REMEMBER TO RETURN A UNIQUE ID FOR THE POLYGON
        polygons.put(key, new CanvasPolygon(x, y, n));
        return key++;
    }

    ///
    // drawPoly - Draw the polygon with the given id.  Should draw the
    //        polygon after applying the current transformation on the
    //        vertices of the polygon.
    //
    // @param polyID - the ID of the polygon to be drawn
    ///

    public void drawPoly (int polyID)
    {
        // YOUR IMPLEMENTATION HERE
        CanvasPolygon poly = new CanvasPolygon(polygons.get(polyID));
        if (poly.size < 3) {
            System.out.println("Illegal CanvasPolygon. < 3 edges. Exiting");
            System.exit(0);
        }
        // model transformation
        transformPolygon(poly,transformMatrix);

        // clipping
        float[] tempX = new float[100];
        float[] tempY = new float[100];
        int size = clipPolygon(poly.size,poly.x,poly.y,tempX,tempY);

        poly = new CanvasPolygon(tempX,tempY,size);
        // viewing transformation
        transformPolygon(poly, viewMatrix);
        // scan conversion
        et = new EdgeTable(poly.size, poly.intX, poly.intY);
        al = new LinkedList<>();

        int scanline = et.getScanlineMin();
        do {
            updateActiveList(scanline);
            if(et.getScanline(scanline) != null){
                al.addAll(et.getScanline(scanline));
            }
            Collections.sort(al);
            drawActiveList(scanline, new LinkedList<>(al));
            scanline++;
            updateBuckets();
        }while(!al.isEmpty());

    }

    /**
     * @param poly - polygon to tranform using the trandormMatrix.
     */
    public void transformPolygon(CanvasPolygon poly, Matrix transformation){
        Matrix cord = new Matrix(3,1,1.0);
        double x,y;
        for(int i=0; i<poly.size;++i){
            cord.set(0,0,poly.x[i]);
            cord.set(1,0,poly.y[i]);
            cord = transformation.times(cord);
            x = cord.get(0, 0);
            y = cord.get(1,0);
            poly.x[i] = (float)x;
            poly.y[i] = (float)y;
            poly.intX[i] = (int)Math.round(x);
            poly.intY[i] = (int)Math.round(y);
        }
    }
    ///
    // clearTransform - Set the current transformation to the identity matrix.
    ///

    public void clearTransform()
    {
        // YOUR IMPLEMENTATION HERE
        transformMatrix = Matrix.identity(3,3);
    }

    ///
    // translate - Add a translation to the current transformation by
    //             pre-multiplying the appropriate translation matrix to
    //             the current transformation matrix.
    //
    // @param x - Amount of translation in x
    // @param y - Amount of translation in y
    ///

    public void translate (float x, float y)
    {
        // YOUR IMPLEMENTATION HERE
        Matrix translate = Matrix.identity(3,3);
        translate.set(0,2, (double) x);
        translate.set(1,2,(double)y);
        transformMatrix = translate.times(transformMatrix);
    }

    ///
    // rotate - Add a rotation to the current transformation by
    //          pre-multiplying the appropriate rotation matrix to the
    //          current transformation matrix.
    //
    // @param degrees - Amount of rotation in degrees
    ///

    public void rotate (float degrees)
    {
        // YOUR IMPLEMENTATION HERE
        double radians = Math.toRadians(degrees);
        Matrix rotate = Matrix.identity(3,3);
        rotate.set(0,0,Math.cos(radians));
        rotate.set(0,1,-Math.sin(radians));
        rotate.set(1,0,Math.sin(radians));
        rotate.set(1,1,Math.cos(radians));
        transformMatrix = rotate.times(transformMatrix);
    }

    ///
    // scale - Add a scale to the current transformation by pre-multiplying
    //         the appropriate scaling matrix to the current transformation
    //         matrix.
    //
    // @param x - Amount of scaling in x
    // @param y - Amount of scaling in y
    ///

    public void scale (float x, float y)
    {
        // YOUR IMPLEMENTATION HERE

        Matrix scale = Matrix.identity(3,3);
        scale.set(0,0,(double)x);
        scale.set(1,1,(double)y);
        transformMatrix = scale.times(transformMatrix);
    }

    ///
    // setClipWindow - defines the clip window
    //
    // @param bottom - y coord of bottom edge of clip window (in world coords)
    // @param top - y coord of top edge of clip window (in world coords)
    // @param left - x coord of left edge of clip window (in world coords)
    // @param right - x coord of right edge of clip window (in world coords)
    ///

    public void setClipWindow (float bottom, float top, float left, float right)
    {
        // YOUR IMPLEMENTATION HERE
        clipWindow.setWindow(new CanvasPoint(left,bottom),new CanvasPoint(right,top));
    }

    ///
    // setViewport - defines the viewport
    //
    // @param xmin - x coord of lower left of view window (in screen coords)
    // @param ymin - y coord of lower left of view window (in screen coords)
    // @param width - width of view window (in world coords)
    // @param height - width of view window (in world coords)
    ///

    public void setViewport (int x, int y, int width, int height)
    {
        // YOUR IMPLEMENTATION HERE
        double sX,sY,tX,tY;
        double xMax = x + width - 1;
        double yMax = y + height - 1;
        double top = clipWindow.UR.y;
        double bottom = clipWindow.LL.y;
        double left = clipWindow.LL.x;
        double right = clipWindow.UR.x;
        sX = (xMax - x)/(right-left);
        sY = (yMax - y)/(top-bottom);
        tX = ((right*x) - (left*xMax))/(right-left);
        tY = ((top*y) - (bottom*yMax))/(top-bottom);

        viewMatrix.set(0, 0, sX);
        viewMatrix.set(1, 1, sY);
        viewMatrix.set(0,2,tX);
        viewMatrix.set(1,2,tY);
    }

    /**
     * draws the active list to the canvas. Removes objects from the Active list until no more objects exist.
     * @param y - scanline to draw
     * @param al - active list.
     */
    private void drawActiveList(int y, LinkedList<Bucket> al){
        Bucket b0 = al.pollFirst();
        Bucket b1 = al.pollFirst();
        if(b0 == null){
            return;
        }
        int x = b0.x;
        // loop until we have no more buckets.
        while( b1 != null && b0 != null){
            if(b0.dy == 0){
                b0 = b1;
                b1 = al.pollFirst();
                continue;
            }
            if(b1.dy == 0){
                b1 = al.pollFirst();
                continue;
            }
            // draw then the current x is greater than the lowest bucket (b0)
            if(x > b0.x) {
                setPixel(x, y);
            }
            // get two new buckets if we reach the highest active bucket (b1)
            if(x == b1.x){
                b0 = al.pollFirst();
                b1 = al.pollFirst();
            }
            x++;
        }
    }

    /**
     * remove from the active list if the yMax of the bucket in the Active list is <= to the current scanline
     * @param y - scanline to update on
     */
    private void updateActiveList(int y) {
        for (Iterator<Bucket> iterator = al.iterator(); iterator.hasNext();) {
            if (iterator.next().yMax <= y) {
                iterator.remove();
            }
        }
    }

    /**
     * updates the buckets with the bucket update method.
     */
    private void updateBuckets(){
        al.forEach(Bucket::update);
    }


    /*
   * MutableInt: wrapper around an int because java does not have pointers.
    */
    class MutableInt{
        public int val;
        MutableInt(){
            this.val = 0;
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
                           float outx[], float outy[])
    {
        // YOUR IMPLEMENTATION GOES HERE
        float x0,y0,x1,y1;
        x0 = clipWindow.LL.x;
        y0 = clipWindow.LL.y;
        x1 =  clipWindow.UR.x;
        y1 = clipWindow.UR.y;
        clipWindow.setCurrentEdge(x1, y0, x1, y1);

        float[] outx0 = new float[outx.length];
        float[] outy0 = new float[outx.length];
        int outLength = SHPC( inx, iny, in,  outx0, outy0,  clipWindow);

        clipWindow.setCurrentEdge(x1, y1, x0, y1);
        float[] outx1 = new float[outx.length];
        float[] outy1 = new float[outx.length];
        outLength = SHPC( outx0, outy0, outLength, outx1, outy1,  clipWindow );

        clipWindow.setCurrentEdge(x0, y1,x0, y0);
        float[] outx2 = new float[outx.length];
        float[] outy2 = new float[outx.length];
        outLength = SHPC( outx1, outy1, outLength, outx2, outy2, clipWindow );

        // for the last edge, use the out array supplied to the function.
        clipWindow.setCurrentEdge(x0, y0, x1, y0);
        outLength = SHPC( outx2, outy2, outLength, outx, outy,  clipWindow );

        return outLength; // should return number of vertices in clipped poly.
    }

    /**
     * @param inx - in x cords of polygon
     * @param iny - in y cords of polygon
     * @param inLength - in Length of polygon
     * @param outx - out x cords of polygon
     * @param outy - out y cords of polygon
     * @param edge - clipwindow with current edge set.
     * @return int - length of out array
     */
    private int SHPC (float inx[], float iny[], int inLength,
                      float outx[], float outy[],
                       ClipWindow edge)
    {
        CanvasPoint p, s = new CanvasPoint(0,0); // init just point s;
        if(inLength == 0){
            return 0;
        }
        p = new CanvasPoint(inx[inLength - 1],iny[inLength - 1]);
        MutableInt outLength = new MutableInt();
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
            p = new CanvasPoint(s.x,s.y);
        }
        return outLength.val;
    }


    /**
     * @param p point to set x,y values as.
     * @param x - the x out array to set values of
     * @param y - the y out array to set values of
     * @param i - mutable int to keep track of the size of the out array.
     */
    public void output(CanvasPoint p, float[] x, float[] y, MutableInt i){
        x[i.val] = p.x;
        y[i.val] = p.y;
        i.val += 1;
    }

}
