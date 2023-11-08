package controller;

import exceptions.Exceptions;
import model.Deposit;
import repository.IRepository;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    IRepository repository;

    public Controller(IRepository _repository) {
        repository = _repository;
    }

    public void addDeposit(Deposit deposit) {
        try {
            repository.addDeposit(deposit);
        }catch(Exceptions exceptions)
        {
            System.out.println("Array has maximum size already!");
        }
    }
    public boolean removeDeposit(Deposit deposit) {
        return repository.removeDeposit(deposit);
    }
    public List<Deposit> getAll() {
        List<Deposit> deposits = new ArrayList<>();
        for (int i = 0; i < repository.getLengthOfArray(); i++) {
            deposits.add(repository.getAllDeposits()[i]);
        }
        return deposits;
    }
    public List<Deposit> filter(float price) {
        List<Deposit> filteredDeposits = new ArrayList<>();
        for (Deposit depo : getAll()) {
            if (depo.getPrice() > price)
                filteredDeposits.add(depo);
        }
        return filteredDeposits;
    }
}
