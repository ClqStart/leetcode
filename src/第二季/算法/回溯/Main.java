package 第二季.算法.回溯;

public class Main {

    public static void main(String[] args) {
        new Main().placeQueens(4);
    }

    int[] cols;
    int way;

    public void placeQueens(int n) {
        if (n < 1) return;
        cols = new int[n];
        place(0);

    }

    private void place(int row) {
        if (row == cols.length) {
            way++;
            show();
            return;
        }
        for (int col = 0; col < cols.length; col++) {
            if (isBValid(row, col)) { //减枝
                cols[row] = col;
                place(row + 1);//回溯
            }
        }
    }

    private void show() {
        for (int row = 0; row < cols.length; row++) {
            for (int col = 0; col < cols.length; col++) {
                if (cols[row] == col) {
                    System.out.print("1 ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
        System.out.println("--------------------------------------------");

    }

    private boolean isBValid(int row, int col) {
        for (int i = 0; i < row; i++) {
            if (cols[i] == col) return false;
            if (row - i == Math.abs(col - cols[i])) return false;
        }
        return true;
    }

}
