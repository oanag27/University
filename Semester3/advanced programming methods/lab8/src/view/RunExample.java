package view;

import controller.Controller;
import model.exception.MyException;

public class RunExample extends Command {
    private Controller ctr;
    public RunExample(String key, String desc,Controller ctr){
        super(key, desc);
        this.ctr=ctr;
    }
    @Override
    public void execute() {
        try{
            ctr.allStep(); }
         catch (MyException e) {
             System.out.println(e.getMessage());
         }
    }
}
