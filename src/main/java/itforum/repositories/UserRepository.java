package itforum.repositories;

import itforum.entities.User;

public interface UserRepository{
	 User getUserById(Long id);
	 User getUserByNick(String nick);
	 User getUserByEmail(String email);
	 boolean isNickAvailable(String nick);
	 boolean isEmailAvailable(String email);
	 void saveUser(User user);
	 User update(User user);
	 Long countUsers();
}
