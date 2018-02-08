package itforum.dao;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import itforum.entities.PostComment;
import itforum.repositories.PostCommentRepository;

@Repository
@Transactional
public class PostCommentDao implements PostCommentRepository{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void saveComment(PostComment comment) {
		entityManager.merge(comment);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PostComment> getAllCommentsByPostId(Long postId) {
		List<PostComment> comments = null;
		
		Query query = entityManager.createNativeQuery(
				"SELECT * FROM postcomment as pc " +
				"WHERE pc.idPost=:postId " +
				"ORDER BY pc.date DESC ");
		query.setParameter("postId", postId);
		
		List<Object[]> commentsByPostId = query.getResultList();
		comments = mapToCommentsList(commentsByPostId);
		
		return comments;
	}
	
	private List<PostComment> mapToCommentsList(List<Object[]> commentsByPostId){
		List<PostComment> mappedComments = new LinkedList<PostComment>();
		
		for(Object[] comment : commentsByPostId){	
			mappedComments.add(
					new PostComment(
							((BigInteger)comment[0]).longValue(),
							(String) comment[1],
							(Timestamp) comment[2]));
		}
		
		return mappedComments;
	}
}
