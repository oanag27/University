public class Transaction {
    private final int amountMoneyTransferred;
    private final int transactionId; //uniquely identifier for transactions
    private final int dispatcherId;
    private final int acceptorId;
    public Transaction(int transactionId, int dispatcherId, int acceptorId, int amountMoneyTransferred) {
        this.amountMoneyTransferred = amountMoneyTransferred;
        this.transactionId = transactionId;
        this.dispatcherId = dispatcherId;
        this.acceptorId = acceptorId;
    }
    public int getAmountMoneyTransferred() {
        return amountMoneyTransferred;
    }
    public int getDispatcherId() {
        return dispatcherId;
    }
    public int getAcceptorId() {
        return acceptorId;
    }
    @Override
    public String toString() {
        return "Transaction id: " + transactionId + " dispatcher id: " + dispatcherId + " acceptor id: " + acceptorId + " money transferred: " + amountMoneyTransferred + "\n";
    }
}
