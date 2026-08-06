package com.simon.algorithm.array;

import java.util.HashMap;
import java.util.Map;

/**
 * 标题：最多有多少个点在同一条直线上
 *
 * 题目描述：
 * 给定一个二维平面，平面上有 n 个点。
 * 求最多有多少个点在同一条直线上。
 */
public class MaxPointsOnLine {

    public int maxPoints(int[][] points) {
        // 没有点时，不存在直线上的点。
        if (points == null || points.length == 0) {
            return 0;
        }

        // 一个点或两个点一定可以在同一条直线上。
        if (points.length <= 2) {
            return points.length;
        }

        // result 记录所有基准点计算后的最大答案。
        int result = 1;

        // 枚举每个点作为基准点，统计其他点相对它的斜率。
        for (int i = 0; i < points.length; i++) {
            // slopeCountMap 统计相同斜率出现的次数。
            Map<String, Integer> slopeCountMap = new HashMap<>();

            // samePointCount 统计和基准点完全重合的点。
            int samePointCount = 1;

            // currentMax 记录当前基准点下，同一斜率最多有多少个其他点。
            int currentMax = 0;

            // 只比较 i 后面的点，避免重复统计同一对点。
            for (int j = i + 1; j < points.length; j++) {
                // dx 和 dy 表示两个点横纵坐标的差。
                int dx = points[j][0] - points[i][0];
                int dy = points[j][1] - points[i][1];

                // 坐标完全相同的点可以计入任意一条经过基准点的直线。
                if (dx == 0 && dy == 0) {
                    samePointCount++;
                    continue;
                }

                // 将斜率化成统一格式，避免浮点数精度问题。
                String slope = buildSlopeKey(dx, dy);

                // 当前斜率上的点数加 1。
                int count = slopeCountMap.getOrDefault(slope, 0) + 1;
                slopeCountMap.put(slope, count);

                // 更新当前基准点下的最大斜率计数。
                currentMax = Math.max(currentMax, count);
            }

            // 当前基准点的答案 = 同斜率点数 + 重复点数。
            result = Math.max(result, currentMax + samePointCount);
        }

        return result;
    }

    private String buildSlopeKey(int dx, int dy) {
        // dx 为 0 表示垂直线，统一用 1/0 表示。
        if (dx == 0) {
            return "1/0";
        }

        // dy 为 0 表示水平线，统一用 0/1 表示。
        if (dy == 0) {
            return "0/1";
        }

        // 用最大公约数约分，保证相同斜率得到相同 key。
        int gcd = gcd(Math.abs(dx), Math.abs(dy));
        dx /= gcd;
        dy /= gcd;

        // 统一符号，让分母 dx 始终为正。
        if (dx < 0) {
            dx = -dx;
            dy = -dy;
        }

        // 用 dy/dx 作为斜率 key。
        return dy + "/" + dx;
    }

    private int gcd(int a, int b) {
        // 使用欧几里得算法计算最大公约数。
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

    public static void main(String[] args) {
        MaxPointsOnLine solution = new MaxPointsOnLine();
        System.out.println(solution.maxPoints(new int[][]{{1, 1}, {2, 2}, {3, 3}}));
        System.out.println(solution.maxPoints(new int[][]{{1, 1}, {3, 2}, {5, 3}, {4, 1}, {2, 3}, {1, 4}}));
    }
}
