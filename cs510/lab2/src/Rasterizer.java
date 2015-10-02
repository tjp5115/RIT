//
//  Rasterizer.java
//
//  Created by Joe Geigel on 1/21/10.
//  Copyright 2010 Rochester Institute of Technology. All rights reserved.
//
//  Contributor: Tyler Paulsen
//

///
// 
// This is a class that performas rasterization algorithms
//
///

import java.util.*;
import java.util.stream.StreamSupport;

public class Rasterizer {


    ///
    // number of scanlines
    ///

    int n_scanlines;

    ///
    // Active List
    ///
    LinkedList<Bucket> al;

    ///
    // Edge Table
    ///
    EdgeTable et;

    ///
    // Constructor
    //
    // @param n - number of scanlines
    //
    ///

    Rasterizer(int n) {
        n_scanlines = n;
    }

    ///
    // Draw a filled polygon in the simpleCanvas C.
    //
    // The polygon has n distinct vertices. The 
    // coordinates of the vertices making up the polygon are stored in the 
    // x and y arrays.  The ith vertex will have coordinate  (x[i], y[i])
    //
    // You are to add the implementation here using only calls
    // to C.setPixel()
    ///

    public void drawPolygon(int n, int x[], int y[], simpleCanvas C) {
        // YOUR IMPLEMENTATION GOES HERE
        if (n < 3) {
            System.out.println("Illegal Polygon. < 3 edges. Exiting");
            System.exit(0);
        }
        et = new EdgeTable(n, x, y);
        al = new LinkedList<>();

        int scanline = et.getScanlineMin();
        do {
            updateActiveList(scanline);
            if(et.getScanline(scanline) != null){
                al.addAll(et.getScanline(scanline));
            }
            Collections.sort(al);
            drawActiveList(scanline, new LinkedList<>(al), C);
            scanline++;
            updateBuckets();
        }while(!al.isEmpty());
    }

    /**
     * draws the active list to the canvas. Removes objects from the Active list until no more objects exist.
     * @param y - scanline to draw
     * @param al - active list.
     * @param C - canvas to draw too
     */
    private void drawActiveList(int y, LinkedList<Bucket> al,simpleCanvas C) {
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
                C.setPixel(x, y);
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
        for(Bucket b : al) b.update();
    }
}
