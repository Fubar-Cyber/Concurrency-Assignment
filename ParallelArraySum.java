package RandomNumberGenerator;

import java.util.Random;

public class ParallelArraySum {

    public static void main(String[] args) throws InterruptedException {
    	
    	// Make an array of 200 million capacity
        int[] arr = generateRandomArray(200000000);
        long start, end;
        
        // Decorative title Parallel thread
        System.out.println("============================");
        System.out.println("     Parallel Thread");
        System.out.println("============================");
               
        // Parallel thread sum
        start = System.nanoTime();
        int sumParallel = parallelthreadSum(arr);
        end = System.nanoTime();
        System.out.println("\nSum:  " + sumParallel);
        System.out.println("Time: " + (end - start) + " nano seconds");
        
        // Decorative title Parallel thread
        System.out.println("\n============================");
        System.out.println("      Single Thread");
        System.out.println("============================");
        
        // Single thread sum
        start = System.nanoTime();
        int sumSingle = singleThreadSum(arr);
        end = System.nanoTime();
        System.out.println("\nSum:  " + sumSingle);
        System.out.println("Time: " + (end - start) + " nano seconds");
    }

    public static int[] generateRandomArray(int size) {
    	// Make numbers between 1 and 10
        Random random = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(10) + 1;
        }
        return arr;
    }

    public static int parallelthreadSum(int[] arr) throws InterruptedException {
        int numThreads = Runtime.getRuntime().availableProcessors();
        SumThread[] threads = new SumThread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new SumThread(arr, i * (arr.length / numThreads), (i + 1) * (arr.length / numThreads));
            threads[i].start();
        }
        int sum = 0;
        for (int i = 0; i < numThreads; i++) {
            threads[i].join();
            sum += threads[i].getSum();
        }
        return sum;
    }

    public static int singleThreadSum(int[] arr) {
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        return sum;
    }

    static class SumThread extends Thread {
        private final int[] arr;
        private final int start;
        private final int end;
        private int sum;

        SumThread(int[] arr, int start, int end) {
            this.arr = arr;
            this.start = start;
            this.end = end;
            this.sum = 0;
        }

        public void run() {
            for (int i = start; i < end; i++) {
                sum += arr[i];
            }
        }

        public int getSum() {
            return sum;
        }
    }
}
