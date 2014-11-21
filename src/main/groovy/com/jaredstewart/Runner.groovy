package com.jaredstewart

class Runner {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in))
        System.out.print("p(1): ")
        BigInteger N = new BigInteger(br.readLine())  + 1 //p(1)+1

        System.out.print "p($N): "
        BigInteger yi = new BigInteger(br.readLine()) //p(N)

        BigInteger ai

        def coeffs = []
        while (yi > 0) {
            ai = yi.mod(N)
            coeffs << ai
            yi = (yi - ai) / N
        }

        System.out.print "$coeffs \n"

        String polynomial
        coeffs.eachWithIndex {coeff, i ->
            if (i == 0) {
                polynomial = coeff} else {
                polynomial += "+$coeff x^$i" }
        }

        System.out.print new Polynomial(coeffs).toString()

    }
}
