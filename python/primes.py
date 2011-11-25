import math, number, polynomial

def primes(n):

  # 1
  if number.integralPower(n):
    return 0

  # 2
  r = 2

  # 3
  while r < n:

    # 4
    if number.gcd(n, r) != 1:
      return 0

    # 5
    if r in number.SMALL_PRIMES:

      # 6
      factors = number.factor(r - 1)
      if len(factors) == 0:
        q = 0 # hack
      else:
        q = factors[-1]

      # 7
      if (q >= 4 * math.sqrt(r) * number.lg(n)) and (number.modPow(n, (r - 1) / q, r) != 1):

        # 8
        break

    # 9
    r = r + 1

  # 10

  print "r for ", n, ":", r
  return r

  # 11
  #for a in range(1, int(2 * math.sqrt(r) * number.lg(n)) + 2)):

    # 12
    #poly = [-1] + [0] * (r - 1) + [1]
    #lhs = polynomial.modPow([-a, 1], n, poly)
    #rhs = polynomial.mod([-a] + [0] * (n - 1) + [1], poly)
    #if lhs != rhs:
    #  return 0

  # 13
  return 1

if __name__ == '__main__':

  #print primes(9901)
  print 2 * math.sqrt(10000) * number.lg(10000)

  print number.factor(17902)
  print 2 * math.sqrt(17903) * number.lg(99991)
  #print primes(99991)

  #for n in number.SMALL_PRIMES:
  #  if n != primes(n):
  #    break