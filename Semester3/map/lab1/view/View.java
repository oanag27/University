package view;

import controller.Controller;
import exceptions.Exceptions;
import model.Deposit;
import model.Flour;
import model.Salt;
import model.Sugar;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class View {
    Controller controller;

    public View(Controller contr) {
        this.controller = contr;
    }

    public void printMenu() {
        System.out.println("0-exit");
        System.out.println("1-add");
        System.out.println("2-print all");
        System.out.println("3-filter");
        System.out.println("4-remove");
    }

    public void run() {
        controller.addDeposit(new Flour(220));
        controller.addDeposit(new Salt(120));
        controller.addDeposit(new Sugar(122));
        do {
            printMenu();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Option: ");
            String option = scanner.nextLine();
            switch (option) {
                case "0":
                    break;
                case "1":
                    Scanner depositScanner = new Scanner(System.in);
                    System.out.println("Enter deposit type (Flour, Salt, Sugar): ");
                    String depositType = depositScanner.nextLine();
                    try {
                        System.out.println("Enter the price for " + depositType + ": ");
                        float depositPrice = depositScanner.nextFloat();

                        Deposit newDeposit;
                        if (depositType.equalsIgnoreCase("Flour")) {
                            newDeposit = new Flour(depositPrice);
                        } else if (depositType.equalsIgnoreCase("Salt")) {
                            newDeposit = new Salt(depositPrice);
                        } else if (depositType.equalsIgnoreCase("Sugar")) {
                            newDeposit = new Sugar(depositPrice);
                        } else {
                            System.out.println("Invalid deposit type.");
                            break;
                        }
                        controller.addDeposit(newDeposit);
                        System.out.println("Deposit added successfully.");
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        depositScanner.next();
                    }
                    break;
                case "2":
                    System.out.println("Deposits are: ");
                    for (Deposit deposits : controller.getAll())
                        System.out.println(deposits.toString());
                    break;
                case "3":
                    float price;
                    try {
                        System.out.println("Provide price for filtering: ");
                        price = scanner.nextFloat();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.next();
                        continue; // Skip the rest of the loop and start over
                    }
                    List<Deposit> filteredDeposits = controller.filter(price);
                    for (Deposit depo : filteredDeposits)
                        System.out.println(depo.toString());
                    break;
                case "4":
                    System.out.println("Choose what do you want to remove from the deposit:(Flour, Salt, Sugar)");
                    String item = scanner.next();
                    if (item.equals("Flour") || item.equals("Salt") || item.equals("Sugar")) {
                        try {
                            System.out.println("Provide the price for " + item + ": ");
                            float priceItem = scanner.nextFloat();
                            boolean removed;
                            if (item.equals("Flour")) {
                                removed = controller.removeDeposit(new Flour(priceItem));
                            } else if (item.equals("Salt")) {
                                removed = controller.removeDeposit(new Salt(priceItem));
                            } else {
                                removed = controller.removeDeposit(new Sugar(priceItem));
                            }
                            if (removed) {
                                System.out.println(item + " deposit removed successfully.");
                            } else {
                                System.out.println("No matching " + item.toLowerCase() + " deposit found.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                            scanner.next();
                        }
                    } else {
                        System.out.println("Invalid option. Please choose 'Flour', 'Salt', or 'Sugar'.");
                    }
                    break;
            }
        } while (true);
    }
}