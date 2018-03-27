package service.client;

import java.util.List;

import model.Client;
import model.validation.Notification;

public interface ClientService {

	 Notification<Boolean> register(String cnp, String name,  String address);
	 
	 List<Client> findAll();
	 
	 Notification<Boolean>  updateClient(Long clientId, String cnp, String name, String address);
}
