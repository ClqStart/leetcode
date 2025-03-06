package 第二季.算法.回溯;

public class Queens0 {
    public static void main(String[] args) {
        new Queens0().placeQueens(4);
    }

    int[] clos;
    int ways;

    public void placeQueens(int n) {
        if (n < 1) return;
        clos = new int[n];
        place(0);
        System.out.println(ways);
    }

    private void place(int row) {
        if (row == clos.length) {
            ways++;
            show();
            return;
        }
        for (int col = 0; col < clos.length; col++) {
            if (isVality(row, col)) {
                clos[row] = col;
                place(row + 1);
            }

        }
    }

    private void show() {
        for (int row = 0; row < clos.length; row++) {
            for (int col = 0; col < clos.length; col++) {
                if(clos[row]==col){
                    System.out.print("1 ");
                }else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
        System.out.println("------------------------");
    }

    private boolean isVality(int row, int col) {
        for (int i = 0; i < row; i++) {
            if (clos[i] == col) return false;
            if (row - i == Math.abs(col-clos[i] )) return false;
        }
        return true;
    }
}
