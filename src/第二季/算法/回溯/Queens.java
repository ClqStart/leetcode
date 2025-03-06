package 第二季.算法.回溯;

public class Queens {
    public static void main(String[] args) {
        new Queens().placeQueens(8 );
    }

    /**
     * 标记一列是否有 皇后
     */
    boolean[] cols;

    /**
     * 标记对角线（左上角-》右下角）row-col+n-1
     */
    boolean[] leftTop;

    /**
     * 标记对角线（右上角-》左下角）row+col
     */
    boolean[] rightTop;
    int ways;

    public void placeQueens(int n) {
        if (n < 1) return;
        cols = new boolean[n];
        leftTop = new boolean[(n << 1) - 1];
        rightTop = new boolean[leftTop.length];
        place(0);
        System.out.println(n + "皇后一共有" + ways + "种摆法");
    }

    private void place(int row) {
        if(row==cols.length){
            ways++;
            return;
        }
        for (int col = 0; col < cols.length; col++) {
            if(cols[col]) continue;
            if(leftTop[row-col+cols.length-1])continue;
            if(rightTop[row+col])continue;

            cols[col] =true;
            leftTop[row-col+cols.length-1]=true;
            rightTop[row+col]=true;
            place(row+1);
            cols[col] =false;
            leftTop[row-col+cols.length-1]=false;
            rightTop[row+col]=false;
        }

    }


}
