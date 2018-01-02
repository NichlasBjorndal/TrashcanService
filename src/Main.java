import java.math.BigInteger;
import java.util.Random;

public class Main {

    private static final BigInteger ZERO = BigInteger.ZERO;
    private static final BigInteger ONE = BigInteger.ONE;
    private static final BigInteger TWO = new BigInteger("2");
    private static final BigInteger THREE = new BigInteger("3");

    public static boolean isProbablePrime(BigInteger n, int k) {
        if(n.mod(TWO).equals(ZERO))
            return false;

        if (n.compareTo(THREE) < 0)
            return true;
        int s = 0;
        BigInteger d = n.subtract(ONE);
        while (d.mod(TWO).equals(ZERO)) {
            s++;
            d = d.divide(TWO);
        }
        for (int i = 0; i < k; i++) {
            BigInteger a = uniformRandom(TWO, n.subtract(ONE));
            BigInteger x = a.modPow(d, n);
            if (x.equals(ONE) || x.equals(n.subtract(ONE)))
                continue;
            int r = 1;
            for (; r < s; r++) {
                x = x.modPow(TWO, n);
                if (x.equals(ONE))
                    return false;
                if (x.equals(n.subtract(ONE)))
                    break;
            }
            if (r == s) // None of the steps made x equal n-1.
                return false;
        }
        return true;
    }

    private static BigInteger uniformRandom(BigInteger bottom, BigInteger top) {
        Random rnd = new Random();
        BigInteger res;
        do {
            res = new BigInteger(top.bitLength(), rnd);
        } while (res.compareTo(bottom) < 0 || res.compareTo(top) > 0);
        return res;
    }

    public static void main(String[] args) {
        int primes[] = new int[10];
        for (int k = 1; k < 10; k++) {
            for (int n = 25; n < 25001; n++) {
                if(isProbablePrime(new BigInteger(Integer.toString(n)),k))
                    primes[k]++;
            }
        }

        for (int count: primes) {
            System.out.println(count);
        }
    }
}
