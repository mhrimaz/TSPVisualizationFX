package solvers;


import java.util.Random;

public class ArrayUtil {

    private ArrayUtil(){

    }

    public static void shuffle(Object[] a) {
        int n = a.length;
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(a, i, change);
        }
    }

    public static void swap(Object[] a, int firstIndex, int secondIndex) {
        Object helper = a[firstIndex];
        a[firstIndex] = a[secondIndex];
        a[secondIndex] = helper;
    }

    public static boolean contains(Object[] array, Object target) {
        for(Object item:array){
            if(item!=null && item.equals(target)){
                return true;
            }
        }
        return false;
    }

    public static int findIndex(Object[] array, Object target){
        for(int i = 0 ; i<array.length;i++){
            if(array[i].equals(target)){
                return i;
            }
        }
        return -1;
    }
}
