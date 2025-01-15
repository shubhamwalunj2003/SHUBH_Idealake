
import java.util.*;

public class Swap_Array {

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7};

        Random rand = new Random();

        for (int i = 0; i < array.length; i++) {
            int randomIndex = rand.nextInt(array.length);

            int temp = array[randomIndex];
            array[randomIndex] = array[i];
            array[i] = temp;
        }

        System.out.println(Arrays.toString(array));
    }

}
