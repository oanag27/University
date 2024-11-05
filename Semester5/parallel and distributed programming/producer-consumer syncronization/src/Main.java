import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
public class Main {
    private static volatile boolean isThreadActive = true;
    private static volatile int currentNumberOfTransactions = 0;
    public static void main(String[] args) {
        List<Account> listOfAccounts = IntStream.rangeClosed(1, 8)
                .mapToObj(i -> new Account(i, 299))
                .collect(Collectors.toList());
        Bank bank = new Bank(listOfAccounts);
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(5);

            Runnable performMoneyTransferTask = () -> {
                while (currentNumberOfTransactions < 10) {
                    Random rand = new Random();
                    int dispatcherId, acceptorId;
                    dispatcherId = rand.nextInt(8);
                    acceptorId = rand.nextInt(8);
                    if(dispatcherId==0)
                        dispatcherId = 1;
                    if(acceptorId==0)
                        acceptorId = 1;
                    while(dispatcherId == acceptorId)
                    {
                        dispatcherId = rand.nextInt(8);
                        acceptorId = rand.nextInt(8);
                    }
                    int minimumAmountToTransfer = 5, maximumAmountToTransfer = 200;
                    int randomAmount = rand.nextInt(maximumAmountToTransfer - minimumAmountToTransfer + 1) + minimumAmountToTransfer;
                    try {
                        int transactionId = bank.transferOperation(dispatcherId, acceptorId, randomAmount);
                        if (transactionId != -1) {
                            Transaction newTransaction = new Transaction(transactionId, dispatcherId, acceptorId, randomAmount);
                            System.out.println("Transaction:" + newTransaction.toString());
                            currentNumberOfTransactions +=1;
                        }
                    } catch (Exception e) {
                        System.out.println("Error!!");
                    }
                }
                isThreadActive = false; //done thread
            };
            Runnable taskChecker = () -> {
                while (isThreadActive) {
                    try {
                        Thread.sleep(2000);
                        if (bank.checkValidLog() && bank.consistencyCheck())
                            System.out.println("\nChecks passed!\n");
                        else
                            System.out.println("\nChecks failed!\n");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            Instant start= Instant.now();
            executorService.submit(performMoneyTransferTask);
            executorService.submit(taskChecker);
            executorService.shutdown(); //initiates termination
            executorService.awaitTermination(1, TimeUnit.MINUTES);  //wait for all tasks to be completed
            Instant end = Instant.now();
            long timeConsumed = Duration.between(start, end).toMillis();
            System.out.println("Duration: " + timeConsumed / 1000.0 + " seconds");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bank.checkValidLog() && bank.consistencyCheck())
                System.out.println("\nChecks passed!\n");
            else
                System.out.println("\nChecks failed!\n");
            for (Account a : bank.getAccountList()) {
                System.out.println(a);
            }
        }
    }
}