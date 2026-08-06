package com.simon.algorithm.array;

/**
 * 搜索旋转有序数组。
 *
 * 旋转有序数组示例：
 * 原数组 [1,2,3,4,5,6,7] 旋转后可能变成 [5,6,7,1,2,3,4]。
 */
public class BinarySearch {
        public int search(int[] nums, int target) {
            return binarySearch(0,nums.length-1,nums,target);
        }

        private int binarySearch(int left,int right,int[] nums ,int target){
            // 搜索区间已经为空，说明 target 不存在。
            if(left>right){
                return -1;
            }

            // 取当前区间的中间位置，避免直接 (left + right) 可能产生整数溢出。
            int mid = left + (right-left)/2;

            // 先检查左端点、中点、右端点，可以减少后续区间判断的边界复杂度。
            if(nums[left] == target){
                return left;
            }
            if(nums[mid]==target){
                return mid;
            }
            if(nums[right]==target){
                return right;
            }

            // 如果 nums[left] < nums[mid]，说明 left 到 mid 这一段是正常升序的。
            if(nums[left]<nums[mid]){
                // target 落在左半段的升序范围内，就继续搜索左半段。
                if(nums[left] < target && target < nums[mid]){
                    return binarySearch(left+1,mid-1,nums,target);
                }else{
                    // 否则 target 只能在右半段。
                    return binarySearch(mid+1,right-1,nums,target);
                }
            }else{
                // 否则 mid 到 right 这一段是正常升序的。
                // target 落在右半段的升序范围内，就继续搜索右半段。
                if(nums[mid] < target && target < nums[right]){
                    return binarySearch(mid+1,right-1,nums,target);
                }else{
                    // 否则 target 只能在左半段。
                    return binarySearch(left+1,mid-1,nums,target);
                }
            }

        }


    public static void main(String[] args) {
        BinarySearch bs = new BinarySearch();
        int[] nums = new int[]{5,6,7,8,9,10,1,2,3,4};
        int target = 1;
        System.out.println(bs.search(nums,target));
    }
}
