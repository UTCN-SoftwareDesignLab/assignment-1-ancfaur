package factory;
import view.*;

public class ViewFactory {

	private static ViewFactory instance;
	private AdministatorView administrationView;
	private EmployeeView employeeView;
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
		administrationView = new AdministatorView();
		employeeView = new EmployeeView();
		loginView = new LoginView();
		transferView = new TransferView();
		billView = new BillView();
		
	}

	public AdministatorView getAdministrationView() {
		return administrationView;
	}

	public EmployeeView getEmployeeView() {
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
