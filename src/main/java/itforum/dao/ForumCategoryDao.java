package itforum.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import itforum.entities.ForumCategory;
import itforum.repositories.ForumCategoryRepository;

@Repository
@Transactional
public class ForumCategoryDao implements ForumCategoryRepository{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void saveForumCategory(ForumCategory forumCategory) {
		entityManager.merge(forumCategory);	
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ForumCategory> findAllCategories() {
		List<ForumCategory> categories = entityManager.createQuery("SELECT fc FROM ForumCategory fc").getResultList();
		return categories;
	}
}
