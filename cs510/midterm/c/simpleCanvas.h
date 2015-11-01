//
//  simpleCanvas.h
//
//  Created by Warren R. Carithers on 02/28/14.
//  Based on a C++ version created by Joe Geigel on 11/30/11.
//  Copyright 2014 Rochester Institute of Technology. All rights reserved.
//
//  This file should not be modified by students.
//

#ifndef _SIMPLECANVAS_H_
#define _SIMPLECANVAS_H_

#include <SFML/Graphics.h>
#include <sys/types.h>

///
// define an alternative to the STL vector<sfConvexShape> class
///

typedef struct st_shapeVector {
    size_t size;    // number of occupied slots in the vector
    size_t length;    // total number of slots in the vector
    size_t growth;    // how many slots to add when growing the vector
    sfConvexShape **vec;    // the vector itself
} shapeVector_t;


///
// Manipulation functions
///

///
// shapeVectorGrowthFactor -- set the growth factor for a shapeVector
//
// Returns the old growth factor.
///

size_t shapeVectorGrowthFactor( shapeVector_t *vec, size_t size );

///
// shapeVectorClear -- return a shapeVector to its original state
///

void shapeVectorClear( shapeVector_t *vec );

///
// shapeVectorPushBack -- add a shape to the end of the vector,
// automatically extending the vector if need be
//
// If the vector must be extended and the memory reallocation
// fails, this method will print an error message and exit.
///

void shapeVectorPushBack( shapeVector_t *vec, sfConvexShape *shape );

///
// Pseudo-function: return the count of elements in a shapeVector_t
//
// Note: the argument is a pointer, for consistency with the
// other methods that operate on shapeVector_t variables.
///

#define shapeVectorSize(svp)  ((svp)->size)

///
// Simple canvas structure that allows for pixel-by-pixel rendering.
///

typedef struct st_simpleCanvas {

    ///
    // height and width of the canvas
    ///
    int width;
    int height;

    sfImage *myImage;
    sfColor myColor;

    shapeVector_t polys;

} simpleCanvas;

///
// Constructor
//
// @param w width of canvas
// @param h height of canvas
///

simpleCanvas *simpleCanvas_create( int w, int h );

///
// Destructor
//
// @param C canvas to destroy
///

void simpleCanvas_destroy( simpleCanvas *C );

///
// Clears the canvas using the current color.
///

void simpleCanvas_clear( simpleCanvas *C );

///
// Draw yourself into a given renderWindow.
//
// @param R - the window in which to draw
///

void simpleCanvas_draw (sfRenderWindow *R, simpleCanvas *C);

///
// Sets the current color.
//
// @param r - The red component of the new color (between 0-1)
// @param g - The green component of the new color (between 0-1)
// @param b - The blue component of the new color (between 0-1);
///

void simpleCanvas_setColor (float r, float g, float b, simpleCanvas *C);

///
// Use this drawOutline() method only if you were NOT able to
// create a working drawPolygon() Rasterizer routine of your own.
// This method will only draw the outline of the polygon
///

void simpleCanvas_drawOutline (int n, int x[], int y[], simpleCanvas *C );

///
// Crites a pixel using the passed in color.  Does not modify the
// current color.
//
// @param x - The x coord of the pixel to be set
// @param y - The y coord of the pixel to be set
// @param r - The red component of the new color (between 0-1)
// @param g - The green component of the new color (between 0-1)
// @param b - The blue component of the new color (between 0-1)
///

void simpleCanvas_setPixelColor (int x, int y, float r, float g, float b,
    simpleCanvas *C);

///
// Writes a pixel using the current color.
//
// @param x - The x coord of the pixel to be set
// @param y - The y coord of the pixel to be set
///

void simpleCanvas_setPixel (int x, int y, simpleCanvas *C);


#endif
