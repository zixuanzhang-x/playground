package me.zixuan.leetcode;

import java.util.*;

public class _827 {
    public int largestIsland(int[][] grid) {
        int n = grid.length;
        int index = 2;
        Map<Integer, Integer> islandSizes = new HashMap<>();
        int maxSize = 0;

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] != 1) continue;

                int size = traverseIsland(grid, r, c, index);
                islandSizes.put(index, size);
                index++;
                maxSize = Math.max(maxSize, size);
            }
        }

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 0) {
                    Set<Integer> visitedIslands = new HashSet<>();
                    int curSize = 1;
                    for (int[] coordinate : getValidNeighbor(r, c, n)) {
                        int x = coordinate[0], y = coordinate[1];
                        if (grid[x][y] == 0) continue;
                        int curIndex = grid[x][y];
                        if (!visitedIslands.contains(curIndex)) {
                            curSize += islandSizes.get(curIndex);
                            visitedIslands.add(curIndex);
                        }
                    }
                    maxSize = Math.max(maxSize, curSize);
                }
            }
        }
        return maxSize;
    }

    // traverse an island and mark its lands to an index, return its size
    private int traverseIsland(int[][] grid, int r, int c, int index) {
        int size = 1;
        grid[r][c] = index;
        for (int[] coordinate : getValidNeighbor(r, c, grid.length)) {
            int x = coordinate[0], y = coordinate[1];
            if (grid[x][y] == 1) {
                size += traverseIsland(grid, x, y, index);
            }
        }
        return size;
    }

    private List<int[]> getValidNeighbor(int r, int c, int n) {
        List<int[]> neighbors = new ArrayList<>();
        if (isValid(r + 1, c, n)) neighbors.add(new int[]{r + 1, c});
        if (isValid(r - 1, c, n)) neighbors.add(new int[]{r - 1, c});
        if (isValid(r, c + 1, n)) neighbors.add(new int[]{r, c + 1});
        if (isValid(r, c - 1, n)) neighbors.add(new int[]{r, c - 1});
        return neighbors;
    }

    private boolean isValid(int r, int c, int n) {
        return 0 <= r && r < n && 0 <= c && c < n;
    }
}
