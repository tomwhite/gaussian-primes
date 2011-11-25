import math

NegativeNumberNotAllowedException = "Negative number not allowed"

def gcd(x, y):
  if x < 0 or y < 0:
    raise NegativeNumberNotAllowedException
  if x == 0 or y == 0:
    return 0
  if x < y:
    x, y = y, x
  while y > 0:
    x, y = y, x % y
  return x

def power(x, y):
  if x < 0 or y < 0:
    raise NegativeNumberNotAllowedException
  if x == 0:
    return 0
  if y == 0:
    return 1

  exp = y
  bin = []
  while exp != 0:
    bin.append(exp % 2)
    exp = exp / 2

  z = x
  for i in range(len(bin) - 2, -1, -1):
    z = z * z
    if bin[i] == 1:
      z = z * x
  return z

def modPow(x, y, N):
  if x < 0 or y < 0 or N < 0:
    raise NegativeNumberNotAllowedException
  if x == 0 or N == 0:
    return 0
  if y == 0:
    return 1

  exp = y
  bin = []
  while exp != 0:
    bin.append(exp % 2)
    exp = exp / 2

  z = x
  for i in range(len(bin) - 2, -1, -1):
    z = (z * z) % N
    if bin[i] == 1:
      z = (z * x) % N
  return z

def lg(x):
  return math.log(x) / math.log(2)

def integralPower(n):
  "I return 1 if n is of the form a^b, where a and b are integers and b > 1."
  for i in range(2, int(lg(n)) + 1):
    ithRoot = int(pow(n, 1 / float(i)))
    if n == power(ithRoot, i):
      return 1
  return 0

def sieve(n):
  sieve = [1] * n
  sieve[0] = 0
  for i in range(math.sqrt(n)):
    if sieve[i] == 0:
      continue
    multiple = i + 1
    for j in range(2, n/multiple + 1):
      sieve[j * multiple - 1] = 0

  primes = []
  for i in range(len(sieve)):
    if sieve[i] == 1:
      primes.append(i + 1)
  return primes

SMALL_PRIMES = sieve(1000000)

def factor(n):
  for p in SMALL_PRIMES:
    if n < p:
      return []
    if n % p == 0:
      factors = [p] + factor(n / p)
      factors.sort()
      return factors

import time
def timing1(n, f, a):
  print f.__name__,
  r = range(n)
  t1 = time.clock()
  for i in r:
      f(a); f(a); f(a); f(a); f(a); f(a); f(a); f(a); f(a); f(a)
  t2 = time.clock()
  print round(t2 - t1, 3), "seconds"

def timing3(n, f, a, b, c):
  print f.__name__,
  r = range(n)
  t1 = time.clock()
  for i in r:
      f(a, b, c); f(a, b, c); f(a, b, c); f(a, b, c); f(a, b, c); f(a, b, c); f(a, b, c); f(a, b, c); f(a, b, c); f(a, b, c)
  t2 = time.clock()
  print round(t2 - t1, 3), "seconds"


if __name__ == '__main__':

  #timing3(1000, expMod, 2, 1092, 1093 ** 2)
  #timing3(1000, expMod2, 2, 1092, 1093 ** 2)

  #print SMALL_PRIMES[-1]

  timing1(10, factor, 99991)