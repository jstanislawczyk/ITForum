package itforum.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import itforum.entities.ForumCategory;
import itforum.repositories.ForumCategoryRepository;


@Controller
@RequestMapping("/admin/addNewCategory")
public class AddNewCategoryController {
	
	private ForumCategoryRepository forumCategoryRepository;
	
	@Autowired
	public AddNewCategoryController(ForumCategoryRepository forumCategoryRepository) {
		this.forumCategoryRepository = forumCategoryRepository;
	}
	
	@RequestMapping(method = GET)
	public String addNewCategory(Model model){
		model.addAttribute("forumCategory", new ForumCategory());
		return "addNewCategoryPage";
	}
	
	@RequestMapping(method = POST)
	public String processAddNewCategory(
			@Valid ForumCategory forumCategory,
			BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return "addNewCategoryPage";
		}else{
			forumCategoryRepository.saveForumCategory(forumCategory);
			return "adminPage";
		}
	}
	
}
