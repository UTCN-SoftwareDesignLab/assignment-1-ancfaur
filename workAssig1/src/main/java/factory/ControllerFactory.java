package factory;
import controller.*;

public class ControllerFactory {

	private static ControllerFactory instance;
	private ServiceFactory serviceFactory;
	private ViewFactory viewFactory;
	

	private IController loginController;
	private IController administratorController;
	private IEmployeeController employeeController;
	private  IBillTransferController transferController;
	private  IBillTransferController billController;
	
	
	
	public static ControllerFactory instance(Boolean componentsForTests) {
	        if (instance == null) {
	            instance = new ControllerFactory(componentsForTests);
	        }
	        return instance;
	    }

	private ControllerFactory(Boolean componentsForTests) {
        this.serviceFactory = ServiceFactory.instance(componentsForTests);
        this.viewFactory = ViewFactory.instance();  
        
        
        this.billController = new BillController(viewFactory.getBillView(), serviceFactory.getBillRepository(), serviceFactory.getAccountService(),
    			serviceFactory.getReportRepository());
        
        this.transferController = new TransferController(viewFactory.getTransferView(), serviceFactory.getAccountService(), serviceFactory.getReportRepository());
        
        
        this.employeeController = new EmployeeController(viewFactory.getEmployeeView(), serviceFactory.getClientService(),
        		serviceFactory.getAccountService(), serviceFactory.getReportRepository(), 
        		this.billController, this.transferController);
        
        
        this.administratorController = new AdministratorController(viewFactory.getAdministrationView(),
        		serviceFactory.getUserRepository(), serviceFactory.getAuthenticationService(),
        		serviceFactory.getReportRepository());
        
        
        this.loginController = new LoginController(viewFactory.getLoginView(), serviceFactory.getAuthenticationService(), this.administratorController, 
        		this.employeeController);
        
        
    }

	public IController getLoginController() {
		return loginController;
	}

	
}
