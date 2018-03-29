package repository.client;

import model.Client;
import model.validation.Notification;
import java.util.List;

public interface ClientRepository {

    List<Client> findAll();

    Client findByCnp(String cnp);
    
    Client findById(Long id);

    boolean save(Client client);

    void removeAll();
    
    boolean update(Client client);
}