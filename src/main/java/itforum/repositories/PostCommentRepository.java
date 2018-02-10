package itforum.repositories;

import java.util.List;

import itforum.entities.PostComment;

public interface PostCommentRepository{
	void saveComment(PostComment comment);
	List<PostComment> getAllCommentsWithUserIdByPostId(Long postId);
}
