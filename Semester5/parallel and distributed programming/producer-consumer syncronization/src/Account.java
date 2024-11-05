import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
public class Account {
    private final ReentrantLock mutexForAccountOp = new ReentrantLock(); // thread safety
    private int accountId;
    private int balanceInitially;
    private int balanceCurrently;
    private List<Transaction> transactionLogs;
    public Account(int accountId, int balanceInitially) {
        this.transactionLogs = new ArrayList<>();
        this.accountId = accountId;
        this.balanceInitially = balanceInitially;
        this.balanceCurrently = balanceInitially;
    }
    public ReentrantLock getMutexForAccountOperations() {
        return mutexForAccountOp;
    }
    public List<Transaction> getTransactionLogList() {
        return transactionLogs;
    }
    public int getAccountId() {
        return accountId;
    }
    public int getBalanceInitialState() {
        return balanceInitially;
    }
    public int getBalanceCurrentState() {
        return balanceCurrently;
    }
    public void setBalanceCurrentState(int balanceCurrently) {
        this.balanceCurrently = balanceCurrently;
    }
    @Override
    public String toString() {
        return "AccountId:" + accountId + " balance in the initial state: " + balanceInitially + " balance in the initial state: " + balanceCurrently + " transaction logs " + transactionLogs + "\n";
    }
}
