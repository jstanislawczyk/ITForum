package itforum.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.LinkedList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import itforum.entities.ForumPost;
import itforum.exceptions.CategoryNotFoundException;
import itforum.repositories.ForumPostRepository;

@Controller
public class ForumCategoryController {
	
	private ForumPostRepository forumPostRepository;
	
	public ForumCategoryController(ForumPostRepository forumPostRepository) {
		this.forumPostRepository = forumPostRepository;
	}

	@RequestMapping(value="/category/{category}", method=GET)
	public String showAllPostsInCategory(@PathVariable String category, Model model){
		
		LinkedList<ForumPost> postsByCategory = forumPostRepository.findAllPostsByCategory(category);
		
		if(postsByCategory.isEmpty()){
			throw new CategoryNotFoundException();
		}
		
		model.addAttribute("categoryTitle", category);
		model.addAttribute("posts", postsByCategory);
		return "categoryPage";
	}
}
