//
//  midtermMain.c
//
//  Main class for CG 2D Pipeline midterm
//
//  Created by Warren R. Carithers on 02/28/14.
//  Based on a C++ version created by Joe Geigel on 11/30/11.
//  Copyright 2014 Rochester Institute of Technology. All rights reserved.
//
//  This file should not be modified by students.
//

#include <SFML/Graphics.h>
#include <stdio.h>
#include <stdbool.h>

#include "cgCanvas.h"

///
// some globals since I didn't want to make a midtermMain class.
///

// Which image are we showing?
int displayNumber = 1;

// Polygon IDs for our objects
int triangle;
int square;
int octagon;
int star;
int R;
int I;
int T;
int H;

// Dimensions of the drawing window;
int drawHeight = 500;
int drawWidth = 500;

///
// Draw plain old polygons.
///

void drawPolysNorm( cgCanvas *C )
{
    ///
    // Draw a dark blue/purple triangle
    ///
    cgCanvas_clearTransform( C );
    cgCanvas_setColor( 0.25f, 0.0f, 0.74f, C );
    cgCanvas_drawPoly( triangle, C );

    ///
    // Draw a green square
    ///
    cgCanvas_setColor( 0.0f, 0.91f, 0.08f, C );
    cgCanvas_drawPoly( square, C );

    ///
    // Draw a pink octagon
    ///
    cgCanvas_setColor( 0.98f, 0.0f, 0.48f, C );
    cgCanvas_drawPoly( octagon, C );

    ///
    // Draw a green star
    ///
    cgCanvas_setColor( 0.68f, 0.0f, 0.75f, C );
    cgCanvas_drawPoly( star, C );
}


///
// Draw the world transformed...used for transformation tests.
///

void drawPolysXform( cgCanvas *C ) {

    ///
    // Draw a dark blue/purple triangle rotated
    ///
    cgCanvas_clearTransform( C );
    cgCanvas_rotate( -25.0f, C );
    cgCanvas_setColor( 0.25f, 0.0f, 0.74f, C );
    cgCanvas_drawPoly( triangle, C );

    ///
    // Draw a green square translated
    ///
    cgCanvas_clearTransform( C );
    cgCanvas_translate( 80.0f, 75.0f, C );
    cgCanvas_setColor( 0.0f, 0.91f, 0.08f, C );
    cgCanvas_drawPoly( square, C );

    ///
    // Draw a pink octagon scaled
    ///
    cgCanvas_clearTransform( C );
    cgCanvas_scale( 0.75f, 0.5f, C );
    cgCanvas_setColor( 0.98f, 0.0f, 0.48f, C );
    cgCanvas_drawPoly( octagon, C );

    ///
    // Draw a green star translated, scaled, rotated, then scaled back
    ///
    cgCanvas_clearTransform( C );
    cgCanvas_translate( 50.0f, 50.0f, C );
    cgCanvas_scale( 2.0f, 2.0f, C );
    cgCanvas_rotate( -10.0f, C );
    cgCanvas_translate( -50.0f, 50.0f, C );
    cgCanvas_setColor( 0.68f, 0.0f, 0.75f, C );
    cgCanvas_drawPoly( star, C );
}


///
// Draw the RIT letters.
///

void drawLetters( cgCanvas *C )
{
    ///
    // Draw with staggered translation
    ///

    cgCanvas_clearTransform( C );
    cgCanvas_translate(15.0f, 0.0f, C);
    cgCanvas_drawPoly( R, C );

    cgCanvas_clearTransform( C );
    cgCanvas_translate(10.0f, 0.0f, C);
    cgCanvas_drawPoly( I, C );

    cgCanvas_clearTransform( C );
    cgCanvas_translate(5.0f, 0.0f, C);
    cgCanvas_drawPoly( T, C );

    cgCanvas_clearTransform( C );
    cgCanvas_translate(0.0f, 0.0f, C);
    cgCanvas_setColor(0.0f, 0.0f, 0.0f, C);
    cgCanvas_drawPoly( H, C );

}


///
// The display function.
///

