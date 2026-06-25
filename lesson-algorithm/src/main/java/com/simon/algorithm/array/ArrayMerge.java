package com.simon.algorithm.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ArrayMerge {
    public int[][] merge(int[][] intervals) {
        if(intervals.length == 0){
            return new int[0][2];
        }
        Arrays.sort(intervals, new Comparator<int[]>(){
            public int compare(int [] intervals,int[] intervals2){
                return intervals[0]-intervals2[0];
            }
        });
        List<int[]> merged = new ArrayList<int[]>();
        for(int i=0;i<intervals.length;i++){
            int L = intervals[i][0],R=intervals[i][1];
            int size = merged.size();
            if(size==0 ||merged.get(size-1)[1] < L){
                merged.add(new int[]{L,R});
            }else{
                merged.get(size-1)[1] = Math.max(merged.get(size-1)[1],R);
            }
        }
        return merged.toArray(new int[merged.size()][2]);
    }
}
