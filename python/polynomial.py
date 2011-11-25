import operator, types

# Polynomials

class Polynomial:
  def __init__(self, f, mod):
    self.f = f
    self.mod = mod
    self.normalise()

  def normalise(self):
    f = self.f
    while len(f) > 1 and f[-1] == 0:
      del f[-1]

  def __add__(self, other):
    f = self.f
    g = other.f
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
        result[x] = (f[x] + g[x]) % self.mod
    return Polynomial(result, self.mod)

  def __neg__(self):
    return Polynomial(map(lambda x: -x % self.mod, self.f), self.mod)

  def __sub__(self, other):
    f = self.f
    g = other.f
    len1 = len(f)
    len2 = len(g)
    length = max(len1, len2)
    result = [0] * length
    for x in range(length):
      if x >= len1:
        result[x] = (-g[x]) % self.mod
      elif x >= len2:
        result[x] = f[x]
      else:
        result[x] = (f[x] - g[x]) % self.mod
    return Polynomial(result, self.mod)

  def __eq__(self, other):
    return other != None and self.f == other.f and self.mod == other.mod

  def __ne__(self, other):
    return not self.__eq__(other)

  def _scalarMultiply(self, k):
    f = self.f
    result = [0] * len(f)
    for i in range(len(f)):
      result[i] = (k * f[i]) % self.mod
    return result

  def __mul__(self, other):
    f = self.f
    g = other.f
    result = Polynomial(self._scalarMultiply(g[0]), self.mod)
    for i in range(1, len(g)):
      result = result + Polynomial([0] * i + self._scalarMultiply(g[i]), self.mod)
    return result

  def __mod__(self, other):
    f = self.f
    g = other.f
    degf = self.degree()
    degg = other.degree()
    while degf >= degg:
      poly = Polynomial([0] * (degf - degg) + g, self.mod)
      poly = Polynomial(poly._scalarMultiply(f[-1] / g[-1]), self.mod)
      f = (poly - Polynomial(f, self.mod)).f # horrid!
      degf = len(f) - 1
    return -Polynomial(f, self.mod)

  def __pow__(self, n, modulo=None):
    k = 0
    exp = n
    bin = []
    while exp != 0:
      bin[:0] = [exp % 2]
      exp = exp / 2
    r = self
    if modulo == None:
      for i in range(1, len(bin)):
        r = (r * r)
        if bin[i] == 1:
          r = (self * r)
    else:
      for i in range(1, len(bin)):
        r = (r * r) % modulo
        if bin[i] == 1:
          r = (self * r) % modulo
    return r

  def __len__(self):
    return len(self.f)

  def __getitem__(self, index):
    return self.f[index]

  def __repr__(self):
    f = self.f
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
    return s + " (mod " + `self.mod` + ")"

  def degree(self):
    return len(self.f) - 1

if __name__ == '__main__':
  print Polynomial([-1, 1], 13)
  print Polynomial([-1, 1, 0], 13) + Polynomial([-1, 1], 13)
  print Polynomial([-1, 1], 13) * Polynomial([-1, 1], 13)
  print Polynomial([-1] + [0] * 12 + [1], 13)
  print

  def f1(a, p):
    return pow(Polynomial([-a, 1], p), p)

  def f2(a, p, r):
    return pow(Polynomial([-a, 1], p), p, Polynomial([-1] + [0] * (r - 1) + [1], p))

  def f3(a, p, r):
    return Polynomial([-a] + [0] * (p - 1) + [1], p) % Polynomial([-1] + [0] * (r - 1) + [1], p)

  for a in range(1, 13):
    print a, ":", f1(a, 13)
  print

  for p in range(2, 20):
    print p, ":", f1(1, p)
  print

  for a in range(1, 12):
    for r in range(1, 12):
      if f2(a, 12, r) == f3(a, 12, r):
        print a, r, "=="


  print pow(Polynomial([-4, 1], 12), 12, Polynomial([-1, 1], 12))
  print Polynomial([-4] + [0] * 11 + [1], 12) % Polynomial([-1, 1], 12)

  print pow(Polynomial([-9, 1], 12), 12, Polynomial([-1, 0, 1], 12))
  print Polynomial([-9] + [0] * 11 + [1], 12) % Polynomial([-1, 0, 1], 12)
