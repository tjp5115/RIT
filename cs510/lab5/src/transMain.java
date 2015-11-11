//
//  transMain.java
//
//  Main program for transformation assignment.
//
//  Students should not be modifying this file.
//

import java.awt.*;
import java.nio.*;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;

public class transMain implements GLEventListener, KeyListener, MouseListener
{

    ///
    // buffer info
    ///
    private int vbuffer[];
    private int ebuffer[];

    ///
    // program and variable IDs
    ///
    public int program;
    int numVerts[];

    ///
    // view params
    ///
    viewParams vp;
    int viewMode;

    ///
    // image selection and camera/transformation control
    ///
    int counter;
    boolean cameraAdjust;
    boolean transformsOn;

    ///
    // shape info
    ///
    teapot myShape;

    ///
    // my canvas
    ///
    GLCanvas myCanvas;
    private static Frame frame;

    ///
    // constructor
    ///
    public transMain( GLCanvas G )
    {
        vbuffer = new int [1];
        ebuffer = new int[1];
        numVerts = new int [1];
        vp = new viewParams();
        viewMode = 1;

        counter = 0;
        cameraAdjust = false;
        transformsOn = false;

        myCanvas = G;

        G.addGLEventListener( this );
        G.addKeyListener( this );
        G.addMouseListener( this );
    }

    private void errorCheck( GL2 gl2 )
    {
        int code = gl2.glGetError();
        if( code == GL.GL_NO_ERROR )
            System.err.println( "All is well" );
        else
            System.err.println( "Problem - error code : " + code );

    }


    ///
    // Called by the drawable to initiate OpenGL rendering by the client.
    ///
    public void display( GLAutoDrawable drawable )
    {
        // get GL
        GL2 gl2 = (drawable.getGL()).getGL2();

        // clear your frame buffers
        gl2.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );

        // bind your vertex buffer
        gl2.glBindBuffer( GL.GL_ARRAY_BUFFER, vbuffer[0] );

        // bind your element array buffer
        gl2.glBindBuffer( GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer[0] );

        // set up your attribute variables
        gl2.glUseProgram( program );
        int  vPosition = gl2.glGetAttribLocation( program, "vPosition" );
        gl2.glEnableVertexAttribArray( vPosition );
        gl2.glVertexAttribPointer( vPosition, 4, GL.GL_FLOAT, false,
                                       0, 0l );

        // set up viewing and projection parameters

        if( viewMode == 1 )
        vp.setUpFrustum( program, gl2 );
        else if( viewMode == 2 )
        vp.setUpOrtho( program, gl2 );

        // set up camera
        //
        // changing the camera sets eyepoint to (0,1.3,-0.5), lookat
        // to (0,-0.4,-1.0), and up to (0,1,0)

        if( cameraAdjust )
            vp.setUpCamera( program, gl2, 0.0f, 1.3f, -0.5f,
              0.0f, -0.4f, -1.0f, 0.0f, 1.0f, 0.0f );
            //vp.setUpCamera(program,gl2, 0f,0f,0f,0f,0f,-1f,0f,1f,0f );
        else
            vp.clearCamera( program, gl2 );

        // set up transformations
        //
        // transformations are applied in this order (if you are having trouble
        // recreating the solution image, check your order of matrix
        // multiplication):
        //
        //    scale Y by 2
        //    rotate around Z by 335 degrees
        //    rotate around Y by 10 degrees
        //    translate in X by -0.2
        //    translate in Y by 0.2

        if( transformsOn )
            vp.setUpTransforms( program, gl2, 1.0f, 2.0f, 1.0f,
                    0.0f, 10.0f, 335.0f,
                    -0.2f, 0.2f, 0.0f );
        else
            vp.clearTransforms( program, gl2 );

