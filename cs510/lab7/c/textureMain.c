//
//  textureMain
//
//  Main program for texture assignment
//
//  This code can be compiled as either C or C++.
//

#ifdef __cplusplus
#include <cstdlib>
#include <iostream>
#else
#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#endif

#ifdef __APPLE__ 
#include <GLUT/GLUT.h>
#include <OpenGL/gl.h>
#include <SOIL.h>
#else
#include <GL/glew.h>
#include <GL/glut.h>
#include <GL/gl.h>
#include <SOIL.h>
#endif

#include "shaderSetup.h"
#include "simpleShape.h"
#include "cgShape.h"
#include "textureParams.h"

#ifdef __cplusplus
using namespace std;
#endif

#define BUFFER_OFFSET(i) ((char *)NULL + (i))

// vertex and element array IDs
// one for each object to be drawn
GLuint buffer[1];
GLuint ebuffer[1];
int NumElements;

// program IDs...for program and parameters
GLuint program;//, theta, trans, scale, vPosition;
int numVerts[1];

bool bufferInit = false;


void 
createShapes()
{
    //Clear shape
    clearShape();
        
    //make a shape
    makeDefaultShape();
    
    // get the points for your shape
    NumElements = nVertices() / 3;
    float *points = getVertices();
    int dataSize = nVertices() * 4 * sizeof (float);
    float *texCoords = getUV();
    int tdataSize = nVertices() * 2 * sizeof (float);
    GLushort *elements = getElements();
    int edataSize = nVertices() * sizeof (GLushort);

    //generate the buffer
    glGenBuffers( 1 , &buffer[0] );
    //bind the buffer
    glBindBuffer( GL_ARRAY_BUFFER , buffer[0] );
    //buffer data
    glBufferData( GL_ARRAY_BUFFER, dataSize + tdataSize , 0 , GL_STATIC_DRAW );
    glBufferSubData ( GL_ARRAY_BUFFER, 0, dataSize, points);
    glBufferSubData ( GL_ARRAY_BUFFER, dataSize, tdataSize, texCoords);

    //generate the buffer
    glGenBuffers( 1 , &ebuffer[0] );
    //bind the buffer
    glBindBuffer( GL_ELEMENT_ARRAY_BUFFER , ebuffer[0] );
    //buffer data
    glBufferData( GL_ELEMENT_ARRAY_BUFFER, edataSize,
        elements, GL_STATIC_DRAW );

    //store the num verts
    numVerts[0] = nVertices();
}


// OpenGL initialization 
void init() {
    
    // Load shaders and use the resulting shader program
    program = shaderSetup( "texture.vert", "texture.frag" );
    if (!program) {
#ifdef __cplusplus
        cerr << "Error setting up shaders - "
	     << errorString(shaderErrorCode) << endl;
#else
        fprintf( stderr, "Error setting up shaders - %s\n",
	    errorString(shaderErrorCode) );
#endif
        exit(1);
    }
    
    // Some openGL initialization...probably best to keep
    glEnable( GL_DEPTH_TEST ); 
    glEnable(GL_CULL_FACE);
    glCullFace(GL_BACK);
    glClearColor( 1.0, 1.0, 1.0, 1.0 );
    
    // create the geometry for your shapes.
    createShapes();
    
    // load your textures
    loadTexture ("texture.jpg");
       
}


#ifdef __cplusplus
extern "C" {
#endif

void
display( void )
{
    // clear and draw params..
    glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );
    
    
    //bind buffers
    glBindBuffer( GL_ARRAY_BUFFER , buffer[0] );
    glBindBuffer( GL_ELEMENT_ARRAY_BUFFER , ebuffer[0] );
        
    //use program
    glUseProgram( program );
        
    //set up the vertex arrays
    int dataSize = numVerts[0] * 4 * sizeof (float);
    GLuint vPosition = glGetAttribLocation( program , "vPosition" );
    glEnableVertexAttribArray( vPosition );
    glVertexAttribPointer( vPosition , 4 , GL_FLOAT , GL_FALSE, 0 ,
                           BUFFER_OFFSET(0) );
    
    GLuint vTexCoords = glGetAttribLocation( program , "vTexCoord" );
    glEnableVertexAttribArray( vTexCoords );
    glVertexAttribPointer( vTexCoords , 2 , GL_FLOAT , GL_FALSE, 0 ,
                          BUFFER_OFFSET(dataSize) );

        
    //setup uniform variables to shader
    setUpTexture (program);
     
            
    // draw your shape
    glDrawElements(GL_TRIANGLES ,numVerts[0],GL_UNSIGNED_SHORT, (void *)0 );
    
    // swap the buffers
    glutSwapBuffers();
}


void
keyboard( unsigned char key, int x, int y )
{
    switch( key ) {
        case 033:  // Escape key
        case 'q': case 'Q':
            exit( 0 );
        break;
    } 
    
    glutPostRedisplay();
}

#ifdef __cplusplus
}
#endif

int main (int argc, char **argv)
{
    glutInit( &argc, argv );
    glutInitDisplayMode( GLUT_RGBA | GLUT_DOUBLE | GLUT_DEPTH );
    glutInitWindowSize( 512, 512 );
    glutCreateWindow( "CG - Texture Assignment" );
    
#ifndef __APPLE__
    glewInit();
#endif
    
    init();
    
    glutDisplayFunc( display );
    glutKeyboardFunc( keyboard );
    
    glutMainLoop();
    return 0;
}
