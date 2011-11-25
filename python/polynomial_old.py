# A polynomial is represented by a list. E.g. [1, 2, 3] <-> 1 + 2x + 3x^2

# I am destructive!
def normalise(f):
  while len(f) > 1 and f[-1] == 0:
    del f[-1]
  return f

# I am destructive!
def degree(f):
  return len(normalise(f)) - 1

def negative(f):
  return map(operator.neg, f)

def add(f, g):
  len1 = len(f)
  len2 = len(g)
  length = max(len1, len2)
  result = [0] * length
  for x in range(length):
    if x >= len1:
      result[x] = g[x]
    elif x >= len2:
      result[x] = f[x]
    else:
      result[x] = f[x] + g[x]
  return normalise(result)

def scalarMultiply(k, f):
  result = [0] * len(f)
  for i in range(len(f)):
    result[i] = k * f[i]
  return result

def multiply(f, g):
  result = scalarMultiply(g[0], f)
  for i in range(1, len(g)):
    result = add(result, [0] * i + scalarMultiply(g[i], f))
  return result

def mod(g, f):
  while degree(g) >= degree(f):
    g1 = [0] * (degree(g) - degree(f)) + f
    g1 = scalarMultiply(g[-1] / f[-1], g1)
    g = add(g1, negative(g))
  return normalise(negative(g))

def modPow(g, n, f):
  k = 0
  exp = n
  bin = []
  while exp != 0:
    bin[:0] = [exp % 2]
    exp = exp / 2
  r = g
  for i in range(1, len(bin)):
    r = mod(multiply(r, r), f)
    if bin[i] == 1:
      r = mod(multiply(g, r), f)
  return r

def modCoef(f, n):
  result = [0] * len(f)
  for i in range(len(f)):
    result[i] = f[i] % n
  return normalise(result)

def toString(f):
  s = `f[0]`
  for i in range(1, len(f)):
    if f[i] != 0:
      if abs(f[i]) == -f[i]:
        s = s + " - "
      else:
        s = s + " + "
      if abs(f[i]) != 1:
        s = s + `abs(f[i])`
      s = s + "x^" + `i`
  return s

