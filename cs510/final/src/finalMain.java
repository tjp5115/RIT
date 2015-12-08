//
//  finalMain.java
//
//  Main program for transformation assignment.
//
//  Students should not be modifying this file.
//

import com.jogamp.opengl.util.Animator;

import java.awt.*;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;

public class finalMain implements GLEventListener, KeyListener
{


    ///
    // program and variable IDs
    ///
    public int program;

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
    // animation control
    ///
    private boolean animating = false;
    private int level = 0;
    Animator anime;

    ///
    // rotation angles
    ///
    public int theta;
    public float angles[];
    private float angleInc = 5.0f;

    private boolean updateNeeded = false;
    ///
    // my canvas
    ///
    GLCanvas myCanvas;
    private static Frame frame;

    BlenderObj obj;
    ///
    // constructor
    ///
    public finalMain(GLCanvas G)
    {

        angles = new float[3];
        angles[0] = 30.0f;
        angles[1] = 30.0f;
        angles[2] = 0.0f;

        vp = new viewParams();
        viewMode = 1;

        counter = 0;
        cameraAdjust = false;
        transformsOn = false;

        myCanvas = G;

        G.addGLEventListener(this);
        G.addKeyListener(this);

        obj = new BlenderObj("modified_cylinder.obj");
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

        if( animating ) {
            animate();
        }

        // pass in our rotations as a uniform variable
        theta = gl2.glGetUniformLocation (program, "theta");
        gl2.glUniform3fv (theta, 1, angles, 0);

        // clear your frame buffers
        gl2.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );
        obj.drawModel(gl2,program);

    }
    ///
    // Rotates the shapes along x,y,z.
    //
    // Causes gimbal lock which also happened on Apollo 11
    // http://en.wikipedia.org/wiki/Gimbal_lock#Gimbal_lock_on_Apollo_11
    // Solution? Use Quaternions (Taught in Comp. Animation: Algorithms)
    //
    // TIDBIT:
    // Quaternion plaque on Brougham (Broom) Bridge, Dublin, which says:
    //
    //  "Here as he walked by
    //  on the 16th of October 1843
    //
    //  Sir William Rowan Hamilton
    //
    //  in a flash of genius discovered
    //  the fundamental formula for
    //  quaternion multiplication
    //  i^2 = j^2 = k^2 = ijk = -1
    //  & cut it on a stone of this bridge"
    ///
    public void animate() {

        if( level >= 450 ) {
            level = 0;
            animating = false;
        }

        if( !animating ) {
            anime.stop();
            return;
        }

        if( level < 150 ) {
            angles[0] += angleInc / 3;
        } else if( level < 300 ) {
            angles[1] += angleInc / 3;
        } else {
            angles[2] += angleInc / 3;
        }
        // myCanvas.display();
        updateNeeded = true;
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
        // create the Animator now that we have the drawable
        anime = new Animator( drawable );
        // Load shaders
        shaderSetup myShaders = new shaderSetup();
        program = myShaders.readAndCompile( gl2, "shader.vert", "shader.frag");
        if( program == 0 ) {
            System.err.println(myShaders.errorString(myShaders.shaderErrorCode));
        }

        // Other GL initialization
        gl2.glEnable( GL.GL_DEPTH_TEST );
        gl2.glEnable(GL.GL_CULL_FACE);
        gl2.glCullFace(GL.GL_BACK);
        //gl2.glPolygonMode(GL.GL_FRONT_AND_BACK,GL.GL_FILL);
        gl2.glFrontFace(GL.GL_CCW);
        gl2.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl2.glDepthFunc(GL.GL_LESS);
        gl2.glClearDepth(1.0f);


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

            // automated animation
            case 'a': animating = !animating; anime.start(); break;
            case 's': animating = false; break;

            // incremental rotation along the axes
            case 'x': angles[0] -= angleInc; break;
            case 'y': angles[1] -= angleInc; break;
            case 'z': angles[2] -= angleInc; break;
            case 'X': angles[0] += angleInc; break;
            case 'Y': angles[1] += angleInc; break;
            case 'Z': angles[2] += angleInc; break;
            // termination
            case 033:  // Escape key
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

        // create your finalMain
        finalMain myMain = new finalMain( canvas );

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


}
