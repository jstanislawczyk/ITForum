package itforum.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

	@Override
	public ForumCategory findCategoryByTitle(String title) {
		
		try{
			ForumCategory category = entityManager
								.createQuery("SELECT fc FROM ForumCategory fc WHERE fc.title=:title", ForumCategory.class)
								.setParameter("title", title)
								.getSingleResult();
			return category;
		}catch(NoResultException nre){
			return null;
		}
	}

	@Override
	public boolean checkIfCategoryExists(String title) {
		try{
			entityManager
					.createQuery("SELECT fc.title FROM ForumCategory fc WHERE fc.title=:title", String.class)
					.setParameter("title", title)
					.getSingleResult();
			return true;
		}catch(NoResultException nre){
			return false;
		}
	}
}
