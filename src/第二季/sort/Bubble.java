package 第二季.sort;

public class Bubble {
    static void  test01(){
        int[]  array = {10,9,56,19,28,37,34};
        for (int i = 0; i < array.length-1; i++) {
            for (int j = i; j < array.length-1; j++) {
                if(array[j]>array[j+1]) {
                    int tmp = array[j];
                    array[j]=array[j+1];
                    array[j+1]=tmp;
                }
            }
        }
        for (int i = 0; i <array.length ; i++) {
            System.out.print(array[i]+"_");
        }
    }


    public static void main(String[] args) {
            test01();
    }

}
