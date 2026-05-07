public class Utils {

    public boolean isPrime(int value) {
        if (value < 2) return false;
        for (int i = 2; i <= Math.sqrt(value); i++) {
            if (value % i == 0) return false;
        }
        return true;
    }

    public int randomBuckets(int min, int max) {
        int random = (int) (Math.random() * (max - min)) + min;
        while (!isPrime(random)) {
            random = (int) (Math.random() * (max - min)) + min;
        }
        return random;
    }
}