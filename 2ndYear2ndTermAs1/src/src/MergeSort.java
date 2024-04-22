import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeSort {
   public int[] merge_sort(int[] arr) {
       int n = arr.length;
       if (n <= 1) {
           return arr;
       }
       int mid = n / 2;
       int[] left = merge_sort(Arrays.copyOfRange(arr, 0, mid));
       int[] right = merge_sort(Arrays.copyOfRange(arr, mid, n));

       return merge(left, right);
   }
    private int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int leftIndex = 0, rightIndex = 0, resultIndex = 0;

        while (leftIndex < left.length && rightIndex < right.length) {
            if (left[leftIndex] < right[rightIndex]) {
                result[resultIndex++] = left[leftIndex++];
            } else {
                result[resultIndex++] = right[rightIndex++];
            }
        }

        while (leftIndex < left.length) {
            result[resultIndex++] = left[leftIndex++];
        }

        while (rightIndex < right.length) {
            result[resultIndex++] = right[rightIndex++];
        }

        return result;
    }
    int[] reverseArray(int[] arr){
        int reversed[] =new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            reversed[arr.length-1-i]=arr[i];
        }
        return reversed;
    }

}
