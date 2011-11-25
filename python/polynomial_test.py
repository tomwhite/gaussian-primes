import unittest

from polynomial import Polynomial

class PolynomialTestCase(unittest.TestCase):

  def testNormalise(self):
    self.assertEquals(Polynomial([0], 13), Polynomial([0], 13))
    self.assertEquals(Polynomial([1, 2, 1], 13), Polynomial([1, 2, 1], 13))
    self.assertEquals(Polynomial([1, 2, 1], 13), Polynomial([1, 2, 1, 0], 13))

  def testDegree(self):
    self.assertEquals(2, Polynomial([1, 2, 3], 13).degree())

  def testNegative(self):
    f = Polynomial([1], 13)
    -f
    self.assertEquals(Polynomial([1], 13), f)

    self.assertEquals(Polynomial([12, 2, 10], 13), -Polynomial([1, -2, 3], 13))

  def testAdd(self):
    f, g = Polynomial([1], 13), Polynomial([1, 2], 13)
    f + g
    self.assertEquals(Polynomial([1], 13), f)
    self.assertEquals(Polynomial([1, 2], 13), g)

    self.assertEquals(Polynomial([3, 5, 1, 2], 13), Polynomial([1, 2, 1], 13) + Polynomial([2, 3, 0, 2], 13));
    self.assertEquals(Polynomial([3, 5, 1, 2], 13), Polynomial([2, 3, 0, 2], 13) + Polynomial([1, 2, 1], 13));
    self.assertEquals(Polynomial([3, 5, 1, 1], 13), Polynomial([1, 2, 1, -1], 13) + Polynomial([2, 3, 0, 2], 13));

  def testMultiply(self):
    f, g = Polynomial([1], 13), Polynomial([1, 2], 13)
    f * g
    self.assertEquals(Polynomial([1], 13), f)
    self.assertEquals(Polynomial([1, 2], 13), g)

    self.assertEquals(Polynomial([12, 1, 5, 3], 13), Polynomial([1, 2, 1], 13) * Polynomial([-1, 3], 13));
    self.assertEquals(Polynomial([12, 1, 5, 3], 13), Polynomial([-1, 3], 13) * Polynomial([1, 2, 1], 13));

  def testMod(self):
    f, g = Polynomial([-1, 0, 0, 1, 0, 0, 0, 2], 13), Polynomial([4, 0, 0, 1], 13)
    f % g
    self.assertEquals(Polynomial([-1, 0, 0, 1, 0, 0, 0, 2], 13), f)
    self.assertEquals(Polynomial([4, 0, 0, 1], 13), g)

    self.assertEquals(Polynomial([8, 6], 13), Polynomial([-1, 0, 0, 1, 0, 0, 0, 2], 13) % Polynomial([4, 0, 0, 1], 13));
    self.assertEquals(Polynomial([1], 13), Polynomial([5, 0, 0, 1], 13) % Polynomial([4, 0, 0, 1], 13));

  def testPow(self):
    self.assertEquals(Polynomial([10, 3, 9], 13), pow(Polynomial([-1, 1], 13), 10, Polynomial([1, 0, 0, 1], 13)));

  def testRepr(self):
    self.assertEquals("-1 + x^3 - x^4 + 2x^7 (mod 13)", `Polynomial([-1, 0, 0, 1, -1, 0, 0, 2], 13)`);

if __name__ == '__main__':
    unittest.main()

