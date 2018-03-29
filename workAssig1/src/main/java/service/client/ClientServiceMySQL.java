package service.client;

import java.util.List;

import model.Client;
import model.builders.ClientBuilder;
import model.validation.Notification;
import repository.client.ClientRepository;
import model.validation.ClientValidator;

public class ClientServiceMySQL implements ClientService {

	private final ClientRepository clientRepository;

	public ClientServiceMySQL(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public Notification<Boolean> register(String cnp, String name, String address) {
		Client client = new ClientBuilder().setCnp(cnp).setName(name).setAddress(address).build();

		ClientValidator clientValidator = new ClientValidator(client);
		boolean clientValid = clientValidator.validate();
		Notification<Boolean> clientRegisterNotification = new Notification<>();

		if (!clientValid) {
			clientValidator.getErrors().forEach(clientRegisterNotification::addError);
			clientRegisterNotification.setResult(Boolean.FALSE);
		} else {
			clientRegisterNotification.setResult(clientRepository.save(client));
		}
		return clientRegisterNotification;
	}

	@Override
	public List<Client> findAll() {
		return clientRepository.findAll();
	}

	@Override
	public Notification<Boolean> updateClient(Long clientId, String cnp, String name, String address) {
		Client client = new ClientBuilder().setId(clientId).setCnp(cnp).setName(name).setAddress(address).build();

		ClientValidator clientValidator = new ClientValidator(client);
		boolean clientValid = clientValidator.validate();
		Notification<Boolean> userRegisterNotification = new Notification<>();
		if (!clientValid) {
			clientValidator.getErrors().forEach(userRegisterNotification::addError);
			userRegisterNotification.setResult(Boolean.FALSE);
		} else {
			userRegisterNotification.setResult(clientRepository.update(client));
		}
		return userRegisterNotification;
	}

	public Client findByCnp(String cnp) {
		return clientRepository.findByCnp(cnp);
	}
}
