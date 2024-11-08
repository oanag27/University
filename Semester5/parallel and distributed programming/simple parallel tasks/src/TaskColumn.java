public class TaskColumn implements Runnable {
    private final int[][] firstMatrix;
    private final int[][] secondMatrix;
    private final int[][] resultMatrix;
    private final int indexStart;    //the position number of the first element to compute
    private final int numberOfElements;    //the number of elements to compute

    public TaskColumn(int[][] firstMatrix, int[][] secondMatrix, int[][] resultMatrix, int indexStart, int numberOfElements) {
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.resultMatrix = resultMatrix;
        this.indexStart = indexStart;
        this.numberOfElements = numberOfElements;
    }
    private int calculateDotProduct(int i, int j) {  //compute element
        int result = 0;
        for (int m = 0; m < firstMatrix[0].length; m++)     //common number of columns/rows
            result += firstMatrix[i][m] * secondMatrix[m][j];
        return result;
    }
    @Override
    public void run() {
        int line = indexStart % resultMatrix.length;  //line of first element to compute
        int column = indexStart / resultMatrix.length;  //column of first element to compute
        // Compute `count` number of elements starting from the calculated index.
        for (int i = 0; i < numberOfElements; i++) {
           resultMatrix[line][column] = calculateDotProduct(line, column);
            line++;
            if (line == resultMatrix.length) {   //reset row if equal to number of rows
                line = 0;
                column++;
            }
        }
    }
}
