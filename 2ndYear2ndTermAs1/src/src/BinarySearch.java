public class BinarySearch {

    public int binarySearch(int[] sortedArr,int value){
        int low=0;
        int high=sortedArr.length-1;
        while (high>=low){
            int mid=(high+low)/2;
            if(sortedArr[mid]==value){
                return mid;
            }else if(sortedArr[mid]<value){
                low=mid+1;
            }else {
                high=mid-1;
            }
        }
        return -1;
    }

}
