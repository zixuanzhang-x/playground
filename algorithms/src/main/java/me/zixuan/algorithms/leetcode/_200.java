package me.zixuan.algorithms.leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class _200 {
    // method 1: search
    public int numIslands(char[][] grid) {
        int count = 0;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    search(grid, i, j);
                }
            }
        }
        return count;
    }

    // depth-first search
    private void search(char[][] grid, int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] == '0')
            return;
        grid[i][j] = '0';
        search(grid, i + 1, j);
        search(grid, i - 1, j);
        search(grid, i, j + 1);
        search(grid, i, j - 1);
    }

    // breadth-first search
    private void search2(char[][] grid, int i, int j) {
        int m = grid.length, n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, j});
        grid[i][j] = '0';
        while (!queue.isEmpty()) {
            int[] coords = queue.poll();
            int x = coords[0], y = coords[1];
            int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for (int k = 0; k < directions.length; k++) {
                int dx = x + directions[k][0];
                int dy = y + directions[k][1];
                if (dx >= 0 && dx < m && dy >= 0 && dy < n && grid[dx][dy] == '1') {
                    queue.offer(new int[]{dx, dy});
                    grid[dx][dy] = '0';
                }
            }
        }
    }

    // method 2: union-find
//    public int numIslands2(char[][] grid) {
//
//    }
}
