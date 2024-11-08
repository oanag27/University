public class TaskRow implements Runnable {
    private final int[][] firstMatrix;
    private final int[][] secondMatrix;
    private final int[][] resultMatrix;
    private final int indexStart;    //the position number of the first element to compute
    private final int numberOfElements;    //the number of elements to compute

    public TaskRow(int[][] firstMatrix, int[][] secondMatrix, int[][] resultMatrix, int indexStart, int numberOfElements) {
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
        int totalColumns = resultMatrix[0].length;
        int totalRows = resultMatrix.length;

        // Start from indexStart, which is computed linearly
        for (int i = 0; i < numberOfElements; i++) {
            // Calculate the row and column from the index
            int index = indexStart + i;
            int line = index / totalColumns;
            int column = index % totalColumns;

            // Make sure you don't exceed the bounds of the matrix
            if (line < totalRows) {
                resultMatrix[line][column] = calculateDotProduct(line, column);
            }
        }
    }
}