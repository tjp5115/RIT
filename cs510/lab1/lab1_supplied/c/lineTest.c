//
//  lineTest.c
//
//  Created by Warren R. Carithers on 01/28/14.
//  Based on a C++ version created by Joe Geigel on 11/30/11.
//  Copyright 2014 Rochester Institute of Technology. All rights reserved.
//
//  Contributor:  YOUR_NAME_HERE
//

#include <SFML/Graphics.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdbool.h>

#include "simpleCanvas.h"
#include "Rasterizer.h"

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
// main program for line drawing assignment
//
///

int main( int argc, char *argv[] ) {
    simpleCanvas *T = simpleCanvas_create( 600, 600 );
    Rasterizer *R = Rasterizer_create( 600 );
    sfColor black = { 0.0, 0.0, 0.0 };

    if( T == NULL ) {
        fputs( "error - cannot create simpleCanvas\n", stderr );
        return( 1 );
    }

    if( R == NULL ) {
        fputs( "error - cannot create Rasterizer\n", stderr );
        return( 1 );
    }

    simpleCanvas_setColor( 0.0, 0.0, 0.0, T );
    simpleCanvas_clear( T );

// Idea for lettering style from:
// http://patorjk.com/software/taag/#p=display&f=Star%20Wars&t=Type%20Something
//          _______   ______   
//         /  ____|  /  __  \
//        |  |  __  |  |  |  | 
//        |  | |_ | |  |  |  | 
//        |  |__| | |  `--'  | 
//         \______|  \______/


    // ######## The letter 'G' in green ########
    simpleCanvas_setColor( 0.0, 1.0, 0.0, T );
    drawLine( 80, 340, 220, 340, T, R );   // Horizontal left to right 
    drawLine( 40, 380, 80, 340, T, R );    // 315 degree slope        
    drawLine( 220, 340, 260, 380, T, R );  // 45 degree slope          
    drawLine( 260, 380, 260, 440, T, R );  // Vertical bottom to top
    drawLine( 260, 440, 180, 440, T, R );  // Horizontal right to left
    drawLine( 180, 440, 180, 400, T, R );
    drawLine( 180, 400, 220, 400, T, R );
    drawLine( 220, 400, 200, 380, T, R );
    drawLine( 200, 380, 100, 380, T, R );
    drawLine( 100, 380, 80, 400, T, R );
    drawLine( 80, 400, 80, 500, T, R );
    drawLine( 80, 500, 100, 520, T, R );
    drawLine( 100, 520, 200, 520, T, R );
    drawLine( 200, 520, 220, 500, T, R );
    drawLine( 220, 500, 220, 480, T, R );
    drawLine( 220, 480, 260, 480, T, R );
    drawLine( 260, 480, 260, 520, T, R );
    drawLine( 260, 520, 220, 560, T, R );  // 135 degree slope
    drawLine( 220, 560, 80, 560, T, R );
    drawLine( 80, 560, 40, 520, T, R );    // 225 degree slope
    drawLine( 40, 520, 40, 380, T, R );    // Vertical top to bottom

    // ######## The letter 'O' in red ########
    simpleCanvas_setColor( 1.0, 0.0, 0.0, T );
    drawLine( 450, 320, 520, 340, T, R );  // 16.6 degree slope
    drawLine( 520, 340, 540, 360, T, R );  // 45 degree slope
    drawLine( 540, 360, 560, 450, T, R );  // 77.47 degree slope
    drawLine( 560, 450, 540, 540, T, R );  // 102.83 degree slope
    drawLine( 540, 540, 520, 560, T, R );  // 135 degree slope
    drawLine( 520, 560, 450, 580, T, R );  // 163.3 degree slope
    drawLine( 450, 580, 380, 560, T, R );  // 196.71 degree slope
    drawLine( 380, 560, 360, 540, T, R );  // 225 degree slope
    drawLine( 360, 540, 340, 450, T, R );  
    drawLine( 340, 450, 360, 360, T, R );
    drawLine( 360, 360, 380, 340, T, R );
    drawLine( 380, 340, 450, 320, T, R );
    drawLine( 420, 380, 480, 380, T, R );
    drawLine( 480, 380, 520, 420, T, R );
    drawLine( 520, 420, 520, 480, T, R );
    drawLine( 520, 480, 480, 520, T, R );
    drawLine( 480, 520, 420, 520, T, R );
    drawLine( 420, 520, 380, 480, T, R );
    drawLine( 380, 480, 380, 420, T, R );
    drawLine( 380, 420, 420, 380, T, R );

    // ######## Use blue color (0,0.5,1) to write your initials ######## 

    simpleCanvas_setColor( 0.0, 0.5, 1.0, T );

    //
    // YOUR CODE HERE
    //


    sfVideoMode mode = { 600, 600, 32 };
    sfRenderWindow *App = sfRenderWindow_create( mode, "Line Test", 
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
        simpleCanvas_draw( App, T );

        sfRenderWindow_display( App );
    }

    Rasterizer_destroy( R );
    simpleCanvas_destroy( T );

    return 0;

}
