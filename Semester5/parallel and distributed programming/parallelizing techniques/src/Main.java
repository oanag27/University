import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static Polynomial createRandomPolynomial(int degree) {
        Random random = new Random();
        List<Integer> coefficients = new ArrayList<>();
        for (int i = 0; i <= degree; i++) {
            coefficients.add(random.nextInt(10));
        }
        return new Polynomial(coefficients);
    }

    private static void menu() {
        System.out.println("1. Perform Sequential Multiplication");
        System.out.println("2. Perform Parallel Multiplication");
        System.out.println("3. Use Sequential Karatsuba Method");
        System.out.println("4. Use Parallel Karatsuba Method");
        System.out.println("5. Exit!");
        System.out.print("Select an option: ");
    }

    private static void printResults(double timeElapsed, Polynomial polynomial1, Polynomial polynomial2, Polynomial result) {
        System.out.println("First Polynomial: " + polynomial1);
        System.out.println("Second Polynomial: " + polynomial2);
        System.out.println("Result: " + result);
        System.out.println("Execution Time: " + timeElapsed + " seconds");
    }


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        PolynomialMultiplication polynomialMultiplicationHandler = new PolynomialMultiplication();
        Scanner input = new Scanner(System.in);
        int polynomialDegree = new Random().nextInt(10000) + 1;
        Polynomial polynomial1 = createRandomPolynomial(polynomialDegree);
        Polynomial polynomial2 = createRandomPolynomial(polynomialDegree);

        // Loop to keep showing the menu until the user exits
        while (true) {
            menu();
            int choice = input.nextInt();  // Get the user's choice
            Polynomial result;  // Variable to hold the result of the multiplication

            if (choice == 1) {
                // Perform sequential multiplication
                long startTime = System.nanoTime();
                result = polynomialMultiplicationHandler.multiplySequentially(polynomial1, polynomial2);
                long endTime = System.nanoTime();
                double timeElapsed = (endTime - startTime) / 1_000_000_000.0;
                printResults(timeElapsed, polynomial1, polynomial2, result);
            } else if (choice == 2) {
                // Perform parallel multiplication
                long startTime = System.nanoTime();
                result = polynomialMultiplicationHandler.multiplyInParallel(polynomial1, polynomial2);
                long endTime = System.nanoTime();
                double timeElapsed = (endTime - startTime) / 1_000_000_000.0;
                printResults(timeElapsed, polynomial1, polynomial2, result);
            } else if (choice == 3) {
                // Perform sequential Karatsuba multiplication
                long startTime = System.nanoTime();
                result = polynomialMultiplicationHandler.KaratsubaSequential(polynomial1, polynomial2);
                long endTime = System.nanoTime();
                double timeElapsed = (endTime - startTime) / 1_000_000_000.0;
                printResults(timeElapsed, polynomial1, polynomial2, result);
            } else if (choice == 4) {
                // Perform parallel Karatsuba multiplication
                long startTime = System.nanoTime();
                result = polynomialMultiplicationHandler.KaratsubaParallel(polynomial1, polynomial2, 1);
                long endTime = System.nanoTime();
                double timeElapsed = (endTime - startTime) / 1_000_000_000.0;
                printResults(timeElapsed, polynomial1, polynomial2, result);
            } else if (choice == 5) {
                // Exit the program
                break;
            } else {
                // Handle invalid option
                System.out.println("Invalid option.");
                continue;
            }
        }

        input.close();  // Close the scanner
    }
}



//    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        PolynomialMultiplication polynomialMultiplicationHandler = new PolynomialMultiplication();
//        Scanner input = new Scanner(System.in);
//        while (true) {
//            menu();
//            int choice = input.nextInt();
//            int polynomialDegree = new Random().nextInt(10) + 1;
//            Polynomial polynomial1 = createRandomPolynomial(polynomialDegree), polynomial2 = createRandomPolynomial(polynomialDegree), result;
//            long startTime = System.nanoTime(); //currentTimeMillis();
//            if (choice == 1) {
//                result = polynomialMultiplicationHandler.multiplySequentially(polynomial1, polynomial2);
//            } else if (choice == 2) {
//                result = polynomialMultiplicationHandler.multiplyInParallel(polynomial1, polynomial2);
//            } else if (choice == 3) {
//                result = polynomialMultiplicationHandler.KaratsubaSequential(polynomial1, polynomial2);
//            } else if (choice == 4) {
//                result = polynomialMultiplicationHandler.KaratsubaParallel(polynomial1, polynomial2, 1);
//            } else if (choice == 5) {
//                break;
//            } else {
//                System.out.println("Invalid option.");
//                continue;
//            }
//            long endTime = System.nanoTime();  //currentTimeMillis();
//            double timeElapsed = (endTime - startTime) / 1_000_000_000.0;
//            printResults(timeElapsed, polynomial1, polynomial2, result);
//        }
//        input.close();
//    }


















