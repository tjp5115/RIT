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
// A simple class for performing rasterization algorithms.
//
///

import java.util.*;
import java.lang.Math;

public class Rasterizer {
    private int x0, y0, x1, y1;
    simpleCanvas C;
    ///
    // number of scanlines
    ///

    int n_scanlines;
    
    ///
    // Constructor
    //
    // @param n number of scanlines
    //
    ///

    Rasterizer (int n)
    {
        n_scanlines = n;
    }
    
    ///
    // Draw a line from (x0,y0) to (x1, y1) on the simpleCanvas C.
    //
    // Implementation should be using the Midpoint Method
    //
    // You are to add the implementation here using only calls
    // to C.setPixel()
    //
    // @param x0 x coord of first endpoint
    // @param y0 y coord of first endpoint
    // @param x1 x coord of second endpoint
    // @param y1 y coord of second endpoint
    // @param C  The canvas on which to apply the draw command.
    ///
    public void drawLine (int x0, int y0, int x1, int y1, simpleCanvas C)
    {
        // YOUR IMPLEMENTATION GOES HERE
        /*
        //System.out.println("*********************************************************");
        //System.out.println("*********************************************************");
        //System.out.println("Drawing Line ("+x0+","+y0+")-("+x1+","+y1+")");
        */
        if(x0 > x1){
            //System.out.println("Swapping points");
            drawLine(x1, y1, x0, y0, C);
            return;
        }
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
        this.C = C;
        if(x0 == x1 || y0 == y1){
            drawNoSlopeLine(x0, y0, x1, y1, C);
            return;
        }
        int dx = Math.abs(x0 - x1);
        int dy = Math.abs(y0 - y1);
        boolean isLargeSlope = dx < dy;

        if(y0 > y1 && isLargeSlope) {
            midpointLarge(x1,y1,-1,y0);
        }else if( isLargeSlope ) {
            midpointLarge(x0,y0,1,y1);
        }else if(y0 > y1){
            midpointSmall(-1);
        }else {
            midpointSmall(1);
        }
    }

    /**
     * uses the midpoint line algorithm to draw pixels point to point. This if for a line with a 'normal'
     * slope -- |dx| > |dy|;
     * @param Ydec - decrement for the y value. Positive for lines in with y0 < y1 negative otherwise.
     */
    public void midpointSmall(int Ydec){
        ////System.out.println("Drawing Line ("+x0+","+y0+")-("+x1+","+y1+")");
        int dE, dNE, x,y,d;
        int dy = Math.abs(y1 - y0);
        int dx = x1 - x0;
        dE = 2 * dy;
        dNE = 2 * (dy - dx);
        d = dE - dx;
        for( x = x0, y = y0;x<=x1; ++x){
            C.setPixel(x,y);
            if(d <= 0){
                d += dE;
            }else{
                y+= Ydec;
                d += dNE;
            }
        }
        ////System.out.println("SetPixel("+x+","+y+")");
    }

    /**
     * uses the midpoint line algorithm to draw pixels point to point. This is for a line with a 'large' 
     * slope -- |dx| < |dy|.
     * @param x - cord to start x off as. if y1>y0, start with x1, else x = x1
     * @param y - cord to start y off as. if y1>y0, start with y1, else y = y1
     * @param Xdec - decrement for the x value. Positive for lines in with y0 < y1 negative otherwise.
     * @param thresh - threshold to stop the for loop at. if y0 > y1, thresh = y0, else thresh = y1.
     */
    public void midpointLarge(int x, int y, int Xdec,int thresh) {
        ////System.out.println("Drawing Line ("+x0+","+y0+")-("+x1+","+y1+")");
        int dy = Math.abs(y1 - y0);
        int dx = x1 - x0;
        int dE = 2 * dx;
        int dNE = 2 * (dx - dy);
        int d = dE - dy;
        for( ;y<=thresh; y++){
            C.setPixel(x,y);
            if(d <= 0){
                d += dE;
            }else{
                x+=Xdec;
                d += dNE;
            }
        }
        //System.out.println("SetPixel("+x+","+y+")");
    }


    /**
     * @param x0 - start X for line
     * @param y0 - start Y for line
     * @param x1 - end X for line
     * @param y1 - end Y for line
     * @param C - Canvas to draw line to.
     */
    public void drawNoSlopeLine(int x0, int y0, int x1, int y1, simpleCanvas C){
        //System.out.println("Drawing Line ("+x0+","+y0+")-("+x1+","+y1+")");
        int x,y;
        if (x0 == x1){
            //draw horizontal line
            //System.out.println("Drawing Horizontal Line");

            //swap y values if needed
            if(y0 > y1){
                int tempP = y0;
                y0 = y1;
                y1 = tempP;
            }
            for(x = x0, y = y0;y<=y1;y++){
                C.setPixel(x,y);
            }
        }else{
            //draw vertical line.
            //System.out.println("Drawing Vertical Line");
            for(x = x0, y = y0;x<=x1;x++){
                C.setPixel(x,y);
            }
        }

    }

}
