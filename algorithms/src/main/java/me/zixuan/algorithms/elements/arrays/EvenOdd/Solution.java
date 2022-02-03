package me.zixuan.algorithms.elements.arrays.EvenOdd;

import java.util.Arrays;

public class Solution {
    public static void evenOdd(int[] A) {
        int nextEven = 0, nextOdd = A.length - 1;
        while (nextEven < nextOdd) {
            if (A[nextEven] % 2 == 0) {
                nextEven++;
            } else {
                int temp = A[nextEven];
                A[nextEven] = A[nextOdd];
                A[nextOdd--] = temp;
            }
        }
    }

    public static void main(String[] args) {
        int[] A = new int[]{3, 7, 6, 8, 9, -4, 11};
        evenOdd(A);

        // convert Java Array to String
        System.out.println(Arrays.toString(A));
    }
}
