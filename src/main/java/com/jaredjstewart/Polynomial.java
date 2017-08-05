/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.jaredjstewart;

import java.util.List;
/*************************************************************************
 *  See: http://introcs.cs.princeton.edu/java/92symbolic/Polynomial.java.html
 *  Compilation:  javac com.jaredstewart.Polynomial.java
 *  Execution:    java com.jaredstewart.Polynomial
 *
 *  Polynomials with integer coefficients.
 *
 *  zero(x) =     0
 *  p(x) =        4x^3 + 3x^2 + 2x + 1
 *  q(x) =        3x^2 + 5
 *  p(x) + q(x) = 4x^3 + 6x^2 + 2x + 6
 *  p(x) * q(x) = 12x^5 + 9x^4 + 26x^3 + 18x^2 + 10x + 5
 *  p(q(x))     = 108x^6 + 567x^4 + 996x^2 + 586
 *  0 - p(x)    = -4x^3 - 3x^2 - 2x - 1
 *  p(3)        = 142
 *  p'(x)       = 12x^2 + 6x + 2
 *  p''(x)      = 24x + 6
 *
 *************************************************************************/

public class Polynomial {
  private int[] coef;  // coefficients p(x) = sum { coef[i] * x^i }
  private int deg;     // degree of polynomial (0 for the zero polynomial)

  // a * x^b
  public Polynomial(int a, int b) {
    coef = new int[b+1];
    coef[b] = a;
    deg = degree();
  }

  // return the degree of this polynomial (0 for the zero polynomial)
  public int degree() {
    int d = 0;
    for (int i = 0; i < coef.length; i++)
      if (coef[i] != 0) d = i;
    return d;
  }

  // return c = a + b
  public Polynomial plus(Polynomial b) {
    Polynomial a = this;
    Polynomial c = new Polynomial(0, Math.max(a.deg, b.deg));
    for (int i = 0; i <= a.deg; i++) c.coef[i] += a.coef[i];
    for (int i = 0; i <= b.deg; i++) c.coef[i] += b.coef[i];
    c.deg = c.degree();
    return c;
  }

  // return (a - b)
  public Polynomial minus(Polynomial b) {
    Polynomial a = this;
    Polynomial c = new Polynomial(0, Math.max(a.deg, b.deg));
    for (int i = 0; i <= a.deg; i++) c.coef[i] += a.coef[i];
    for (int i = 0; i <= b.deg; i++) c.coef[i] -= b.coef[i];
    c.deg = c.degree();
    return c;
  }

  // return (a * b)
  // takes quadratic time (faster algorithms, e.g., via FFT are known)
  public Polynomial times(Polynomial b) {
    Polynomial a = this;
    Polynomial c = new Polynomial(0, a.deg + b.deg);
    for (int i = 0; i <= a.deg; i++)
      for (int j = 0; j <= b.deg; j++)
        c.coef[i+j] += (a.coef[i] * b.coef[j]);
    c.deg = c.degree();
    return c;
  }

  // return a(b(x))  - compute using Horner's method
  public Polynomial compose(Polynomial b) {
    Polynomial a = this;
    Polynomial c = new Polynomial(0, 0);
    for (int i = a.deg; i >= 0; i--) {
      Polynomial term = new Polynomial(a.coef[i], 0);
      c = term.plus(b.times(c));
    }
    return c;
  }


  // do a and b represent the same polynomial?
  public boolean eq(Polynomial b) {
    Polynomial a = this;
    if (a.deg != b.deg) return false;
    for (int i = a.deg; i >= 0; i--)
      if (a.coef[i] != b.coef[i]) return false;
    return true;
  }


  // use Horner's method to compute and return the polynomial evaluated at x
  public int evaluate(int x) {
    int p = 0;
    for (int i = deg; i >= 0; i--)
      p = coef[i] + (x * p);
    return p;
  }

  // differentiate this polynomial and return it
  public Polynomial differentiate() {
    if (deg == 0) return new Polynomial(0, 0);
    Polynomial deriv = new Polynomial(0, deg - 1);
    deriv.deg = deg - 1;
    for (int i = 0; i < deg; i++)
      deriv.coef[i] = (i + 1) * coef[i + 1];
    return deriv;
  }

  // convert to string representation
  public String toString() {
    if (deg ==  0) return "" + coef[0];
    if (deg ==  1) return coef[1] + "x + " + coef[0];
    String s = coef[deg] + "x^" + deg;
    for (int i = deg-1; i >= 0; i--) {
      if      (coef[i] == 0) continue;
      else if (coef[i]  > 0) s = s + " + " + ( coef[i]);
      else if (coef[i]  < 0) s = s + " - " + (-coef[i]);
      if      (i == 1) s = s + "x";
      else if (i >  1) s = s + "x^" + i;
    }
    return s;
  }
}