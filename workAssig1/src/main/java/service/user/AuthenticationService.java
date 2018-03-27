package service.user;

import model.User;
import model.validation.Notification;
import repository.user.AuthenticationException;


public interface AuthenticationService {

    Notification<Boolean> register(String username, String password,  String roleName);

    Notification<User> login(String username, String password) throws AuthenticationException;
    
    Notification<Boolean> updateUserAccount(String username, String password, Long userId) ;

    boolean logout(User user);

}