package 第二季.算法.贪心;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class Knapsack {
    public static void main(String[] args) {
        select("价值主导",(Article a1, Article a2) -> a2.value - a1.value);
        select("重量主导", Comparator.comparingInt((Article a) -> a.weight));
        select("价值密度主导",(Article a1, Article a2) -> Double.compare(a2.valueDensity,a1.valueDensity));
    }

    static void select(String title,Comparator<Article> cmp) {
        Article[] articles = new Article[]{
                new Article(35, 10), new Article(30, 40),
                new Article(60, 30), new Article(50, 50),
                new Article(40, 35), new Article(10, 40),
                new Article(25, 30),
        };
        Arrays.sort(articles, cmp);

        int capacity = 150, weight = 0, value = 0;
        {
            LinkedList<Article> selectedArtice = new LinkedList<>();
            for (int i = 0; i < articles.length && weight < capacity; i++) {
                int newWeight = weight + articles[i].weight;
                if (newWeight <= capacity) {
                    weight = newWeight;
                    value += articles[i].value;
                    selectedArtice.add(articles[i]);
                }
            }
            System.out.println("【"+title+"】");
            System.out.println("总价值： "+value);{
            for (int i = 0; i < selectedArtice.size(); i++) {
                System.out.println(selectedArtice.get(i));
            }
            System.out.println("------------------------------------");
        }
        }


    }
}