void doDraw( cgCanvas *C )
{
    ///
    // Set clear color to black
    ///
    cgCanvas_setColor( 0.0f, 0.0f, 0.0f, C );
    cgCanvas_clear( C );

    ///
    // plain old polygon test
    ///
    if ( (displayNumber % 4) == 1) {

        // default clipping
        cgCanvas_setClipWindow( 0.0f, 500.0f, 0.0f, 500.0f, C );

        // default viewport
        cgCanvas_setViewport( 0, 0, drawWidth, drawHeight, C );

        // draw the polys
        drawPolysNorm( C );
    }
    else if ( (displayNumber % 4) == 2) {


        // clipping test
        cgCanvas_setClipWindow( 35.0f, 175.0f, 35.0f, 165.0f, C );

        // default viewport
        cgCanvas_setViewport( 0, 0, drawWidth, drawHeight, C );

        // draw the polys
        drawPolysNorm( C );

    }
    else if ( (displayNumber % 4) == 3) {

        // default clipping
        cgCanvas_setClipWindow( 0.0f, 500.0f, 0.0f, 500.0f, C );

        // default viewport
        cgCanvas_setViewport( 0, 0, drawWidth, drawHeight, C );

        // draw the tranformed polys
        drawPolysXform( C );
    }

    else { // displayNumber == 0

        // default clipping
        cgCanvas_setClipWindow( 0.0f, 500.0f, 0.0f, 500.0f, C );

        // have some fun with the view port
        int wdiff = drawWidth / 3;
        int hdiff = drawHeight / 3;

	// will use xaspect and yaspect to
	// draw each row with a different ratio
	int xaspect = drawWidth / 3;
	int yaspect = drawHeight / 3;
        int x = 0;
        int y = 0;
        int i, j;

        for( i = 0; i < 3; i++ ) {
	    // adjust yaspect
	    yaspect = hdiff/(i+1);

	    for( j = 0; j < 3; j++ ) {
	        // let's play around with colors
		if( i == j ) // 1::1 ratios
		    cgCanvas_setColor( 0.98f, 0.31f, 0.08f, C );
		else if( i < j ) // yaspect is smaller
		    cgCanvas_setColor( 0.0f, 0.91f, 0.08f, C );
		else // i > j, xaspect larger
		    cgCanvas_setColor( 0.98f, 0.0f, 0.48f, C );

		// adjust xaspect and shift to next column
		xaspect = wdiff/(j+1);
                cgCanvas_setViewport( x, y, xaspect, yaspect, C );
                drawLetters( C );
                x += wdiff + 35;
	    }

	    // shift to next row, also add a little extra space
	    // due to aspect ratio stuff making lots of blank canvas
	    y += hdiff + 35;

	    // change y aspect ratio
	    xaspect = wdiff;

	    // reset to left side of canvas
	    x = 0;

        }
    }
}


///
// Callback for key presses.  Returns a flag indicating if the app should
// close as a result of this key press.
//
// CSFML, in its infinite wisdom, doesn't actually give you the key that
// was pressed; instead, you get an sfKeyCode enumerated type value
// representing the key, with A == 0 and Z == 25.
///

bool keyTyped( sfKeyCode key )
{
    switch( key ) {
        case sfKeyQ:
		return true;                     // quit

        case sfKeyP:
	case sfKeyNum1:
		displayNumber = 1;  break;       // polygon

        case sfKeyC:
	case sfKeyNum2:
		displayNumber = 2;  break;       // clip

        case sfKeyT:
	case sfKeyNum3:
		displayNumber = 3;  break;       // transform

        case sfKeyV:
	case sfKeyNum4:
		displayNumber = 0;  break;       // viewport

    }

    return false;
}


///
// Main program for the 2D pipeline assignment.
///

