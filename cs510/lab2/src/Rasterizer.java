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

        System.out.println(et);
        int scanline = et.getScanlineMin();
        do {
            System.out.println(et);
            updateActiveList(scanline);
            if(et.getScanline(scanline) != null){
                al.addAll(et.getScanline(scanline));
                //et.remove(scanline);
            }
            Collections.sort(al);
            drawActiveList(scanline,new LinkedList<>(al),C);
            System.out.println(scanline);
            scanline++;
            updateBuckets();
        }while(!al.isEmpty());
        System.out.println("All done");
    }

    private void drawActiveList(int y, LinkedList<Bucket> al,simpleCanvas C) {
        System.out.println("init: "+al);
        Bucket b0 = al.pollFirst();
        Bucket b1 = al.pollFirst();
        if(b0 == null){
            return;
        }
        int x = b0.x;
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

            //System.out.println("b0: "+b0);
            //System.out.println("b1: "+b1);
            if(x > b0.x) {
                System.out.print(x);
                System.out.println(" " + y);
                C.setPixel(x, y);
            }
            //System.out.println("x="+x);
            if(x == b1.x){
                b0 = al.pollFirst();
                b1 = al.pollFirst();
            }
            x++;
        }
    }

    private void updateActiveList(int y) {
        for (Iterator<Bucket> iterator = al.iterator(); iterator.hasNext();) {
            if (iterator.next().yMax <= y) {
                iterator.remove();
            }
        }
    }
    private void updateBuckets(){
        for(Bucket b : al) b.update();
    }
}
