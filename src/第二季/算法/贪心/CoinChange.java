package 第二季.算法.贪心;

import java.util.Arrays;

public class CoinChange {
    public static void main(String[] args) {
        Integer[] faces = {25, 5, 10, 1};
        Arrays.sort(faces, (Integer f1, Integer f2) -> f2 - f1);
        int money = 41, coins = 0, i = 0;
        while (i < faces.length) {
            if(money<faces[i]){
                i++;
                continue;
            }
            System.out.println(faces[i]);
            money -= faces[i];
            coins++;
        }
        System.out.println(coins);
    }

    static void coinChange() {
        Integer[] faces = {25, 5, 10, 1};
        Arrays.sort(faces, (Integer f1, Integer f2) -> f2 - f1);

        int money = 41, coins = 0;
        for (int i = 0; i < faces.length; i++) {
            if (money < faces[i]) {
                continue;
            }
            System.out.println(faces[i]);
            money -= faces[i--];
            coins++;
        }
        System.out.println(coins);

    }
}
