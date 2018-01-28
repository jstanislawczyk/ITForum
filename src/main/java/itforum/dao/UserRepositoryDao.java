package itforum.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import itforum.entities.User;
import itforum.repositories.UserRepository;

@Repository
@Transactional
public class UserRepositoryDao implements UserRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public User getUserById(Long id){
		return entityManager.find(User.class, id);
	}

	@Override
	public void saveUser(User user) {
		entityManager.merge(user);	
	}

	@Override
	public User getUserByNick(String nick){	
			try{
				return entityManager.createQuery("SELECT u from User u WHERE u.nick = :nick", User.class)
					.setParameter("nick", nick)
					.getSingleResult();		
			}catch(NoResultException nre){
				return null;
			}
	}

	@Override
	public User getUserByEmail(String email){
		return entityManager.createQuery("SELECT u from User u WHERE u.email = :email", User.class)
				.setParameter("email", email)
				.getSingleResult();
	}

	@Override
	public boolean isNickAvailable(String nick) {
		Long numberOfUsersByNick = (Long) entityManager.createQuery("SELECT COUNT(*) from User u WHERE u.nick = :nick").setParameter("nick", nick).getSingleResult();
		if(numberOfUsersByNick==0){
			return true;
		}else{
			return false;
		}

	}

	@Override
	public boolean isEmailAvailable(String email) {
		Long numberOfUsersByEmail = (Long) entityManager.createQuery("SELECT COUNT(*) from User u WHERE u.email = :email").setParameter("email", email).getSingleResult();
		if(numberOfUsersByEmail==0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Long countUsers() {
		Long numberOfUsers = (Long) entityManager.createQuery("SELECT COUNT(*) from User u").getSingleResult();
		return numberOfUsers;
	}
}