import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    public int uid;
    public int balance;  //current balance
    public Log log;  //record transactions Send-Receive
    public int initialBalance;
    public Lock mtx;  //ReentrantLock -> only one thread can modify the balance of this account


    public Account(int uid, int balance) {
        this.uid=uid;
        this.initialBalance = balance;
        this.balance = balance;
        this.mtx = new ReentrantLock();
        this.log = new Log();
    }

    public boolean makeTransfer(Account other, int sum){   //transfer money from one account to another
        if (sum>balance){
            return false;
        }

        //prevent simultaneous modification by multiple threads
        if (this.uid<other.uid){
            this.mtx.lock();
            other.mtx.lock();
        }
        else {
            other.mtx.lock();
            this.mtx.lock();
        }

        balance-=sum;   //substract from current account
        other.balance+=sum;  //add to the other account

        long timestamp = System.currentTimeMillis();
        logTransfer(OperationType.SEND,this.uid, other.uid,sum, timestamp);
        other.logTransfer(OperationType.RECEIVE,other.uid, this.uid, sum, timestamp);

        //release the locks
        this.mtx.unlock();
        other.mtx.unlock();

        return true;   //successful transfer
    }


    public void logTransfer(OperationType type, int src, int dest, int sum, long timestamp){
        log.log(type, sum, src, dest, timestamp);
    }

    public boolean check() {   //balance consistent
        int initBalance = this.initialBalance;
        for (Operation operation: this.log.operations){
            if (operation.type==OperationType.SEND)
                initBalance-=operation.amount;
            else
                initBalance+=operation.amount;
        }
        return initBalance==this.balance;
    }
}