int main (int argc, char *argv[])
{

    // define your canvas
    cgCanvas *C = cgCanvas_create( drawWidth, drawHeight );
    sfColor black = { 0.0, 0.0, 0.0 };

    if( C == NULL ) {
        fputs( "error - cannot create cgCanvas\n", stderr );
        return( 1 );
    }

    // load all of your polygons
    float x[12];
    float y[12];

    // triangle
    x[0] = 25.0f; y[0] = 125.0f;
    x[1] = 75.0f; y[1] = 125.0f;
    x[2] = 50.0f; y[2] = 175.0f;
    triangle = cgCanvas_addPoly (x, y, 3, C);

    // square
    x[0] = 125.0f; y[0] = 125.0f;
    x[1] = 175.0f; y[1] = 125.0f;
    x[2] = 175.0f; y[2] = 175.0f;
    x[3] = 125.0f; y[3] = 175.0f;
    square = cgCanvas_addPoly (x, y, 4, C);

    // octagon
    x[0] = 25.0f; y[0] = 25.0f;
    x[1] = 35.0f; y[1] = 15.0f;
    x[2] = 55.0f; y[2] = 15.0f;
    x[3] = 75.0f; y[3] = 25.0f;
    x[4] = 75.0f; y[4] = 55.0f;
    x[5] = 55.0f; y[5] = 75.0f;
    x[6] = 35.0f; y[6] = 75.0f;
    x[7] = 25.0f; y[7] = 55.0f;
    octagon = cgCanvas_addPoly (x, y, 8, C);

    // star
    x[0] = 150.0f; y[0] = 90.0f;
    x[1] = 140.0f; y[1] = 65.0f;
    x[2] = 110.0f; y[2] = 65.0f;
    x[3] = 140.0f; y[3] = 40.0f;
    x[4] = 110.0f; y[4] = 10.0f;
    x[5] = 150.0f; y[5] = 25.0f;
    x[6] = 190.0f; y[6] = 10.0f;
    x[7] = 160.0f; y[7] = 40.0f;
    x[8] = 190.0f; y[8] = 65.0f;
    x[9] = 160.0f; y[9] = 65.0f;
    star = cgCanvas_addPoly (x, y, 10, C);

    // R
    x[0] = 10.0f;  y[0] = 480.0f;
    x[1] = 140.0f; y[1] = 480.0f;
    x[2] = 175.0f; y[2] = 450.0f;
    x[3] = 175.0f; y[3] = 430.0f;
    x[4] = 140.0f; y[4] = 370.0f;
    x[5] = 90.0f;  y[5] = 370.0f;
    x[6] = 175.0f; y[6] = 140.0f;
    x[7] = 145.0f; y[7] = 140.0f;
    x[8] = 65.0f;  y[8] = 370.0f;
    x[9] = 35.0f;  y[9] = 370.0f;
    x[10] = 35.0f; y[10] = 140.0f;
    x[11] = 10.0f; y[11] = 140.0f;
    R = cgCanvas_addPoly( x, y, 12, C);

    // I
    x[0] = 190.0f;  y[0] = 480.0f;
    x[1] = 340.0f;  y[1] = 480.0f;
    x[2] = 340.0f;  y[2] = 440.0f;
    x[3] = 280.0f;  y[3] = 440.0f;
    x[4] = 280.0f;  y[4] = 180.0f;
    x[5] = 340.0f;  y[5] = 180.0f;
    x[6] = 340.0f;  y[6] = 140.0f;
    x[7] = 190.0f;  y[7] = 140.0f;
    x[8] = 190.0f;  y[8] = 180.0f;
    x[9] = 250.0f;  y[9] = 180.0f;
    x[10] = 250.0f; y[10] = 440.0f;
    x[11] = 190.0f; y[11] = 440.0f;
    I = cgCanvas_addPoly (x, y, 12, C);

    // T
    x[0] = 360.0f; y[0] = 480.0f;
    x[1] = 480.0f; y[1] = 480.0f;
    x[2] = 480.0f; y[2] = 440.0f;
    x[3] = 430.0f; y[3] = 440.0f;
    x[4] = 430.0f; y[4] = 140.0f;
    x[5] = 400.0f; y[5] = 140.0f;
    x[6] = 400.0f; y[6] = 440.0f;
    x[7] = 360.0f; y[7] = 440.0f;
    T = cgCanvas_addPoly (x, y, 8, C);

    // H (hole in R)
    x[0] = 35.0f;  y[0] = 450.0f;
    x[1] = 110.0f; y[1] = 450.0f;
    x[2] = 130.0f; y[2] = 430.0f;
    x[3] = 110.0f; y[3] = 410.0f;
    x[4] = 35.0f;  y[4] = 410.0f;
    H = cgCanvas_addPoly (x, y, 5, C);

    sfVideoMode mode = { drawWidth, drawHeight, 32 };
    sfRenderWindow *App = sfRenderWindow_create( mode, "CG Midterm",
        sfResize | sfClose, 0 );

    if( App == NULL ) {
        fputs( "error - cannot create sfRenderWindow\n", stderr );
        return( 1 );
    }

    while( sfRenderWindow_isOpen(App) )
    {
        // Process events
        sfEvent Event;
        while( sfRenderWindow_pollEvent(App,&Event) )
        {
            // Close window : exit
            if( Event.type == sfEvtClosed )
                sfRenderWindow_close( App );

            // Keypress
            if( Event.type == sfEvtKeyPressed )
                if( keyTyped(Event.key.code) )
                    sfRenderWindow_close( App );

            // Mouse click
            if( Event.type == sfEvtMouseButtonPressed )
                displayNumber++;
        }

        // Clear screen
        sfRenderWindow_clear( App, black );

        // draw on your canvas
        doDraw( C );

        // Draw your canvas
        cgCanvas_draw( App, C );

        sfRenderWindow_display( App );
    }

    cgCanvas_destroy( C );

    return 0;

}
