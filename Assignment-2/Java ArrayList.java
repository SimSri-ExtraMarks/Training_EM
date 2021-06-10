import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int arrayCount = scanner.nextInt();
        int[][] arrays = new int[arrayCount][];
        for (int i = 0; i < arrayCount; i++){
            int[] array = new int[scanner.nextInt()];
            for (int j = 0; j < array.length; j++) {
                array[j] = scanner.nextInt();
            }
            arrays[i] = array;
        }
        
        int queryCount = scanner.nextInt();
        for (int i = 0; i < queryCount; i++) {
            try {
                int x = scanner.nextInt() - 1;
                int y = scanner.nextInt() - 1;
                System.out.println(arrays[x][y]);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("ERROR!");
            }
        }
    }
}
