import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    public List<Account> accounts;
    private static final int NO_THREADS = 10;
    private static final int NO_ACCOUNTS = 100;
    private static final long NO_OPERATIONS = 50000;
    private static final long OPERATIONS_PER_THREAD = NO_OPERATIONS/NO_THREADS;

    public Lock mtx = new ReentrantLock();

    private boolean check = false;

    public Bank() {
        accounts = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Bank{" +
                "accounts=" + accounts +
                '}';
    }

    public void run() {
        createAccounts();

        float start = System.nanoTime() / 1000000;


        List<Thread> threads = new ArrayList<>();
        for (int i=0;i<NO_THREADS;i++){
            int finalI = i;
            threads.add(new Thread(() -> {
                Random r = new Random();
                for(long j = 0; j <OPERATIONS_PER_THREAD; ++j){
                    int accId = r.nextInt(NO_ACCOUNTS);
                    int accId2 = r.nextInt(NO_ACCOUNTS);
                    if (accId == accId2){
                        --j;
                        continue;
                    }

                    int sum = r.nextInt(25);
                    accounts.get(accId).makeTransfer(accounts.get(accId2),sum);

                    //System.out.println("[Thread " + finalI + "]:" + sum + "$ were transferred from acc " + accId + " to acc " + accId2);
                }
            }));
        }


        threads.forEach(Thread::start);

        Thread checker = new Thread(()->{
            mtx.lock();
            while (!check){
                mtx.unlock();
                Random r = new Random();
                if (r.nextInt(9)==0){
                    runCorrectnessCheck();
                }
                mtx.lock();
            }
            mtx.unlock();
        });

        checker.start();
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        mtx.lock();
        check = true;
        mtx.unlock();
        try {
            checker.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        runCorrectnessCheck();

        float end = System.nanoTime() / 1000000;

        System.out.println("Time elapsed: " + (end-start)/1000 + " seconds");

    }

    private void createAccounts(){
        int uid = 0;
        for (int i=0;i<NO_ACCOUNTS;++i){
            accounts.add(new Account(uid++,200));
        }
    }

    private void runCorrectnessCheck() {
        System.out.println("Started checking logs");
        AtomicInteger failedAccounts = new AtomicInteger();
        accounts.forEach(account -> {
            account.mtx.lock();// check the balance of each account
            if (!account.check()){
                failedAccounts.getAndIncrement();
            }
            account.mtx.unlock();
        });

        for (Account account : accounts) { // check that for each operation in each account there is a "symmetric" operation in the destination account of the operation
            account.mtx.lock();
            for (Operation op : account.log.operations) {
                Account targetAccount = accounts.get(op.dest);
                if (!targetAccount.log.operations.contains(new Operation(OperationType.RECEIVE, op.dest, op.src, op.amount, op.timestamp))) {
                    failedAccounts.getAndIncrement();
                }
            }
            account.mtx.unlock();
        }

        if (failedAccounts.get() >0){
            throw new RuntimeException("Accounts are no longer correct and consistent");
        }
        System.out.println("Ended checking logs");
    }
}
