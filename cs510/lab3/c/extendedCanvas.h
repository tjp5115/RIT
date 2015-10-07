//
//  extendedCanvas.h
//
//  Created by Warren R. Carithers on 02/27/14.
//  Based on a C++ version created by Joe Geigel on 11/30/11.
//  Copyright 2014 Rochester Institute of Technology. All rights reserved.
//
//  This file should not be modified by students.
//

#ifndef _EXTENDEDCANVAS_H_
#define _EXTENDEDCANVAS_H_

#include <SFML/Graphics/ConvexShape.h>
#include <SFML/Graphics/Color.h>
#include <SFML/Graphics/Image.h>
#include <SFML/Graphics/RenderWindow.h>

#include "simpleCanvas.h"
#include "shapeVector.h"

///
// This is a special subclass of simpleCanvas with functionality
// for testing out the clipping assignment.
//
// Note, this class should only be used for the clipping assignment
// and only for testing purposes!!!
///

typedef struct st_extendedCanvas {

	///
	// our simpleCanvas "object"
	///
	simpleCanvas *sc;

	///
	// CSFML polygons
	///
	shapeVector_t polys;

} extendedCanvas;

///
// Constructor
//
// @param w width of canvas
// @param h height of canvas
///

extendedCanvas *extendedCanvas_create( int w, int h );

///
// Destructor
//
// @param E extendedCanvas to destroy
///

void extendedCanvas_destroy( extendedCanvas *E );

///
// draw yourself into a given renderWindow
//
// @param R - the window in which to draw.
///

void extendedCanvas_draw( sfRenderWindow *R, extendedCanvas *E );

///
// prints a poly outline -- for cliptest assignment
///

void extendedCanvas_printLoop( int n, const float x[], const float y[],
    extendedCanvas *E );

///
// draws a filled poly -- for cliptest assignment
///

void extendedCanvas_printPoly( int n, const float x[], const float y[],
    extendedCanvas *E );

///
// "Inherited" methods from simpleCanvas
//
// These are all implemented as simple wrapper functions
// that invoke the corresponding simpleCanvas function
// using the simpleCanvas that is contained in the
// supplied extendedCanvas "object".
///

///
// clears the canvas using the current color
///

void extendedCanvas_clear( extendedCanvas *E );

///
// Sets the current color
//
// @param r - The red component of the new color (between 0-1)
// @param g - The green component of the new color (between 0-1)
// @param b - The blue component of the new color (between 0-1);
///

void extendedCanvas_setColor( float r, float g, float b, extendedCanvas *E );

///
// writes a pixel using the passed in color.  Does not modify the
// current color
//
// @param x - The x coord of the pixel to be set
// @param y - The y coord of the pixel to be set
// @param r - The red component of the new color (between 0-1)
// @param g - The green component of the new color (between 0-1)
// @param b - The blue component of the new color (between 0-1)
///

void extendedCanvas_setPixelColor( int x, int y, float r, float g, float b,
    extendedCanvas *E );

///
// writes a pixel using the current color
//
// @param x - The x coord of the pixel to be set
// @param y - The y coord of the pixel to be set
///

void extendedCanvas_setPixel( int x, int y, extendedCanvas *E );

#endif
