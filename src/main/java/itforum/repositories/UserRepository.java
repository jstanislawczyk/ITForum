package itforum.repositories;

import java.util.List;

import itforum.entities.User;

public interface UserRepository{
	 User getUserById(Long id);
	 User getUserByNick(String nick);
	 User getUserByEmail(String email);
	 List<User> getAllActiveUsers();
	 boolean isNickAvailable(String nick);
	 boolean isEmailAvailable(String email);
	 User saveUser(User user);
	 User update(User user);
	 void disableUser(User user);
	 void disableUserByNick(String nick);
	 Long countUsers();
}
