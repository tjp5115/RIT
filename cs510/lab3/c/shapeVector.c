/*
** shapeVector.c
**
** A replacement for the C++ STL vector<sfShape> class.
**
** Author:  Warren R. Carithers
** Date:    2014/02/27 13:18:57
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#include "shapeVector.h"

/*
** Default amount to "grow" the vector by when it fills up
**
** Smaller values => more frequent growth, but higher utilization
** Larger values =>  less frequent growth, but potentially more wasted space
*/

#define    DEFAULT_GROWTH    256

/*
** shapeVectorGrowthFactor -- set the growth factor for a shapeVector_t
**
** returns the old growth factor
*/

size_t shapeVectorGrowthFactor( shapeVector_t *vec, size_t size ) {
    size_t old = vec->growth;
    vec->growth = size;
    return( old );
}

/*
** shapeVectorClear -- return a shapeVector_t to its original state
*/

void shapeVectorClear( shapeVector_t *vec ) {
    int i;

    // verify that we were given a non-NULL pointer
    if( vec == 0 ) {
        return;
    }

    // release any existing allocated space
    if( vec->vec != 0 ) {
        // release all sfConvexShape objects held in the vector
        for( i = 0; i < vec->size; ++i ) {
            sfConvexShape_destroy( (vec->vec)[i] );
        }
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
** shapeVectorPushBack -- add a shape to the end of the vector,
** automatically extending the vector if need be
*/

void shapeVectorPushBack( shapeVector_t *vec, sfConvexShape *shape ) {
    sfConvexShape **tmp;

    // verify that we were given a non-NULL pointer
    if( vec == 0 ) {
        return;
    }

    // allocate initial vector if we need to
    if( vec->length == 0 ) {
        if( vec->growth == 0 ) {
            vec->growth = DEFAULT_GROWTH;
        }
        vec->length = vec->growth;
        tmp = (sfConvexShape **) calloc( vec->growth, sizeof(sfConvexShape *) );
        if( tmp == 0 ) {
            perror( "vector allocation failed" );
            exit( 1 );
        }
        vec->vec = tmp;
    }

    // extend the vector if we need to
    if( vec->size >= vec->length ) {
        if( vec->growth == 0 ) {
            vec->growth = DEFAULT_GROWTH;
        }
        vec->length += vec->growth;
        tmp = (sfConvexShape **) realloc( vec->vec,
                     vec->length * sizeof(sfConvexShape *) );
        if( tmp == 0 ) {
            perror( "vector reallocation failed" );
            exit( 2 );
        }
        vec->vec = tmp;
    }

    // add the new shape to the vector
    vec->vec[ vec->size ] = shape;
    vec->size += 1;

}
