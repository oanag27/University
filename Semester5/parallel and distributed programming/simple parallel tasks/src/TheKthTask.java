public class TheKthTask implements Runnable {
    private final int[][] firstMatrix;
    private final int[][] secondMatrix;
    private final int[][] resultMatrix;
    private final int indexStart;
    private final int numberOfElementsToSkip;    //equal to nr of threads

    public TheKthTask(int[][] firstMatrix, int[][] secondMatrix, int[][] resultMatrix, int indexStart, int numberOfElementsToSkip) {
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.resultMatrix = resultMatrix;
        this.indexStart = indexStart;
        this.numberOfElementsToSkip = numberOfElementsToSkip;
    }

    private int calculateDotProduct(int i, int j) {
        int result = 0;
        for (int m = 0; m < firstMatrix[0].length; m++)     //common number of columns/rows
            result += firstMatrix[i][m] * secondMatrix[m][j];
        return result;
    }

    @Override
    public void run() {
        int totalElements = resultMatrix.length * resultMatrix[0].length;
        int currentIndex = indexStart;  // Each thread starts from its own task index.

        // Iterate through the matrix in k-steps until all assigned elements are processed
        while (currentIndex < totalElements) {
            // Convert linear index to 2D coordinates
            int i = currentIndex / resultMatrix[0].length;   // Row index
            int j = currentIndex % resultMatrix[0].length;   // Column index

            // Calculate the element and store it in the result matrix
            resultMatrix[i][j] = calculateDotProduct(i, j);

            // Move to the next k-th element (numberOfThreads away)
            currentIndex += numberOfElementsToSkip;
        }
    }
}
