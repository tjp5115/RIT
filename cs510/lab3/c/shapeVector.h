/*
** shapeVector.h
**
** A replacement for the C++ STL vector<sfConvexShape> class.
**
** Author:  Warren R. Carithers
** Date:    2013/10/16 10:43:50
*/

#ifndef _SHAPEVECTOR_H_
#define _SHAPEVECTOR_H_

#include <sys/types.h>

#include <SFML/Graphics/ConvexShape.h>

/*
** define an alternative to the STL vector<sfShape> class
*/

typedef struct st_shapeVector {
	size_t size;		// number of occupied slots
	size_t length;		// total number of slots
	size_t growth;		// how many slots to add when growing
	sfConvexShape **vec;	// the vector itself
} shapeVector_t;


/*
** Manipulation functions
*/

/*
** shapeVectorGrowthFactor -- set the growth factor for a shapeVector
**
** Returns the old growth factor.
*/

size_t shapeVectorGrowthFactor( shapeVector_t *vec, size_t size );

/*
** shapeVectorClear -- return a shapeVector to its original state
*/

void shapeVectorClear( shapeVector_t *vec );

/*
** shapeVectorPushBack -- add a shape to the end of the vector,
** automatically extending the vector if need be
**
** If the vector must be extended and the memory reallocation
** fails, this method will print an error message and exit.
*/

void shapeVectorPushBack( shapeVector_t *vec, sfConvexShape *shape );

/*
** Pseudo-function: return the count of elements in a shapeVector_t
**
** Note: the argument is a pointer, for consistency with the
** other methods that operate on shapeVector_t variables.
*/

#define shapeVectorSize(svp)  ((svp)->size)

#endif
