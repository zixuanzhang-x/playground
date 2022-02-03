package me.zixuan.algorithms.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class _1640 {
    public boolean canFormArray(int[] arr, int[][] pieces) {
        Map<Integer, int[]> map = new HashMap<>();
        for (int[] piece: pieces) {
            map.put(piece[0], piece);
        }
        int[] result = new int[arr.length];
        int i = 0;
        for (int a: arr) {
            if (map.containsKey(a)) {
                for (int num: map.get(a)) {
                    result[i++] = num;
                }
            }
        }
        return Arrays.equals(arr, result);
    }
}
