package itforum.repositories;

import java.util.LinkedList;

import itforum.entities.ForumPost;

public interface ForumPostRepository{
	Long countPosts();
	LinkedList<ForumPost> findAllPostsByCategory(String category);
}
