__author__ = 'Tyler Paulsen'
# Name: Tyler Paulsen 
# Time spent on assignment: ~4 hrs
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
    print(m,n,i)
    if exp(m, i) > n:
        return i-1
    else:
        log_helper(m, n, i+1)

'''
# Exercise 5
# DEFINE choose HERE
def  choose n k
	if = k 0 
		1
		if = k n 
			1
			+ choose - n 1 - k 1  choose - n 1 k  
		
	


# Exercise 6
# DEFINE fib HERE
def  fib m
	if = m 0
		0
		if = m 1
			1
			fibHelper m 2 1 1
		
	


def  fibHelper m i k j
	if = m i
		j
		 fibHelper m ++ i j + k j 
	


# Exercise 7a
# DEFINE prime? HERE
def  prime? n
	if = n 2
		1
		if = n 3
			1
			if = mod n 2 0 
				0
				prime?Helper n 2 
			
		
	


def  prime?Helper n i
	if = / n 2 i
		1
		if =  mod n i  0
			0
			prime?Helper n ++ i 
		
	


# Exercise 7b
# DEFINE nthprime HERE
def  nthprime n
	nthprimeHelper n 1 2

def  nthprimeHelper  n i k
	if prime? k 
		if  = i n
			k
			nthprimeHelper  n ++ i ++ k
		
		nthprimeHelper n i ++ k
	


# Exercise 7c
# DEFINE sumprimes HERE
def  sumprimes n
	sumprimesHelper n 1

def  sumprimesHelper n i
	if = n i
		nthprime i
		+ nthprime i sumprimesHelper n ++ i
	


# Exercise 7d
# DEFINE relprime? HERE
def  relprime? n m
	if or = mod n m 0 = mod m n 0 
		0
		if and = mod n 2 0 = mod m 2 0
			0
			if and = prime? n 1 = prime? m 1 
				1
				relprimeHelper n m 2
			
		
	

def  relprimeHelper n m i
	if = i / n 2
		1
		if = i / m 2
			1
			if = mod m i 0
				if = mod n i 0
					0
					relprimeHelper n m ++ i
				
				relprimeHelper n m ++ i
			
		
	

# Exercise 8
# DEFINE binary HERE
def  binary n
	if = n 0
		0
		if < n 0
			* -1 binaryHelper * n -1 1 
			binaryHelper n 1 
		
	

def  binaryHelper n i
	if < n 2 
		* exp 10 - i 1 mod n 2
		begin 
			print n
			+ binaryHelper / n 2 ++ i * exp 10 - i 1 mod n 2
'''
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
print (log(2,1))
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

