//
//  cgShape
//
//  Routines for tessellating a number of basic shapes.
//
//  Students are to supply their implementations for the functions in
//  this file using the function "addTriangle()" to do the tessellation.
//
//  This code can be compiled as either C or C++.
//
//  Contributor:  YOUR_NAME_HERE
//

#ifdef WIN32
#include <windows.h>
#endif

#ifdef __APPLE__
#include <OpenGL/gl.h>
#else
#include <GL/glew.h>
#include <GL/gl.h>
#endif

#include "simpleShape.h"
#include <math.h>
#include <iostream>
using namespace std;

/*
draws a square with two triangles in counter clockwise direction.
@param int x[] x values
@param int y[] y values
@param int z[] z values
*/
void drawSquare(float x[], float y[], float z[]){

	addTriangle(x[0], y[0], x[0],
		x[1], y[1], z[1],
		x[2], y[2], z[2]);
	addTriangle(x[0], y[0], x[0],
		x[2], y[2], z[2],
		x[3], y[3], z[3]);
}
void updateVerts(float x[], float y[], float z[]){

}
///
// makeCube - Create a unit cube, centered at the origin, with a given number
// of subdivisions in each direction on each face.
//
// @param subdivision - number of equal subdivisons to be made in each
//        direction along each face
//
// Can only use calls to addTriangle()
///
void makeCube(int subdivisions)
{
	if (subdivisions < 1)
		subdivisions = 1;
	float length = 1.0 / subdivisions;
	float p[] = { -.5, -.5, 0 };
	float inc[] = { length, length, 0, length, 0, 0 };
	printf("%f", length);
	float p1[] = { -.5, .5, 0 };
	float p2[] = { .5, .5, 0 };
	float p3[] = { -.5, -.5, 0 };
	float p4[] = { .5, -.5, 0 };

	if (subdivisions == 1){
		addTriangle(
			p1[0], p1[1], p1[2],
			p4[0], p4[1], p4[2],
			p2[0], p2[1], p2[2]
			);
		addTriangle(
			p1[0], p1[1], p1[2],
			p3[0], p3[1], p3[2],
			p4[0], p4[1], p4[2]
			);
	}

	/*
	for (int r = 0; r < subdivisions; ++r){
		float tempx = p[0];
		for (int c = 0; c < subdivisions; ++c){
			addTriangle(p[0], p[1], p[2],
				p[0] + inc[0], p[1], p[2],
				p[0] + inc[0], p[1] + inc[1], p[2]); 

			addTriangle(p[0], p[1], p[2],
				p[0] + inc[0], p[1] + inc[1], p[2] ,
				p[0], p[1] + inc[1], p[2]);
			p[0] += length;
		}
		p[0] = tempx;
		p[1] += length;
	}
	*/

	// YOUR IMPLEMENTATION HERE
	//drawSquare(x, y, z);
}


///
// makeCylinder - Create polygons for a cylinder with unit height, centered at
// the origin, with separate number of radial subdivisions and height
// subdivisions.
//
// @param radius - Radius of the base of the cylinder
// @param radialDivision - number of subdivisions on the radial base
// @param heightDivisions - number of subdivisions along the height
//
// Can only use calls to addTriangle()
///
void makeCylinder (float radius, int radialDivisions, int heightDivisions)
{
    if( radialDivisions < 3 )
        radialDivisions = 3;

    if( heightDivisions < 1 )
        heightDivisions = 1;

    // YOUR IMPLEMENTATION HERE

}


///
// makeCone - Create polygons for a cone with unit height, centered at the
// origin, with separate number of radial subdivisions and height
// subdivisions
//
// @param radius - Radius of the base of the cone
// @param radialDivision - number of subdivisions on the radial base
// @param heightDivisions - number of subdivisions along the height
//
// Can only use calls to addTriangle()
///
void makeCone (float radius, int radialDivisions, int heightDivisions)
{
    if( radialDivisions < 3 )
        radialDivisions = 3;

    if( heightDivisions < 1 )
        heightDivisions = 1;

    // YOUR IMPLEMENTATION HERE

}


///
// makeSphere - Create sphere of a given radius, centered at the origin,
// using spherical coordinates with separate number of thetha and
// phi subdivisions.
//
// @param radius - Radius of the sphere
// @param slides - number of subdivisions in the theta direction
// @param stacks - Number of subdivisions in the phi direction.
//
// Can only use calls to addTriangle
///
void makeSphere (float radius, int slices, int stacks)
{
    // IF USING RECURSIVE SUBDIVISION, MODIFY THIS TO USE
    // A MINIMUM OF 1 AND A MAXIMUM OF 5 FOR 'slices'

    if( slices < 3 )
        slices = 3;

    if( stacks < 3 )
        stacks = 3;

    // YOUR IMPLEMENTATION HERE
}
