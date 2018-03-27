package repository.user;


import model.Role;
import model.User;
import model.validation.Notification;
import java.util.List;

/**
 * Created by Alex on 11/03/2017.
 */
public interface UserRepository {
	
	List<User> findAll();
    
    List<User> findAllWithRole(String roleName);

    Notification<User> findByUsernameAndPassword(String username, String password) throws AuthenticationException;;
    
    boolean save(User user);
    
    boolean update(User user);
    
    boolean delete(Long userId);

    void removeAll();

}