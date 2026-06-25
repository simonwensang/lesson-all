package com.simon.algorithm.array;

public class BinarySearch {
        public int search(int[] nums, int target) {
            return binarySearch(0,nums.length-1,nums,target);
        }

        private int binarySearch(int left,int right,int[] nums ,int target){
            if(left>right){
                return -1;
            }
            int mid = left + (right-left)/2;

            if(nums[left] == target){
                return left;
            }
            if(nums[mid]==target){
                return mid;
            }
            if(nums[right]==target){
                return right;
            }

            if(nums[left]<nums[mid]){
                if(nums[left]<target && nums[mid]>target){
                    return binarySearch(left+1,mid-1,nums,target);
                }else{
                    return binarySearch(mid+1,right-1,nums,target);
                }
            }else{
                if(nums[right]>target && nums[mid]<target){
                    return binarySearch(mid+1,right-1,nums,target);
                }else{
                    return binarySearch(left+1,mid-1,nums,target);
                }
            }

        }

    public static void main(String[] args) {
        BinarySearch bs = new BinarySearch();
        int[] nums = new int[]{5,6,7,8,9,10,1,2,3,4};
        int target = 0;
        System.out.println(bs.search(nums,target));
    }
}
