;Author: Tyler Paulsen
(class Polygon Object
	(sides area perimeter)
	(method setSides: ( s ) 
		(set sides s)
		self
	)
	;getters	
	(method getArea () area)
	(method getPerimeter () perimeter)
	
	;set perimeter of polygon
	(method setPerimeter: (p)
		(set perimeter p)
		self
	)
	;set area of polygon
	(method setArea: (a)
		(set area a)
	)
	
	(class-method initPoly: (s)
		(setSides: (new self) s)
	)
)


(class Rectangle Polygon
	(length width)
	(method initRect:: (l w)
		(setRect:: self l w)
		(setSides: super 4)
	)
	(method setRect:: ( l w )
		(set length l)
		(set width w)
		self
	)

	(method getPerimeter () 
		 (setPerimeter: super  ( + (* length 2) (* width 2) )) 
		 (getPerimeter super)
	)

	(method getArea ()
		(setArea: super (* length width))
		(getArea super)
	)
)


(class Square Rectangle 
	(side)
	(method initSquare: (s)
		(setRect:: super s s)
		(setSides: super 4)
	)
)


(class Triangle Polygon
	(a b c height base)
	(method initTriangle:::: (s1 s2 s3 h)
		(setTriangle:::: self s1 s2 s3 h)
		(setSides: super 3)
	)
	(method setTriangle:::: ( s1 s2 s3 h )
		(set a s1)
		(set b s2)
		(set c s3)
		(set height h)
		(if (> s1 s2)
			[(if (> s1 s3)
				[(set base s1)]
				[(set base s3)]
			)]
			[(if (> s2 s3)
				[(set base s2)]
				[(set base s3)]
			)]
		)
		self
	)

	(method getPerimeter () 
		 (setPerimeter: super ( + (+ a b) c ))
		 (getPerimeter super)
	)

	(method getArea ()
		(setArea: super ( asInteger (/ (* height base) 2)))
		(getArea super)
	)
)

(class RightTriangle Triangle
	()
	(method initRightTriangle::: (s1 s2 s3)
		(setTriangle:::: super s1 s2 s3 0)
		(setSides: super 3)
	)

	(method getArea ()
		(setArea: super ( asInteger ( / (* a b) 2) ) )
	)
)

(class Circle Object
	(radius perimeter area)
	(method initCircle: (r)
		(set radius r)
		self
	)
	(method setPerimeter: (p)
		(set perimeter p)
	)
	(method getPerimeter ()
		(setPerimeter: self (* ( * 2 3) radius))
	)

	(method setArea: (a)
		(set area  a)
	)
	(method getArea ()
		(setArea: self (* 3 (* radius radius)	))
	)
)

;Rectangle Tests
;(val rect (new Rectangle))
;(initRect:: rect 2 4)
;(getPerimeter rect)
;(getArea rect)

;Square Tests
;(val square (new Square))
;(initSquare: square 2)
;(getPerimeter square)
;(getArea square)

;Triangle Tests
;(val tri (new Triangle))
;(initTriangle:::: tri 18 30 24 18)
;(getPerimeter tri)
;(getArea tri)

;Right Triangle Tests
;(val tri (new RightTriangle))
;(initRightTriangle::: tri 18 24 30)
;(getPerimeter tri)
;(getArea tri)

;Circle
;(val circle (new Circle))
;(initCircle: circle 3)
;(getArea circle)
;(getPerimeter circle)
