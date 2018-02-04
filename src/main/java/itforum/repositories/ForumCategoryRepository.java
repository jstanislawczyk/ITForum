package itforum.repositories;

import java.util.List;

import itforum.entities.ForumCategory;

public interface ForumCategoryRepository{

	void saveForumCategory(ForumCategory forumCategory);
	List<ForumCategory> findAllCategories();
	ForumCategory findCategoryByTitle(String title);
	boolean checkIfCategoryExistsByTitle(String title);
	boolean checkIfCategoryExistsById(Long title);
	String findCategoryTitleById(Long id);
}
