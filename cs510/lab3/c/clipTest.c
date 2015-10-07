//
//  clipTest.c
//
//  Created by Warren R. Carithers on 02/27/14.
//  Based on a C++ version created by Joe Geigel on 11/30/11.
//  Copyright 2011 Rochester Institute of Technology. All rights reserved.
//
//  This file should not be modified by students.
//

#include <SFML/Graphics.h>
#include <stdio.h>
#include <stdbool.h>

#include "extendedCanvas.h"
#include "clipper.h"

///
// Support function that draws clip regions as line loops
///

static void drawClipRegion( float llx, float lly, float urx, float ury,
	extendedCanvas *T ) {
    float x[4];
    float y[4];

    x[0] = llx; y[0] = lly;
    x[1] = urx; y[1] = lly;
    x[2] = urx; y[2] = ury;
    x[3] = llx; y[3] = ury;

    extendedCanvas_printLoop( 4, x, y, T );
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
        case sfKeyQ:  return true;                     // quit
    }

    return false;
}

///
//
// main program for clipping assignment
//
///

int main (int argc, char *argv[])
{
    extendedCanvas *T = extendedCanvas_create( 300, 300 );
    sfColor black = { 0.0, 0.0, 0.0 };

    if( T == NULL ) {
        fputs( "error - cannot create extendedCanvas\n", stderr );
        return( 1 );
    }

    extendedCanvas_setColor( 1.0, 1.0, 1.0, T );
    extendedCanvas_clear( T );

    float quad1x[4];
    float quad1y[4];
    quad1x[0] =  20; quad1x[1] =  20; quad1x[2] =  40; quad1x[3] =  40;
    quad1y[0] = 120; quad1y[1] = 140; quad1y[2] = 140; quad1y[3] = 120;

    float quad2x[4];
    float quad2y[4];
    quad2x[0] =  80; quad2x[1] =  80; quad2x[2] =  60; quad2x[3] =  60;
    quad2y[0] = 160; quad2y[1] = 200; quad2y[2] = 200; quad2y[3] = 160;

    float quad3x[4];
    float quad3y[4];
    quad3x[0] = 20; quad3x[1] = 50; quad3x[2] = 50; quad3x[3] = 20;
    quad3y[0] = 60; quad3y[1] = 60; quad3y[2] = 50; quad3y[3] = 50;

    float quad4x[4];
    float quad4y[4];
    quad4x[0] =  44; quad4x[1] =  60; quad4x[2] =  60; quad4x[3] =  44;
    quad4y[0] = 122; quad4y[1] = 122; quad4y[2] = 146; quad4y[3] = 146;

    float pent1x[5];
    float pent1y[5];
    pent1x[0] = 80; pent1x[1] = 90; pent1x[2] = 110; pent1x[3] = 100; pent1x[4] = 80;
    pent1y[0] = 20; pent1y[1] = 10; pent1y[2] = 20; pent1y[3] = 50; pent1y[4] = 40;

    float hept1x[7];
    float hept1y[7];
    hept1x[0] = 120; hept1x[1] = 140; hept1x[2] = 160; hept1x[3] = 160; hept1x[4] = 140; hept1x[5] = 120; hept1x[6] = 110;
    hept1y[0] = 70; hept1y[1] = 70; hept1y[2] = 80; hept1y[3] = 100; hept1y[4] = 110; hept1y[5] = 100; hept1y[6] = 90;

    float wx[50];
    float wy[50];
    int wl;

    ///
    // Draw the clipping regions
    ///
    extendedCanvas_setColor ( 0.0f, 0.0f, 0.0f, T );
    drawClipRegion(  10, 110,  50, 150, T );
    drawClipRegion(  30,  10,  70,  80, T );
    drawClipRegion(  90,  34, 120,  60, T );
    drawClipRegion(  90,  80, 130, 110, T );

    ///
    // first polygon:  entirely within region
    ///

    wl = 0;
    wl = clipPolygon( 4, quad1x, quad1y, wx, wy, 10, 110, 50, 150 );
    extendedCanvas_setColor ( 1.0f, 0.0f, 0.0f, T );	// red
    extendedCanvas_printLoop( 4, quad1x, quad1y, T );
    extendedCanvas_printPoly( wl, wx, wy, T );

    ///
    // second polygon:  entirely outside region
    ///

    wl = 0;
    extendedCanvas_setColor ( 0.0f, 1.0f, 0.0f, T ); // green
    extendedCanvas_printLoop( 4, quad2x, quad2y, T );
    wl = clipPolygon( 4, quad2x, quad2y, wx, wy, 10, 110, 50, 150 );
    // shouldn't draw anything!
    if( wl > 0 ) {
        extendedCanvas_printPoly( wl, wx, wy, T );
    }

    ///
    // third polygon:  halfway outside on left
    ///

    wl = 0;
    wl = clipPolygon( 4, quad3x, quad3y, wx, wy, 30, 10, 70, 80 );
    extendedCanvas_setColor ( 0.0f, 0.0f, 1.0f, T );	// blue
    extendedCanvas_printLoop( 4, quad3x, quad3y, T );
    extendedCanvas_printPoly( wl, wx, wy, T );

    ///
    // fourth polygon:  part outside on right
    ///

    wl = 0;
    wl = clipPolygon( 4, quad4x, quad4y, wx, wy, 10, 110, 50, 150 );
    extendedCanvas_setColor ( 1.0f, 0.0f, 1.0f, T );	// magenta
    extendedCanvas_printLoop( 4, quad4x, quad4y, T );
    extendedCanvas_printPoly( wl, wx, wy, T );

    ///
    // fifth polygon:  outside on left and bottom
    ///

    wl = 0;
    wl = clipPolygon( 5, pent1x, pent1y, wx, wy, 90, 34, 120, 60 );
    extendedCanvas_setColor ( 1.0f, 0.5f, 1.0f, T ); // red-greenish-blue
    extendedCanvas_printLoop( 5, pent1x, pent1y, T );
    extendedCanvas_printPoly( wl, wx, wy, T );

    ///
    // sixth polygon:  outside on top, right, and bottom
    ///

    wl = 0;
    wl = clipPolygon( 7, hept1x, hept1y, wx, wy, 90, 80, 130, 110 );
    extendedCanvas_setColor ( 0.7f, 0.7f, 0.7f, T );	// gray
    extendedCanvas_printLoop( 7, hept1x, hept1y, T );
    extendedCanvas_printPoly( wl, wx, wy, T );

    sfVideoMode mode = { 300, 300, 32 };
    sfRenderWindow *App = sfRenderWindow_create( mode, "Clipping Test",
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
        }

        // Clear screen
        sfRenderWindow_clear( App, black );

        // Draw your canvas
	extendedCanvas_draw( App, T );

        sfRenderWindow_display( App );
    }

    extendedCanvas_destroy( T );

    return 0;

}
