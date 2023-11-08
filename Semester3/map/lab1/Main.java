
//exception daca depasim limita array
//exception dati string de la tastatura, in loc de int

//Intr-un depozit de alimente exista sare,
//zahar si faina. Sa se afiseze produsele
//cu pretul mai mare decat 20Ron/kg


import controller.Controller;
import repository.IRepository;
import repository.Repository;
import view.View;

public class Main {
    public static void main(String[] args) {
        IRepository repo=new Repository();
        Controller controller=new Controller(repo);
        View view=new View(controller);
        view.run();
    }
}