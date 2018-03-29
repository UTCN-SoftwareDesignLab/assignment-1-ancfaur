package factory;
import view.*;

public class ViewFactory {

	private static ViewFactory instance;
	private AdministatorMainView administrationView;
	private EmployeeMainView employeeView;
	private LoginView loginView;
	private TransferView transferView;
	private BillView billView;
	
	public static ViewFactory instance() {
        if (instance == null) {
            instance = new ViewFactory();
        }
        return instance;
    }
	
	private ViewFactory() {
		administrationView = new AdministatorMainView();
		employeeView = new EmployeeMainView();
		loginView = new LoginView();
		transferView = new TransferView();
		billView = new BillView();
		
	}

	public AdministatorMainView getAdministrationView() {
		return administrationView;
	}

	public EmployeeMainView getEmployeeView() {
		return employeeView;
	}

	public LoginView getLoginView() {
		return loginView;
	}

	public TransferView getTransferView() {
		return transferView;
	}

	public BillView getBillView() {
		return billView;
	}
	
	
	
}
