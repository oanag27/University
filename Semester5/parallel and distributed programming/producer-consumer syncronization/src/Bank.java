import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    private final ReentrantLock mutexTrackingNumber = new ReentrantLock(); //only one thread can increment the counter
    private final List<Account> accountList;
    private int counter;
    public Bank(List<Account> accountList) {
        this.accountList = accountList;
        this.counter = 1;
    }
    public List<Account> getAccountList() {
        return accountList;
    }
    public Account getDispatcherByDispatcherId(int did) {
        for (Account account : accountList) {
            if (account.getAccountId() == did) {
                return account;
            }
        }
        return null;
    }
    public Account getAcceptorByAcceptorId(int aid) {
        for (Account account : accountList) {
            if (account.getAccountId() == aid) {
                return account;
            }
        }
        return null;
    }
    public boolean checkValidLog() {
        List<Integer> dispatcherListOfIds = new ArrayList<>();
        List<Integer> acceptorListOfIds = new ArrayList<>();
        for (Account account : accountList) {
            account.getMutexForAccountOperations().lock();
            for (Transaction t : account.getTransactionLogList()) {
                if (t.getDispatcherId() == account.getAccountId())
                    dispatcherListOfIds.add(t.getDispatcherId());
                if (t.getAcceptorId() == account.getAccountId())
                    acceptorListOfIds.add(t.getDispatcherId());
            }
            account.getMutexForAccountOperations().unlock();
        }
        if (!acceptorListOfIds.containsAll(dispatcherListOfIds)) {
            return false;
        }
        return true;
    }
    private int transactionsBalance(Account account) {
        int balance = account.getBalanceInitialState();
        for (Transaction transaction : account.getTransactionLogList()) {
            if (transaction.getDispatcherId() == account.getAccountId()) {
                balance -= transaction.getAmountMoneyTransferred();
            } else if (transaction.getAcceptorId() == account.getAccountId()) {
                balance += transaction.getAmountMoneyTransferred();
            }
        }
        return balance;
    }
    public boolean consistencyCheck() {
        for (Account account : accountList) {
            account.getMutexForAccountOperations().lock();
            int balance = transactionsBalance(account);
            if (balance != account.getBalanceCurrentState())
                return false;
            account.getMutexForAccountOperations().unlock();
        }
        return true;
    }
    public int transferOperation(int dispatcherId, int acceptorId, int amountMoneyTransferred) throws InterruptedException {
        Account dispatcher = getDispatcherByDispatcherId(dispatcherId);
        Account acceptor = getAcceptorByAcceptorId(acceptorId);
        if(dispatcher == null || acceptor ==null )
        {
            return -1;
        }
        if (dispatcher.getBalanceCurrentState() >= amountMoneyTransferred) {
            dispatcher.getMutexForAccountOperations().lock();
            acceptor.getMutexForAccountOperations().lock();
            mutexTrackingNumber.lock();
            int transactionId = counter++;
            mutexTrackingNumber.unlock();
            dispatcher.setBalanceCurrentState(dispatcher.getBalanceCurrentState() - amountMoneyTransferred);
            acceptor.setBalanceCurrentState(acceptor.getBalanceCurrentState() + amountMoneyTransferred);
            Transaction transaction = new Transaction(transactionId, dispatcherId, acceptorId, amountMoneyTransferred);
            dispatcher.getTransactionLogList().add(transaction);
            acceptor.getTransactionLogList().add(transaction);
            Thread.sleep(2000);  //delay
            dispatcher.getMutexForAccountOperations().unlock();
            acceptor.getMutexForAccountOperations().unlock();
            return transactionId;
        }
        return -1;
    }
}
