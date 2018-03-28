package repository.bill;

import model.Bill;
import repository.EntityNotFoundException;

public interface BillRepository {

	Bill findById(Long id) throws EntityNotFoundException;
	boolean updateStatus(Bill bill);
	boolean save(Bill bill);
    
}
