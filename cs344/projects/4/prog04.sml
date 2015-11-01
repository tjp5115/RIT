(* By: Tyler Paulsen *)

(*Helper functions*)
fun orCond(n:bool,m:bool):bool =
	if n then
		true
	else
		if m then
			true
		else
			false;

fun andCond(n:bool,m:bool):bool =
	if n then
		if m then
			true
		else
			false
	else
		false;

fun modu (m:int,n:int):int = 
	m - n*(m div n);


(*Exercise 3*)
fun sigma (m:int, n:int):int =
	if m < n then
			m + sigma ( m+1, n)
	else
			n;

(*Exercise 4a*)
fun exp (m:int, n:int):int = 
	if n = 0 then
		1
	else
			m * exp(m,n-1);


(*Exercise 4a*)
fun logHelper (m:int, n:int, i:int):int = 
	if exp(m,i) > n then
		i-1
	else
		logHelper(m,n,i+1);

fun log (m:int, n:int):int = 
	logHelper(m,n,0);
(*Exercise 5*)
fun choose(n:int,k:int):int =
	if k = 0 then
		1
	else
		if k = n then
			1
		else
			choose(n-1,k-1) + choose(n-1,k);

(*Exercise 6*)
fun fibHelper(m:int,i:int,k:int,j:int):int =
	if m = i then
		j
	else
		fibHelper(m,i+1,j,k+j);

fun fib(m:int):int = 
	if m = 0 then
		0
	else
		if m = 1 then
			1
		else
			fibHelper(m,2,1,1);
(*Exercise 7a*)
fun primeHelper(n:int,i:int):int =
	if n div 2 = i then
		1
	else
		if modu(n, i) = 0 then
			0
		else
				primeHelper(n,i+1);

fun prime(n:int):int = 
	if n = 2 then
		1
	else
		if n = 3 then
			1
		else
			if modu(n, 2) = 0 then
				0
			else
				primeHelper(n, 2);

(*Exercise 7b*)
fun nthprimeHelper(n:int,i:int,k:int):int = 
	if prime(k) = 1 then
		if i = n then
			k
		else
			nthprimeHelper(n,i+1,k+1)
	else
		nthprimeHelper(n,i,k+1);

fun nthprime(n:int):int = 
	nthprimeHelper(n,1,2);

(*Exercise 7c*)
fun sumprimesHelper(n:int,i:int):int =
	if n = i then
		nthprime(i)
	else nthprime(i) + sumprimesHelper(n,i+1);

fun sumprimes(n:int):int =
	sumprimesHelper(n,1);

(*Exercise 7d*)
fun relprimeHelper(n:int,m:int,i:int):int =
	if i = n div 2 then
		1
	else
		if i = m div 2 then
			1
		else
			if modu(m,i) = 0 then
				if modu(n,i) = 0 then
					0
				else
					relprimeHelper(n,m,i+1)
			else
				relprimeHelper(n,m,i+1);

fun relprime(n:int,m:int):int = 
	if orCond((modu(m,n) = 0), (modu(n,m) = 0)) then
		0
	else
		if andCond(modu(n,2)=0,modu(m,2)=0) then
			0
		else
			if andCond(prime(n) = 1, prime(m) = 1) then
				1
			else
				relprimeHelper(n,m,2);
(*Question 8 
	binary
*)
fun binaryHelper(n:int,i:int):int =
	if n < 2 then
		exp(10,i-1) * modu(n,2)
	else
			binaryHelper(n div 2,i+1) + (exp(10,i-1) * modu(n,2));


fun binary(n:int):int =
	if n = 0 then
		0
	else
		if n < 0 then
			0-binaryHelper(0-n,1)
		else
			binaryHelper(n,1);

(*
nthprime
sumprimes
relprime?
binary
*)
(*
sigma(2,5);
exp(2,3);
exp(3,3);
log(2,1);
log(3,6560);
choose(0,0) = 1;
choose(2,0) = 1;
choose(2,2) = 1;
choose(2,1) = 2;
choose(20,10) = 184756
fib(0) = 0;
fib(1) = 1;
fib(4) = 3;
fib(5) = 5;
fib(20) = 6765 ;
fib(25) = 75025;
prime(2) = 1;
prime(3) = 1;
prime(5) = 1;
prime(1013) = 1;
prime(10) = 0;
prime(1012) = 0;
nthprime(1) = 2;
nthprime(2) = 3;
nthprime(8) = 19;
nthprime(75) = 379;
sumprimes(1) = 2;
sumprimes(2) = 5;
sumprimes(3) = 10;
sumprimes(10) = 129;
sumprimes(40) = 3087;
binary(0) = 0;
binary(11);
binary(~11);
relprime(2,2) = 0;
relprime(617,289) = 1;
relprime(18,712) = 0;
relprime(2,3) = 1;
relprime(2,5) = 1;
*)
