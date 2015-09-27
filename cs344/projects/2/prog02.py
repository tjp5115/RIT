__author__ = 'Tyler Paulsen'
# Name: Tyler Paulsen 
# Time spent on assignment: ~1 hr
# Collaborators: 

#helper functions

# permutation function
# i - number to computate permuation on.
def  perm (i):
    if i == 0:
        return 1
    elif i == 1:
        return 1
    else:
        return i * perm(--i)

# take an integer to a power. n^m
# n - the base of the power
# m - the exponent of the power
def  pow(n, m):
    if n == 0:
        return n
    else:
        return pow(n*n, --m)

# Exercise 3
# DEFINE sigma HERE
def  sigma (m, n):
    if m > n:
        return 0
    else:
        return m + sigma(m+1, n)


# Exercise 4a
# DEFINE exp HERE
def exp(m, n):
    if n == 0:
        return 1
    else:
        return m * exp(m, n-1)




# Exercise 4a
# DEFINE log HERE
def log(m, n):
    return log_helper(m, n, 0)


def log_helper(m, n, i):
    if exp(m, i) > n:
        return i-1
    else:
        return log_helper(m, n, i+1)

# Exercise 5
# DEFINE choose HERE
def choose (n, k):
	if k == 0:
		return 1
	elif k == n:
		return 1
	else:
		return choose(n-1, k-1) + choose(n-1, k)




# Exercise 6
# DEFINE fib HERE
def  fib (m):
	if m == 0:
		return 0
	elif m == 1:
		return 1
	else:
		return fibHelper(m, 2, 1, 1)

	


def  fibHelper (m, i, k,j):
	if m == i:
		return j
	else:
		return fibHelper(m, i+1, j, k + j)


# Exercise 7a
# DEFINE prime? HERE
def  prime (n):
	if n == 2:
		return 1
	elif n == 3:
		return 1
	elif n%2 == 0:
		return 0
	else:
		return primeHelper(n, 2)
			
def  primeHelper(n, i):
	if int(n/2) == i:
		return 1
	elif n%i == 0:
		return 0
	else:
		return primeHelper(n,i+1)

# Exercise 7b
# DEFINE nthprime HERE
def  nthprime(n):
	return nthprimeHelper(n,1, 2)

def  nthprimeHelper (n, i, k):
	if prime(k) == 1:
		if  i == n:
			return k
		else:
			return nthprimeHelper(n, i+1, k+1)
	else:
		return nthprimeHelper(n, i, k+1)
	

# Exercise 7c
# DEFINE sumprimes HERE
def  sumprimes(n):
	return sumprimesHelper(n, 1)

def  sumprimesHelper(n, i):
	if n == i:
		return nthprime(i)
	else:
		return nthprime(i) + sumprimesHelper(n, i+1)



# Exercise 7d
# DEFINE relprime? HERE
def  relprime(n, m):
	if n%m == 0 or m%n == 0:
		return 0
	elif n%2 == 0 and m%2 == 0:
		return 0
	elif prime(n) == 1 and prime(m) == 1:
		return 1
	else:
		return relprimeHelper(n, m, 2)
			

def  relprimeHelper(n, m, i):
	if int(n / 2) == i:
		return 1
	elif int(m / 2) == i:
		return 1
	elif m % i == 0:
		if n % i == 0:
			return 0
		else:
			return relprimeHelper(n, m, i+1)
	else:
		return relprimeHelper(n, m, i+1)

'''
def  binary (n):
	if n == 0:
		return 0
	elif n < 0:
		return -1 * binaryHelper(n*-1, 1)
	else:
		return binaryHelper(n, 1)

def  binaryHelper(n, i):
	print(n)
	if n < 2:
		return exp(10,i-1) * n%2
	else:
		return binaryHelper(int(n/2), i+1) + exp(10, i-1)*n%2
'''
# Exercise 8
# DEFINE binary HERE
def binary(n):
	if n < 0:
		#this feels like cheating

		return -1*int(bin(n*-1)[2:])
	else:
		return int(bin(n)[2:])

