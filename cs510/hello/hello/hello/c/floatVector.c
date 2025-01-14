/**
 * floatVector.c
 *
 * A replacement for the C++ STL vector<float> class.
 *
 * Author:  Warren R. Carithers
 * Date:    2013/10/16 10:43:50
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#include "floatVector.h"

/*
** Default amount to "grow" the vector by when it fills up
**
** Smaller values => more frequent growth, but higher utilization
** Larger values =>  less frequent growth, but potentially more wasted space
*/

#define	DEFAULT_GROWTH	256

/*
** floatVectorGrowthFactor -- set the growth factor for a floatVector_t
**
** returns the old growth factor
*/

size_t floatVectorGrowthFactor( floatVector_t *vec, size_t size ) {
	size_t old = vec->growth;
	vec->growth = size;
	return( old );
}

/*
** floatVectorClear -- return a floatVector_t to its original state
*/

void floatVectorClear( floatVector_t *vec ) {

	// verify that we were given a non-NULL pointer
	if( vec == 0 ) {
		return;
	}

	// release any existing allocated space
	if( vec->vec != 0 ) {
		free( vec->vec );
		vec->vec = 0;
	}

	// record the fact that the vector is now empty
	vec->length = vec->size = 0;

	// ensure that there is a growth factor for this vector
	if( vec->growth == 0 ) {
		vec->growth = DEFAULT_GROWTH;
	}

}

/*
** floatVectorPushBack -- add a float to the end of the vector,
** automatically extending the vector if need be
*/

void floatVectorPushBack( floatVector_t *vec, float coord ) {
	float *tmp;

	// verify that we were given a non-NULL pointer
	if( vec == 0 ) {
		return;
	}

	// allocate initial vector if we need to
	if( vec->length == 0 ) {
		if( vec->growth == 0 ) {
			vec->growth = DEFAULT_GROWTH;
		}
		tmp = (float *) malloc( vec->growth * sizeof(float) );
		if( tmp == 0 ) {
			perror( "vector allocation failed" );
			exit( 1 );
		}
		vec->vec = tmp;
		vec->length = vec->growth;
	}

	// extend the vector if we need to
	if( vec->size >= vec->length ) {
		if( vec->growth == 0 ) {
			vec->growth = DEFAULT_GROWTH;
		}
		tmp = (float *) realloc( vec->vec,
			         (vec->length + vec->growth) * sizeof(float) );
		if( tmp == 0 ) {
			perror( "vector reallocation failed" );
			exit( 2 );
		}
		vec->vec = tmp;
		vec->length += vec->growth;
	}

	// add the new coordinate to the vector
	vec->vec[ vec->size ] = coord;
	vec->size += 1;

}
