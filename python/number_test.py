import number, unittest

class NumberTestCase(unittest.TestCase):

  def testGcd(self):
    self.assertRaises(number.NegativeNumberNotAllowedException, number.gcd, 1, -1);
    self.assertRaises(number.NegativeNumberNotAllowedException, number.gcd, -1, 1);
    self.assertEquals(0, number.gcd(0, 1))
    self.assertEquals(0, number.gcd(0, 1))
    self.assertEquals(3, number.gcd(27, 15))
    self.assertEquals(3, number.gcd(15, 27))
    self.assertEquals(1, number.gcd(27, 16))
    self.assertEquals(1, number.gcd(16, 27))

  def testPower(self):
    self.assertRaises(number.NegativeNumberNotAllowedException, number.power, 1, -1);
    self.assertRaises(number.NegativeNumberNotAllowedException, number.power, -1, 1);
    self.assertEquals(1, number.power(2, 0))
    self.assertEquals(0, number.power(0, 2))
    self.assertEquals(1267650600228229401496703205376, number.power(2, 100))

  def testModPow(self):
    self.assertRaises(number.NegativeNumberNotAllowedException, number.modPow, 1, 1, -1);
    self.assertRaises(number.NegativeNumberNotAllowedException, number.modPow, 1, -1, 1);
    self.assertRaises(number.NegativeNumberNotAllowedException, number.modPow, -1, 1, 1);
    self.assertEquals(0, number.modPow(2, 2, 0))
    self.assertEquals(1, number.modPow(2, 0, 2))
    self.assertEquals(0, number.modPow(0, 2, 2))
    self.assertEquals(1, number.modPow(2, 1092, 1093 ** 2))

  def testIntegralPower(self):
    self.assertEquals(0, number.integralPower(1))
    self.assertEquals(0, number.integralPower(2))
    self.assertEquals(0, number.integralPower(3))
    self.assertEquals(1, number.integralPower(4))
    self.assertEquals(0, number.integralPower(24), "5^2 - 1")
    self.assertEquals(1, number.integralPower(25), "5^2")
    self.assertEquals(0, number.integralPower(26), "5^2 + 1")
    self.assertEquals(0, number.integralPower(31), "2^5 - 1")
    self.assertEquals(1, number.integralPower(32), "2^5")
    self.assertEquals(0, number.integralPower(33), "2^5 + 1")
    self.assertEquals(0, number.integralPower(16806), "7^5 - 1")
    self.assertEquals(1, number.integralPower(16807), "7^5")
    self.assertEquals(0, number.integralPower(16808), "7^5 + 1")

  def testFactor(self):
    self.assertEquals([], number.factor(1))
    self.assertEquals([2], number.factor(2))
    self.assertEquals([2, 2, 3], number.factor(12))
    self.assertEquals([13], number.factor(13))

if __name__ == '__main__':
    unittest.main()

