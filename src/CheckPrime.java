
public class CheckPrime {
	public static boolean isPrime(int n) {
	    if (n == 2) {
	        return true;
	    } else if (n == 1 || n % 2 == 0) {
	        return false;
	    }
	    for (int i = 3; i * i <= n; i += 2) {
	        if (n % i == 0) {
	            return false;
	        }
	    }
	    return true;
	}

}
