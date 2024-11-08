import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void initializeMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = 1;
            }
        }
    }

    public static void displayMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++)
                System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
    }

    public static void executeTasks(int taskNumber, int approach, int numberOfThreads, int[][] firstMatrix, int[][] secondMatrix, int[][] resultMatrix) throws InterruptedException {
        List<Runnable> runnables = new ArrayList<>();

        int nrOfElemsToExecute = (firstMatrix.length * secondMatrix[0].length) / numberOfThreads;     //number of elements for each task to compute
        int nrOfExtraElems = (firstMatrix.length * secondMatrix[0].length) % numberOfThreads;     //number of extra elements left for last task if applicable

        for (int k = 0; k < numberOfThreads; k++) {
            int startPosition = k * nrOfElemsToExecute;      //position number of the first element to compute in the current task
            if (k + 1 == numberOfThreads && nrOfExtraElems != 0)     //add leftover elements to last task
                nrOfElemsToExecute += nrOfExtraElems;
            if (taskNumber == 1)
                runnables.add(new TaskRow( firstMatrix, secondMatrix, resultMatrix,startPosition, nrOfElemsToExecute));
            else if (taskNumber == 2)
                runnables.add(new TaskColumn( firstMatrix, secondMatrix, resultMatrix,startPosition, nrOfElemsToExecute ));
            else
                //k - position number of the first element to compute in the current task
                //numberOfThreads - number of elements to step over to get to next element
                runnables.add(new TheKthTask(firstMatrix, secondMatrix, resultMatrix, k, numberOfThreads));
        }

        if (approach == 1) {    //actual thread
            List<Thread> threads = new ArrayList<>();
            for (Runnable runnable : runnables)   //each task wrapped in a thread
                threads.add(new Thread(runnable));

            for (Thread thread : threads)
                thread.start();

            for (Thread thread : threads) //main thread waits for the others to complete their execution
                thread.join();
        } else {    //thread pool
            ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
            for (Runnable runnable : runnables)
                executorService.submit(runnable);
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Rows first matrix:");
        int rows = scanner.nextInt();

        System.out.println("\nColumns first matrix/ Rows second matrix:");
        int columns_rows = scanner.nextInt();

        System.out.println("\nColumns second matrix:");
        int columns = scanner.nextInt();
        int[][] matrix1 = new int[rows][columns_rows];
        int[][] matrix2 = new int[columns_rows][columns];
        int[][] result = new int[rows][columns];
        initializeMatrix(matrix1);
        initializeMatrix(matrix2);

        System.out.println("\nTask (1 - row, 2 - column, 3 - kth):");
        int task = scanner.nextInt();

        System.out.println("\nApproach (1 - actual thread, 2 - thread pool):");
        int approach = scanner.nextInt();

        System.out.println("\nNumber of threads:");
        int numberOfThreads = scanner.nextInt();

        long startTime = System.currentTimeMillis();
        System.out.println();
        executeTasks(task, approach, numberOfThreads, matrix1, matrix2, result);
        long endTime = System.currentTimeMillis();
        System.out.println("-- Time consumed: " + (endTime - startTime) / 1000.0 + " seconds --");

        System.out.println("\nFirst Matrix:");
        //displayMatrix(matrix1);
        System.out.println("\nSecond Matrix:");
        //displayMatrix(matrix2);
        System.out.println("\nResult Matrix:");
        //displayMatrix(result);
    }
}