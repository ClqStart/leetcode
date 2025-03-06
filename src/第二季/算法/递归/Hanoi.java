package 第二季.算法.递归;

public class Hanoi {
    public static void main(String[] args) {
        Hanoi ha = new Hanoi();
        ha.hanoi(4,"A","B","C");
    }
    void hanoi(int n,String p1,String p2,String p3){
        if(n==1){
            move(n,p1,p3);
            return;
        }
        hanoi(n-1,p1,p3,p2);
        move(n,p1,p3);
        hanoi(n-1,p2,p1,p3);
    }

    private void move(int no, String from, String to) {
        System.out.println("将"+no+"号盘子从"+from+"移动到"+to);
    }
}
