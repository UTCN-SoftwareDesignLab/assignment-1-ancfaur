import factory.ControllerFactory;

public class Launcher {

    public static void main(String[] args) {
      ControllerFactory controllerFactory = ControllerFactory.instance(false);
      controllerFactory.getLoginController().setVisible(true);
    
    }
}