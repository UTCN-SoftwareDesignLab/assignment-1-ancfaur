import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import factory.ControllerFactory;

public class Launcher {

    public static void main(String[] args) {
      ControllerFactory controllerFactory = ControllerFactory.instance(false);
      controllerFactory.getLoginController().setVisible(true);
    
    }
}