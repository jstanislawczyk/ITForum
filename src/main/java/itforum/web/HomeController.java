package itforum.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import itforum.entities.ForumCategory;
import itforum.repositories.ForumCategoryRepository;
import itforum.repositories.ForumPostRepository;
import itforum.repositories.UserRepository;

@Controller
@RequestMapping({ "/", "homepage" })
public class HomeController {

	private UserRepository userRepository;
	private ForumPostRepository forumPostRepository;
	private ForumCategoryRepository forumCategoryRepository;
	
	@Autowired
	public HomeController(UserRepository userRepository, ForumPostRepository forumPostRepository, ForumCategoryRepository forumCategoryRepository) {
		this.userRepository = userRepository;
		this.forumPostRepository = forumPostRepository;
		this.forumCategoryRepository = forumCategoryRepository;
	}
	
	@RequestMapping(method = GET)
	public String home(Model model) {
		model.addAttribute("totalPosts", Objects.toString(forumPostRepository.countPosts()));
		model.addAttribute("totalMembers", Objects.toString(userRepository.countUsers()));
		
		List<ForumCategory> categories = forumCategoryRepository.findAllCategories();
		model.addAttribute("categories",categories);
		return "home";
	}
}