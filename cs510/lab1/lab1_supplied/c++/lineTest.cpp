//
//  lineTest.cpp
//
//  Created by Joe Geigel on 11/30/11.
//  Copyright 2011 Rochester Institute of Technology. All rights reserved.
//
//  Modified and updated to SFML 2.1 by Vasudev Prasad Bethamcherla
//  on 08/19/2014
//
//  Contributor:  YOUR_NAME_HERE
//

#include <SFML/Graphics.hpp>
#include "simpleCanvas.h"
#include "Rasterizer.h"

///
// Callback for key presses.  Returns a flag indicating if the app should
// close as a result of this key press.
//
// SFML, in its infinite wisdom, doesn't actually give you the key that
// was pressed; instead, you get an sf::Keyboard::Key enumerated type
// value representing the key, with A == 0 and Z == 25.
///

bool keyTyped(enum sf::Keyboard::Key key)
{
    switch( key ) {
        case sf::Keyboard::Q:  return true;                     // quit
    }

    return false;
}


///
// 
// main program for line drawing assignment
//
///

int main (int argc, char *argv[])
{
    simpleCanvas T( 600, 600 );
    Rasterizer R( 600 );
    T.setColor( 0.0, 0.0, 0.0 );
    T.clear();

// Idea for lettering style from:
// http://patorjk.com/software/taag/#p=display&f=Star%20Wars&t=Type%20Something
//          _______   ______   
//         /  _____| /  __  \
//        |  |  __  |  |  |  | 
//        |  | |_ | |  |  |  | 
//        |  |__| | |  `--'  | 
//         \______|  \______/


    // ######## The letter 'G' in green ########
    T.setColor( 0.0, 1.0, 0.0 );
    R.drawLine( 80, 340, 220, 340, T );   // Horizontal left to right 
    R.drawLine( 40, 380, 80, 340, T );    // 315 degree slope        
    R.drawLine( 220, 340, 260, 380, T );  // 45 degree slope          
    R.drawLine( 260, 380, 260, 440, T );  // Vertical bottom to top
    R.drawLine( 260, 440, 180, 440, T );  // Horizontal right to left
    R.drawLine( 180, 440, 180, 400, T );
    R.drawLine( 180, 400, 220, 400, T );
    R.drawLine( 220, 400, 200, 380, T );
    R.drawLine( 200, 380, 100, 380, T );
    R.drawLine( 100, 380, 80, 400, T );
    R.drawLine( 80, 400, 80, 500, T );
    R.drawLine( 80, 500, 100, 520, T );
    R.drawLine( 100, 520, 200, 520, T );
    R.drawLine( 200, 520, 220, 500, T );
    R.drawLine( 220, 500, 220, 480, T );
    R.drawLine( 220, 480, 260, 480, T );
    R.drawLine( 260, 480, 260, 520, T );
    R.drawLine( 260, 520, 220, 560, T );  // 135 degree slope
    R.drawLine( 220, 560, 80, 560, T );
    R.drawLine( 80, 560, 40, 520, T );    // 225 degree slope
    R.drawLine( 40, 520, 40, 380, T );    // Vertical top to bottom

    // ######## The letter 'O' in red ########
    T.setColor( 1.0, 0.0, 0.0 );
    R.drawLine( 450, 320, 520, 340, T );  // 16.6 degree slope
    R.drawLine( 520, 340, 540, 360, T );  // 45 degree slope
    R.drawLine( 540, 360, 560, 450, T );  // 77.47 degree slope
    R.drawLine( 560, 450, 540, 540, T );  // 102.83 degree slope
    R.drawLine( 540, 540, 520, 560, T );  // 135 degree slope
    R.drawLine( 520, 560, 450, 580, T );  // 163.3 degree slope
    R.drawLine( 450, 580, 380, 560, T );  // 196.71 degree slope
    R.drawLine( 380, 560, 360, 540, T );  // 225 degree slope
    R.drawLine( 360, 540, 340, 450, T );  
    R.drawLine( 340, 450, 360, 360, T );
    R.drawLine( 360, 360, 380, 340, T );
    R.drawLine( 380, 340, 450, 320, T );
    R.drawLine( 420, 380, 480, 380, T );
    R.drawLine( 480, 380, 520, 420, T );
    R.drawLine( 520, 420, 520, 480, T );
    R.drawLine( 520, 480, 480, 520, T );
    R.drawLine( 480, 520, 420, 520, T );
    R.drawLine( 420, 520, 380, 480, T );
    R.drawLine( 380, 480, 380, 420, T );
    R.drawLine( 380, 420, 420, 380, T );

    // ######## Use blue color (0,0.5,1) to write your initials ######## 

    T.setColor( 0.0, 0.5, 1.0 );

    //
    // YOUR CODE HERE
    //

    sf::RenderWindow App( sf::VideoMode(600, 600), "Line Test" );
    while( App.isOpen() )
    {
        // Process events
        sf::Event Event;
        while( App.pollEvent(Event) )
        {
            // Close window : exit
            if( Event.type == sf::Event::Closed )
                App.close();
            
           // Keypress
	   if( Event.type == sf::Event::KeyPressed )
	      if( keyTyped(Event.key.code) )
	         App.close();
        }

        // Clear screen
        App.clear();

        // Draw your canvas
        T.draw( App );

        App.display();
    }

    return 0;

}
