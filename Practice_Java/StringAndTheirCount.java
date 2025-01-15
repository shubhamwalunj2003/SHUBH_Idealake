import java.util.HashMap;
import java.util.Map;

public class StringAndTheirCount {

    public static void main(String[] args) {
        String str1 = "abcdABCDabcd";

        char ch[] = str1.toCharArray();

        Map<Character, Integer> map = new HashMap<>();

        for(Character c: ch) {
            if(map.containsKey(c)){
                map.put(c, map.get(c) + 1);
            }
            else{
                map.put(c, 1);
            }
        }

        System.out.println(map);
    }
}
