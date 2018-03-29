package repository.user;

import model.Client;
import model.Role;
import model.User;
import model.builders.ClientBuilder;
import model.builders.UserBuilder;
import model.validation.Notification;
import repository.security.RightsRolesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static database.Constants.Tables.USER;

/**
 * Created by Alex on 11/03/2017.
 */
public class UserRepositoryMySQL implements UserRepository {

	private final Connection connection;
	private final RightsRolesRepository rightsRolesRepository;

	public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
		this.connection = connection;
		this.rightsRolesRepository = rightsRolesRepository;
	}

	@Override
	public Notification<User> findByUsernameAndPassword(String username, String password)
			throws AuthenticationException {
		Notification<User> findByUsernameAndPasswordNotification = new Notification<>();
		try {
			Statement statement = connection.createStatement();
			String fetchUserSql = "Select * from `" + USER + "` where `username`=\'" + username + "\' and `password`=\'"
					+ password + "\'";
			ResultSet userResultSet = statement.executeQuery(fetchUserSql);
			if (userResultSet.next()) {
				User user = new UserBuilder()
						.setId(userResultSet.getLong("id"))
						.setUsername(userResultSet.getString("username"))
						.setPassword(userResultSet.getString("password"))
						.setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id"))).build();
				findByUsernameAndPasswordNotification.setResult(user);
				return findByUsernameAndPasswordNotification;
			} else {
				findByUsernameAndPasswordNotification.addError("Invalid email or password!");
				return findByUsernameAndPasswordNotification;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AuthenticationException();
		}
	}

	public List<User> findAll() {
		List<User> users = new ArrayList<>();
		try {
			Statement statement = connection.createStatement();
			String fetchUserSql = "Select * from user";
			ResultSet rs = statement.executeQuery(fetchUserSql);

			while (rs.next()) {
				User user = getUserFromResultSet(rs);
				users.add(user);
			}
			return users;
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public boolean save(User user) {
		try {
			PreparedStatement insertUserStatement = connection.prepareStatement("INSERT INTO user values (null, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			insertUserStatement.setString(1, user.getUsername());
			insertUserStatement.setString(2, user.getPassword());
			insertUserStatement.executeUpdate();

			ResultSet rs = insertUserStatement.getGeneratedKeys();
			rs.next();
			long userId = rs.getLong(1);
			user.setId(userId);

			System.out.println("save user!");
			rightsRolesRepository.addRolesToUser(user, user.getRoles());

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean update(User user) {
		try {
			PreparedStatement updateUserStatement = connection
					.prepareStatement("UPDATE user  SET username=?, password=? WHERE id=?;");
			updateUserStatement.setString(1, user.getUsername());
			updateUserStatement.setString(2, user.getPassword());
			updateUserStatement.setLong(3, user.getId());
			updateUserStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean deleteUser(Long userId) {
		try {
			PreparedStatement deleteUserStatement = connection.prepareStatement("DELETE FROM user WHERE id = ?");
			deleteUserStatement.setLong(1, userId);
			deleteUserStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean deleteUserRoleRelation(Long userId) {
		try {
			PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM user_role WHERE id = ?");
			deleteStatement.setLong(1, userId);
			deleteStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}

	public boolean delete(Long userId) {
		return deleteUserRoleRelation(userId)&& deleteUser(userId);
	}
	
	@Override
	public void removeAll() {
		try {
			Statement statement = connection.createStatement();
			String sql = "DELETE from user where id >= 0";
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<User> findAllWithRole(String roleName) {
		List<User> allUsers = findAll();
		List<User> selectedUsers = new ArrayList<>();
		for (User user : allUsers) {
			List<Role> userRoles = user.getRoles();
			for (Role role : userRoles) {
				if (role.getRole().equals(roleName))
					selectedUsers.add(user);
			}
		}
		return selectedUsers;
	}

	private User getUserFromResultSet(ResultSet rs) throws SQLException {
		Long userId = rs.getLong("id");
		return new UserBuilder().setId(userId).setUsername(rs.getString("username"))
				.setPassword(rs.getString("password")).setRoles(rightsRolesRepository.findRolesForUser(userId)).build();
	}

}