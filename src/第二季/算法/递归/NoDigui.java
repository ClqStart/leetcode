package 第二季.算法.递归;

import java.util.Stack;

public class NoDigui {
    public static void main(String[] args) {
        NoDigui lg = new NoDigui();
        lg.log(4);
        System.out.println("--------------------------");
        lg.log1(4);
        System.out.println("-----------------------------");
        lg.log2(4);
    }

    void log(int n) {
        if (n < 1) return;
        log(n - 1);
        int v = n + 10;
        System.out.println(v);
    }

    class Frame {
        int n;
        int v;

        Frame(int n, int v) {
            this.n = n;
            this.v = v;
        }

    }

    void log1(int n) {
        Stack<Frame> frames = new Stack<>();
        while (n > 0) {
            frames.push(new Frame(n, n + 10));
            n--;
        }
        while (!frames.isEmpty()) {
            Frame frame = frames.pop();
            System.out.println(frame.v);
        }
    }
    void log2(int n){
        for (int i = 1; i < n; i++) {
            System.out.println(i+10);
        }
    }
}
