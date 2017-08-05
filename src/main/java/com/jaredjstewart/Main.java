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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    System.out.print("p(1): ");
    BigInteger N = new BigInteger(br.readLine()).add(BigInteger.ONE); //p(1)+1

    System.out.print(String.format("p(%s): ", N));
    BigInteger yi = new BigInteger(br.readLine()); //p(N)

    BigInteger ai;

    List<BigInteger> coeffs = new ArrayList<>();
    while (yi.compareTo(BigInteger.ZERO) > 0) {
      ai = yi.mod(N);
      coeffs.add(ai);
      yi = (yi.subtract(ai)).divide(N);
    }

    String polynomial = coeffs.get(0).toString();

    if (coeffs.size() > 1) {
      polynomial += "+" + coeffs.get(1) + "x";
    }

    for (int i = 1; i < coeffs.size(); i++) {
      polynomial += "+" + coeffs.get(i) + " x^" + i;
    }
    System.out.println(polynomial);
  }

}
