package itforum.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import itforum.entities.ForumCategory;
import itforum.entities.ForumPost;
import itforum.entities.User;
import itforum.repositories.ForumCategoryRepository;
import itforum.repositories.ForumPostRepository;
import itforum.repositories.UserRepository;

@Controller
@RequestMapping("/post/createNewPost")
public class CreateNewPostController {
	
	private ForumPostRepository forumPostRepository;
	private UserRepository userRepository;
	private ForumCategoryRepository forumCategoryRepository;
	
	private final int pointsForNewPost = 50;
	
	@Autowired
	public CreateNewPostController(ForumPostRepository forumPostRepository, UserRepository userRepository, ForumCategoryRepository forumCategoryRepository) {
		this.forumPostRepository = forumPostRepository;
		this.userRepository = userRepository;
		this.forumCategoryRepository = forumCategoryRepository;
	}
	
	@RequestMapping(method=GET)
	public String createNewPost(Model model, ForumCategory categoryId){
		List<ForumCategory> categories = forumCategoryRepository.findAllCategories();
		model.addAttribute("forumCategory", new ForumCategory());
		model.addAttribute("categories", categories);
		model.addAttribute("forumPost", new ForumPost());
		return "createNewPostPage";
	}
	
	@RequestMapping(method=POST)
	public String processCreateNewPost(
				@Valid ForumPost forumPost,
				ForumCategory forumCategory,
				BindingResult bindingResult,
				RedirectAttributes attributes){
		
		if(bindingResult.hasErrors()){
			return "createNewPostPage";
		}else{		
			
			User user = currentLoggedUser();
			
			forumPost = setForumPostDefaultValues(forumPost, forumCategory, user);
			forumPostRepository.savePost(forumPost);
			attributes.addAttribute("categoryTitle", getCategoryTitleById(forumCategory.getId()));
			user = addPointsToUserAccount(user);
			return "redirect:/category/{categoryTitle}";
		}	
	}
	
	private ForumPost setForumPostDefaultValues(ForumPost forumPost, ForumCategory forumCategory, User user){
		forumPost.setId(null);
		forumPost.setUser(user);
		forumPost.setCategory(forumCategory);
		forumPost.setDate(new Timestamp(System.currentTimeMillis()));
		
		return forumPost;
	}
	
	private User currentLoggedUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userRepository.getUserByNick(auth.getName());
	}
	
	private String getCategoryTitleById(Long id){
		String title = forumCategoryRepository.findCategoryTitleById(id);
		return title;
	}
	
	private User addPointsToUserAccount(User user){
		user.setPoints(user.getPoints()+pointsForNewPost);
		user = userRepository.update(user);
		return user;
	}
}
