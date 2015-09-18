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

public class Rasterizer {
    
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
        System.out.println("*********************************************************");
        System.out.println("*********************************************************");
        System.out.println("Drawing Line ("+x0+","+y0+")-("+x1+","+y1+")");
        // YOUR IMPLEMENTATION GOES HERE
        if(needSwap(x0,x1)){
            System.out.println("Swapping points");
            drawLine(x1, y1, x0, y0, C);
            return;
        }

        if(x0 == x1 || y0 == y1){
            drawNoSlopeLine(x0, y0, x1, y1, C);
            return;
        }

        if(y0 > y1){
            System.out.println("Decreasing slope");
            if( x1 - x0 < y0 - y1){
                System.out.println("large Decreasing slope");
                drawLargeSlopeNeg(x0, y0, x1, y1, C);
            }else {
                drawNormSlopeNeg(x0, y0, x1, y1, C);
            }
            return;
        }

        if(isLargeSlope(x0,x1,y0,y1)){
            System.out.println("Large Slope");
            drawLargeSlope(x0, y0, x1, y1, C);
        }else{
            drawNormSlope(x0, y0, x1, y1, C);
        }


    }
    public boolean isLargeSlope(int x0,int x1, int y0, int y1){
        if(y0 < y1){
            return x0 - x1 > y0 - y1;
        }else {
            return x0 - x1 < y0 - y1;
        }
    }

    public void drawNormSlopeNeg(int x0, int y0, int x1, int y1, simpleCanvas C){
        System.out.println("Drawing Line ("+x0+","+y0+")-("+x1+","+y1+")");
        int dE, dNE, x,y,d;
        int dy = y0 - y1;
        int dx = x1 - x0;
        dE = 2 * dy;
        dNE = 2 * (dy - dx);
        d = dE - dx;
        for( x = x0, y = y0;x<=x1; ++x){
            C.setPixel(x,y);
            if(d <= 0){
                d += dE;
            }else{
                --y;
                d += dNE;
            }
        }
        System.out.println("SetPixel("+x+","+y+")");
    }
    public void drawLargeSlopeNeg(int x0, int y0, int x1, int y1, simpleCanvas C){
        System.out.println("Drawing Line ("+x0+","+y0+")-("+x1+","+y1+")");
        int dE, dNE, x,y,d;
        int dy = y0 - y1;
        int dx = x1 - x0;
        dE = 2 * dx;
        dNE = 2 * (dx - dy);
        d = dE - dy;
        for( x = x0, y = y0;y>=y1; --y){
            C.setPixel(x,y);
            if(d <= 0){
                d += dE;
            }else{
                ++x;
                d += dNE;
            }
        }
        System.out.println("SetPixel("+x+","+y+")");
    }
    public void drawLargeSlope(int x0, int y0, int x1, int y1, simpleCanvas C){
        System.out.println("Drawing Line ("+x0+","+y0+")-("+x1+","+y1+")");
        int dE, dNE, x,y,d;
        int dy = y1 - y0;
        int dx = x1 - x0;
        dE = 2 * dx;
        dNE = 2 * (dx - dy);
        d = dE - dy;
        for( x = x0, y = y0;y<=y1; ++y){
            C.setPixel(x,y);
            if(d <= 0){
                d += dE;
            }else{
                x++;
                d += dNE;
            }
        }
        System.out.println("SetPixel("+x+","+y+")");
    }
    public void drawNormSlope(int x0,int y0, int x1, int y1, simpleCanvas C){
        System.out.println("Drawing Line ("+x0+","+y0+")-("+x1+","+y1+")");
        int dE, dNE, x,y,d;
        int dy = y1 - y0;
        int dx = x1 - x0;
        dE = 2 * dy;
        dNE = 2 * (dy - dx);
        d = dE - dx;
        for( x = x0, y = y0;x<=x1; ++x){
            C.setPixel(x,y);
            if(d <= 0){
                d += dE;
            }else{
                ++y;
                d += dNE;
            }
        }
        System.out.println("SetPixel("+x+","+y+")");
    }

    public void drawNoSlopeLine(int x0, int y0, int x1, int y1, simpleCanvas C){
        System.out.println("Drawing Line ("+x0+","+y0+")-("+x1+","+y1+")");
        int x,y;
        if (x0 == x1){
            System.out.println("Drawing Horizontal Line");
            if(y0 > y1){
                int tempP = y0;
                y0 = y1;
                y1 = tempP;
            }
            for(x = x0, y = y0;y<=y1;y++){
                C.setPixel(x,y);
            }
        }else{
            System.out.println("Drawing Vertical Line");

            for(x = x0, y = y0;x<=x1;x++){
                C.setPixel(x,y);
            }
        }

    }
    public boolean needSwap(int x0, int x1){
        return x0 > x1;
    }
      
}
