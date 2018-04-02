package controller;

import java.util.Date;

public interface IBillTransferController extends IController{

	void cleanView();
	void setAccountId(Long id);
	void setClientId(Long id);
	void setDate(Date date);
	void setUserId(Long id);
	
}
