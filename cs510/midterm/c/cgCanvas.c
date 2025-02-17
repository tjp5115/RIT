//
//  cgCanvas.c
//
//  Created by Warren R. Carithers on 02/28/14.
//  Based on a C++ version created by Joe Geigel on 11/30/11.
//  Copyright 2014 Rochester Institute of Technology. All rights reserved.
//
//  Contributor:  YOUR_NAME_HERE
//

#include <SFML/Config.h>
#include <SFML/Graphics/Sprite.h>

#include <stdlib.h>

#include "cgCanvas.h"

///
// Simple wrapper class for midterm assignment
//
// Only methods in the implementation file whose bodies
// contain the comment
//
//    // YOUR IMPLEMENTATION HERE
//
// are to be modified by students; all of these are
// found at the end of the file.
///

///
// "Inherited" methods from simpleCanvas
//
// These are all implemented as simple wrapper functions that invoke
// the corresponding simpleCanvas function using the simpleCanvas that
// is contained in the supplied cgCanvas "object".
///

///
// Draw yourself into a given renderWindow.
//
// @param R - the window in which to draw
// @param C - the cgCanvas to be used
///

void cgCanvas_draw( sfRenderWindow *R, cgCanvas *C )
{
    simpleCanvas_draw( R, C->sc );
}

///
// Clears the canvas using the current color.
//
// @param C - the cgCanvas to be used
///

void cgCanvas_clear( cgCanvas *C )
{
    simpleCanvas_clear( C->sc );
}

///
// Sets the current color.
//
// @param r - The red component of the new color (between 0-1)
// @param g - The green component of the new color (between 0-1)
// @param b - The blue component of the new color (between 0-1)
// @param C - the cgCanvas to be used
///

void cgCanvas_setColor( float r, float g, float b, cgCanvas *C )
{
    simpleCanvas_setColor( r, g, b, C->sc );
}

///
// Writes a pixel using the passed in color.  Does not modify the
// current color.
//
// @param x - The x coord of the pixel to be set
// @param y - The y coord of the pixel to be set
// @param r - The red component of the new color (between 0-1)
// @param g - The green component of the new color (between 0-1)
// @param b - The blue component of the new color (between 0-1)
// @param C - the cgCanvas to be used
///

void cgCanvas_setPixelColor( int x, int y, float r, float g, float b,
    cgCanvas *C )
{
    simpleCanvas_setPixelColor( x, y, r, g, b, C->sc );
}

///
// Writes a pixel using the current color.
//
// @param x - The x coord of the pixel to be set
// @param y - The y coord of the pixel to be set
// @param C - the cgCanvas to be used
///

void cgCanvas_setPixel( int x, int y, cgCanvas *C )
{
    simpleCanvas_setPixel( x, y, C->sc );
}

///
// Methods which extend simpleCanvas by providing
// additional functionality related to the midterm
// project requirements.
//
// These methods are to be implemented by the student.
///

///
// Constructor
//
// @param w width of canvas
// @param h height of canvas
///

cgCanvas* cgCanvas_create( int w, int h )
{
    cgCanvas *new = calloc( 1, sizeof(cgCanvas) );
    if( new != NULL ) {
        new->sc = simpleCanvas_create( w, h );
        if( new->sc == NULL ) {
            free( new );
            new = NULL;
        }
    }

    // YOUR IMPLEMENTATION HERE if you need to modify the constructor

    return( new );
}

///
// Destructor
//
// @param C canvas to destroy
///

void cgCanvas_destroy( cgCanvas *C )
{
    if( !C )
    	return;
    if( C->sc )
        simpleCanvas_destroy( C->sc );
    free( C );
}

///
// addPoly - Add a polygon to the canvas.  This method does not draw
//           the polygon, but merely stores it for later drawing.
//           Drawing is initiated by the drawPoly() method.
//
//           Returns a unique integer id for the polygon.
//
// @param x - Array of x coords of the vertices of the polygon to be added
// @param y - Array of y coords of the vertices of the polygon to be added
// @param n - Number of vertices in polygon
// @param C - the cgCanvas to be used
//
// @return a unique integer identifier for the polygon
///

int cgCanvas_addPoly( const float x[], const float y[], int n, cgCanvas *C )
{
    // YOUR IMPLEMENTATION HERE

    // REMEMBER TO RETURN A UNIQUE ID FOR THE POLYGON
    return 0;
}

///
// drawPoly - Draw the polygon with the given id.  The polygon should
//            be drawn after applying the current transformation to
//            the vertices of the polygon.
//
// @param polyID - the ID of the polygon to be drawn
// @param C - the cgCanvas to be used
///

void cgCanvas_drawPoly (int polyID, cgCanvas *C )
{
    // YOUR IMPLEMENTATION HERE
}

///
// clearTransform - Set the current transformation to the identity matrix.
//
// @param C - the cgCanvas to be used
///

void cgCanvas_clearTransform( cgCanvas *C )
{
    // YOUR IMPLEMENTATION HERE
}

///
// translate - Add a translation to the current transformation by
//             premultiplying the appropriate translation matrix to
//             the current transformtion matrix.
//
// @param x - Amount of translation in x
// @param y - Amount of translation in y
// @param C - the cgCanvas to be used
///

void cgCanvas_translate (float x, float y, cgCanvas *C )
{
    // YOUR IMPLEMENTATION HERE
}

///
// rotate - Add a rotation to the current transformation by premultiplying
//          the appropriate rotation matrix to the current transformtion
//          matrix.
//
// @param degrees - Amount of rotation in degrees
// @param C - the cgCanvas to be used
///

void cgCanvas_rotate (float degrees, cgCanvas *C )
{
    // YOUR IMPLEMENTATION HERE
}

///
// scale - Add a scale to the current transformation by premultiplying
//         the appropriate scaling matrix to the current transformtion
//         matrix.
//
// @param x - Amount of scaling in x
// @param y - Amount of scaling in y
// @param C - the cgCanvas to be used
///

void cgCanvas_scale (float x, float y, cgCanvas *C )
{
    // YOUR IMPLEMENTATION HERE
}

///
// setClipWindow - Define the clip window.
//
// @param bottom - y coord of bottom edge of clip window (in world coords)
// @param top - y coord of top edge of clip window (in world coords)
// @param left - x coord of left edge of clip window (in world coords)
// @param right - x coord of right edge of clip window (in world coords)
// @param C - the cgCanvas to be used
///

void cgCanvas_setClipWindow (float bottom, float top, float left, float right,
    cgCanvas *C )
{
    // YOUR IMPLEMENTATION HERE
}

///
// setViewport - Define the viewport.
//
// @param xmin - x coord of lower left of view window (in screen coords)
// @param ymin - y coord of lower left of view window (in screen coords)
// @param width - width of view window (in world coords)
// @param height - width of view window (in world coords)
// @param C - the cgCanvas to be used
///

void cgCanvas_setViewport (int x, int y, int width, int height, cgCanvas *C )
{
    // YOUR IMPLEMENTATION HERE
}
