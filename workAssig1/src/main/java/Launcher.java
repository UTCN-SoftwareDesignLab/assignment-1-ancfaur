import controller.AdministratorMainController;
import controller.LoginController;
import view.AdministatorMainView;
import view.LoginView;

public class Launcher {

    public static void main(String[] args) {
       ComponentFactory componentFactory = ComponentFactory.instance(false);
       /* new LoginController(new LoginView(), componentFactory.getAuthenticationService());
        System.out.println("BANK APPLICATION");*/
       
       new AdministratorMainController(new AdministatorMainView(), componentFactory.getUserRepository(), componentFactory.getAuthenticationService());
       System.out.println("BANK APPLICATION");
    }

}