import mpi.MPI;
import mpi.MPIException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main {
    private static Polynomial generateRandomPolynomial(int degree) {
        Random random = new Random();
        List<Integer> coefficients = new ArrayList<>();
        for (int i = 0; i <= degree; i++)
            coefficients.add(random.nextInt(1000));
        return new Polynomial(coefficients);
    }
    private static void runMaster(Polynomial firstPolynomial, Polynomial secondPolynomial, int size) throws MPIException {
        long startTime = System.nanoTime();
        // the length of each segment to be calculated by a worker process
        int length = firstPolynomial.getLength() / (size - 1);
        int start = 0;
        int end = length;

        // distribute tasks to worker processes
        for (int i = 1; i < size; i++) {
            // send the segment bound data to each worker process
            MPI.COMM_WORLD.Send(new int[]{start}, 0, 1, MPI.INT, i, 0);
            MPI.COMM_WORLD.Send(new int[]{end}, 0, 1, MPI.INT, i, 0);
            // send the polynomial data to each worker process
            MPI.COMM_WORLD.Send(new Object[]{firstPolynomial}, 0, 1, MPI.OBJECT, i, 0);
            MPI.COMM_WORLD.Send(new Object[]{secondPolynomial}, 0, 1, MPI.OBJECT, i, 0);
            start += length;
            end += length;
            if (i + 1 == size - 1)
                end = firstPolynomial.getLength();
        }
        // variable to store the results
        Object[] results = new Object[size - 1];
        // receive the results from the worker processes
        for (int i = 1; i < size; i++)
            MPI.COMM_WORLD.Recv(results, i - 1, 1, MPI.OBJECT, i, 0);
        // combine the results from the worker processes
        Polynomial result = Multiplication.combineResults(results);
        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1000000000.0;
        System.out.println("Master-> result:" + result + " time:" + duration + " seconds");
    }

    private static void runRegularWorker(int rank) throws MPIException {
        // variables to store the segment bound data
        int[] s = new int[1];
        int[] e = new int[1];
        // variables to store the polynomial data
        Object[] p1 = new Object[1];
        Object[] p2 = new Object[1];

        // receive the segment bound data from the master process
        MPI.COMM_WORLD.Recv(s, 0, 1, MPI.INT, 0, 0);
        MPI.COMM_WORLD.Recv(e, 0, 1, MPI.INT, 0, 0);
        // receive the polynomial data from the master process
        MPI.COMM_WORLD.Recv(p1, 0, 1, MPI.OBJECT, 0, 0);
        MPI.COMM_WORLD.Recv(p2, 0, 1, MPI.OBJECT, 0, 0);

        // extract the segment bounds from the received segment bound data
        int start = s[0];
        int end = e[0];
        // extract the polynomials from the received polynomial data
        Polynomial polynomial1 = (Polynomial) p1[0];
        Polynomial polynomial2 = (Polynomial) p2[0];

        // perform regular multiplication on the assigned segment
        Polynomial result = Multiplication.multiplySequentiallyWithBounds(start, end, polynomial1, polynomial2);

        // send the result back to the master process
        MPI.COMM_WORLD.Send(new Object[]{result}, 0, 1, MPI.OBJECT, 0, 0);

        System.out.println("Worker had interval [" + start + "," + end + ") and coefficients " + result.getCoefficients() + ".");
    }

    private static void runKaratsubaWorker(int rank) throws MPIException {
        // variables to store the segment bound data
        int[] s = new int[1];
        int[] e = new int[1];
        // variables to store the polynomial data
        Object[] p1 = new Object[1];
        Object[] p2 = new Object[1];

        // receive the segment bound data from the master process
        MPI.COMM_WORLD.Recv(s, 0, 1, MPI.INT, 0, 0);
        MPI.COMM_WORLD.Recv(e, 0, 1, MPI.INT, 0, 0);
        // receive the polynomial data from the master process
        MPI.COMM_WORLD.Recv(p1, 0, 1, MPI.OBJECT, 0, 0);
        MPI.COMM_WORLD.Recv(p2, 0, 1, MPI.OBJECT, 0, 0);

        // extract the segment bounds from the received segment bound data
        int start = s[0];
        int end = e[0];
        // extract the polynomials from the received polynomial data
        Polynomial polynomial1 = (Polynomial) p1[0];
        Polynomial polynomial2 = (Polynomial) p2[0];

        // set the coefficients outside the assigned segment to zero
        for (int i = 0; i < start; i++)
            polynomial1.setCoefficient(i, 0);
        for (int j = end; j < polynomial1.getLength(); j++)
            polynomial1.setCoefficient(j, 0);

        // perform Karatsuba multiplication on the assigned segment
        Polynomial result = Multiplication.multiplyKaratsuba(polynomial1, polynomial2);

        // send the result back to the master process
        MPI.COMM_WORLD.Send(new Object[]{result}, 0, 1, MPI.OBJECT, 0, 0);

        System.out.println("Worker had interval [" + start + "," + end + ") and coefficients " + result.getCoefficients() + ".");
    }

    public static void main(String[] args) throws MPIException {
        // initialize the MPI environment
        MPI.Init(args);
        // the unique identifier for the process
        int rank = MPI.COMM_WORLD.Rank();
        // the number of launched processes
        int size = MPI.COMM_WORLD.Size();
        if (rank == 0) {
            // the master process
            Random random = new Random();
            int degree = random.nextInt(10)+1;
            Polynomial firstPolynomial = generateRandomPolynomial(degree);
            Polynomial secondPolynomial = generateRandomPolynomial(degree);
            System.out.println("First Polynomial: " + firstPolynomial);
            System.out.println("Second Polynomial: " + secondPolynomial);
            runMaster(firstPolynomial, secondPolynomial, size);
        } else {
            // the worker processes
            //runRegularWorker(rank);
            runKaratsubaWorker(rank);
        }
        // stop the MPI environment
        MPI.Finalize();
    }
}