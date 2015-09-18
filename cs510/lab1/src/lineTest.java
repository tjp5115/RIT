//
//  lineTest.java
//
//  Created by Joe Geigel on 1/21/10.
//  Copyright 2014 Rochester Institute of Technology. All rights reserved.
//
//  Modified by Vasudev Prasad Bethamcherla on 08/19/2014
//
//  Contributor: Tyler Paulsen
//

import java.awt.*;
import java.awt.event.*;

public class lineTest implements KeyListener {

    private static int drawHeight = 600;
    private static int drawWidth = 600;

    private simpleCanvas S;
    private Rasterizer R;

    lineTest( int w, int h )
    {

        S = new simpleCanvas( 600, 600 );
        R = new Rasterizer( 600 );

        S.setColor (0.0f, 0.0f, 0.0f);
        S.clear();
        S.setColor (1.0f, 1.0f, 1.0f);

// Idea for lettering style from:
// http://patorjk.com/software/taag/#p=display&f=Star%20Wars&t=Type%20Something
//          _______   ______   
//         /  _____| /  __  \
//        |  |  __  |  |  |  | 
//        |  | |_ | |  |  |  | 
//        |  |__| | |  `--'  | 
//         \______|  \______/
    /*
        // ######## The letter 'G' in green ########
        S.setColor( 0.0f, 1.0f, 0.0f );
        R.drawLine( 80, 340, 220, 340, S );   // Horizontal left to right 
        R.drawLine( 40, 380, 80, 340, S );    // 315 degree slope        
        R.drawLine( 220, 340, 260, 380, S );  // 45 degree slope          
        R.drawLine( 260, 380, 260, 440, S );  // Vertical bottom to top
        R.drawLine( 260, 440, 180, 440, S );  // Horizontal right to left
        R.drawLine( 180, 440, 180, 400, S );
        R.drawLine( 180, 400, 220, 400, S );
        R.drawLine( 220, 400, 200, 380, S );
        R.drawLine( 200, 380, 100, 380, S );
        R.drawLine( 100, 380, 80, 400, S );
        R.drawLine( 80, 400, 80, 500, S );
        R.drawLine( 80, 500, 100, 520, S );
        R.drawLine( 100, 520, 200, 520, S );
        R.drawLine( 200, 520, 220, 500, S );
        R.drawLine( 220, 500, 220, 480, S );
        R.drawLine( 220, 480, 260, 480, S );
        R.drawLine( 260, 480, 260, 520, S );
        R.drawLine( 260, 520, 220, 560, S );  // 135 degree slope
        R.drawLine( 220, 560, 80, 560, S );
        R.drawLine( 80, 560, 40, 520, S );    // 225 degree slope
        R.drawLine( 40, 520, 40, 380, S );    // Vertical top to bottom

        // ######## The letter 'O' in red ########
        S.setColor( 1.0f, 0.0f, 0.0f );
        R.drawLine( 450, 320, 520, 340, S );  // 16.6 degree slope
        R.drawLine( 520, 340, 540, 360, S );  // 45 degree slope
        R.drawLine( 540, 360, 560, 450, S );  // 77.47 degree slope
        R.drawLine( 560, 450, 540, 540, S );  // 102.83 degree slope
        R.drawLine( 540, 540, 520, 560, S );  // 135 degree slope
        R.drawLine( 520, 560, 450, 580, S );  // 163.3 degree slope
        R.drawLine( 450, 580, 380, 560, S );  // 196.71 degree slope
        R.drawLine( 380, 560, 360, 540, S );  // 225 degree slope
        R.drawLine( 360, 540, 340, 450, S );  
        R.drawLine( 340, 450, 360, 360, S );
        R.drawLine( 360, 360, 380, 340, S );
        R.drawLine( 380, 340, 450, 320, S );
        R.drawLine( 420, 380, 480, 380, S );
        R.drawLine( 480, 380, 520, 420, S );
        R.drawLine( 520, 420, 520, 480, S );
        R.drawLine( 520, 480, 480, 520, S );
        R.drawLine( 480, 520, 420, 520, S );
        R.drawLine( 420, 520, 380, 480, S );
        R.drawLine( 380, 480, 380, 420, S );
        R.drawLine( 380, 420, 420, 380, S );
*/
        // ######## Use blue color (0,0.5,1) to write your initials ########
        //normal slope positive
        R.drawLine( 200, 300, 400, 500, S );
        R.drawLine( 200, 300, 400, 400, S );

        //normal slope negative
        R.drawLine( 200, 300, 0, 100, S );
        R.drawLine( 200, 300, 0, 200, S );

        //steep slope positive
        R.drawLine( 200, 300, 300, 500, S );

        //steep slope negative
        R.drawLine( 200, 300, 100, 100, S );
        //horizontal and vertical
        R.drawLine( 200, 300 ,200, 500, S );
        R.drawLine( 200, 300 ,400, 300, S );
        R.drawLine( 200, 300 ,0, 300, S );
        R.drawLine( 200, 300, 200, 100, S );

        //normal slope negative -- 4
        R.drawLine( 200, 300, 400, 200, S );
        R.drawLine( 200, 300, 400, 100, S );

        //steep slop negative -- 4
        R.drawLine( 200, 300, 300, 100, S );

        //normal slope negative -- 2
        R.drawLine( 200, 300, 0, 500, S );
        R.drawLine( 200, 300, 0, 400, S );

        //steep slope negative -- 2
        R.drawLine( 200, 300, 100, 500, S );

        S.setColor( 0.0f, 0.5f, 1.0f );

        //
        // YOUR CODE HERE
        //


    }

    // Because we are a KeyListener
    public void keyTyped(KeyEvent e)
    {
        // What key did we type?
        char key = e.getKeyChar();

        if( (key == 'Q') || (key == 'q') ) System.exit(0); // quit

    }
    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}

    static public void main(String[] args)
    {

        lineTest L = new lineTest (drawWidth, drawHeight);
        L.S.addKeyListener (L);

        Frame f = new Frame( "Line Test" );
        f.add("Center", L.S);
        f.pack();
        f.setResizable (false);
        f.setVisible(true);

        f.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        } );


    }
}
