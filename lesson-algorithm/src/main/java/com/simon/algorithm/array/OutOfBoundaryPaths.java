package com.simon.algorithm.array;

/**
 * 标题：出界的路径数
 *
 * 题目描述：
 * 给你一个大小为 m x n 的网格和一个球，球的起始坐标为 [startRow, startColumn]。
 * 每次可以将球移动到上下左右相邻的单元格，也可以穿过边界到达网格之外。
 * 最多移动 maxMove 次，返回可以将球移出边界的路径数量。
 * 因为答案可能很大，需要对 1e9 + 7 取余。
 */
public class OutOfBoundaryPaths {

    // 按题目要求，对结果取模。
    private static final int MOD = 1_000_000_007;

    // 四个移动方向：下、上、右、左。
    private static final int[][] DIRECTIONS = {
            {1, 0},
            {-1, 0},
            {0, 1},
            {0, -1}
    };

    public int findPaths(int m, int n, int maxMove, int startRow, int startColumn) {
        // dp[row][column] 表示当前移动次数后，球到达该位置的路径数。
        int[][] dp = new int[m][n];

        // 初始时，球只有一种方式位于起点。
        dp[startRow][startColumn] = 1;

        // result 累计所有已经出界的路径数量。
        long result = 0;

        // 每一轮循环表示多移动一步，最多移动 maxMove 次。
        for (int move = 1; move <= maxMove; move++) {
            // nextDp 记录下一步后仍然留在网格内的路径数。
            int[][] nextDp = new int[m][n];

            // 枚举当前所有可能停留的位置。
            for (int row = 0; row < m; row++) {
                for (int column = 0; column < n; column++) {
                    // 当前格子没有路径能到达，直接跳过。
                    if (dp[row][column] == 0) {
                        continue;
                    }

                    // 从当前格子分别向四个方向移动。
                    for (int[] direction : DIRECTIONS) {
                        int nextRow = row + direction[0];
                        int nextColumn = column + direction[1];

                        // 下一步走出网格，说明找到了一批出界路径。
                        if (nextRow < 0 || nextRow >= m || nextColumn < 0 || nextColumn >= n) {
                            result = (result + dp[row][column]) % MOD;
                        } else {
                            // 下一步仍在网格内，累加到下一轮状态。
                            nextDp[nextRow][nextColumn] = (nextDp[nextRow][nextColumn] + dp[row][column]) % MOD;
                        }
                    }
                }
            }

            // 当前轮结束后，用下一轮状态覆盖当前状态。
            dp = nextDp;
        }

        // 返回取模后的出界路径数。
        return (int) result;
    }

    public static void main(String[] args) {
        OutOfBoundaryPaths solution = new OutOfBoundaryPaths();
        System.out.println(solution.findPaths(2, 2, 2, 0, 0));
        System.out.println(solution.findPaths(1, 3, 3, 0, 1));
    }
}
