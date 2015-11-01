//
//  simpleShape.c
//
//  Routines for adding triangles to create a new mesh.
//
//  Students are not to modify this file.
//

#ifdef __APPLE__
#include <OpenGL/gl.h>
#else
#include <GL/glew.h>
#include <GL/gl.h>
#endif

#include <stdio.h>
#include <stdlib.h>

#include "simpleShape.h"
#include "floatVector.h"

///
// common variables...should probably make this a class and make these
// data members
///

floatVector_t points;
float *pointArray = 0;
GLushort *elemArray = 0;


///
// clear the current shape
///
void clearShape( void )
{
    if( pointArray ) {
        free( pointArray );
        pointArray = 0;
    }
    if( elemArray ) {
        free( elemArray );
        elemArray = 0;
    }
    floatVectorClear( &points );
}


///
// adds a triangle to the current shape
///
void addTriangle( float x0, float y0, float z0,
                  float x1, float y1, float z1,
                  float x2, float y2, float z2 )
{
    floatVectorPushBack( &points, x0 );
    floatVectorPushBack( &points, y0 );
    floatVectorPushBack( &points, z0 );
    floatVectorPushBack( &points, 1.0 );

    floatVectorPushBack( &points, x1 );
    floatVectorPushBack( &points, y1 );
    floatVectorPushBack( &points, z1 );
    floatVectorPushBack( &points, 1.0 );

    floatVectorPushBack( &points, x2 );
    floatVectorPushBack( &points, y2 );
    floatVectorPushBack( &points, z2 );
    floatVectorPushBack( &points, 1.0 );
}


///
// gets the array of vertices for the current shape
///
float *getVertices( void )
{
    int i;

    // delete the old point array if we have one
    if( pointArray ) {
        free( pointArray );
    }

    // create and fill a new point array
    pointArray = (float *) malloc(
        floatVectorSize(&points) * sizeof(float) );
    if( pointArray == 0 ) {
    	perror( "point allocation failure" );
	exit( 1 );
    }
    for( i=0; i < points.size; i++ ) {
        pointArray[i] = points.vec[i];
    }

    return pointArray;
}


///
// gets the array of elements for the current shape
///
GLushort *getElements( void )
{
    int i;

    // delete the old point array if we have one
    if( elemArray ) {
        free( elemArray );
    }

    // create and fill a new point array
    elemArray = (GLushort *) malloc(
        floatVectorSize(&points) * sizeof(GLushort) );
    if( elemArray == 0 ) {
    	perror( "element allocation failure" );
	exit( 1 );
    }
    for( i=0; i < floatVectorSize(&points); i++ ) {
        elemArray[i] = i;
    }

    return elemArray;
}


///
// returns number of vertices in current shape
///
int nVertices( void )
{
    return floatVectorSize(&points) / 4;
}
