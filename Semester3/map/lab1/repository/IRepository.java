package repository;

import exceptions.Exceptions;
import model.Deposit;

public interface IRepository {
    Deposit[] getAllDeposits();
    int getLengthOfArray();
    boolean removeDeposit(Deposit deposit);
    void addDeposit(Deposit deposit) throws Exceptions;
}
