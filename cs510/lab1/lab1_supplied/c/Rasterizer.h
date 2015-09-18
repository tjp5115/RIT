//
//  Rasterizer.h
//
//  Created by Warren R. Carithers on 01/27/14.
//  Based on a C++ version created by Joe Geigel on 11/30/11.
//  Copyright 2014 Rochester Institute of Technology. All rights reserved.
//
//  Contributor:  YOUR_NAME_HERE
//

#ifndef _RASTERIZER_H_
#define _RASTERIZER_H_

#include "simpleCanvas.h"

///
//
// Simple structure that performs rasterization algorithms
//
///

typedef struct st_Rasterizer {

    /**
     * number of scanlines
     */
    int n_scanlines;

} Rasterizer;

///
// Constructor
//
// @param n number of scanlines
//
///

Rasterizer *Rasterizer_create( int n );

///
// Destructor
//
// @param R Rasterizer to destroy
//
///

void Rasterizer_destroy( Rasterizer *R );

///
// Draw a line from (x0,y0) to (x1, y1) on the simpleCanvas C.
//
// Implementation should be using the Midpoint Method
//
// You are to add the implementation here using only calls
// to simpleCanvas_setPixel()
//
// @param x0 x coord of first endpoint
// @param y0 y coord of first endpoint
// @param x1 x coord of second endpoint
// @param y1 y coord of second endpoint
// @param C  The canvas on which to apply the draw command.
// @param R  The relevant rasterizer (not currently used)
///

void drawLine (int x0, int y0, int x1, int y1,
    simpleCanvas *C, Rasterizer *R );

#endif
