package itforum.repositories;

import java.util.List;

import itforum.entities.ForumCategory;

public interface ForumCategoryRepository{

	void saveForumCategory(ForumCategory forumCategory);
	List<ForumCategory> findAllCategories();
	ForumCategory findCategoryByTitle(String title);
	boolean checkIfCategoryExists(String title);
}
