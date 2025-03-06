package 第二季.算法.回溯;

public class Queens8 {
    public static void main(String[] args) {
        new Queens8().placeQueens();
    }

    private byte cols;
    private short leftTop; //row-col+n-1
    private short rightTop; //row+col
    private int way;
    private int[] queens;

    public void placeQueens() {
        queens = new int[8];
        place(0);
        System.out.println("8皇后一共有" + way + "种摆法");
    }

    private void place(int row) {
        if (row == 8) {
            way++;
            show();
        }
        for (int col = 0; col < 8; col++) {
            if ((cols & (1 << col)) != 0) continue;   // 左移取与

            int leftIndex = row - col + 7;
            if ((leftTop & (1 << leftIndex)) != 0) continue;

            int rightIndex = row + col;
            if ((rightTop & (1 << rightIndex)) != 0) continue;
            queens[row] = col;

            cols |= (1 << col);
            leftTop |= (1 << leftIndex);            //左移取或 置1
            rightTop |= (1 << rightIndex);
            place(row + 1);

            cols &= ~(1 << col);                 //左移取反再获  置0
            leftTop &= ~(1<<leftIndex);
            rightTop &= ~(1<<rightIndex);
        }
    }

    private void show() {
        for (int row = 0; row < queens.length; row++) {
            for (int col = 0; col < queens.length; col++) {
                if(queens[row]==col){
                    System.out.print("1 ");
                }else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
        System.out.println("--------------------------------------------");
    }


}
