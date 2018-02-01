package itforum.dao;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import itforum.entities.ForumPost;
import itforum.repositories.ForumPostRepository;

@Repository
@Transactional
public class ForumPostDao implements ForumPostRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countPosts() {
		Long numberOfPosts = (Long) entityManager.createQuery("SELECT COUNT(*) FROM ForumPost p").getSingleResult();
		return numberOfPosts;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LinkedList<ForumPost> findAllPostsByCategory(String category) {
		Query query = entityManager.createNativeQuery(
				  "SELECT fp.id, fp.content, fp.title, fp.date FROM forumpost AS fp "
				+ "INNER JOIN forumcategory AS fc "
				+ "ON fc.id = fp.idCategory "
				+ "WHERE fc.title=?1");
		query.setParameter(1, category);
		
		List<Object[]> postsInCategory = query.getResultList();
		LinkedList<ForumPost> postsInCategoryMapped = mapToPostList(postsInCategory);
		
		return postsInCategoryMapped;
	}
	
	private LinkedList<ForumPost> mapToPostList(List<Object[]> posts){
		LinkedList<ForumPost> mappedPosts = new LinkedList<ForumPost>();	
		
		for(Object[] post : posts){	
			mappedPosts.add(
					new ForumPost(
							((BigInteger)post[0]).longValue(),
							(String) post[1],
							(String) post[2],
							(Timestamp) post[3]
					));
		}
		
		return mappedPosts;
	}

	@Override
	public ForumPost findPostById(Long id) {
		try{
			ForumPost post = entityManager.createQuery("SELECT * FROM ForumPost p WHERE id=:postId", ForumPost.class)
					.setParameter("postId", id)
					.getSingleResult();
			return post;
		}catch(NoResultException nre){
			return null;
		}
		
	}
}
