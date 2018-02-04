package itforum.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.LinkedList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import itforum.entities.ForumPost;
import itforum.exceptions.PageNotFoundException;
import itforum.repositories.ForumCategoryRepository;
import itforum.repositories.ForumPostRepository;

@Controller
public class ForumCategoryController {
	
	private ForumPostRepository forumPostRepository;
	private ForumCategoryRepository forumCategoryRepository;
	
	public ForumCategoryController(ForumPostRepository forumPostRepository, ForumCategoryRepository forumCategoryRepository) {
		this.forumPostRepository = forumPostRepository;
		this.forumCategoryRepository = forumCategoryRepository;
	}

	@RequestMapping(value="/category/{categoryTitle}", method=GET)
	public String showAllPostsInCategoryByCategoryTitle(@PathVariable String categoryTitle, Model model){
		
		LinkedList<ForumPost> postsByCategory = forumPostRepository.findAllPostsByCategorySortByNewest(categoryTitle);
		
		if(forumCategoryRepository.checkIfCategoryExistsByTitle(categoryTitle)){
			model.addAttribute("categoryTitle", categoryTitle);
			model.addAttribute("posts", postsByCategory);
		
			return "categoryPage";
		}else{
			throw new PageNotFoundException();
		}
	}
}
