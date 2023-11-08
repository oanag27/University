package repository;

import exceptions.Exceptions;
import model.Deposit;

public class Repository implements IRepository{
    Deposit[] deposits;
    int length;
    private int capacity = 100;
    public Repository(){
        deposits = new Deposit[capacity];
        length = 0;
    }
    @Override
    public Deposit[] getAllDeposits() {
        return deposits;
    }

    @Override
    public int getLengthOfArray() {
        return length;
    }
    @Override
    public void addDeposit(Deposit deposit) throws Exceptions {
        if(length == capacity)
        {
            throw new Exceptions();
        }
        deposits[length++]=deposit;
    }
    @Override
    public boolean removeDeposit(Deposit deposit) {
        for (int index=0; index<length; index++) {
            Deposit depo = deposits[index];
            if (depo.getPrice() == deposit.getPrice()){
                deposits[index] = deposits[length-1];
                deposits[length-1] = null;
                length-=1;
                return true;
            }
        }
        return false;
    }
}