'''
#all of the check-expect tests from impcore converted to asserts.
assert sigma(   0,  0) == 0
assert sigma(   0 , 5) == 15
assert sigma(   0 ,10) == 55
assert sigma(   5 , 5) == 5
assert sigma(   5 ,10) == 45
assert sigma(   5 ,15) == 110
assert sigma(  -5 , 0) == -15
assert sigma( -10 , 0) == -55
assert sigma(  -5 ,-5) == -5
assert sigma( -10 ,-5) == -45
assert sigma( -15 ,-5) == -110

assert exp(0,  1) == 0
assert exp(0,  5) == 0
assert exp(0,10) == 0
assert exp(2,  0) == 1
assert exp(2,  5) == 32
assert exp(2, 10) == 1024
assert exp(2, 15) == 32768
assert exp(5,  0) == 1
assert exp(5,  5) == 3125
assert exp(5, 10) == 9765625
#assert exp(5, 15) == 452807053
assert log( 2,1) == 0
assert log( 2,    2) == 1
assert log( 2,   3) == 1
assert log( 2,     4) == 2
assert log( 2,     5) == 2
assert log( 2,     6) == 2
assert log( 2,     7) == 2
assert log( 2,     8) == 3
assert log( 2,     9) == 3
assert log( 2,    10) == 3
assert log( 2,   127) == 6
assert log( 2,   128) == 7
assert log( 2,   129) == 7
assert log( 2,  1023) == 9
assert log( 2,  1024) == 10
assert log( 2,  1025) == 10
assert log( 3,     1) == 0
assert log( 3,     2) == 0
assert log( 3,     3) == 1
assert log( 3,     4) == 1
assert log( 3,     5) == 1
assert log( 3,     6) == 1
assert log( 3,     7) == 1
assert log( 3,     8) == 1
assert log( 3,     9) == 2
assert log( 3,    10) == 2
assert log( 3,  6560) == 7
assert log( 3,  6561) == 8
assert log( 3,  6562) == 8
assert log( 3, 59048) == 9
assert log( 3, 59049) == 10
assert log( 3, 59050) == 10
assert log( 4, 59050) == 7
assert log( 5, 59050) == 6
assert log( 6, 59050) == 6
assert log( 7, 59050) == 5
assert log( 8, 59050) == 5
assert log( 9, 59050) == 5

assert choose(  0,  0) == 1
assert choose(  2 , 0) == 1
assert choose(  2 , 1) == 2
assert choose(  2 , 2) == 1
assert choose( 10 , 0) == 1
assert choose( 10 , 2) == 45
assert choose( 10 , 4) == 210
assert choose( 10 , 5) == 252
assert choose( 10 , 6) == 210
assert choose( 10 , 8) == 45
assert choose( 10 ,10) == 1
assert choose( 20 , 0) == 1
assert choose( 20 , 4) == 4845
assert choose( 20 , 9) == 167960
assert choose( 20 ,10) == 184756
assert choose( 20 ,11) == 167960
assert choose( 20 ,16) == 4845
assert choose( 20 ,20) == 1

assert fib(  0) == 0
assert fib(  1) == 1
assert fib(  2) == 1
assert fib(  3) == 2
assert fib(  4) == 3
assert fib(  5) == 5
assert fib( 10) == 55
assert fib( 15) == 610
assert fib( 20) == 6765
assert fib( 25) == 75025

assert prime(    2) == 1
assert prime(    3) == 1
assert prime(    4) == 0
assert prime(    5) == 1
assert prime(    6) == 0
assert prime(    7) == 1
assert prime(    8) == 0
assert prime(    9) == 0
assert prime(   10) == 0
assert prime(   11) == 1
assert prime(   12) == 0
assert prime(   13) == 1
assert prime(   14) == 0
assert prime( 1008) == 0
assert prime( 1009) == 1
assert prime( 1010) == 0
assert prime( 1011) == 0
assert prime( 1012) == 0
assert prime( 1013) == 1
assert prime( 1014) == 0

assert nthprime(  1) == 2
assert nthprime(  2) == 3
assert nthprime(  3) == 5
assert nthprime(  4) == 7
assert nthprime(  5) == 11
assert nthprime(  6) == 13
assert nthprime(  7) == 17
assert nthprime(  8) == 19
assert nthprime(  9) == 23
assert nthprime( 10) == 29
assert nthprime( 20) == 71
assert nthprime( 30) == 113
assert nthprime( 40) == 173
assert nthprime( 50) == 229
assert nthprime( 75) == 379

assert sumprimes(  1) == 2
assert sumprimes(  2) == 5
assert sumprimes(  3) == 10
assert sumprimes(  4) == 17
assert sumprimes(  5) == 28
assert sumprimes(  6) == 41
assert sumprimes(  7) == 58
assert sumprimes(  8) == 77
assert sumprimes(  9) == 100
assert sumprimes( 10) == 129
assert sumprimes( 20) == 639
assert sumprimes( 30) == 1593
assert sumprimes( 40) == 3087
assert sumprimes( 50) == 5117

assert relprime(     2,    2) == 0
assert relprime(     2 ,   3) == 1
assert relprime(     2  ,  4) == 0
assert relprime(     2  ,  5) == 1
assert relprime(     2  ,  6) == 0
assert relprime(     3  ,  2) == 1
assert relprime(     3  ,  3) == 0
assert relprime(     3  ,  4) == 1
assert relprime(     3  ,  5) == 1
assert relprime(     3  ,  6) == 0
assert relprime(     4  ,  2) == 0
assert relprime(     4  ,  3) == 1
assert relprime(     4  ,  4) == 0
assert relprime(     4  ,  5) == 1
assert relprime(     4  ,  6) == 0
assert relprime(     5  ,  2) == 1
assert relprime(     5  ,  3) == 1
assert relprime(     5  ,  4) == 1
assert relprime(     5  ,  5) == 0
assert relprime(     5  ,  6) == 1
assert relprime(     6  ,  2) == 0
assert relprime(     6  ,  3) == 0
assert relprime(     6  ,  4) == 0
assert relprime(     6  ,  5) == 1
assert relprime(     6  ,  6) == 0
assert relprime(    18 , 712) == 0
assert relprime(   514 , 793) == 1
assert relprime(     8 , 819) == 1
assert relprime(   813 , 286) == 1
assert relprime(   759 , 824) == 1
assert relprime(   863 , 133) == 1
assert relprime(   255 , 695) == 0
assert relprime(   254 , 726) == 0
assert relprime(   708 , 102) == 0
assert relprime(   781 ,  87) == 1
assert relprime(   510 , 248) == 0
assert relprime(   445 , 220) == 0
assert relprime(   572 , 977) == 1
assert relprime(   983 , 736) == 1
assert relprime(   376 , 875) == 1
assert relprime(   901 , 876) == 1
assert relprime(   879 , 675) == 0
assert relprime(   302 , 377) == 1
assert relprime(   852 , 569) == 1
assert relprime(   726 , 408) == 0
assert relprime(   617 , 289) == 1

assert binary(    0) == 0
assert binary(    1) == 1
assert binary(    2) == 10
assert binary(    3) == 11
assert binary(    4) == 100
assert binary(    5) == 101
assert binary(   11) == 1011
assert binary(   12) == 1100
assert binary(   13) == 1101
assert binary(   14) == 1110
assert binary(   15) == 1111
assert binary(   16) == 10000
assert binary(   -5) == -101
assert binary(  -12) == -1100

'''
