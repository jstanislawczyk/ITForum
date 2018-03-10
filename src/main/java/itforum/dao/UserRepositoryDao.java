package itforum.dao;

import java.sql.Timestamp;

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
	public User saveUser(User user) {
		return entityManager.merge(user);	
	}
	
	public void disableUser(User user) {
		entityManager.createNativeQuery(
						"UPDATE user SET "+
						"date='"+new Timestamp(System.currentTimeMillis())+"', "+
						"email='deleted@deleted.com', "+
						"enabled=0, "+
						"password='-------------', "+
						"points=0, "+
						"role='DELETED' "+
						"WHERE id=:id")
					.setParameter("id", user.getId())
					.executeUpdate();
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

	@Override
	public User update(User user) {
		return entityManager.merge(user);
	}
}