package itforum.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import itforum.repositories.ForumPostRepository;

@Repository
@Transactional
public class ForumPostDao implements ForumPostRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countPosts() {
		Long numberOfPosts = (Long) entityManager.createQuery("SELECT COUNT(*) from ForumPost p").getSingleResult();
		return numberOfPosts;
	}
}
