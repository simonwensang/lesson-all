package com.simon.algorithm.array;

public class Searcher {
    /**
     * 顺序遍历查找 O[N]
     * @param arr
     * @param key
     * @return
     */
    public int orderSearch(int[] arr, int key){
        int count = 0;
       for (int i=0;i<arr.length;i++){
           System.out.println("count =" + ++count);
           if(arr[i]==key){
               return i;
           }
       }
       return -1;
    }

    /**
     *  二分查找法
     *  条件：有序数组, O(lgN)
     * @param arr
     * @param target
     * @return
     */
    public int binarySearch(int[] arr, int target){
        int low = 0;
        int high = arr.length - 1;
        while(low <= high){
            int mid = low + (high - low)/2;
            if(arr[mid] == target){
                return mid;
            }else if(arr[mid] < target){
                low = mid + 1;
            }else if(arr[mid] > target){
                high = mid - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Searcher searcher = new Searcher();
        int[] arr = new int[]{1,2,3,4,5,6,7,8,9,10};
        System.out.println(searcher.orderSearch(arr, 9));
        System.out.println(searcher.binarySearch(arr, 9));
    }

}
