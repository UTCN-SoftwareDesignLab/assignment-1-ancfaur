package service.user;

import model.Role;
import model.User;
import model.builders.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import model.validation.Validator;
import repository.security.RightsRolesRepository;
import repository.user.AuthenticationException;
import repository.user.UserRepository;

import java.security.MessageDigest;
import java.util.Collections;

public class AuthenticationServiceMySQL implements AuthenticationService {

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public AuthenticationServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

  
    @Override
    public Notification<User> login(String username, String password) throws AuthenticationException {
        return userRepository.findByUsernameAndPassword(username, encodePassword(password));
    }

    @Override
    public boolean logout(User user) {
        return false;
    }

    private String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Notification<Boolean> register(String username, String password, String roleName) {
        Role role = rightsRolesRepository.findRoleByTitle(roleName);
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(role))
                .build();

        Validator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        if (!userValid) {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        } else {
            user.setPassword(encodePassword(password));
            userRegisterNotification.setResult(userRepository.save(user));
        }
        return userRegisterNotification;
    }

	@Override
	public Notification<Boolean>  updateUserAccount(String username, String password, Long userId) {
	        User user = new UserBuilder()
	        		.setId(userId)
	                .setUsername(username)
	                .setPassword(password)
	                .build();

	        Validator userValidator = new UserValidator(user);
	        boolean userValid = userValidator.validate();
	        Notification<Boolean> userRegisterNotification = new Notification<>();
	        if (!userValid) {
	            userValidator.getErrors().forEach(userRegisterNotification::addError);
	            userRegisterNotification.setResult(Boolean.FALSE);
	        } else {
	            user.setPassword(encodePassword(password));
	            userRegisterNotification.setResult(userRepository.update(user));
	        }
	        return userRegisterNotification;
	}
}