        // draw your shapes
        gl2.glDrawElements( GL.GL_TRIANGLES, numVerts[0],
            GL.GL_UNSIGNED_SHORT, 0l );

    }


    ///
    // Notifies the listener to perform the release of all OpenGL
    // resources per GLContext, such as memory buffers and GLSL
    // programs.
    ///
    public void dispose( GLAutoDrawable drawable )
    {

    }

    ///
    // Called by the drawable immediately after the OpenGL context is
    // initialized.
    ///
    public void init( GLAutoDrawable drawable )
    {
        // get the gl object
        GL2 gl2 = drawable.getGL().getGL2();

        // Load shaders
        shaderSetup myShaders = new shaderSetup();
        program = myShaders.readAndCompile( gl2, "shader.vert",
                "shader.frag");
        if( program == 0 ) {
            System.err.println(
                    myShaders.errorString(myShaders.shaderErrorCode)
            );
        }

        // Other GL initialization
        gl2.glEnable( GL.GL_DEPTH_TEST );
        gl2.glEnable( GL.GL_CULL_FACE );
        gl2.glCullFace( GL.GL_BACK );
        gl2.glPolygonMode( GL.GL_FRONT_AND_BACK, GL2GL3.GL_LINE );
        gl2.glFrontFace( GL.GL_CCW );
        gl2.glClearColor( 1.0f, 1.0f, 1.0f, 1.0f );
        gl2.glDepthFunc( GL.GL_LEQUAL );
        gl2.glClearDepth( 1.0f );

        // initially create a new Shape
        createShapes( gl2 );

    }


    ///
    // Called by the drawable during the first repaint after the component
    // has been resized.
    ///
    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
                     int height)
    {

    }


    ///
    // creates a new shape
    ///
    public void createShapes(GL2 gl2)
    {
        // clear the old shape
        myShape = new teapot();

        // clear the old shape
        myShape.clear();

        // make a shape
        myShape.makeTeapot();

        // get your vertices and elements
        Buffer points = myShape.getVertices();
        Buffer elements = myShape.getElements();

        // set up the vertex buffer
        int bf[] = new int[1];
        gl2.glGenBuffers( 1, bf, 0 );
        vbuffer[0] = bf[0];
        long vertBsize = myShape.getNVerts() * 4l * 4l;
        gl2.glBindBuffer( GL.GL_ARRAY_BUFFER, vbuffer[0] );
        gl2.glBufferData( GL.GL_ARRAY_BUFFER, vertBsize,
            points, GL.GL_STATIC_DRAW );

        gl2.glGenBuffers( 1, bf, 0 );
        ebuffer[0] = bf[0];
        long eBuffSize = myShape.getNVerts() * 2l;
        gl2.glBindBuffer( GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer[0] );
        gl2.glBufferData( GL.GL_ELEMENT_ARRAY_BUFFER, eBuffSize,
            elements, GL.GL_STATIC_DRAW );

        numVerts[0] = myShape.getNVerts();
    }

    ///
    // Because I am a Key Listener...we'll only respond to key presses
    ///
    public void keyTyped(KeyEvent e){}
    public void keyReleased(KeyEvent e){}

    ///
    // Invoked when a key has been pressed.
    ///
    public void keyPressed(KeyEvent e)
    {
        // Get the key that was pressed
        char key = e.getKeyChar();

        // Respond appropriately
        switch( key ) {
            case '1':
                viewMode = 1;
                break;

            case '2':
                viewMode = 2;
                break;

            case 'q': case 'Q':
		frame.dispose();
                System.exit( 0 );
                break;
        }

        // do a redraw
        myCanvas.display();
    }


    ///
    // main program
    ///
    public static void main( String [] args )
    {
        // GL setup
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities( glp );
        GLCanvas canvas = new GLCanvas( caps );

        // create your transMain
        transMain myMain = new transMain( canvas );

        frame = new Frame( "CG - 3D Transformation and Viewing" );
        frame.setSize( 512, 512 );
        frame.add( canvas );
        frame.setVisible( true );

        // by default, an AWT Frame doesn't do anything when you click
        // the close button; this bit of code will terminate the program when
        // the window is asked to close
        frame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
		frame.dispose();
                System.exit(0);
            }
        } );
    }

    @Override
    public void mouseClicked( MouseEvent arg0 ) {
        // want to cycle through the screens on mouse click
        counter = (counter + 1) % 4;
        
        switch( counter ){
        case 0:
            // sets defaults
            cameraAdjust = false;
            transformsOn = false;
            break;
        case 1:
            // turn on transforms
            transformsOn = true;
            break;
        case 2:
            // turn camera adjust on, turn transforms off
            cameraAdjust = true;
            transformsOn = false;
            break;
	case 3:
	    // turn transforms back on
	    transformsOn = true;
	    break;
        }
        
        myCanvas.display();
    }

    @Override
    public void mouseEntered( MouseEvent arg0 ) {
        // not used
    }

    @Override
    public void mouseExited( MouseEvent arg0 ) {
        // not used
    }

    @Override
    public void mousePressed( MouseEvent arg0 ) {
        // not used
    }

    @Override
    public void mouseReleased( MouseEvent arg0 ) {
        // not used
    }

}
