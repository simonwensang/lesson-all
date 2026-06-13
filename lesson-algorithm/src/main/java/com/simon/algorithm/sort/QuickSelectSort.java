package com.simon.algorithm.sort;

public class QuickSelectSort implements Sort<int[]>{

    @Override
    public void sort(int[] ints) {
        int n = ints.length;
        int left = 0;
        int right = n-1;
        quickSort(ints,left,right);
    }

    public void quickSort(int[] ints, int left, int right){
        if(left >= right){
            return;
        }
        int pivotIndex = partition(ints,left,right);
        quickSort(ints,left,pivotIndex-1);
        quickSort(ints,pivotIndex+1,right);
    }

    public int partition(int[] ints, int left, int right){
        int pivot = ints[right];
        int i = left;

        for(int j = left; j < right; j++){
            if(ints[j] < pivot){
                swap(ints,i,j);
                i++;
            }
        }
        swap(ints,i,right);
        return i;
    }

    public void swap(int[] ints,int i,int j){
        int temp = ints[i];
        ints[i] = ints[j];
        ints[j] = temp;
    }

}
