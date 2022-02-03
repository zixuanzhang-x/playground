package me.zixuan.patterns.slidingwindow;

import java.util.Arrays;

// Given an array, find the average of all contiguous subarrays of size ‘K’ in it.
public class AverageOfSubarrayOfSizeK {
    public static double[] findAverages(int K, int[] arr) {
        if (arr.length < K) {
            System.out.println("Invalid Input Error: K should be smaller than or equal to Array length");
        }

        double[] result = new double[arr.length - K + 1];
        for (int i = 0; i < arr.length - K + 1; i++) {
            // find sum of next 'K' elements
            double sum = 0;
            for (int j = i; j < i + K; j++)
                sum += arr[j];
            result[i] = sum / K; // calculate average
        }
        return result;
    }

    public static double[] findAverages2(int K, int[] arr) {
        double[] result = new double[arr.length - K + 1];
        double windowSum = 0;
        int windowStart = 0;
        for (int windowEnd = 0; windowEnd < arr.length; windowEnd++) {
            windowSum += arr[windowEnd]; // add the next element
            // slice the window, we don't need to slide if we've not hit the required window size of 'K'
            if (windowEnd >= K - 1) {
                result[windowStart] = windowSum / K; // calculate the average
                windowSum -= arr[windowStart]; // subtract the element going out
                windowStart++; // slide the window ahead
            }
        }
        return result;
    }

    public static void main(String[] args) {
        double[] result = AverageOfSubarrayOfSizeK.findAverages(5, new int[]{1, 3, 2, 6, -1, 4, 1, 8, 2});
        System.out.println("Average of subarrays of size K: " + Arrays.toString(result));

        result = AverageOfSubarrayOfSizeK.findAverages2(5, new int[]{1, 3, 2, 6, -1, 4, 1, 8, 2});
        System.out.println("Average of subarrays of size K: " + Arrays.toString(result));
    }
}
