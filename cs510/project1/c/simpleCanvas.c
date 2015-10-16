//
//  simpleCanvas.c
//
//  Created by Warren R. Carithers on 01/15/14.
//  Based on a C++ version created by Joe Geigel on 11/30/11.
//  Copyright 2014 Rochester Institute of Technology.  All rights reserved.
//
//  This file should not be modified by students.
//

#include <SFML/Config.h>
#include <SFML/Graphics/Sprite.h>

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

#include "simpleCanvas.h"

///
// Default amount to "grow" the vector by when it fills up
//
// Smaller values => more frequent growth, but higher utilization
// Larger values =>  less frequent growth, but potentially more wasted space
///

#define DEFAULT_GROWTH  256

///
// shapeVectorGrowthFactor -- set the growth factor for a shapeVector_t
//
// returns the old growth factor
///

size_t shapeVectorGrowthFactor( shapeVector_t *vec, size_t size ) {
    size_t old = vec->growth;
    vec->growth = size;
    return( old );
}

///
// shapeVectorClear -- return a shapeVector_t to its original state
///

void shapeVectorClear( shapeVector_t *vec ) {
    int i;

    // verify that we were given a non-NULL pointer
    if( vec == 0 ) {
        return;
    }

    // release any existing allocated space
    if( vec->vec != 0 ) {
        // release all sfConvexShape objects held in the vector
        for( i = 0; i < vec->size; ++i ) {
            sfConvexShape_destroy( (vec->vec)[i] );
        }
        free( vec->vec );
        vec->vec = 0;
    }

    // record the fact that the vector is now empty
    vec->length = vec->size = 0;

    // ensure that there is a growth factor for this vector
    if( vec->growth == 0 ) {
        vec->growth = DEFAULT_GROWTH;
    }

}

///
// shapeVectorPushBack -- add a shape to the end of the vector,
// automatically extending the vector if need be
///

void shapeVectorPushBack( shapeVector_t *vec, sfConvexShape *shape ) {
    sfConvexShape **tmp;

    // verify that we were given a non-NULL pointer
    if( vec == 0 ) {
        return;
    }

    // allocate initial vector if we need to
    if( vec->length == 0 ) {
        if( vec->growth == 0 ) {
            vec->growth = DEFAULT_GROWTH;
        }
        vec->length = vec->growth;
        tmp = (sfConvexShape **) calloc( vec->growth,
	             sizeof(sfConvexShape *) );
        if( tmp == 0 ) {
            perror( "vector allocation failed" );
            exit( 1 );
        }
        vec->vec = tmp;
    }

    // extend the vector if we need to
    if( vec->size >= vec->length ) {
        if( vec->growth == 0 ) {
            vec->growth = DEFAULT_GROWTH;
        }
        vec->length += vec->growth;
        tmp = (sfConvexShape **) realloc( vec->vec,
                     vec->length * sizeof(sfConvexShape *) );
        if( tmp == 0 ) {
            perror( "vector reallocation failed" );
            exit( 2 );
        }
        vec->vec = tmp;
    }

    // add the new shape to the vector
    vec->vec[ vec->size ] = shape;
    vec->size += 1;

}

///
// Simple canvas class that allows for pixel-by-pixel rendering.
///

///
// Constructor
//
// @param w width of canvas
// @param h height of canvas
///

simpleCanvas *simpleCanvas_create( int w, int h )
{
    simpleCanvas *new = calloc( 1, sizeof(simpleCanvas) );
    if( new != NULL ) {
        new->width = w;
        new->height = h;
        new->myColor.r = 0;
        new->myColor.g = 0;
        new->myColor.b = 0;
        new->myColor.a = 255;
        new->myImage = sfImage_createFromColor( w, h, new->myColor );
    }
    return( new );
}


///
// Destructor
//
// @param C canvas to destroy
///

void simpleCanvas_destroy( simpleCanvas *C )
{
    if( !C )
        return;
    if( C->myImage )
        sfImage_destroy( C->myImage );
    free( C );
}


///
// Clears the canvas using the current color.
///

void simpleCanvas_clear( simpleCanvas *C )
{
    for (int i=0; i < C->width; i++)
        for (int j=0; j < C->height; j++)
            sfImage_setPixel( C->myImage, i, j, C->myColor );
}

///
// Draw yourself into a given renderWindow.
//
// @param R - the window in which to draw
///

void simpleCanvas_draw (sfRenderWindow *R, simpleCanvas *C )
{
    sfSprite *sprite;
    sfTexture *texture;

    texture = sfTexture_createFromImage( C->myImage, NULL );
    sfTexture_setSmooth( texture, sfFalse );

    // Add your image as a sprite
    sprite = sfSprite_create();
    sfSprite_setTexture( sprite, texture, sfFalse );

    // draw it
    sfRenderWindow_drawSprite( R, sprite, 0 );

    // release the sprite and texture
    sfSprite_destroy( sprite );
    sfTexture_destroy( texture );
}


///
// Use this drawOutline() method only if you were NOT able to
// create a working drawPolygon() Rasterizer routine of your own.
// This method will only draw the outline of the polygon.
///

void simpleCanvas_drawOutline( int n, int x[], int y[], simpleCanvas *C )
{
    sfConvexShape *P = sfConvexShape_create();
    sfConvexShape_setPointCount( P, n );
    sfConvexShape_setOutlineColor( P, C->myColor );
    sfConvexShape_setOutlineThickness( P, 1 );

    for( int i=0; i < n; i++ ) {
        sfVector2f tmp = { x[i], C->height - y[i] };
        sfConvexShape_setPoint( P, i, tmp );
    }

    shapeVectorPushBack( &(C->polys), P );
}


///
// Sets the current color
//
// @param r - The red component of the new color (between 0-1)
// @param g - The green component of the new color (between 0-1)
// @param b - The blue component of the new color (between 0-1);
///

void simpleCanvas_setColor (float r, float g, float b, simpleCanvas *C )
{
    C->myColor.r = (unsigned char)(r * 255);
    C->myColor.g = (unsigned char)(g * 255);
    C->myColor.b = (unsigned char)(b * 255);
}


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

void simpleCanvas_setPixelColor (int x, int y, float r, float g, float b,
    simpleCanvas *C )
{
    unsigned char R = (unsigned char)(r * 255);
    unsigned char G = (unsigned char)(g * 255);
    unsigned char B = (unsigned char)(b * 255);
    sfColor color = { R, G, B };
    sfImage_setPixel( C->myImage, x, y, color );
}

///
// writes a pixel using the current color
//
// @param x - The x coord of the pixel to be set
// @param y - The y coord of the pixel to be set
///

void simpleCanvas_setPixel (int x, int y, simpleCanvas *C )
{
    sfImage_setPixel( C->myImage, x, C->height - y - 1, C->myColor );
}
