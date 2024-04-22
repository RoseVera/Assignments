import java.util.Arrays;

public class CountingSort {
    public int[] countingSort(int[] arr ){
        int k = Arrays.stream(arr).max().getAsInt(); //max
        int[] countArr = new int[k+1];
        Arrays.fill(countArr, 0);
        int[] outArr = new int[arr.length];

        for (int j : arr) {
            countArr[j]++;
        }

        for (int i = 1; i < k+1; i++) {
            countArr[i]=countArr[i]+countArr[i-1];
        }

        for (int m = arr.length-1; m >=0 ; m--) {
            int j=arr[m];
            countArr[j]=countArr[j]-1;
            outArr[countArr[j]]=arr[m];
        }

        return outArr;
    }

}
