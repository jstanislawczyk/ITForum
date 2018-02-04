package itforum.repositories;

import java.util.LinkedList;

import itforum.entities.ForumPost;

public interface ForumPostRepository{
	Long countPosts();
	LinkedList<ForumPost> findAllPostsByCategorySortByNewest(String category);
	LinkedList<ForumPost> findAllPostsById(Long id);
	ForumPost findPostById(Long id);
	void savePost(ForumPost forumPost);
}
